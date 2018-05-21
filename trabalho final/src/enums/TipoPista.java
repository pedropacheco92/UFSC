package enums;

public enum TipoPista {

	PISTA_AREIA(1L, "Pista de areia"), PISTA_ASFALTO(2L, "Pista de asfalto"), PISTA_BRITA(3L, "Pista de brita");

	TipoPista(Long id, String descricao) {
		this.setId(id);
		this.setDescricao(descricao);
	}

	private Long id;

	private String descricao;

	@Override
	public String toString() {
		return this.getDescricao();
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
