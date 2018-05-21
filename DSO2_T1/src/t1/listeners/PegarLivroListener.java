package t1.listeners;

import t1.view.dados.Livro;

public interface PegarLivroListener {

	void pegarLivro(Livro livro);

	void devolverLivro(Livro livro);

}
