package helper;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_NAME = "messages";

	private static ResourceBundle RESOURCE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT);

	public static final String ABRIR = "ABRIR";
	public static final String ANALISAR = "ANALISAR";
	public static final String ARABICO = "ARABICO";
	public static final String ARQUIVO = "ARQUIVO";
	public static final String CODIGO = "CODIGO";
	public static final String COMPILADOR = "COMPILADOR";
	public static final String INGLES = "INGLES";
	public static final String LEXICO = "LEXICO";
	public static final String LINGUAGEM = "LINGUAGEM";
	public static final String PORTUGUES = "PORTUGUES";
	public static final String SALVAR = "SALVAR";
	public static final String SEMANTICO = "SEMANTICO";
	public static final String SINTATICO = "SINTATICO";

	public static String getString(String key) {
		return RESOURCE.getString(key);
	}

	public static void changeLocale(Locale l) {
		ResourceBundle.clearCache();
		RESOURCE = ResourceBundle.getBundle(BUNDLE_NAME, l);
	}

}
