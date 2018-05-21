/*
 * Copyright(C) 2014-2016 Pedro H. Penna <pedrohenriquepenna@gmail.com>
 *
 * This file is part of compress.
 *
 * compress is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * compress is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with compress. If not, see <http://www.gnu.org/licenses/>.
 */

#include <buffer.h>
#include <dictionary.h>
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <util.h>
#include <semaphore.h>
#include <pthread.h>

/*
 * Parameters.
 */
#define RADIX 256 /* Radix of input data. */
#define WIDTH  12 /* Width of code word.  */

buffer_t inbuf;  /* Input buffer.  */
buffer_t outbuf; /* Output buffer. */
pthread_t threadA, threadB, mainThread;

sem_t semaphore;

/*============================================================================*
 *                           Bit Buffer Reader/Writer                         *
 *============================================================================*/

/*
 * Writes data to a file.
 */
static void *lzw_writebits(void * param)
{
	printf("write_bits\n");
	FILE *out = (FILE *)param;

	int bits;   /* Working bits. */
	unsigned n; /* Current bit.  */
	int buf;    /* Buffer.       */

	n = 0;
	buf = 0;

	/*
	 * Read data from input buffer
	 * and write to output file.
	 */
	while ((bits = buffer_get(outbuf)) != EOF)
	{
		buf  = buf << WIDTH;
		buf |= bits & ((1 << WIDTH) - 1);
		n += WIDTH;
		printf("escrevendo bit: %u\n", n);
		/* Flush bytes. */
		while (n >= 8)
		{
			fputc((buf >> (n - 8)) & 0xff, out);
			n -= 8;
		}
	}

	if (n > 0)
		fputc((buf << (8 - n)) & 0xff, out);

	return 0;
}

/*============================================================================*
 *                           Bit Buffer Reader/Writer                         *
 *============================================================================*/

/*
 * Reads data from a file.
 */
static void *lzw_readbits(void * param)
{
	FILE *in = (FILE *)param;

	int bits;   /* Working bits. */
	unsigned n; /* Current bit.  */
	int buf;    /* Buffer.       */

	n = 0;
	buf = 0;

	/*
	 * Read data from input file
	 * and write to output buffer.
	 */
	while ((bits = fgetc(in)) != EOF)
	{
		buf = buf << 8;
		buf |= bits & 0xff;
		n += 8;

		/* Flush bytes. */
		while (n >= WIDTH)
		{
			buffer_put(inbuf, (buf >> (n - WIDTH)) & ((1 << WIDTH) - 1));
			n -= WIDTH;
		}
	}

	buffer_put(inbuf, EOF);
	return 0;
}

/*============================================================================*
 *                                Readbyte                                    *
 *============================================================================*/

/*
 * Reads data from a file.
 */

static void *lzw_readbytes(void * param)
{

	FILE *infile = (FILE *)param;
	printf("read bytes\n");
	int ch;

	/* Read data from file to the buffer. */
	while ((ch = fgetc(infile)) != EOF)
		buffer_put(inbuf, ch & 0xff);

	buffer_put(inbuf, EOF);
	return 0;
}

/*============================================================================*
 *                                writebytes                                  *
 *============================================================================*/

/*
 * Writes data to a file.
 */
static void *lzw_writebytes(void * param)
{
	FILE *outfile = (FILE *)param;
	int ch;

	/* Read data from file to the buffer. */
	while ((ch = buffer_get(outbuf)) != EOF)
		fputc(ch, outfile);

	return 0;
}

/*============================================================================*
 *                                   LZW                                      *
 *============================================================================*/

/*
 * Initializes dictionary.
 */
static code_t lzw_init(dictionary_t dict, int radix)
{
	for (int i = 0; i < radix; i++)
		dictionary_add(dict, 0, i, i);

	return (radix);
}

/*
 * Compress data.
 */
static void *lzw_compress()
{
	printf("compress\n");
	unsigned ch;       /* Working character. */
	int i, ni;         /* Working entries.   */
	code_t code;       /* Current code.      */
	dictionary_t dict; /* Dictionary.        */

	dict = dictionary_create(1 << WIDTH);

	i = 0;
	code = lzw_init(dict, RADIX);

	/* Compress data. */
	ch = buffer_get(inbuf);
	printf("---- comprimindo char: %u\n", (unsigned int)ch);
	while (ch != EOF)
	{

		ni = dictionary_find(dict, i, (char)ch);

		/* Find longest prefix. */
		if (ni >= 0)
		{
			ch = buffer_get(inbuf);
			printf("comprimindo char: %u\n", (unsigned int)ch);
			i = ni;

			/* Next character. */
			if (ch != EOF)
				continue;
		}

		buffer_put(outbuf, dict->entries[i].code);

		if (code == ((1 << WIDTH) - 1))
		{
			i = 0;
			dictionary_reset(dict);
			code = lzw_init(dict, RADIX);
			buffer_put(outbuf, RADIX);
			continue;
		}

		dictionary_add(dict, i, ch, ++code);
		i = 0;
	}

	buffer_put(outbuf, EOF);
	dictionary_destroy(dict);
	return 0;
}

/*
 * Builds a string.
 */
static char *buildstr(char *base, char ch)
{
	char *s, *p;

	p = s = smalloc((strlen(base) + 2)*sizeof(char));

	while (*base != '\0')
		*p++ = *base++;

	*p++ = ch;
	*p = '\0';

	return (s);
}

/*
 * Decompress data.
 */
static void *lzw_decompress()
{
	char *s, *p;   /* Working string. */
	unsigned code; /* Working code.   */
	unsigned i;    /* Loop index.     */
	char **st;     /* String table.   */

	st = smalloc(((1 << WIDTH) + 2)*sizeof(char *));

	/* Initializes the symbol table. */
	for (i = 0; i < RADIX; i++)
		st[i] = buildstr("", i);

	st[i++] =  buildstr("", ' ');

	code = buffer_get(inbuf);

	/* Broken file. */
	if (code >= i)
		error("broken file");

	s = st[code];

	/* Decompress data. */
	while (1)
	{
		/* Output current string. */
		for (p = s; *p != '\0'; p++)
			buffer_put(outbuf, (unsigned)(*p & 0xff));

		code = buffer_get(inbuf);	

		/* End of input. */
		if (code == EOF)
			break;

		/* Reset symbol table. */
		if (code == RADIX)
		{
			while (i > 0)
				free(st[--i]);

			/* Initializes the symbol table. */
			for (i = 0; i < RADIX; i++)
				st[i] = buildstr("", i);

			st[i++] =  buildstr("", ' ');

			code = buffer_get(inbuf);

			/* Broken file. */
			if (code >= i)
				error("broken file");

			s = st[code];

			continue;
		}

		/* Broken file. */
		if (code > i)
			error("broken file");

		p = st[code];

		if (i == code)
			p = buildstr(s, s[0]);

		st[i++] = buildstr(s, p[0]);

		s = p;
		printf("descomprimindo %s: \n", s);
	}

	buffer_put(outbuf, EOF);

	/* House keeping. */
	while (i > 0)
		free(st[--i]);
	free(st);

	return 0;
}

/*
 * Compress/Decompress a file using the LZW algorithm.
 */
void lzw(FILE *input, FILE *output, int compress)
{
	inbuf = buffer_create(1024);
	outbuf = buffer_create(1024);	
	printf("inicia o lzw\n");
//	sem_init(&semaphore, 0, 2);	
	/* Compress mode. */
	if (compress)
	{		
		printf("inicia compress\n");
	//	sem_wait(&semaphore);
		//lzw_readbytes(input);	
		pthread_create(&mainThread, NULL, lzw_readbytes, input);	
		pthread_create(&threadA, NULL, lzw_compress, NULL);
		pthread_create(&threadB, NULL, lzw_writebits, output);		
		pthread_join(mainThread, NULL);
		pthread_join(threadA, NULL);
		pthread_join(threadB, NULL);
//		sem_post(&semaphore);
		
	}

	/* Decompress mode. */
	else
	{
//		sem_wait(&semaphore);
	//	lzw_readbits(input, inbuf);
		pthread_create(&mainThread, NULL, lzw_readbits, input);		
		pthread_create(&threadA, NULL, lzw_decompress, NULL);
		pthread_create(&threadB, NULL, lzw_writebytes, output);
		pthread_join(mainThread, NULL);
		pthread_join(threadA, NULL);
		pthread_join(threadB, NULL);
//		sem_post(&semaphore);	
	}
	
	
	printf("destroi buffer!!\n");
	buffer_destroy(outbuf);
	buffer_destroy(inbuf);	
	printf("mata threads\n");
	pthread_exit(NULL);
}