package model.forms;

import java.util.Date;
import java.util.List;

public class OcorrenciaForm implements Form {

	private LocalForm local;

	private List<AdicionarEnvolvidoForm> envolvidos;

	private String descricao;

	private Date data;

	private Date hora;

	private String cpf;

	public LocalForm getLocal() {
		return this.local;
	}

	public void setLocal(LocalForm local) {
		this.local = local;
	}

	public List<AdicionarEnvolvidoForm> getEnvolvidos() {
		return this.envolvidos;
	}

	public void setEnvolvidos(List<AdicionarEnvolvidoForm> envolvidos) {
		this.envolvidos = envolvidos;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
