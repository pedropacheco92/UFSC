package t1.view;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import t1.constants.ViewConstants;
import t1.controller.MainControllerImpl;
import t1.view.dados.Dados;
import t1.view.dados.DadosLogin;
import t1.view.dados.ListaLivrosEmprestados;
import t1.view.dados.Livro;
import t1.view.objects.LivroTableModel;

public class MainViewImpl extends AbstractView<MainControllerImpl> {

	private JFrame frame;

	private JTable table;

	private Map<String, List<Livro>> livros;

	private DadosLogin dadosLogin;

	public MainViewImpl(MainControllerImpl controller) {
		super(controller);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createScreen() {
		this.frame = new JFrame("titulo");
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 784, 21);
		this.frame.getContentPane().add(menuBar);

		JMenu mnAes = new JMenu("A\u00E7\u00F5es");
		menuBar.add(mnAes);

		JMenuItem mntmPegarLivro = new JMenuItem("Pegar Livro");
		mntmPegarLivro.addActionListener(event -> this.controller.showPegarLivro());
		mnAes.add(mntmPegarLivro);

		JMenuItem mntmDevolverLivro = new JMenuItem("Devolver Livro");
		mntmDevolverLivro.addActionListener(event -> this.controller.showDevolverLivro());
		mnAes.add(mntmDevolverLivro);

		JMenu mnOpes = new JMenu("Op\u00E7\u00F5es");
		menuBar.add(mnOpes);

		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mnOpes.add(mntmSobre);

		JMenuItem mntmAjuda = new JMenuItem("Ajuda");
		mnOpes.add(mntmAjuda);

		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(event -> System.exit(0));
		mnOpes.add(mntmSair);

		JLabel lblLivrosEmprestados = new JLabel("Livros Emprestados:");
		lblLivrosEmprestados.setBounds(7, 32, 770, 39);
		lblLivrosEmprestados.setFont(new Font("Tahoma", Font.PLAIN, 27));
		this.frame.getContentPane().add(lblLivrosEmprestados);

		this.table = new JTable();
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		this.table.setBorder(new LineBorder(new Color(0, 0, 0)));
		JScrollPane scrollPane = new JScrollPane(this.table);
		scrollPane.setSize(784, 481);
		scrollPane.setLocation(0, 80);
		this.frame.getContentPane().add(scrollPane);

		this.frame.setSize(ViewConstants.DEFAULT_WIDTH, ViewConstants.DEFAULT_HEIGHT);
		this.frame.setLocationRelativeTo(null);
	}

	@Override
	public void setDados(Dados dados) {
		if (dados instanceof DadosLogin) {
			this.dadosLogin = (DadosLogin) dados;
		}

		if (dados instanceof ListaLivrosEmprestados) {
			this.livros = ((ListaLivrosEmprestados) dados).getLivrosEmprestados();
			List<Livro> livrosTableModel = this.livros.get(this.dadosLogin.getLogin());

			LivroTableModel model = new LivroTableModel(livrosTableModel);
			this.table.setModel(model);
			this.table.repaint();
		}
	}

	@Override
	public Dados getDados() {
		return new ListaLivrosEmprestados(this.livros);
	}
}
