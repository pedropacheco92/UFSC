package main;

import presenter.impl.LoginPresenterImpl;
import view.impl.LoginViewImpl;

public class MainApp {

	public static void main(String[] args) {
		LoginViewImpl view = new LoginViewImpl();
		LoginPresenterImpl presenter = new LoginPresenterImpl();
		view.setPresenter(presenter);
		presenter.setView(view);
		presenter.init();
	}

}
