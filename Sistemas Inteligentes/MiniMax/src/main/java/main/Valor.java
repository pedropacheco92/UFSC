package main;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Valor {
	// @formatter:off
	VAZIO("-"), X("X"), O("O");
	// @formatter:on

	private String value;

	public static Valor contrario(Valor v) {
		return O.equals(v) ? X : O;
	}

}
