package t1.bd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import t1.exceptions.BookNotFoundException;
import t1.exceptions.UserNotFoundException;
import t1.view.dados.DadosLogin;
import t1.view.dados.ListaLivrosEmprestados;
import t1.view.dados.Livro;

public class CSVReader {

	public static Livro loadDadosLivrosById(String id)
			throws FileNotFoundException, IOException, BookNotFoundException {
		BufferedReader br = CSVUtils.load(CSVUtils.LIVROS);

		String idNoZeros = id.replaceFirst("^0+(?!$)", "");

		String line = null;
		try {
			line = br.lines()
					.filter(l -> (!l.startsWith(CSVUtils.COMMENT)
							&& (l.replaceFirst("^0+(?!$)", "").startsWith(idNoZeros + CSVUtils.CVS_SPLIT)))
							|| l.startsWith(id + CSVUtils.CVS_SPLIT))
					.findAny().get();
		} catch (NoSuchElementException e) {
			throw new BookNotFoundException();
		}

		String[] lineArray = line.split(CSVUtils.CVS_SPLIT);

		Livro livro = new Livro();
		livro.setID(Long.valueOf(lineArray[0]));
		livro.setTituloLivro(lineArray[1]);
		livro.setNomeAutor(lineArray[2]);

		return livro;
	}

	public static ListaLivrosEmprestados loadAllLivrosByUserId(String login)
			throws UserNotFoundException, FileNotFoundException, IOException {
		BufferedReader br = CSVUtils.load(CSVUtils.EMPRESTIMOS);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		Map<String, List<Livro>> map = new HashMap<>();

		br.lines().filter(l -> !l.startsWith(CSVUtils.COMMENT)).forEach(line -> {
			String[] lineArray = line.split(CSVUtils.CVS_SPLIT);
			Livro livro = null;
			try {
				livro = loadDadosLivrosById(lineArray[0]);
				livro.setDataDevolucao(!("null".equals(lineArray[3])) ? formatter.parse(lineArray[3]) : null);
				livro.setDataRetirada(!("null".equals(lineArray[2])) ? formatter.parse(lineArray[2]) : null);

				if (map.get(lineArray[1]) == null) {
					map.put(lineArray[1], new ArrayList<Livro>());
				}
				map.get(lineArray[1]).add(livro);
			} catch (IOException | BookNotFoundException | ParseException e) {
				e.printStackTrace();
			}

		});

		return new ListaLivrosEmprestados(map);
	}

	public static DadosLogin loadDadosLogin(String login)
			throws UserNotFoundException, FileNotFoundException, IOException {
		BufferedReader br = CSVUtils.load(CSVUtils.LOGINS);

		String lineDados = null;
		try {
			lineDados = br.lines()
					.filter(l -> !l.startsWith(CSVUtils.COMMENT) && l.startsWith(login + CSVUtils.CVS_SPLIT)).findAny()
					.get();
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException();
		}

		String[] lineDadosArray = lineDados.split(CSVUtils.CVS_SPLIT);

		return new DadosLogin(lineDadosArray[0], lineDadosArray[1]);
	}

}
