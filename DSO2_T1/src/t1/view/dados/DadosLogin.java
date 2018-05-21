package t1.view.dados;

public class DadosLogin implements Dados {

	private String login;

	private String senha;

	public DadosLogin(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public DadosLogin() {
	}

	public String getLogin() {
		return this.login;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
