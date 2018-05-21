package model.lexico;

import model.gals.LexicalError;
import model.gals.Lexico;
import model.gals.SemanticError;
import model.gals.Semantico;
import model.gals.Sintatico;
import model.gals.SyntaticError;

public class AnalisadorSintatico {

	public static void analisar(String value) throws LexicalError, SyntaticError, SemanticError {
		Sintatico sintatico = new Sintatico();
		Lexico lexico = new Lexico(value);
		sintatico.parse(lexico, new Semantico());
	}

}
