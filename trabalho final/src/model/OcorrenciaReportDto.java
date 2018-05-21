package model;

public class OcorrenciaReportDto {

	private String data;

	private String horario;

	private String data_acidente;

	private String horario_acidente;

	private String descricao;

	private Integer count;

	public String getData() {
		return this.data;
	}

	public String getHorario() {
		return this.horario;
	}

	public String getData_acidente() {
		return this.data_acidente;
	}

	public String getHorario_acidente() {
		return this.horario_acidente;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public void setData_acidente(String data_acidente) {
		this.data_acidente = data_acidente;
	}

	public void setHorario_acidente(String horario_acidente) {
		this.horario_acidente = horario_acidente;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
