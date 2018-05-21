package t1.model;

import java.util.Calendar;
import java.util.Date;

import t1.view.dados.DadosLogin;
import t1.view.dados.ListaLivrosEmprestados;

public class MainModelImpl extends AbstractModel {

	private DadosLogin dadosLogin;

	private ListaLivrosEmprestados livros;

	private static final int DIAS_DE_VENCIMENTO = 7;

	public ListaLivrosEmprestados getLivros() {
		return this.livros;
	}

	public void setLivros(ListaLivrosEmprestados livros) {
		livros.getLivrosEmprestados().forEach((user, livro) -> {
			livro.forEach(l -> {
				if (l.getDataDevolucao() == null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(l.getDataRetirada());
					cal.add(Calendar.DATE, DIAS_DE_VENCIMENTO);
					l.setVencido(cal.getTime().before(new Date()));
				}
			});
		});

		this.livros = livros;
	}

	public DadosLogin getDadosLogin() {
		return this.dadosLogin;
	}

	public void setDadosLogin(DadosLogin dadosLogin) {
		this.dadosLogin = dadosLogin;
	}

}
