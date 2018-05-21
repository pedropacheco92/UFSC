package modelos;

import static java.lang.Math.abs;

import java.io.PrintWriter;

public class Retangulo implements Persistivel {

	private Ponto p1, p2;

	protected Retangulo(Ponto a, Ponto b) {
		this.p1 = a;
		this.p2 = b;
	}

	protected int x0() {
		if (this.p1.getX() < this.p2.getX()) {
			return this.p1.getX();
		}

		return this.p2.getX();
	}

	protected int y0() {
		if (this.p1.getY() < this.p2.getY()) {
			return this.p1.getY();
		}

		return this.p2.getY();
	}

	protected int largura() {
		return abs(this.p1.getX() - this.p2.getX());
	}

	protected int altura() {
		return abs(this.p1.getY() - this.p2.getY());
	}

	@Override
	public void persistir(PrintWriter out) {
		out.println("RETANGULO");
		out.println("" + this.p1.getX());
		out.println("" + this.p1.getY());
		out.println("" + this.p2.getX());
		out.println("" + this.p2.getY());
	}
}
