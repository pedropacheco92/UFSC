package view.impl;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import enums.SituacaoPessoa;
import enums.TipoAcidente;
import enums.TipoEnvolvido;
import enums.TipoVeiculo;
import model.LocalAtendimentoDto;
import model.forms.AdicionarEnvolvidoForm;
import presenter.AdicionarEnvolvidosPresenter;
import utilidades.ViewConstants;
import view.AbstractView;
import view.LoginView;

public class AdicionarEnvolvidoViewImpl extends AbstractView<AdicionarEnvolvidosPresenter, AdicionarEnvolvidoForm>
		implements LoginView<AdicionarEnvolvidosPresenter, AdicionarEnvolvidoForm> {

	private JFrame frame;
	private JFormattedTextField textFieldCpf;
	private JTextField textFieldNome;
	private JLabel lblSituao;
	private JTextField textFieldDescricao;
	private JComboBox<SituacaoPessoa> comboBoxSituacao;
	private JComboBox<LocalAtendimentoDto> comboBoxLocal;
	private JComboBox<TipoVeiculo> comboBoxTpVeiculo;
	private JComboBox<TipoEnvolvido> comboBoxTpEnvolvimento;
	private JComboBox<TipoAcidente> comboBoxTpAcidente;

	/**
	 * Initialize the contents of the frame.
	 *
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void paint() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, ViewConstants.DEFAULT_HEIGHT, ViewConstants.LOGIN_WIDTH);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(null);

		JLabel lblPessoa = new JLabel("Pessoa");
		lblPessoa.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPessoa.setBounds(10, 11, 118, 26);
		this.frame.getContentPane().add(lblPessoa);

		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setBounds(10, 48, 46, 14);
		this.frame.getContentPane().add(lblCpf);

		this.textFieldCpf = new JFormattedTextField(this.createFormatter("###.###.###-##"));
		this.textFieldCpf.setBounds(10, 65, 151, 20);
		this.frame.getContentPane().add(this.textFieldCpf);
		this.textFieldCpf.setColumns(10);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(220, 48, 46, 14);
		this.frame.getContentPane().add(lblNome);

		this.textFieldNome = new JTextField();
		this.textFieldNome.setBounds(220, 65, 317, 20);
		this.frame.getContentPane().add(this.textFieldNome);
		this.textFieldNome.setColumns(10);

		this.lblSituao = new JLabel("Situa\u00E7\u00E3o");
		this.lblSituao.setBounds(10, 96, 73, 14);
		this.frame.getContentPane().add(this.lblSituao);

		this.comboBoxSituacao = new JComboBox<>(SituacaoPessoa.values());
		this.comboBoxSituacao.setBounds(10, 115, 151, 20);
		this.frame.getContentPane().add(this.comboBoxSituacao);

		JLabel lblEmAtendimento = new JLabel("Local de atendimento");
		lblEmAtendimento.setBounds(10, 152, 179, 14);
		this.frame.getContentPane().add(lblEmAtendimento);

		this.comboBoxLocal = new JComboBox<>();
		this.presenter.getLocaisAtendimento().stream().forEach(l -> this.comboBoxLocal.addItem(l));

		this.comboBoxLocal.setBounds(10, 170, 317, 20);
		this.frame.getContentPane().add(this.comboBoxLocal);

		JLabel lblVeculo = new JLabel("Ve\u00EDculo");
		lblVeculo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVeculo.setBounds(10, 197, 118, 26);
		this.frame.getContentPane().add(lblVeculo);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setBounds(10, 226, 73, 14);
		this.frame.getContentPane().add(lblDescrio);

		this.textFieldDescricao = new JTextField();
		this.textFieldDescricao.setBounds(10, 244, 292, 20);
		this.frame.getContentPane().add(this.textFieldDescricao);
		this.textFieldDescricao.setColumns(10);

		JLabel lblTipoDeVeculo = new JLabel("Tipo de Ve\u00EDculo");
		lblTipoDeVeculo.setBounds(325, 226, 102, 14);
		this.frame.getContentPane().add(lblTipoDeVeculo);

		this.comboBoxTpVeiculo = new JComboBox<>(TipoVeiculo.values());
		this.comboBoxTpVeiculo.setBounds(325, 244, 212, 20);
		this.frame.getContentPane().add(this.comboBoxTpVeiculo);

		JLabel lblEnvolvimento = new JLabel("Envolvimento");
		lblEnvolvimento.setBounds(10, 275, 93, 14);
		this.frame.getContentPane().add(lblEnvolvimento);

		this.comboBoxTpEnvolvimento = new JComboBox<>(TipoEnvolvido.values());
		this.comboBoxTpEnvolvimento.setBounds(10, 300, 179, 20);
		this.frame.getContentPane().add(this.comboBoxTpEnvolvimento);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(l -> this.frame.dispose());
		btnCancelar.setBounds(277, 327, 102, 23);
		this.frame.getContentPane().add(btnCancelar);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(a -> {
			this.presenter.onPessoaCadastrada();
			this.frame.dispose();
		});
		btnCadastrar.setBounds(418, 327, 102, 23);
		this.frame.getContentPane().add(btnCadastrar);

		JLabel lblTipoAcidente = new JLabel("Tipo Acidente");
		lblTipoAcidente.setBounds(220, 96, 118, 14);
		this.frame.getContentPane().add(lblTipoAcidente);

		this.comboBoxTpAcidente = new JComboBox<>(TipoAcidente.values());
		this.comboBoxTpAcidente.setBounds(220, 115, 159, 20);
		this.frame.getContentPane().add(this.comboBoxTpAcidente);

		this.frame.setVisible(true);
	}

	@Override
	public void fillIn(AdicionarEnvolvidoForm form) {
		// TODO Auto-generated method stub

	}

	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	@Override
	public AdicionarEnvolvidoForm fillOut() {
		AdicionarEnvolvidoForm form = new AdicionarEnvolvidoForm();
		form.setCpf(this.textFieldCpf.getText().replaceAll("\\D", ""));
		form.setDescricaoVeiculo(this.textFieldDescricao.getText());
		form.setLocalAtendimento((LocalAtendimentoDto) this.comboBoxLocal.getSelectedItem());
		form.setNome(this.textFieldNome.getText());
		form.setSituacao((SituacaoPessoa) this.comboBoxSituacao.getSelectedItem());
		form.setTipoEnvolvimento((TipoEnvolvido) this.comboBoxTpEnvolvimento.getSelectedItem());
		form.setTipoVeiculo((TipoVeiculo) this.comboBoxTpVeiculo.getSelectedItem());
		form.setTipoAcidente((TipoAcidente) this.comboBoxTpAcidente.getSelectedItem());

		return form;
	}
}
