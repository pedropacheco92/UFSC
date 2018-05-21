package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import enums.SituacaoPessoa;
import enums.TipoAcidente;
import model.LocalAtendimentoDto;
import model.OcorrenciaReportDto;
import model.PessoaReportDto;
import model.PistaReportDto;
import utilidades.CsvHelper;

public class RelatorioService {

	private DataBaseConnector connector;

	private OcorrenciaService ocorrenciaService;

	private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");

	public RelatorioService() {
		this.connector = new DataBaseConnector();
		this.ocorrenciaService = new OcorrenciaService();
	}

	public byte[] createRodoviasReport() {
		Statement st;
		List<PistaReportDto> list = new ArrayList<>();
		try {
			st = this.connector.getConnection().createStatement();
			String query = "SELECT * FROM public.info_pista_view3;";
			System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				list.add(new PistaReportDto(rs.getString(2), new Long(rs.getInt(5))));
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Map<String, List<PistaReportDto>> map = list.stream().collect(Collectors.groupingBy(p -> p.getRodovia()));

		List<List<String>> lines = new LinkedList<>();

		for (String s : map.keySet()) {
			List<PistaReportDto> listP = map.get(s);

			int qtAtropelamento = listP.stream()
					.filter(p -> TipoAcidente.ATROPELAMENTO.getId().equals(p.getTipoAcidenteId()))
					.collect(Collectors.toList()).size();
			int qtColisao = listP.stream()
					.filter(p -> TipoAcidente.COLISAO_FRONTAL.getId().equals(p.getTipoAcidenteId()))
					.collect(Collectors.toList()).size();
			int qtEngavetamento = listP.stream()
					.filter(p -> TipoAcidente.ENGAVETAMENTO.getId().equals(p.getTipoAcidenteId()))
					.collect(Collectors.toList()).size();

			lines.add(Arrays.asList(s, String.valueOf(qtAtropelamento), String.valueOf(qtColisao),
					String.valueOf(qtEngavetamento)));
		}

		List<String> header = Arrays.asList("Rodovia", "Quantidade de Atropelamentos",
				"Quantidade de colisoes frontais", "Quantidade de engavetamentos");

		return CsvHelper.createReport(header, lines);
	}

	public byte[] createOcorrenciaReport() {
		Statement st;
		List<OcorrenciaReportDto> list = new ArrayList<>();
		try {
			st = this.connector.getConnection().createStatement();
			String query = "SELECT * FROM public.info_ocorrencias_pista_view2;";
			System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				OcorrenciaReportDto dto = new OcorrenciaReportDto();
				dto.setData(this.date.format(rs.getDate(2)));
				dto.setHorario(this.hora.format(rs.getDate(3)));
				dto.setData_acidente(this.date.format(rs.getDate(4)));
				dto.setHorario_acidente(this.hora.format(rs.getDate(5)));
				dto.setDescricao(rs.getString(6));
				dto.setCount(rs.getInt(7));
				list.add(dto);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<List<String>> lines = new LinkedList<>();

		for (OcorrenciaReportDto dto : list) {
			lines.add(Arrays.asList(dto.getDescricao(), dto.getData(), dto.getHorario(), dto.getData_acidente(),
					dto.getHorario_acidente(), String.valueOf(dto.getCount())));
		}

		List<String> header = Arrays.asList("Descrição Ocorrencia", "Data", "Horário", "Data acidente",
				"Horário acidente", "Quantidade de pessoas");

		return CsvHelper.createReport(header, lines);
	}

	public byte[] createPessoaReport() {
		Map<Integer, String> mapLocal = this.ocorrenciaService.getListLocalAtendimento().stream()
				.collect(Collectors.toMap(LocalAtendimentoDto::getId, LocalAtendimentoDto::getDescricao));

		Statement st;
		List<PessoaReportDto> list = new ArrayList<>();
		try {
			st = this.connector.getConnection().createStatement();
			String query = "SELECT * FROM public.info_vitima_local_view2;";
			System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				PessoaReportDto dto = new PessoaReportDto();
				dto.setCpf(rs.getString(1));
				dto.setNome(rs.getString(2));
				dto.setSituacaoId(new Long(rs.getInt(3)));
				dto.setLocalAtendimentoId(rs.getInt(4));
				list.add(dto);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<List<String>> lines = new LinkedList<>();
		for (PessoaReportDto dto : list) {
			lines.add(Arrays.asList(dto.getCpf(), dto.getNome(), SituacaoPessoa.getDescricaoById(dto.getSituacaoId()),
					mapLocal.get(dto.getLocalAtendimentoId())));
		}

		List<String> header = Arrays.asList("CPF", "Nome", "Situação", "Local de Atendimento");
		return CsvHelper.createReport(header, lines);
	}

}
