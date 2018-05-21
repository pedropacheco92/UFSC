package t1.view;

import t1.view.dados.Dados;

public interface View {

	void showMessage(String message);

	void createScreen();

	void setDados(Dados dados);

	Dados getDados();

}
