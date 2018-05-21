package model.analisador;

import model.gals.LexicalError;
import model.gals.Lexico;
import model.gals.Token;

public class AnalisadorLexico {

	public static void analisar(String value) throws LexicalError {
		Lexico lexico = new Lexico();
		lexico.setInput(value);

		Token t = null;
		while ((t = lexico.nextToken()) != null) {
			System.out.println(t.getLexeme());
		}

	}

}
