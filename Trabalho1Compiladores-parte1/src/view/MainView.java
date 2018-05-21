package view;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.MainController;
import helper.FileHelper;

public class MainView {

	private JFrame frmCompilador;

	private JTextPane textPane;

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
		this.frmCompilador = new JFrame();
		this.frmCompilador.setTitle("Compilador");
		this.frmCompilador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		this.frmCompilador.setJMenuBar(menuBar);

		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);

		JMenuItem mntmSalvar = new JMenuItem("Salvar");
		mntmSalvar.addActionListener(e -> this.save());
		mnArquivo.add(mntmSalvar);

		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.addActionListener(e -> this.load());
		mnArquivo.add(mntmAbrir);

		JMenu mnLexico = new JMenu("L\u00E9xico");
		menuBar.add(mnLexico);

		JMenuItem mntmAnalisar = new JMenuItem("Analisar");
		mntmAnalisar.addActionListener(l -> this.controller.analisarLexico(this.textPane.getText()));
		mnLexico.add(mntmAnalisar);

		JMenu mnSintatico = new JMenu("Sint\u00E1tico");
		menuBar.add(mnSintatico);

		JMenuItem mntmAnalisarSintatico = new JMenuItem("Analisar");
		mntmAnalisarSintatico.addActionListener(e -> this.controller.analisarSintatico(this.textPane.getText()));
		mnSintatico.add(mntmAnalisarSintatico);

		JMenu mnSemantico = new JMenu("Sem\u00E2ntico");
		menuBar.add(mnSemantico);

		JMenu mnCodigo = new JMenu("C\u00F3digo");
		menuBar.add(mnCodigo);

		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);

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

	public void setMainController(MainController controller) {
		this.controller = controller;
	}

}
