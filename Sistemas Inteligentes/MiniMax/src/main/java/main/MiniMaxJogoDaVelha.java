package main;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiniMaxJogoDaVelha {

	private Valor jogadorCPU, jogadorVerdadeiro, turno;

	public int minimax(Map<Integer, Valor> tabuleiro, Valor jogador) {
		if (JogoDaVelhaHelper.ganhou(this.jogadorVerdadeiro, tabuleiro)) {
			System.out.println("-10");
			return -10;
		}

		if (JogoDaVelhaHelper.ganhou(this.jogadorCPU, tabuleiro)) {
			System.out.println("10");
			return 10;
		}

		List<Integer> casasVazias = JogoDaVelhaHelper.casasVazias(tabuleiro);

		if (casasVazias.isEmpty()) {
			System.out.println("0");
			return 0;
		}

		List<Entry<Integer, Integer>> jogadas = new ArrayList<>();

		for (Integer i : casasVazias) {
			System.out.println("Casas vazias: " + casasVazias);
			System.out.println("Casa: " + i);
			Entry<Integer, Integer> jogada;

			tabuleiro.replace(i, jogador);

			if (this.turno.equals(this.jogadorCPU)) {
				int result = minimax(tabuleiro, this.jogadorCPU);
				jogada = new SimpleEntry<>(i, result);
			} else {
				int result = minimax(tabuleiro, this.jogadorVerdadeiro);
				jogada = new SimpleEntry<>(i, result);
			}

			tabuleiro.replace(jogada.getKey(), jogador);
			jogadas.add(jogada);
		}

		casasVazias.stream().forEach(i -> tabuleiro.replace(i, Valor.VAZIO));

		int melhorJogada = 0;

		if (jogador.equals(this.jogadorCPU)) {
			melhorJogada = jogadas.stream().max((e1, e2) -> Integer.compare(e1.getValue(), e2.getValue())).get().getKey();
		} else {
			melhorJogada = jogadas.stream().min((e1, e2) -> Integer.compare(e1.getValue(), e2.getValue())).get().getKey();
		}

		System.out.println("Melhor Jogada: " + melhorJogada);
		return melhorJogada;
	}

}
