package t1.controller;

import java.io.IOException;

import t1.bd.CSVReader;
import t1.exceptions.BookNotFoundException;
import t1.exceptions.UserNotHaveBookException;
import t1.listeners.PegarLivroListener;
import t1.model.DevolverLivroModelImpl;
import t1.view.DevolverLivroViewImpl;
import t1.view.dados.Livro;

public class DevolverLivroControllerImpl extends AbstractController<DevolverLivroModelImpl, DevolverLivroViewImpl> {

	private PegarLivroListener pegarLivroListener;

	public void buscarLivroPorId(String text) {
		try {
			Livro livro = CSVReader.loadDadosLivrosById(text);
			this.model.verificaUsuarioPossuiLivro(livro);
			this.view.setDados(livro);
		} catch (IOException | BookNotFoundException | UserNotHaveBookException e) {
			this.view.showMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	public void devolverLivro() {
		this.pegarLivroListener.devolverLivro((Livro) this.view.getDados());

	}

	public void setPegarLivroListener(PegarLivroListener pegarLivroListener) {
		this.pegarLivroListener = pegarLivroListener;
	}
}
