package model.gals;

import model.ActionExecuter;

public class Semantico implements Constants {

	private ActionExecuter ae;

	public Semantico() {
		this.ae = new ActionExecuter();
	}

	public void executeAction(int action, Token token) throws SemanticError {
		this.ae.executeAction(action, token);
		System.out.println("Ação" + action + ", Token: " + token);
	}
}
