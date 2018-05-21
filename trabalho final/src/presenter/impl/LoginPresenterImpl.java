package presenter.impl;

import exceptions.UsuarioNaoEncontradoException;
import model.forms.LoginForm;
import presenter.AbstractPresenter;
import presenter.LoginPresenter;
import service.LoginService;
import view.impl.LoginViewImpl;
import view.impl.MainViewImpl;

public class LoginPresenterImpl extends AbstractPresenter<LoginViewImpl, LoginForm, LoginPresenter>
		implements LoginPresenter {

	private LoginService service;

	@Override
	public void init() {
		super.init();
		this.service = new LoginService();
	}

	@Override
	public void onLoginClicked() throws UsuarioNaoEncontradoException {
		LoginForm fillOut = this.view.fillOut();
		Boolean b = this.service.checkLogin(fillOut);
		this.onLogin(b, fillOut.getCpf());
	}

	private void onLogin(Boolean policial, String cpf) {
		MainViewImpl view = new MainViewImpl();
		MainPresenterImpl presenter = new MainPresenterImpl();
		view.setPresenter(presenter);
		view.setUsuarioPolicial(policial);
		presenter.setView(view);
		presenter.setCpf(cpf);
		presenter.init();
	}

}
