package t1.controller;

import java.io.IOException;

import t1.bd.CSVReader;
import t1.exceptions.BookNotFoundException;
import t1.listeners.PegarLivroListener;
import t1.model.PegarLivroModelImpl;
import t1.view.PegarLivroViewImpl;
import t1.view.dados.Livro;

public class PegarLivroControllerImpl extends AbstractController<PegarLivroModelImpl, PegarLivroViewImpl> {

	private PegarLivroListener pegarLivroListener;

	public void buscarLivroPorId(String text) {
		try {
			Livro livro = CSVReader.loadDadosLivrosById(text);
			this.view.setDados(livro);
		} catch (IOException | BookNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void pegarLivro() {
		this.pegarLivroListener.pegarLivro((Livro) this.view.getDados());
	}

	public void setPegarLivroListener(PegarLivroListener pegarLivroListener) {
		this.pegarLivroListener = pegarLivroListener;
	}
}
