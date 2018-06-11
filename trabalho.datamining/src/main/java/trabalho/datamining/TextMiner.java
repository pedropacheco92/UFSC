package trabalho.datamining;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.time.StopWatch;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

public class TextMiner {

	private Map<String, Set<Long>> listaInvertida = new HashMap<>();

	private Map<Long, String> registros = new HashMap<>();

	private List<String> stopwords = new ArrayList<>();
	
	private Set<Long> ids = new HashSet<>();

	public TextMiner() throws Exception {
		this.stopwords();

		Jdbc jdbc = new Jdbc();
		this.registros = jdbc.getData();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		this.criaListaInvertida();
		stopWatch.stop();
		System.out.println("Tempo decorrido: " + stopWatch.getTime() + "ms");

//		this.listaInvertida.entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
//				.forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
		
		
		ids();
		this.ids.stream().sorted(Long::compare).forEach(l -> System.out.print(l + ", "));
		System.out.println("");
		System.out.println("asdasd");
	}

	private void criaListaInvertida() {
		for (Entry<Long, String> e : this.registros.entrySet()) {
			Iterable<String> tokens = Splitter.on(CharMatcher.anyOf(" ,./\\")).split(e.getValue());

			tokens.forEach(string -> {
				String s = this.clearString(string);

				if (s.equals("") || this.stopwords.contains(s)) {
					return;
				}

				if (this.listaInvertida.containsKey(s)) {
					this.listaInvertida.get(s).add(e.getKey());
				} else {
					this.listaInvertida.put(s, new HashSet<>(Arrays.asList(e.getKey())));
				}
			});
		}
	}

	private String clearString(String s) {
		String string = s.toLowerCase().trim();
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		string = string.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		string = string.replaceAll("[^a-zA-Z0-9]", "");

		return string;
	}

	private void stopwords() throws IOException {
		ClassLoader classLoader = Application.class.getClassLoader();
		File file = new File(classLoader.getResource("stopwords.txt").getFile());
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			this.stopwords.add(this.clearString(line));
		}

		scanner.close();
	}
	
	private void ids() throws IOException {
		ClassLoader classLoader = Application.class.getClassLoader();
		File file = new File(classLoader.getResource("items_casa.txt").getFile());
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = this.clearString(scanner.nextLine());
			this.ids.addAll(this.listaInvertida.get(line));
		}

		scanner.close();
//		this.ids.addAll(this.listaInvertida.get("inseticida"));
	}	

}
