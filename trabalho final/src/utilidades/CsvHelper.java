package utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CsvHelper {

	private static final String EOL = "\r\n";
	private static final String SEPARATOR = ",";
	public static final String FILE_FORMAT = "csv";

	public static byte[] createReport(List<String> header, List<List<String>> lines) {
		StringBuilder sb = new StringBuilder();

		sb.append(listStringToCommaSeparated(header) + EOL);
		lines.stream().forEach(line -> sb.append(listStringToCommaSeparated(line) + EOL));

		return sb.toString().getBytes();
	}

	public static void save(File file, byte[] bytes) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(getFileName(file));
			fos.write(bytes);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static byte[] load(File file) {
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getFileName(File file) {
		if (file.getAbsolutePath().endsWith("." + FILE_FORMAT)) {
			return file.getAbsolutePath();
		}
		return file + "." + FILE_FORMAT;
	}

	private static String listStringToCommaSeparated(List<String> list) {
		return String.join(SEPARATOR, list);
	}
}
