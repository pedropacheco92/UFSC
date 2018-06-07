package trabalho.datamining;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Jdbc {

	String driver = "org.postgresql.Driver";
	String user = "pedro";
	String senha = "root";
	String url = "jdbc:postgresql://localhost:5432/datamining";
	Connection con = null;

	public Map<Long, String> getData() throws SQLException {
		Map<Long, String> result = new HashMap<>();

		try {
			Class.forName(this.driver);
			this.con = DriverManager.getConnection(this.url, this.user, this.senha);
			System.out.println("Conexão realizada com Sucesso!");

			PreparedStatement stm = this.con.prepareStatement(
					"select identificador_caso, concat(nome_popular_comercial, ', ', substancia_genero_especie, ', ', subclasse_agente, ', ', classe_agente) as nome from \"06_agente_intoxicante\";");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				result.put(rs.getLong(1), rs.getString(2));
			}

		} catch (ClassNotFoundException ex) {
			System.err.print(ex.getMessage());
		} catch (SQLException e) {
			System.err.print(e.getMessage());
		}
		this.con.close();
		return result;
	}

}
