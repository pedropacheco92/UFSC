package enums;

public enum SituacaoPessoa {

	ILESA(1L, "Ilesa"), FERIDA(2L, "Ferida"), FERIDA_GRAVE(3L, "Gravemente ferida"), MORTA(4L, "Morta");

	SituacaoPessoa(Long id, String descricao) {
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

	public static String getDescricaoById(Long id) {
		for (SituacaoPessoa s : SituacaoPessoa.values()) {
			if (id.equals(s.getId())) {
				return s.getDescricao();
			}
		}
		return null;
	}

}
