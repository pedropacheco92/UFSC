package model.forms;

import anotacoes.Coluna;
import enums.SituacaoPessoa;
import enums.TipoAcidente;
import enums.TipoEnvolvido;
import enums.TipoVeiculo;
import model.LocalAtendimentoDto;

public class AdicionarEnvolvidoForm implements Form {

	private String cpf;

	private String nome;

	private SituacaoPessoa situacao;

	private LocalAtendimentoDto localAtendimento;

	private String descricaoVeiculo;

	private TipoVeiculo tipoVeiculo;

	private TipoEnvolvido tipoEnvolvimento;

	private TipoAcidente tipoAcidente;

	@Coluna(nome = "CPF", posicao = 1)
	public String getCpf() {
		return this.cpf;
	}

	@Coluna(nome = "Nome", posicao = 2)
	public String getNome() {
		return this.nome;
	}

	@Coluna(nome = "Situação", posicao = 3)
	public SituacaoPessoa getSituacao() {
		return this.situacao;
	}

	@Coluna(nome = "Local Atendimento", posicao = 4)
	public LocalAtendimentoDto getLocalAtendimento() {
		return this.localAtendimento;
	}

	@Coluna(nome = "Veículo", posicao = 5)
	public String getDescricaoVeiculo() {
		return this.descricaoVeiculo;
	}

	public TipoVeiculo getTipoVeiculo() {
		return this.tipoVeiculo;
	}

	@Coluna(nome = "Envolvimento", posicao = 6)
	public TipoEnvolvido getTipoEnvolvimento() {
		return this.tipoEnvolvimento;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSituacao(SituacaoPessoa situacao) {
		this.situacao = situacao;
	}

	public void setLocalAtendimento(LocalAtendimentoDto localAtendimento) {
		this.localAtendimento = localAtendimento;
	}

	public void setDescricaoVeiculo(String descricaoVeiculo) {
		this.descricaoVeiculo = descricaoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public void setTipoEnvolvimento(TipoEnvolvido tipoEnvolvimento) {
		this.tipoEnvolvimento = tipoEnvolvimento;
	}

	@Coluna(nome = "Acidente", posicao = 6)
	public TipoAcidente getTipoAcidente() {
		return this.tipoAcidente;
	}

	public void setTipoAcidente(TipoAcidente tipoAcidente) {
		this.tipoAcidente = tipoAcidente;
	}

}
