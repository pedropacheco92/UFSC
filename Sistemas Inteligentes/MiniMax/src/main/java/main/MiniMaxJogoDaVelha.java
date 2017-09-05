package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class MiniMaxJogoDaVelha {

	private final Valor jogadorCPU, jogadorVerdadeiro;

	// jogada/valor
	public MiniMaxEntry minimax(HashMap<Integer, Valor> t, Valor jogador) {
		HashMap<Integer, Valor> tabuleiro = JogoDaVelhaHelper.clone(t);
		System.out.println("minimax de: " + jogador.getValue() + " e: " + tabuleiro);
		if (JogoDaVelhaHelper.ganhou(this.jogadorVerdadeiro, tabuleiro)) {
			System.out.println("-10");
			return new MiniMaxEntry(null, -10);
		}

		if (JogoDaVelhaHelper.ganhou(this.jogadorCPU, tabuleiro)) {
			System.out.println("10");
			return new MiniMaxEntry(null, 10);
		}

		List<Integer> casasVazias = JogoDaVelhaHelper.casasVazias(tabuleiro);

		if (casasVazias.isEmpty()) {
			System.out.println("0");
			return new MiniMaxEntry(null, 0);
		}

		List<MiniMaxEntry> jogadas = new ArrayList<>();

		for (Integer i : casasVazias) {
			System.out.println("Casas vazias: " + casasVazias);
			System.out.println("Casa: " + i);
			MiniMaxEntry jogada;

			tabuleiro.replace(i, jogador);

			if (jogador.equals(this.jogadorCPU)) {
				MiniMaxEntry result = minimax(tabuleiro, this.jogadorVerdadeiro);
				jogada = new MiniMaxEntry(i, result.getValue());
			} else {
				MiniMaxEntry result = minimax(tabuleiro, this.jogadorCPU);
				jogada = new MiniMaxEntry(i, result.getValue());
			}

			tabuleiro.replace(jogada.getKey(), jogador);
			jogadas.add(jogada);
		}

		casasVazias.stream().forEach(c -> tabuleiro.replace(c, Valor.VAZIO));
		System.out.println("Jogadas: " + jogadas);
		MiniMaxEntry melhorJogada;

		if (jogador.equals(this.jogadorCPU)) {
			melhorJogada = jogadas.stream().max(MiniMaxEntry::compareTo).get();
		} else {
			melhorJogada = jogadas.stream().min(MiniMaxEntry::compareTo).get();
		}

		System.out.println("Melhor Jogada: " + melhorJogada);
		return melhorJogada;
	}
}
