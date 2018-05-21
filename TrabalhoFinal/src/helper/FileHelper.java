package helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileHelper {

	public static final String FILE_FORMAT = "lsi";

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
}
