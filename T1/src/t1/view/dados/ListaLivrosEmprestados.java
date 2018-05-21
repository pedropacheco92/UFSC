package t1.view.dados;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListaLivrosEmprestados implements Dados {

	private Map<String, List<Livro>> livrosEmprestados;

	public ListaLivrosEmprestados(Map<String, List<Livro>> livrosEmprestados) {
		this.livrosEmprestados = livrosEmprestados;
	}

	public Map<String, List<Livro>> getLivrosEmprestados() {
		return this.livrosEmprestados;
	}

	public void setLivrosEmprestados(Map<String, List<Livro>> livrosEmprestados) {
		this.livrosEmprestados = livrosEmprestados;
	}

	public void addLivros(String user, List<Livro> livros) {
		if (this.livrosEmprestados.get(user) == null) {
			this.livrosEmprestados.put(user, new ArrayList<Livro>());
		}
		this.livrosEmprestados.get(user).addAll(livros);
	}

}
