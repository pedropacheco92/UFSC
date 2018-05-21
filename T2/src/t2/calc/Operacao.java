package t2.calc;

import java.util.function.DoubleBinaryOperator;

public enum Operacao implements DoubleBinaryOperator {
	//@formatter:off
	SOMAR("+", (l, r) -> l + r),
    SUBTRAIR("-", (l, r) -> l - r),
    MULTIPLICAR("*", (l, r) -> l * r),
    DIVIDIR("/", (l, r) -> l / r),
	IGUAL("=", null);
	//@formatter:on

	private final String simbolo;
	private final DoubleBinaryOperator operador;

	private Operacao(String simbolo, DoubleBinaryOperator operador) {
		this.simbolo = simbolo;
		this.operador = operador;
	}

	@Override
	public double applyAsDouble(double left, double right) {
		return this.operador.applyAsDouble(left, right);
	}

	public String getSimbolo() {
		return this.simbolo;
	}
}
