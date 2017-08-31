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
			return -10;
		}

		if (JogoDaVelhaHelper.ganhou(this.jogadorCPU, tabuleiro)) {
			return 10;
		}

		List<Integer> casasVazias = JogoDaVelhaHelper.casasVazias(tabuleiro);

		if (casasVazias.isEmpty()) {
			return 0;
		}

		List<Entry<Integer, Integer>> jogadas = new ArrayList<>();

		for (Integer i : casasVazias) {

			Entry<Integer, Integer> jogada;

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

		int melhorJogada;

		if (jogador.equals(this.jogadorCPU)) {
			melhorJogada = jogadas.stream().map(Entry::getValue).max(Integer::compare).get();
		} else {
			melhorJogada = jogadas.stream().map(Entry::getValue).min(Integer::compare).get();
		}

		return melhorJogada;
	}

}
