package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import model.enums.Tipo;

@Getter
@Setter
@NoArgsConstructor
public class Variavel extends Constante {
	private int deslocamento;
	private Tipo tipoElementosVetor;
	int tamanho;

	public void setTipoElementos(Tipo t) {
		if (Tipo.VETOR.equals(this.tipo)) {
			this.tipoElementosVetor = t;
		}
	}
}