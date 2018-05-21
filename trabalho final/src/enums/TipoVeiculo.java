package enums;

public enum TipoVeiculo {

	CAMINHAO(1L, "Caminhão"), CARRO(2L, "Carro"), MOTOCICLETA(3L, "Motocicleta");

	TipoVeiculo(Long id, String descricao) {
		this.setId(id);
		this.setDescricao(descricao);
	}

	@Override
	public String toString() {
		return this.descricao;
	}

	private Long id;

	private String descricao;

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
