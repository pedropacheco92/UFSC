package trabalho.datamining;

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

	public TextMiner() {
		this.registros.put(1L,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempor diam in tellus egestas iaculis. In posuere augue sed augue scelerisque, vel luctus ipsum tempus. Aenean maximus porttitor nunc, ac euismod risus imperdiet ut. Quisque sollicitudin justo ut luctus egestas. Donec lobortis ipsum sed placerat imperdiet. In gravida lectus ut lorem gravida, sed dictum libero cursus. Aenean rutrum, felis vel molestie luctus, quam mauris dictum dui, quis scelerisque mauris metus non velit.");
		this.registros.put(2L,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempor diam in tellus egestas iaculis. In posuere augue sed augue scelerisque, vel luctus ipsum tempus. Aenean maximus porttitor nunc, ac euismod risus imperdiet ut. Quisque sollicitudin justo ut luctus egestas. Donec lobortis ipsum sed placerat imperdiet. In gravida lectus ut lorem gravida, sed dictum libero cursus. Aenean rutrum, felis vel molestie luctus, quam mauris dictum dui, quis scelerisque mauris metus non velit.");
		this.registros.put(3L,
				"Phasellus bibendum augue at auctor porta. Pellentesque placerat varius convallis. Sed laoreet dictum risus, ut lacinia nulla convallis sed. Suspendisse ornare, neque at porta gravida, erat purus cursus tellus, elementum euismod ligula nisl non leo. Maecenas condimentum, mauris eget blandit faucibus, arcu nulla rutrum augue, nec consequat elit velit quis est.");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		this.criaListaInvertidaParalelo();
		stopWatch.stop();
		System.out.println(stopWatch.getTime());
		stopWatch.reset();
		this.listaInvertida = new HashMap<>();
		stopWatch.start();
		this.criaListaInvertida();
		stopWatch.stop();
		System.out.println(stopWatch.getTime());

		this.listaInvertida.entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())).forEach(System.out::println);

		// System.out.println(this.listaInvertida);
	}

	private void criaListaInvertidaParalelo() {
		this.registros.entrySet().parallelStream().forEach(e -> {
			Arrays.asList(e.getValue().split(" ")).parallelStream().forEach(string -> {
				String s = this.clearString(string);

				if (this.listaInvertida.containsKey(s)) {
					this.listaInvertida.get(s).add(e.getKey());
				} else {
					this.listaInvertida.put(s, new ArrayList<>(Arrays.asList(e.getKey())));
				}
			});
		});
	}

	private void criaListaInvertida() {
		for (Entry<Long, String> e : this.registros.entrySet()) {
			for (String string : e.getValue().split(" ")) {
				String s = this.clearString(string);

				if (this.listaInvertida.containsKey(s)) {
					this.listaInvertida.get(s).add(e.getKey());
				} else {
					this.listaInvertida.put(s, new ArrayList<>(Arrays.asList(e.getKey())));
				}
			}
		}
	}

	private String clearString(String s) {
		String string = s.replace(".", "");
		string = string.replace(",", "");

		return string;
	}

}
