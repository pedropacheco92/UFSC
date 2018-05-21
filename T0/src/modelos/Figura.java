package modelos;

public class Figura {
	
	String nomeFigura;
	int quantidadePontos;
	Ponto [] pontos;
	
	public String getNomeFigura() {
		return nomeFigura;
	}
	public void setNomeFigura(String nomeFigura) {
		this.nomeFigura = nomeFigura;
	}
	public int getQuantidadePontos() {
		return quantidadePontos;
	}
	public void setQuantidadePontos(int quantidadePontos) {
		this.quantidadePontos = quantidadePontos;
	}
	public Ponto[] getPontos() {
		return pontos;
	}
	public void setPontos(Ponto[] pontos) {
		this.pontos = pontos;
	}
	
	

}
