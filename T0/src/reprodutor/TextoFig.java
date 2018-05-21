package reprodutor;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;

import modelos.Ponto;
import modelos.Texto;
import poo.edgraf.reprodutor.Quadro;

public class TextoFig extends Texto implements Fig {

	Quadro quadro;
	Color cor;

	public TextoFig(Ponto p1, String textoInformado, Quadro quadro, Color cor) {
		super(p1, textoInformado);
		this.quadro = quadro;
		this.cor = cor;
	}

	public void reproduzir(Graphics g) {
		g.setColor(this.cor);
		g.drawString(this.getTextoInformado(), this.getP1().getX(), this.getP1().getY());
	}

}
