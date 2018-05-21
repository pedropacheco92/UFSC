package listeners;

public interface AnalisadorListener {

	void analisarLexico(String value);

	void analisarSintatico(String value);

	void analisarSemantico(String value);

}
