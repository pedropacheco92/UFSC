package model.forms;

import enums.CondicaoPista;
import enums.TipoPista;

public class LocalForm implements Form {
	
	private String km;
	
	private String rodovia;
	
	private CondicaoPista condicaoPista;
	
	private TipoPista tipoPista;

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getRodovia() {
		return rodovia;
	}

	public void setRodovia(String rodovia) {
		this.rodovia = rodovia;
	}

	public CondicaoPista getCondicaoPista() {
		return condicaoPista;
	}

	public void setCondicaoPista(CondicaoPista condicaoPista) {
		this.condicaoPista = condicaoPista;
	}

	public TipoPista getTipoPista() {
		return tipoPista;
	}

	public void setTipoPista(TipoPista tipoPista) {
		this.tipoPista = tipoPista;
	} 

}
