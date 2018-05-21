package presenter;

public interface MainPresenter extends Presenter {

	void onAdicionarPessoa();

	void onFinalizarClicked();

	byte[] getReportPorEstrada();

	byte[] getReportPorPessoa();

	byte[] getReportPorOcorrencia();

}
