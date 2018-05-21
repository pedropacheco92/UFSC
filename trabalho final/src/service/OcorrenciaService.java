package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enums.SituacaoPessoa;
import enums.TipoVeiculo;
import model.LocalAtendimentoDto;
import model.forms.AdicionarEnvolvidoForm;
import model.forms.LocalForm;
import model.forms.OcorrenciaForm;

public class OcorrenciaService {

	private List<OcorrenciaForm> forms = new ArrayList<>();

	private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private DataBaseConnector connector;

	public OcorrenciaService() {
		this.connector = new DataBaseConnector();
	}

	public void save(OcorrenciaForm form) throws SQLException {
		this.forms.add(form);
		this.saveLocalForm(form.getLocal());
		this.saveOcorrencia(form);
		this.saveAcidente(form);
	}

	private void saveOcorrencia(OcorrenciaForm form) throws SQLException {
		Statement st = this.connector.getConnection().createStatement();
		String query = "INSERT INTO public.ocorrencia VALUES (nextval(\'seq_id_ocorrencia\'), \'" + form.getDescricao()
				+ "\', \'" + this.date.format(new Date()) + "\', \'" + this.hora.format(new Date())
				+ "\', currval(\'seq_id_local\') , " + form.getCpf() + ",  \'" + this.date.format(form.getData())
				+ "\', \'" + this.hora.format(form.getHora()) + "\');";
		System.out.println(query);
		st.execute(query);
		st.close();
	}

	private void saveAcidente(OcorrenciaForm form) throws SQLException {
		for (AdicionarEnvolvidoForm envolvidoForm : form.getEnvolvidos()) {
			this.savePessoa(envolvidoForm);
			this.createVeiculo(envolvidoForm.getDescricaoVeiculo(), envolvidoForm.getTipoVeiculo());

			Statement st = this.connector.getConnection().createStatement();
			String query = "INSERT INTO public.acidente VALUES (" + envolvidoForm.getCpf()
					+ ", currval(\'seq_id_veiculo\'), \'" + this.date.format(form.getData()) + "\', \'"
					+ this.hora.format(form.getHora()) + "\', currval(\'seq_id_ocorrencia\'), "
					+ envolvidoForm.getTipoAcidente().getId() + ", " + envolvidoForm.getTipoEnvolvimento().getId()
					+ ", " + envolvidoForm.getSituacao().getId() + ", " + envolvidoForm.getLocalAtendimento().getId()
					+ ");";
			System.out.println(query);
			st.execute(query);
			st.close();
		}
	}

	private void savePessoa(AdicionarEnvolvidoForm form) throws SQLException {
		int total;
		Statement st;
		st = this.connector.getConnection().createStatement();
		String query = "SELECT COUNT(*) AS total FROM public.pessoa WHERE cpf = " + form.getCpf() + ";";
		System.out.println(query);
		ResultSet rs = st.executeQuery(query);
		rs.next();
		total = rs.getInt("total");
		rs.close();
		if (total == 0) { // nao existe pessoa cadastrada
			this.createPessoa(form.getCpf(), form.getNome(), form.getSituacao(), form.getLocalAtendimento());
		} else {
			String update = "UPDATE public.pessoa SET situacao_pessoa =" + form.getSituacao().getId()
					+ ", local_atendimento=" + form.getLocalAtendimento().getId() + " WHERE cpf = " + form.getCpf()
					+ ";";
			System.out.println(update);
			st.execute(update);
		}
	}

	private void createVeiculo(String descricao, TipoVeiculo tipo) throws SQLException {
		Statement st = this.connector.getConnection().createStatement();
		String query = "INSERT INTO public.veiculo VALUES (nextval(\'seq_id_veiculo\'), \'" + descricao + "\', "
				+ tipo.getId() + ");";
		System.out.println(query);
		st.execute(query);
		st.close();
	}

	private void createPessoa(String cpf, String nome, SituacaoPessoa situacao, LocalAtendimentoDto local)
			throws SQLException {
		Statement st = this.connector.getConnection().createStatement();
		String query = "INSERT INTO public.pessoa VALUES (" + cpf + ", \'" + nome + "\', " + situacao.getId() + ", "
				+ local.getId() + ");";
		System.out.println(query);
		st.execute(query);
		st.close();
	}

	private void saveLocalForm(LocalForm local) throws SQLException {
		int total = 0;
		Statement st = this.connector.getConnection().createStatement();
		String query = "SELECT COUNT(*) AS total FROM public.rodovia WHERE descricao = '" + local.getRodovia() + "'";
		System.out.println(query);
		ResultSet rs = st.executeQuery(query);
		rs.next();
		total = rs.getInt("total");
		rs.close();
		if (total == 0) { // nao existe rodovia cadastrada
			String insert = "INSERT INTO public.rodovia (descricao)" + " values (?)";
			System.out.println(insert);
			PreparedStatement preparedStmt = this.connector.getConnection().prepareStatement(insert);
			preparedStmt.setString(1, local.getRodovia());

			preparedStmt.execute();
		}

		String select = "SELECT id_rodovia FROM public.rodovia WHERE descricao = '" + local.getRodovia() + "'";
		System.out.println(select);
		rs = st.executeQuery(select);
		String id_rodovia = null;

		while (rs.next()) {
			id_rodovia = rs.getString("id_rodovia");
		}

		String insert = "INSERT INTO public.local VALUES (nextval(\'seq_id_local\'), \'" + local.getKm() + "\', "
				+ id_rodovia + ", " + local.getCondicaoPista().getId() + ", " + local.getTipoPista().getId() + ")";
		System.out.println(insert);
		Statement stmt = this.connector.getConnection().createStatement();
		stmt.execute(insert);
		st.close();

	}

	public List<OcorrenciaForm> loadAll() {
		return this.forms;
	}

	public List<LocalAtendimentoDto> getListLocalAtendimento() {
		List<LocalAtendimentoDto> list = new ArrayList<>();

		Statement st;
		try {
			st = this.connector.getConnection().createStatement();
			String query = "SELECT id_local_atendimento, descricao FROM public.local_atendimento;";
			System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				LocalAtendimentoDto dto = new LocalAtendimentoDto(rs.getInt("id_local_atendimento"),
						rs.getString("descricao"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
