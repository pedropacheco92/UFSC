package main;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Data;

@Data
public class MiniMaxJogoDaVelha {

	private final Valor jogadorCPU, jogadorVerdadeiro;

	private Map<Integer, Integer> jogadas;

	public void clear() {
		this.jogadas = new HashMap<>();
	}

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

		for (Integer i : casasVazias) {
			System.out.println("Casas vazias: " + casasVazias);
			System.out.println("Casa: " + i);
			Entry<Integer, Integer> jogada;

			tabuleiro.replace(i, jogador);

			if (jogador.equals(this.jogadorCPU)) {
				int result = minimax(tabuleiro, this.jogadorCPU);
				jogada = new SimpleEntry<>(i, result);
			} else {
				int result = minimax(tabuleiro, this.jogadorVerdadeiro);
				jogada = new SimpleEntry<>(i, result);
			}

			tabuleiro.replace(jogada.getKey(), jogador);
			if (this.jogadas.containsKey(jogada.getKey())) {
				this.jogadas.computeIfPresent(jogada.getKey(), (k, v) -> {
					if (v > jogada.getValue()) {
						return v;
					}
					return jogada.getValue();
				});
			} else {
				this.jogadas.put(jogada.getKey(), jogada.getValue());
			}

		}

		casasVazias.stream().forEach(c -> tabuleiro.replace(c, Valor.VAZIO));

		int melhorJogada = 0;

		if (jogador.equals(this.jogadorCPU)) {
			melhorJogada = this.jogadas.entrySet().stream().map(Entry::getValue).max(Integer::compare).orElse(Integer.MAX_VALUE);
		} else {
			melhorJogada = this.jogadas.entrySet().stream().map(Entry::getValue).min(Integer::compare).orElse(Integer.MIN_VALUE);
		}

		System.out.println("Melhor Jogada: " + melhorJogada);
		return melhorJogada;
	}
}
