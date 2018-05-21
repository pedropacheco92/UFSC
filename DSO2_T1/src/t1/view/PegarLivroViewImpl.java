package t1.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import t1.controller.PegarLivroControllerImpl;
import t1.view.dados.Dados;
import t1.view.dados.Livro;

public class PegarLivroViewImpl extends AbstractView<PegarLivroControllerImpl> {

	private JFrame frame;

	private JTextField textField;

	private JLabel lblTitulovalue, labelAutorValue;

	private Livro livro;

	public PegarLivroViewImpl(PegarLivroControllerImpl pegarLivroController) {
		super(pegarLivroController);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createScreen() {
		this.frame = new JFrame("titulo");
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.getContentPane().setLayout(null);

		JLabel lblDigiteOCdigo = new JLabel("Digite o c\u00F3digo do Livro");
		lblDigiteOCdigo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDigiteOCdigo.setBounds(10, 26, 364, 59);
		this.frame.getContentPane().add(lblDigiteOCdigo);

		this.textField = new JTextField();
		this.textField.setBounds(10, 96, 204, 20);
		this.frame.getContentPane().add(this.textField);
		this.textField.setColumns(10);

		JLabel lblTtulo = new JLabel("T\u00EDtulo");
		lblTtulo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTtulo.setBounds(10, 127, 123, 20);
		this.frame.getContentPane().add(lblTtulo);

		this.lblTitulovalue = new JLabel("");
		this.lblTitulovalue.setBounds(10, 158, 287, 14);
		this.frame.getContentPane().add(this.lblTitulovalue);

		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAutor.setBounds(10, 183, 123, 20);
		this.frame.getContentPane().add(lblAutor);

		this.labelAutorValue = new JLabel("");
		this.labelAutorValue.setBounds(10, 214, 287, 14);
		this.frame.getContentPane().add(this.labelAutorValue);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> this.frame.dispose());
		btnCancelar.setBounds(175, 273, 89, 23);
		this.frame.getContentPane().add(btnCancelar);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(event -> {
			this.controller.pegarLivro();
			this.frame.dispose();
		});
		btnOk.setBounds(43, 273, 89, 23);
		this.frame.getContentPane().add(btnOk);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(event -> this.controller.buscarLivroPorId(this.textField.getText()));
		btnBuscar.setBounds(224, 95, 73, 23);
		this.frame.getContentPane().add(btnBuscar);

		this.frame.setSize(324, 352);
		this.frame.setLocationRelativeTo(null);
	}

	@Override
	public void setDados(Dados dados) {
		this.livro = (Livro) dados;
		this.lblTitulovalue.setText(this.livro.getTituloLivro());
		this.labelAutorValue.setText(this.livro.getNomeAutor());
	}

	@Override
	public Dados getDados() {
		return this.livro;
	}
}
