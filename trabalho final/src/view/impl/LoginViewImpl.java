package view.impl;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import exceptions.UsuarioNaoEncontradoException;
import model.forms.LoginForm;
import presenter.LoginPresenter;
import utilidades.ViewConstants;
import view.AbstractView;
import view.LoginView;

public class LoginViewImpl extends AbstractView<LoginPresenter, LoginForm>
		implements LoginView<LoginPresenter, LoginForm> {

	private JFormattedTextField loginField;

	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void paint() {
		this.frame = new JFrame();
		this.frame.setTitle("Login");
		this.frame.getContentPane().setLayout(null);
		this.loginField = new JFormattedTextField(this.createFormatter("###.###.###-##"));
		this.loginField.setBounds(127, 107, 130, 20);

		this.frame.getContentPane().add(this.loginField);

		JLabel lblLogin = new JLabel("CPF");
		lblLogin.setBounds(127, 83, 97, 14);
		this.frame.getContentPane().add(lblLogin);

		JLabel lblFaaLoginNo = new JLabel("Fa\u00E7a Login No Sistema");
		lblFaaLoginNo.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblFaaLoginNo.setBounds(58, 24, 268, 48);
		this.frame.getContentPane().add(lblFaaLoginNo);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(225, 160, 89, 23);
		this.frame.getContentPane().add(btnLogin);
		btnLogin.addActionListener(event -> this.onLoginClicked());

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(68, 160, 89, 23);
		this.frame.getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(event -> this.frame.dispose());

		this.frame.setVisible(true);
		this.frame.setSize(ViewConstants.LOGIN_WIDTH, ViewConstants.LOGIN_HEIGHT);
		this.frame.setLocationRelativeTo(null);
	}

	@Override
	public void fillIn(LoginForm form) {
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
	public LoginForm fillOut() {
		LoginForm form = new LoginForm();
		form.setCpf(this.loginField.getText().replaceAll("\\D", ""));
		return form;
	}

	private void onLoginClicked() {
		try {
			this.presenter.onLoginClicked();
			this.frame.dispose();
		} catch (UsuarioNaoEncontradoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

}
