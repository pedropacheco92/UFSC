package t1.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import t1.controller.DevolverLivroControllerImpl;
import t1.view.dados.Dados;
import t1.view.dados.Livro;

public class DevolverLivroViewImpl extends AbstractView<DevolverLivroControllerImpl> {

	private JTextField textField;

	private JLabel lblTitulovalue, labelAutorValue;

	private JFrame frame;

	private Livro livro;

	public DevolverLivroViewImpl(DevolverLivroControllerImpl devolverLivroController) {
		super(devolverLivroController);
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
		btnOk.addActionListener(arg0 -> {
			this.controller.devolverLivro();
			this.frame.dispose();
		});
		btnOk.setBounds(40, 265, 89, 23);
		this.frame.getContentPane().add(btnOk);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(event -> this.controller.buscarLivroPorId(this.textField.getText()));
		btnBuscar.setBounds(224, 95, 73, 23);
		this.frame.getContentPane().add(btnBuscar);

		this.frame.setVisible(true);
		this.frame.setBounds(100, 100, 326, 354);
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
