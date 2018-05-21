package view.impl;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import enums.CondicaoPista;
import enums.TipoPista;
import model.PessoaTableModel;
import model.forms.AdicionarEnvolvidoForm;
import model.forms.LocalForm;
import model.forms.OcorrenciaForm;
import presenter.MainPresenter;
import utilidades.CsvHelper;
import utilidades.DateLabelFormatter;
import utilidades.ViewConstants;
import view.AbstractView;
import view.MainView;

public class MainViewImpl extends AbstractView<MainPresenter, OcorrenciaForm>
		implements MainView<MainPresenter, OcorrenciaForm> {

	private JFrame frame;
	private JTextField textField_Km;
	private JTextField textField_Rod;
	private JTable table;

	private List<AdicionarEnvolvidoForm> pessoas = new ArrayList<>();

	private boolean usuarioPolicial;

	private String[] columnNames = { "Nome", "CPF", "Situacao", "Local Atendimento", "Veículo", "Envolvimento" };
	private JSpinner timeSpinner;
	private JComboBox<TipoPista> comboBoxTpPista;
	private JComboBox<CondicaoPista> comboBoxCondicaoPista;
	private JDatePickerImpl datePicker;
	private JTextPane textPane;

	/**
	 * Initialize the contents of the frame.
	 *
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void paint() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, ViewConstants.DEFAULT_WIDTH, ViewConstants.DEFAULT_HEIGHT);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.renderMenu();
		this.frame.setVisible(true);
	}

	private void renderMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.frame.setJMenuBar(menuBar);

		JMenu mnOpes = new JMenu("Op\u00E7\u00F5es");
		menuBar.add(mnOpes);

		JMenuItem mntmCadastrarOcorrncia = new JMenuItem("Cadastrar ocorr\u00EAncia");
		mntmCadastrarOcorrncia.addActionListener(a -> this.renderCadastrarOcorrencia());
		mntmCadastrarOcorrncia.setEnabled(this.usuarioPolicial);
		mnOpes.add(mntmCadastrarOcorrncia);

		JMenu mnRelatrios = new JMenu("Relat\u00F3rios");
		menuBar.add(mnRelatrios);

		JMenuItem mntmPorPessoa = new JMenuItem("Por pessoa");
		mntmPorPessoa.addActionListener(a -> this.reportPorPessoa());
		mnRelatrios.add(mntmPorPessoa);

		JMenuItem mntmPorEstrada = new JMenuItem("Por estrada");
		mntmPorEstrada.addActionListener(l -> this.reportPorEstarda());
		mnRelatrios.add(mntmPorEstrada);

		JMenuItem mntmPorTipoDe = new JMenuItem("Por ocorr\u00EAncia");
		mntmPorTipoDe.addActionListener(a -> this.reportOcorrencia());
		mnRelatrios.add(mntmPorTipoDe);
		this.frame.getContentPane().setLayout(null);

	}

	private void renderCadastrarOcorrencia() {
		this.pessoas.clear();
		JLabel lblOcorrencia = new JLabel("Ocorr\u00EAncia");
		lblOcorrencia.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblOcorrencia.setBounds(10, 11, 106, 29);
		this.frame.getContentPane().add(lblOcorrencia);

		this.textPane = new JTextPane();
		this.textPane.setBounds(10, 65, 764, 79);
		this.frame.getContentPane().add(this.textPane);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		this.datePicker.setBounds(10, 177, 202, 29);
		this.frame.getContentPane().add(this.datePicker);

		this.timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(this.timeSpinner, "HH:mm");
		this.timeSpinner.setEditor(timeEditor);
		this.timeSpinner.setValue(new Date());
		this.timeSpinner.setBounds(239, 177, 86, 29);
		this.frame.getContentPane().add(this.timeSpinner);

		this.textField_Km = new JTextField();
		this.textField_Km.setBounds(10, 245, 86, 20);
		this.frame.getContentPane().add(this.textField_Km);
		this.textField_Km.setColumns(10);

		JLabel lblKm = new JLabel("Km");
		lblKm.setBounds(10, 231, 46, 14);
		this.frame.getContentPane().add(lblKm);

		JLabel lblRodovia = new JLabel("Rodovia");
		lblRodovia.setBounds(160, 231, 67, 14);
		this.frame.getContentPane().add(lblRodovia);

		this.textField_Rod = new JTextField();
		this.textField_Rod.setBounds(160, 245, 86, 20);
		this.frame.getContentPane().add(this.textField_Rod);
		this.textField_Rod.setColumns(10);

		JLabel lblTipoDePista = new JLabel("Tipo de Pista");
		lblTipoDePista.setBounds(330, 231, 86, 14);
		this.frame.getContentPane().add(lblTipoDePista);

		this.comboBoxTpPista = new JComboBox<>(TipoPista.values());
		this.comboBoxTpPista.setBounds(330, 245, 130, 20);
		this.frame.getContentPane().add(this.comboBoxTpPista);

		JLabel lblCondioDaPista = new JLabel("Condi\u00E7\u00E3o da Pista");
		lblCondioDaPista.setBounds(527, 231, 106, 14);
		this.frame.getContentPane().add(lblCondioDaPista);

		this.comboBoxCondicaoPista = new JComboBox<>(CondicaoPista.values());
		this.comboBoxCondicaoPista.setBounds(527, 245, 130, 20);
		this.frame.getContentPane().add(this.comboBoxCondicaoPista);

		JLabel label = new JLabel("Local");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(10, 205, 106, 29);
		this.frame.getContentPane().add(label);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setBounds(10, 50, 90, 14);
		this.frame.getContentPane().add(lblDescrio);

		JLabel lblDataOcorrncia = new JLabel("Data Ocorr\u00EAncia");
		lblDataOcorrncia.setBounds(10, 155, 166, 14);
		this.frame.getContentPane().add(lblDataOcorrncia);

		JLabel lblHoraDaOcorrncia = new JLabel("Hora da Ocorr\u00EAncia");
		lblHoraDaOcorrncia.setBounds(239, 155, 116, 14);
		this.frame.getContentPane().add(lblHoraDaOcorrncia);

		JLabel lblEnvolvidos = new JLabel("Envolvidos");
		lblEnvolvidos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnvolvidos.setBounds(10, 276, 106, 29);
		this.frame.getContentPane().add(lblEnvolvidos);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 316, 607, 136);

		DefaultTableModel tableModel = new DefaultTableModel(6, this.columnNames.length);
		tableModel.setColumnIdentifiers(this.columnNames);

		this.table = new JTable(tableModel);
		this.table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scrollPane.setViewportView(this.table);
		this.frame.getContentPane().add(scrollPane);

		JButton btnAdicionarEnvolvido = new JButton("Adicionar envolvido");
		btnAdicionarEnvolvido.addActionListener(a -> this.presenter.onAdicionarPessoa());
		btnAdicionarEnvolvido.setBounds(627, 316, 147, 29);
		this.frame.getContentPane().add(btnAdicionarEnvolvido);

		JButton btnRemoverEnvolvido = new JButton("Remover envolvido");
		btnRemoverEnvolvido.addActionListener(a -> this.removerEnvolvido());
		btnRemoverEnvolvido.setBounds(627, 366, 147, 29);
		this.frame.getContentPane().add(btnRemoverEnvolvido);

		JButton btnFinalizarESalvar = new JButton("Finalizar e Salvar");
		btnFinalizarESalvar.addActionListener(l -> this.presenter.onFinalizarClicked());
		btnFinalizarESalvar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnFinalizarESalvar.setBounds(462, 463, 180, 50);
		this.frame.getContentPane().add(btnFinalizarESalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(l -> this.clearScreen());
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCancelar.setBounds(141, 463, 180, 50);
		this.frame.getContentPane().add(btnCancelar);

		this.frame.repaint();
	}

	private void removerEnvolvido() {
		this.pessoas.remove(this.table.getSelectedRow());
		PessoaTableModel model = new PessoaTableModel(this.pessoas);
		this.table.setModel(model);
		this.table.repaint();
	}

	@Override
	public void fillIn(OcorrenciaForm form) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	@Override
	public OcorrenciaForm fillOut() {
		OcorrenciaForm form = new OcorrenciaForm();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

		Date startDate = new Date();
		try {
			startDate = dateFormatter.parse(this.datePicker.getJFormattedTextField().getText());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setData(startDate);
		form.setDescricao(this.textPane.getText());
		form.setEnvolvidos(this.pessoas);
		form.setHora((Date) this.timeSpinner.getModel().getValue());

		LocalForm localForm = new LocalForm();
		localForm.setCondicaoPista((CondicaoPista) this.comboBoxCondicaoPista.getSelectedItem());
		localForm.setKm(this.textField_Km.getText());
		localForm.setRodovia(this.textField_Rod.getText());
		localForm.setTipoPista((TipoPista) this.comboBoxTpPista.getSelectedItem());

		form.setLocal(localForm);

		return form;
	}

	@Override
	public void onPessoaCadastrada(AdicionarEnvolvidoForm form) {
		this.pessoas.add(form);
		PessoaTableModel model = new PessoaTableModel(this.pessoas);
		this.table.setModel(model);
		this.table.repaint();
	}

	@Override
	public void clearScreen() {
		this.pessoas.clear();
		this.frame.getContentPane().removeAll();
		this.frame.getContentPane().revalidate();
		this.frame.getContentPane().repaint();
		this.renderMenu();
	}

	private void reportPorEstarda() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", CsvHelper.FILE_FORMAT);
		chooser.setFileFilter(filter);
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			CsvHelper.save(chooser.getSelectedFile(), this.presenter.getReportPorEstrada());
		}
	}

	private void reportPorPessoa() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", CsvHelper.FILE_FORMAT);
		chooser.setFileFilter(filter);
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			CsvHelper.save(chooser.getSelectedFile(), this.presenter.getReportPorPessoa());
		}
	}

	private void reportOcorrencia() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", CsvHelper.FILE_FORMAT);
		chooser.setFileFilter(filter);
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			CsvHelper.save(chooser.getSelectedFile(), this.presenter.getReportPorOcorrencia());
		}
	}

	public boolean isUsuarioPolicial() {
		return this.usuarioPolicial;
	}

	public void setUsuarioPolicial(boolean usuarioPolicial) {
		this.usuarioPolicial = usuarioPolicial;
	}

	@Override
	public void notifica(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

}
