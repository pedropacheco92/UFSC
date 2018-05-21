/**
 * @autor Pedro
 */
package controller;

import model.analisador.AnalisadorLexico;
import model.analisador.AnalisadorSintatico;
import model.gals.LexicalError;
import model.gals.SemanticError;
import model.gals.SyntaticError;
import view.MainView;

/**
 * @author Pedro
 *
 */
public class MainController {

	private MainView view;

	public MainController() {
		this.view = new MainView();
		this.view.setController(this);
	}

	public void analisarLexico(String value) {
		try {
			AnalisadorLexico.analisar(value);
			this.view.showMessage("Sem erro léxico");
		} catch (LexicalError e) {
			System.err.println(e.getMessage() + " em " + e.getPosition());
			this.view.showMessage(e.getMessage() + " em " + e.getPosition());
		}
	}

	public void analisarSintatico(String value) {
		try {
			AnalisadorSintatico.analisar(value);
			this.view.showMessage("Sem erro sintático");
		} catch (LexicalError e) {
			System.err.println(e.getMessage() + " em " + e.getPosition());
			this.view.setPosition(e.getPosition());
			this.view.showMessage("Erro Lexico: " + e.getMessage() + " em " + e.getPosition());
		} catch (SyntaticError e) {
			System.err.println(e.getMessage() + " em " + e.getPosition());
			this.view.setPosition(e.getPosition());
			this.view.showMessage("Erro Sintatico: " + e.getMessage() + " em " + e.getPosition());
		} catch (SemanticError e) {
			System.err.println(e.getMessage() + " em " + e.getPosition());
			this.view.setPosition(e.getPosition());
			this.view.showMessage("Erro Semantico: " + e.getMessage() + "  em " + e.getPosition());
		}
	}

	public void analisarSemantico(String value) {
		try {
			AnalisadorSintatico.analisar(value);
			this.view.showMessage("Sem erro semântico");
		} catch (LexicalError e) {
			System.err.println(e.getMessage() + " em " + e.getPosition());
			this.view.setPosition(e.getPosition());
			this.view.showMessage("Erro Lexico: " + e.getMessage() + " em " + e.getPosition());
		} catch (SyntaticError e) {
			System.err.println(e.getMessage() + " em " + e.getPosition());
			this.view.setPosition(e.getPosition());
			this.view.showMessage("Erro Sintatico: " + e.getMessage() + " em " + e.getPosition());
		} catch (SemanticError e) {
			System.err.println(e.getMessage() + " em " + e.getPosition());
			this.view.setPosition(e.getPosition());
			this.view.showMessage("Erro Semantico: " + e.getMessage() + "  em " + e.getPosition());
		}
	}

}
