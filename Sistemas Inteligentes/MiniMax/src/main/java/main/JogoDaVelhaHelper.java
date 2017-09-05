package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class JogoDaVelhaHelper {

	private static List<List<Integer>> valoresGanhadores = new ArrayList<>();

	static {
		// insere as linhas que se estiverem com os valores iguais ganham o jogo
		valoresGanhadores.add(Arrays.asList(0, 1, 2));
		valoresGanhadores.add(Arrays.asList(3, 4, 5));
		valoresGanhadores.add(Arrays.asList(6, 7, 8));
		valoresGanhadores.add(Arrays.asList(0, 3, 6));
		valoresGanhadores.add(Arrays.asList(1, 4, 7));
		valoresGanhadores.add(Arrays.asList(2, 5, 8));
		valoresGanhadores.add(Arrays.asList(0, 4, 8));
		valoresGanhadores.add(Arrays.asList(2, 4, 6));
	}

	public static String renderTabuleiro(Map<Integer, Valor> valores) {
		StringBuilder builder = new StringBuilder();
	//@formatter:off
	builder.append(" ")
		.append(getValor(0, valores.get(0)))
		.append(" | ")
		.append(getValor(1, valores.get(1)))
		.append(" | ")
		.append(getValor(2, valores.get(2)))
		.append(" \n")
		.append("-----------")
		.append("\n")
		.append(" ")
		.append(getValor(3, valores.get(3)))
		.append(" | ")
		.append(getValor(4, valores.get(4)))
		.append(" | ")
		.append(getValor(5, valores.get(5)))
		.append(" \n")
		.append("-----------")
		.append("\n")
		.append(" ")
		.append(getValor(6, valores.get(6)))
		.append(" | ")
		.append(getValor(7, valores.get(7)))
		.append(" | ")
		.append(getValor(8, valores.get(8)))
		.append(" \n");
	//@formatter:on
		return builder.toString();
	}

	private static String getValor(int i, Valor v) {
		if (Valor.VAZIO.equals(v)) {
			return String.valueOf(i);
		}

		return v.getValue();
	}

	public static boolean ganhou(Valor jogador, Map<Integer, Valor> tabuleiro) {
		for (List<Integer> l : valoresGanhadores) {
			if (l.stream().map(tabuleiro::get).allMatch(jogador::equals)) {
				return true;
			}
		}

		return false;
	}

	public static List<Integer> casasVazias(Map<Integer, Valor> tabuleiro) {
		//@formatter:off
    	return tabuleiro
    			.entrySet()
    			.stream()
    			.filter(e -> Valor.VAZIO.equals(e.getValue()))
    			.map(Entry::getKey)
    			.collect(Collectors.toList());
    	//@formatter:on
	}

}
