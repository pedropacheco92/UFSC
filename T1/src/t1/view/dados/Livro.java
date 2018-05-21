package t1.view.dados;

import java.util.Date;

import t1.view.annotations.Coluna;

public class Livro implements Dados {

	private Long ID;

	private String tituloLivro;

	private String nomeAutor;

	private Date dataRetirada;

	private Date dataDevolucao;

	private Boolean vencido;

	@Coluna(nome = "ID", posicao = 0)
	public Long getID() {
		return this.ID;
	}

	@Coluna(nome = "T�tulo", posicao = 1)
	public String getTituloLivro() {
		return this.tituloLivro;
	}

	@Coluna(nome = "Autor", posicao = 2)
	public String getNomeAutor() {
		return this.nomeAutor;
	}

	@Coluna(nome = "Data Retirada", posicao = 3)
	public Date getDataRetirada() {
		return this.dataRetirada;
	}

	@Coluna(nome = "Data Devolu��o", posicao = 4)
	public Date getDataDevolucao() {
		return this.dataDevolucao;
	}

	@Coluna(nome = "Vencido", posicao = 5)
	public Boolean getVencido() {
		return this.vencido;
	}

	public void setID(Long iD) {
		this.ID = iD;
	}

	public void setTituloLivro(String tituloLivro) {
		this.tituloLivro = tituloLivro;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public void setVencido(Boolean vencido) {
		this.vencido = vencido;
	}
}
