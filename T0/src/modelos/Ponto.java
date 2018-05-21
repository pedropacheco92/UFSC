package modelos;

public class Ponto {

	private int x, y;

	  Ponto(){}
	  
	   public Ponto(int x, int y){
	  	this.x = x;
	  	this.y = y;
	  }

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
	  public String toString() {
	    return "(" + this.x + ","  + this.y + ")";
	  }



}
