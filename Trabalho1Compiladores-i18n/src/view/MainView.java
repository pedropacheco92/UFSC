package view;

import java.awt.ComponentOrientation;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.MainController;
import helper.FileHelper;
import helper.Messages;
import listeners.LocaleChangeListener;
import lombok.Setter;

public class MainView {

	private JFrame2 frmCompilador;

	private JTextPane textPane;

	private List<LocaleChangeListener> components = new LinkedList<>();

	@Setter
	private MainController controller;

	/**
	 * Create the application.
	 */
	public MainView() {
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 *
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.frmCompilador = new JFrame2();
		this.components.add(this.frmCompilador);
		this.frmCompilador.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.frmCompilador.setTitle(Messages.COMPILADOR);
		this.frmCompilador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.frmCompilador.setJMenuBar(menuBar);

		JMenu2 mnArquivo = new JMenu2(Messages.ARQUIVO);
		this.components.add(mnArquivo);
		menuBar.add(mnArquivo);

		JMenuItem2 mntmSalvar = new JMenuItem2(Messages.SALVAR);
		this.components.add(mntmSalvar);
		mntmSalvar.addActionListener(e -> this.save());
		mnArquivo.add(mntmSalvar);

		JMenuItem2 mntmAbrir = new JMenuItem2(Messages.ABRIR);
		this.components.add(mntmAbrir);
		mntmAbrir.addActionListener(e -> this.load());
		mnArquivo.add(mntmAbrir);

		JMenu2 mnLexico = new JMenu2(Messages.LEXICO);
		this.components.add(mnLexico);
		menuBar.add(mnLexico);

		JMenuItem2 mntmAnalisar = new JMenuItem2(Messages.ANALISAR);
		this.components.add(mntmAnalisar);
		mntmAnalisar.addActionListener(l -> this.controller.analisarLexico(this.textPane.getText()));
		mnLexico.add(mntmAnalisar);

		JMenu2 mnSintatico = new JMenu2(Messages.SINTATICO);
		this.components.add(mnSintatico);
		menuBar.add(mnSintatico);

		JMenuItem2 mntmAnalisarSintatico = new JMenuItem2(Messages.ANALISAR);
		this.components.add(mntmAnalisarSintatico);
		mntmAnalisarSintatico.addActionListener(e -> this.controller.analisarSintatico(this.textPane.getText()));
		mnSintatico.add(mntmAnalisarSintatico);

		JMenu2 mnSemantico = new JMenu2(Messages.SEMANTICO);
		this.components.add(mnSemantico);
		menuBar.add(mnSemantico);

		JMenuItem2 mntmAnalisarSemantico = new JMenuItem2(Messages.ANALISAR);
		this.components.add(mntmAnalisarSemantico);
		mntmAnalisarSemantico.addActionListener(e -> this.controller.analisarSemantico(this.textPane.getText()));
		mnSemantico.add(mntmAnalisarSemantico);

		JMenu2 mnCodigo = new JMenu2(Messages.CODIGO);
		this.components.add(mnCodigo);
		menuBar.add(mnCodigo);

		JMenu2 mnLinguagem = new JMenu2(Messages.LINGUAGEM);
		this.components.add(mnLinguagem);
		menuBar.add(mnLinguagem);

		JMenuItem2 rdbtnmntmPortugus = new JMenuItem2(Messages.PORTUGUES);
		this.components.add(rdbtnmntmPortugus);
		rdbtnmntmPortugus.addActionListener(l -> {
			Messages.changeLocale(new Locale("pt", "BR"));
			this.frmCompilador.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			this.components.forEach(LocaleChangeListener::onLocaleChange);
		});
		mnLinguagem.add(rdbtnmntmPortugus);

		JMenuItem2 radioButtonMenuItem = new JMenuItem2(Messages.ARABICO);
		this.components.add(radioButtonMenuItem);
		radioButtonMenuItem.addActionListener(l -> {
			Messages.changeLocale(new Locale("ar"));
			this.frmCompilador.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			this.components.forEach(LocaleChangeListener::onLocaleChange);
		});
		mnLinguagem.add(radioButtonMenuItem);

		JMenuItem2 rdbtnmntmEnglish = new JMenuItem2(Messages.INGLES);
		this.components.add(rdbtnmntmEnglish);
		rdbtnmntmEnglish.addActionListener(l -> {
			Messages.changeLocale(Locale.US);
			this.frmCompilador.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			this.components.forEach(LocaleChangeListener::onLocaleChange);
		});
		mnLinguagem.add(rdbtnmntmEnglish);

		this.frmCompilador.setBounds(100, 100, 800, 600);
		this.frmCompilador.getContentPane()
				.setLayout(new BoxLayout(this.frmCompilador.getContentPane(), BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		this.frmCompilador.getContentPane().add(scrollPane);

		this.textPane = new JTextPane();
		scrollPane.setViewportView(this.textPane);
		this.frmCompilador.setVisible(true);
	}

	private void load() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LSI FILES", FileHelper.FILE_FORMAT);
		chooser.setFileFilter(filter);
		int retrival = chooser.showOpenDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			this.textPane.setText(new String(FileHelper.load(chooser.getSelectedFile())));
		}
	}

	private void save() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LSI FILES", FileHelper.FILE_FORMAT);
		chooser.setFileFilter(filter);
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			FileHelper.save(chooser.getSelectedFile(), this.textPane.getText().getBytes());
		}
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void setPosition(int pos) {
		this.textPane.setCaretPosition(pos);
	}

}
