package t1.controller;

import java.io.IOException;

import t1.bd.CSVReader;
import t1.exceptions.UserNotFoundException;
import t1.model.LoginModelImpl;
import t1.model.MainModelImpl;
import t1.view.LoginViewImpl;
import t1.view.MainViewImpl;
import t1.view.dados.DadosLogin;

public class LoginControllerImpl extends AbstractController<LoginModelImpl, LoginViewImpl> {

	private DadosLogin dadosLogin;

	public void showMainView() {
		MainModelImpl mainModel = new MainModelImpl();
		MainControllerImpl mainController = new MainControllerImpl();
		MainViewImpl mainView = new MainViewImpl(mainController);
		mainController.setModel(mainModel);
		mainController.setView(mainView);
		mainModel.setDadosLogin(this.dadosLogin);
		mainController.init();
	}

	public boolean onLoginClicked(String login, String senha) {
		boolean senhaOk;
		try {
			this.dadosLogin = CSVReader.loadDadosLogin(login);
			senhaOk = this.model.verificaLogin(senha, this.dadosLogin);
			if (!senhaOk) {
				this.view.showMessage("Senha incorreta!");
			}
			return senhaOk;
		} catch (UserNotFoundException | IOException e) {
			this.view.showMessage(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

}
