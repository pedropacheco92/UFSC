package enums;

public enum TipoEnvolvido {

	MOTORISTA(1L, "Motorista"), PASSAGEIRO(2L, "Passageiro"), PEDESTRE(3L, "Pedestre");

	TipoEnvolvido(Long id, String descricao) {
		this.setId(id);
		this.setDescricao(descricao);
	}

	private Long id;

	private String descricao;

	@Override
	public String toString() {
		return this.descricao;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
