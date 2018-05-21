package reprodutor;

import java.awt.Color;
import java.awt.Graphics;

import modelos.Ponto;
import modelos.Reta;
import poo.edgraf.reprodutor.Quadro;

public class RetaFig extends Reta implements Fig {

	Quadro quadro;
	Color cor;

	public RetaFig(Ponto p1, Ponto p2, Quadro quadro, Color cor) {
		super(p1, p2);
		this.quadro = quadro;
		this.cor = cor;

	}

	public void reproduzir(Graphics g) {
		g.setColor(this.cor);
		g.drawLine(this.getP1().getX(), this.getP1().getY(), this.getP2().getX(), this.getP2().getY());
	}

}
