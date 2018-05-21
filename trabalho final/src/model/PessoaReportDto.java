package model;

public class PessoaReportDto {

	private String cpf;

	private String nome;

	private Long situacaoId;

	private Integer localAtendimentoId;

	public String getCpf() {
		return this.cpf;
	}

	public String getNome() {
		return this.nome;
	}

	public Long getSituacaoId() {
		return this.situacaoId;
	}

	public Integer getLocalAtendimentoId() {
		return this.localAtendimentoId;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSituacaoId(Long situacaoId) {
		this.situacaoId = situacaoId;
	}

	public void setLocalAtendimentoId(Integer localAtendimentoId) {
		this.localAtendimentoId = localAtendimentoId;
	}

}
