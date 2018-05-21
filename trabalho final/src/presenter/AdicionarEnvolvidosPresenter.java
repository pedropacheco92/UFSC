package presenter;

import java.util.List;

import model.LocalAtendimentoDto;

public interface AdicionarEnvolvidosPresenter extends Presenter {

	void onPessoaCadastrada();

	List<LocalAtendimentoDto> getLocaisAtendimento();

}
