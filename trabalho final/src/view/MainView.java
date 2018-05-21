package view;

import model.forms.AdicionarEnvolvidoForm;

public interface MainView<PRESENTER, FORM> extends View<PRESENTER, FORM> {

	void onPessoaCadastrada(AdicionarEnvolvidoForm form);

	void notifica(String message);

	void clearScreen();

}
