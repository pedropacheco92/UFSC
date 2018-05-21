package t1.model;

import t1.view.dados.DadosLogin;

public class LoginModelImpl extends AbstractModel {

	public boolean verificaLogin(String senha, DadosLogin loadDadosLogin) {
		return senha.equals(loadDadosLogin.getSenha());
	}

}
