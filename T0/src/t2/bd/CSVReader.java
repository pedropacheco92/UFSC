package t2.bd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import modelos.Figura;

public class CSVReader {

	public static Figura loadFiguras(String id) throws FileNotFoundException, IOException {
		BufferedReader br = CSVUtils.load(CSVUtils.FIGURAS);

		String idNoZeros = id.replaceFirst("^0+(?!$)", "");

		String line = null;

		line = br.lines()
				.filter(l -> (!l.startsWith(CSVUtils.COMMENT)
						&& (l.replaceFirst("^0+(?!$)", "").startsWith(idNoZeros + CSVUtils.CVS_SPLIT)))
						|| l.startsWith(id + CSVUtils.CVS_SPLIT))
				.findAny().get();

		String[] lineArray = line.split(CSVUtils.CVS_SPLIT);

		Figura figura = new Figura();
		figura.setNomeFigura(lineArray[0]);
		figura.setQuantidadePontos(Integer.valueOf(lineArray[1]));
		// figura.setPontos(lineArray[2]);

		return figura;

	};
}
