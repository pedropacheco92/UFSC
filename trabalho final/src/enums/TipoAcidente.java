package enums;

public enum TipoAcidente {

	ATROPELAMENTO(1L, "Atropelamento"), COLISAO_FRONTAL(2L, "Colisão Frontal"), ENGAVETAMENTO(3L, "Engavetamento");

	TipoAcidente(Long id, String descricao) {
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
