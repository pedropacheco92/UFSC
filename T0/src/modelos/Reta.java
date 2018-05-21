package modelos;

import java.io.PrintWriter;

public class Reta extends Ponto implements Persistivel {

	protected Ponto p1, p2;

	protected Reta(Ponto a, Ponto b) {
		this.p1 = a;
		this.p2 = b;
	}

	public Ponto getP2() {
		return this.p2;
	}

	public Ponto getP1() {
		return this.p1;
	}

	@Override
	public void persistir(PrintWriter out) {
		out.println("RETA");
		out.println("" + this.p1.getX());
		out.println("" + this.p1.getY());
		out.println("" + this.p2.getX());
		out.println("" + this.p2.getY());
	}
}
