package t1.exceptions;

public class UserNotHaveBookException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotHaveBookException() {
		super("Usu�rio n�o possui esse livro!");
	}

}
