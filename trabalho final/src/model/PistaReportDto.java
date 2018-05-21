package model;

public class PistaReportDto {

	private String rodovia;

	private Long tipoAcidenteId;

	public PistaReportDto(String rodovia, Long tipoAcidente) {
		this.rodovia = rodovia;
		this.tipoAcidenteId = tipoAcidente;
	}

	public String getRodovia() {
		return this.rodovia;
	}

	public void setRodovia(String rodovia) {
		this.rodovia = rodovia;
	}

	public Long getTipoAcidenteId() {
		return this.tipoAcidenteId;
	}

	public void setTipoAcidenteId(Long tipoAcidenteId) {
		this.tipoAcidenteId = tipoAcidenteId;
	}

}
