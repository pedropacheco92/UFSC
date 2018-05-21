package model.gals;

import java.util.Stack;

public class Sintatico implements Constants {
	private Stack stack = new Stack();
	private Token currentToken;
	private Token previousToken;
	private Lexico scanner;
	private Semantico semanticAnalyser;

	private static final boolean isTerminal(int x) {
		return x < FIRST_NON_TERMINAL;
	}

	private static final boolean isNonTerminal(int x) {
		return (x >= FIRST_NON_TERMINAL) && (x < FIRST_SEMANTIC_ACTION);
	}

	private static final boolean isSemanticAction(int x) {
		return x >= FIRST_SEMANTIC_ACTION;
	}

	private boolean step() throws LexicalError, SyntaticError, SemanticError {
		if (this.currentToken == null) {
			int pos = 0;
			if (this.previousToken != null) {
				pos = this.previousToken.getPosition() + this.previousToken.getLexeme().length();
			}

			this.currentToken = new Token(DOLLAR, "$", pos);
		}

		int x = ((Integer) this.stack.pop()).intValue();
		int a = this.currentToken.getId();

		if (x == EPSILON) {
			return false;
		} else if (isTerminal(x)) {
			if (x == a) {
				if (this.stack.empty()) {
					return true;
				} else {
					this.previousToken = this.currentToken;
					this.currentToken = this.scanner.nextToken();
					return false;
				}
			} else {
				throw new SyntaticError(PARSER_ERROR[x], this.currentToken.getPosition(),
						this.currentToken.getLexeme());
			}
		} else if (isNonTerminal(x)) {
			if (this.pushProduction(x, a)) {
				return false;
			} else {
				throw new SyntaticError(PARSER_ERROR[x], this.currentToken.getPosition(),
						this.currentToken.getLexeme());
			}
		} else // isSemanticAction(x)
		{
			this.semanticAnalyser.executeAction(x - FIRST_SEMANTIC_ACTION, this.previousToken);
			return false;
		}
	}

	private boolean pushProduction(int topStack, int tokenInput) {
		int p = PARSER_TABLE[topStack - FIRST_NON_TERMINAL][tokenInput - 1];
		if (p >= 0) {
			int[] production = PRODUCTIONS[p];
			// empilha a produção em ordem reversa
			for (int i = production.length - 1; i >= 0; i--) {
				this.stack.push(new Integer(production[i]));
			}
			return true;
		} else {
			return false;
		}
	}

	public void parse(Lexico scanner, Semantico semanticAnalyser) throws LexicalError, SyntaticError, SemanticError {
		this.scanner = scanner;
		this.semanticAnalyser = semanticAnalyser;

		this.stack.clear();
		this.stack.push(new Integer(DOLLAR));
		this.stack.push(new Integer(START_SYMBOL));

		this.currentToken = scanner.nextToken();

		while (!this.step()) {
			;
		}
	}
}
