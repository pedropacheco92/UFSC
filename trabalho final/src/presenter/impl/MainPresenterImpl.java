package presenter.impl;

import java.sql.SQLException;

import listeners.CadastroPessoaListener;
import model.forms.AdicionarEnvolvidoForm;
import model.forms.OcorrenciaForm;
import presenter.AbstractPresenter;
import presenter.MainPresenter;
import service.OcorrenciaService;
import service.RelatorioService;
import view.impl.AdicionarEnvolvidoViewImpl;
import view.impl.MainViewImpl;

public class MainPresenterImpl extends AbstractPresenter<MainViewImpl, OcorrenciaForm, MainPresenter>
		implements MainPresenter, CadastroPessoaListener {

	private OcorrenciaService service;

	private RelatorioService relatorioService;

	private String cpf;

	@Override
	public void init() {
		this.service = new OcorrenciaService();
		this.relatorioService = new RelatorioService();
		super.init();
	}

	@Override
	public void onPessoaCadastrada(AdicionarEnvolvidoForm form) {
		this.view.onPessoaCadastrada(form);
	}

	@Override
	public void onAdicionarPessoa() {
		AdicionarEnvolvidoPresenterImpl presenter = new AdicionarEnvolvidoPresenterImpl();
		AdicionarEnvolvidoViewImpl view = new AdicionarEnvolvidoViewImpl();
		presenter.setView(view);
		view.setPresenter(presenter);
		presenter.setListener(this);
		presenter.init();
	}

	@Override
	public void onFinalizarClicked() {
		OcorrenciaForm form = this.view.fillOut();
		form.setCpf(this.cpf);
		try {
			this.service.save(form);
			this.view.notifica("Registro Salvo!");
			this.view.clearScreen();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public byte[] getReportPorEstrada() {
		return this.relatorioService.createRodoviasReport();
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public byte[] getReportPorPessoa() {
		return this.relatorioService.createPessoaReport();
	}

	@Override
	public byte[] getReportPorOcorrencia() {
		return this.relatorioService.createOcorrenciaReport();
	}
}
