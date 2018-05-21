package modelos;

import java.io.PrintWriter;

public class Oval extends Ponto implements Persistivel {

	private Ponto a;
	private int raioAltura;
	private int raioLargura;

	protected Oval(Ponto p, int raio1, int raio2) {
		this.a = p;
		this.raioAltura = raio1;
		this.raioLargura = raio2;
	}

	public Ponto getA() {
		return this.a;
	}

	public int getRaioAltura() {
		return this.raioAltura;
	}

	public int getRaioLargura() {
		return this.raioLargura;
	}

	@Override
	public void persistir(PrintWriter out) {
		out.println("OVAL");
		out.println("" + this.a.getX());
		out.println("" + this.a.getY());
		out.println("" + this.raioAltura);
		out.println("" + this.raioLargura);
	}
}
