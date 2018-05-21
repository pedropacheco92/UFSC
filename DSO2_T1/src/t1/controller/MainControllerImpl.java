package t1.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import t1.bd.CSVReader;
import t1.bd.CSVWriter;
import t1.exceptions.UserNotFoundException;
import t1.listeners.PegarLivroListener;
import t1.model.DevolverLivroModelImpl;
import t1.model.MainModelImpl;
import t1.model.PegarLivroModelImpl;
import t1.view.DevolverLivroViewImpl;
import t1.view.MainViewImpl;
import t1.view.PegarLivroViewImpl;
import t1.view.dados.ListaLivrosEmprestados;
import t1.view.dados.Livro;

public class MainControllerImpl extends AbstractController<MainModelImpl, MainViewImpl> implements PegarLivroListener {

	@Override
	public void init() {
		this.view.setDados(this.model.getDadosLogin());
		this.view.createScreen();
		try {
			this.model.setLivros(CSVReader.loadAllLivrosByUserId(this.model.getDadosLogin().getLogin()));
		} catch (UserNotFoundException | IOException e) {
			this.view.showMessage(e.getMessage());
			e.printStackTrace();
		}

		this.view.setDados(this.model.getLivros());
	}

	public void showPegarLivro() {
		PegarLivroModelImpl pegarLivroModel = new PegarLivroModelImpl();
		PegarLivroControllerImpl pegarLivroController = new PegarLivroControllerImpl();
		PegarLivroViewImpl pegarLivroView = new PegarLivroViewImpl(pegarLivroController);
		pegarLivroController.setModel(pegarLivroModel);
		pegarLivroController.setView(pegarLivroView);
		pegarLivroController.setPegarLivroListener(this);
		pegarLivroController.init();
	}

	public void showDevolverLivro() {
		DevolverLivroModelImpl devolverLivroModel = new DevolverLivroModelImpl();
		DevolverLivroControllerImpl devolverLivroController = new DevolverLivroControllerImpl();
		DevolverLivroViewImpl devolverLivroView = new DevolverLivroViewImpl(devolverLivroController);
		devolverLivroController.setModel(devolverLivroModel);
		devolverLivroController.setView(devolverLivroView);
		devolverLivroController.setPegarLivroListener(this);
		devolverLivroModel.setDadosLogin(this.model.getDadosLogin());
		devolverLivroModel.setLivros(this.model.getLivros());
		devolverLivroController.init();
	}

	@Override
	public void pegarLivro(Livro livro) {
		livro.setDataRetirada(new Date());
		this.model.getLivros().addLivros(this.model.getDadosLogin().getLogin(), Arrays.asList(livro));
		try {
			CSVWriter.writeLivrosEmprestados(this.model.getLivros());
			this.view.setDados(this.model.getLivros());
		} catch (IOException e) {
			this.view.showMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void devolverLivro(Livro livro) {
		ListaLivrosEmprestados livros = this.model.getLivros();
		for (String user : livros.getLivrosEmprestados().keySet()) {
			if (user.equals(this.model.getDadosLogin().getLogin())) {
				for (Livro l : livros.getLivrosEmprestados().get(user)) {
					if (l.getID().equals(livro.getID()) && l.getDataRetirada().equals(livro.getDataRetirada())) {
						l.setDataDevolucao(new Date());
					}
				}
			}
		}
		this.model.setLivros(livros);
		try {
			CSVWriter.writeLivrosEmprestados(this.model.getLivros());
			this.view.setDados(this.model.getLivros());
		} catch (IOException e) {
			this.view.showMessage(e.getMessage());
			e.printStackTrace();
		}
	}
}
