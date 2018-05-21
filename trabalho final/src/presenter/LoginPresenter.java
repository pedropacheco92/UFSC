package presenter;

import exceptions.UsuarioNaoEncontradoException;

public interface LoginPresenter extends Presenter {

	void onLoginClicked() throws UsuarioNaoEncontradoException;

}
