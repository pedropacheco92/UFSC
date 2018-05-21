package presenter.impl;

import java.util.List;

import listeners.CadastroPessoaListener;
import model.LocalAtendimentoDto;
import model.forms.AdicionarEnvolvidoForm;
import presenter.AbstractPresenter;
import presenter.AdicionarEnvolvidosPresenter;
import service.OcorrenciaService;
import view.impl.AdicionarEnvolvidoViewImpl;

public class AdicionarEnvolvidoPresenterImpl
		extends AbstractPresenter<AdicionarEnvolvidoViewImpl, AdicionarEnvolvidoForm, AdicionarEnvolvidosPresenter>
		implements AdicionarEnvolvidosPresenter {

	private CadastroPessoaListener listener;

	private OcorrenciaService service;

	@Override
	public void init() {
		this.service = new OcorrenciaService();
		super.init();
	}

	@Override
	public void onPessoaCadastrada() {
		this.listener.onPessoaCadastrada(this.view.fillOut());
	}

	public void setListener(CadastroPessoaListener listener) {
		this.listener = listener;
	}

	@Override
	public List<LocalAtendimentoDto> getLocaisAtendimento() {
		return this.service.getListLocalAtendimento();
	}

}
