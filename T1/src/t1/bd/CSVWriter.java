package t1.bd;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import t1.view.dados.ListaLivrosEmprestados;
import t1.view.dados.Livro;

public class CSVWriter {

	public static void writeLivrosEmprestados(ListaLivrosEmprestados livros) throws IOException {
		PrintWriter writer = new PrintWriter(new File(CSVUtils.EMPRESTIMOS));
		CSVUtils.writeLine(writer, Arrays.asList("#ID", "Login", "Data_emprestimo", "Data_devolucao"));
		for (String user : livros.getLivrosEmprestados().keySet()) {
			for (Livro livro : livros.getLivrosEmprestados().get(user)) {
				CSVUtils.writeLine(writer, formatLineLivro(user, livro));
			}
		}
		writer.flush();
		writer.close();
	}

	private static List<String> formatLineLivro(String user, Livro livro) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		List<String> returnList = new ArrayList<>();
		returnList.add(String.valueOf(livro.getID()));
		returnList.add(user);
		returnList.add((livro.getDataRetirada() != null) ? formatter.format(livro.getDataRetirada()) : "null");
		returnList.add((livro.getDataDevolucao() != null) ? formatter.format(livro.getDataDevolucao()) : "null");

		return returnList;
	}

}
