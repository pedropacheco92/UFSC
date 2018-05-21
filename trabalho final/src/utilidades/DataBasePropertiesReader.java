package utilidades;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataBasePropertiesReader {

	private static final String ENDERECO = "endereco";
	private static final String PORTA = "porta";
	private static final String USERS = "usuario";
	private static final String SENHA = "senha";
	private static final String DB = "database";

	private Properties prop;

	private InputStream inputStream;

	public DataBasePropertiesReader() {
		try {
			this.getPropresties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getPropresties() throws IOException {
		try {
			this.prop = new Properties();
			String propFileName = "database.properties";

			this.inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);

			if (this.inputStream != null) {
				this.prop.load(this.inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			this.inputStream.close();
		}
	}

	public String getEndereco() {
		return this.prop.getProperty(ENDERECO);
	}

	public String getPorta() {
		return this.prop.getProperty(PORTA);
	}

	public String getUser() {
		return this.prop.getProperty(USERS);
	}

	public String getSenha() {
		return this.prop.getProperty(SENHA);
	}

	public String getDb() {
		return this.prop.getProperty(DB);
	}

}
