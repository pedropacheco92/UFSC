package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import utilidades.DataBasePropertiesReader;

public class DataBaseConnector {

	private Connection connection;

	private DataBasePropertiesReader prop;

	public DataBaseConnector() {
		this.prop = new DataBasePropertiesReader();
		this.connectToDatabaseOrDie();
	}

	private void connectToDatabaseOrDie() {
		this.connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://" + this.prop.getEndereco() + ":" + this.prop.getPorta() + "/"
					+ this.prop.getDb();
			this.connection = DriverManager.getConnection(url, this.prop.getUser(), this.prop.getSenha());
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

}
