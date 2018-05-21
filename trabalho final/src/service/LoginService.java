package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.UsuarioNaoEncontradoException;
import model.forms.LoginForm;

public class LoginService {

	private DataBaseConnector connector;

	public LoginService() {
		this.connector = new DataBaseConnector();
	}

	public Boolean checkLogin(LoginForm fillOut) throws UsuarioNaoEncontradoException {
		try {
			String query = "SELECT COUNT(*) AS total FROM public.policial WHERE cpf = '" + fillOut.getCpf() + "'";
			System.out.println(query);
			Statement st = this.connector.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			int total = rs.getInt("total");
			rs.close();
			st.close();
			return total > 0;
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}

		return false;
	}

}
