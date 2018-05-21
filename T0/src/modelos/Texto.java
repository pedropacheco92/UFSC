package modelos;

import java.io.PrintWriter;

public class Texto extends Ponto{

	private Ponto p1;
	private String textoInformado = "";
	
	protected Texto(Ponto p, String s){
		p1 = p;
		textoInformado = s;
	}
	
	public String getTextoInformado(){
		return textoInformado;
	}
	
	public Ponto getP1(){
		return p1;
	}
	
	  public void persistir(PrintWriter out) {
		    out.println("TEXTO");
			  out.println(""+p1.getX());
			  out.println(""+p1.getY());
			  out.println(""+this.textoInformado);
		  }
}
