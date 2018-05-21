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

#include <assert.h>
#include <buffer.h>
#include <pthread.h>
#include <util.h>
#include <semaphore.h>
#include <stdio.h>

/*
 * Buffer.
 */
struct buffer
{
	unsigned *data; /* Data.                        */
	unsigned size;  /* Max size (in elements).      */
	unsigned first; /* First element in the buffer. */
	unsigned last;  /* Last element in the buffer.  */
	sem_t sem_full;           /* semaforo para cuidar os espaços cheios */
	sem_t sem_empty;          /* semaforo para cuidar os espaços vazios */
	sem_t mutex;
};

/*
 * Creates a buffer.
 */
struct buffer *buffer_create(unsigned size)
{
	printf("Criou o buffer de tamanho: %d\n", size);
	struct buffer *buf;
	
	/* Sanity check. */
	assert(size > 0);
	
	buf = smalloc(size*sizeof(struct buffer));
	
	/* Initialize buffer. */
	sem_init(&(buf->mutex), 0, 1);
	sem_init(&(buf->sem_full), 0, 0);
	sem_init(&(buf->sem_empty), 0, size);
	buf->size = size;
	buf->data = smalloc(size*sizeof(unsigned));
	buf->first = 0;
	buf->last = 0;

	return (buf);
}

/*
 * Destroys a buffer.
 */
void buffer_destroy(struct buffer *buf)
{
	/* Sanity check. */
	assert(buf != NULL);
	
	/* House keeping. */
	free(buf->data);
	free(buf);
}

/*
 * Puts an item in a buffer.
 */
void buffer_put(struct buffer *buf, unsigned item)
{	
	/* Sanity check. */
	assert(buf != NULL);
	printf("buffer put: %d\n", item);
	sem_wait(&(buf->sem_empty));
	sem_wait(&(buf->mutex));

	if (buf->last == buf->size)
	{
		buf->last = ((buf->last) % buf->size);
	}

	buf->data[buf->last++] = item;
  	sem_post(&(buf->mutex));
	sem_post(&(buf->sem_full));
}

/*
 * Gets an item from a buffer.
 */
unsigned buffer_get(struct buffer *buf)
{
	unsigned item;	
	
	/* Sanity check. */
	assert(buf != NULL);
	sem_wait(&(buf->sem_full));
	sem_wait(&(buf->mutex));
	if (buf->first == buf->size)
	{
		buf->first = ((buf->first) % buf->size);
	} 
	item = buf->data[buf->first++];
   	sem_post(&(buf->mutex));
	sem_post(&(buf->sem_empty));
	printf("buffer get: %u\n", (unsigned int)item);

	return (item);	
}
