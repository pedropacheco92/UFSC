package t1.exceptions;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("Usu�rio nao Encontrado!");
	}

}
