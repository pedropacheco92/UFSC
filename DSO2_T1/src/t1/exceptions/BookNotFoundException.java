package t1.exceptions;

public class BookNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookNotFoundException() {
		super("Livro nao Encontrado!");
	}

}
