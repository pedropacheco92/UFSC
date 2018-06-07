package trabalho.datamining;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.time.StopWatch;

public class TextMiner {

	private Map<String, List<Long>> listaInvertida = new HashMap<>();

	private Map<Long, String> registros = new HashMap<>();

	public TextMiner() throws SQLException {
		Jdbc jdbc = new Jdbc();

		this.registros = jdbc.getData();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		this.criaListaInvertida();
		stopWatch.stop();
		System.out.println("Tempo decorrido: " + stopWatch.getTime() + "ms");

		this.listaInvertida.entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())).forEach(System.out::println);
	}

	private void criaListaInvertida() {
		for (Entry<Long, String> e : this.registros.entrySet()) {
			for (String string : e.getValue().split(" ")) {
				String s = this.clearString(string);

				if (s.equals("")) {
					continue;
				}

				if (this.listaInvertida.containsKey(s)) {
					this.listaInvertida.get(s).add(e.getKey());
				} else {
					this.listaInvertida.put(s, new ArrayList<>(Arrays.asList(e.getKey())));
				}
			}
		}
	}

	private String clearString(String s) {
		String string = s.toLowerCase().trim();
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		string = string.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		string = string.replaceAll("[^a-zA-Z0-9]", "");

		return string;
	}

}
