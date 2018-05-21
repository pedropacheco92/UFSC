package enums;

public enum CondicaoPista {

	SECA(1L, "Seca"), MOLHADA(2L, "Molhada");

	CondicaoPista(Long id, String descricao) {
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
