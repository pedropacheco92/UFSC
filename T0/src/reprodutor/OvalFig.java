package reprodutor;

import java.awt.Color;
import java.awt.Graphics;

import modelos.Oval;
import modelos.Ponto;
import poo.edgraf.reprodutor.Quadro;

public class OvalFig extends Oval implements Fig {

	Quadro quadro;
	Color cor;

	public OvalFig(Ponto p1, int raio1, int raio2, Quadro quadro2, Color cor) {
		super(p1, raio1, raio2);
		this.quadro = quadro2;
		this.cor = cor;
	}

	public void reproduzir(Graphics g) {
		g.setColor(this.cor);
		g.drawOval((this.getA().getX() - this.getRaioAltura()), (this.getA().getY() - this.getRaioLargura()),
				(2 * this.getRaioAltura()), (2 * this.getRaioLargura()));
	}

}