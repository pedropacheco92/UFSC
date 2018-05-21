package modelos;

import java.io.PrintWriter;


public class Circulo extends Ponto implements Persistivel {

	private int raio;
	private Ponto a;

	protected Circulo(Ponto p1, int raio) {
		this.a = p1;
		this.raio = raio;
	}

	public int getRaio() {
		return this.raio;
	}

	public Ponto getA() {
		return this.a;
	}

	@Override
	public void persistir(PrintWriter out) {
		out.println("CIRCULO");
		out.println("" + this.a.getX());
		out.println("" + this.a.getY());
		out.println("" + this.raio);
	}
}