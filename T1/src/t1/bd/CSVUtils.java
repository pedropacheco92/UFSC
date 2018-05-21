package t1.bd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVUtils {

	public static final String LIVROS = "csv_files/livros.csv";
	public static final String LOGINS = "csv_files/logins.csv";
	public static final String EMPRESTIMOS = "csv_files/emprestimos.csv";
	public static final String CVS_SPLIT = ",";
	public static final String COMMENT = "#";

	public static void writeLine(Writer w, List<String> values) throws IOException {
		writeLine(w, values, CVS_SPLIT, ' ');
	}

	public static void writeLine(Writer w, List<String> values, String separadores) throws IOException {
		writeLine(w, values, separadores, ' ');
	}

	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

	public static void writeLine(Writer w, List<String> values, String separadores, char customQuote) throws IOException {

		boolean first = true;

		if (separadores == " ") {
			separadores = CVS_SPLIT;
		}

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separadores);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}

	public static BufferedReader load(String path) throws FileNotFoundException, IOException {
		return new BufferedReader(new FileReader(path));
	}

}
