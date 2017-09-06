package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiniMaxJogoDaVelha {

	private Valor jogadorCPU, jogadorVerdadeiro;

	private boolean debug;

	// jogada/valor
	public MiniMaxEntry minimax(HashMap<Integer, Valor> tabuleiro, Valor jogador, Integer alfa, Integer beta) {
		print("minimax de: " + jogador.getValue() + " e: " + tabuleiro);
		if (JogoDaVelhaHelper.ganhou(this.jogadorVerdadeiro, tabuleiro)) {
			print("-10");
			return new MiniMaxEntry(null, -10);
		}

		if (JogoDaVelhaHelper.ganhou(this.jogadorCPU, tabuleiro)) {
			print("10");
			return new MiniMaxEntry(null, 10);
		}

		List<Integer> casasVazias = JogoDaVelhaHelper.casasVazias(tabuleiro);

		if (casasVazias.isEmpty()) {
			print("0");
			return new MiniMaxEntry(null, 0);
		}

		List<MiniMaxEntry> jogadas = new ArrayList<>();

		for (Integer i : casasVazias) {
			print("Casas vazias: " + casasVazias);
			print("Casa: " + i);
			MiniMaxEntry jogada;

			tabuleiro.replace(i, jogador);

			if (jogador.equals(this.jogadorCPU)) {
				MiniMaxEntry result = minimax(tabuleiro, this.jogadorVerdadeiro, alfa, beta);
				if (result.getValue() > alfa) {
					alfa = result.getValue();
				}
				jogada = new MiniMaxEntry(i, result.getValue());
			} else {
				MiniMaxEntry result = minimax(tabuleiro, this.jogadorCPU, alfa, beta);
				if (result.getValue() < beta) {
					alfa = result.getValue();
				}
				jogada = new MiniMaxEntry(i, result.getValue());
			}

			tabuleiro.replace(i, Valor.VAZIO);
			jogadas.add(jogada);

			if (alfa >= beta) {
				System.out.println("BREAK");
				break;
			}
		}

		casasVazias.stream().forEach(c -> tabuleiro.replace(c, Valor.VAZIO));
		print("Jogadas: " + jogadas);
		MiniMaxEntry melhorJogada;

		if (jogador.equals(this.jogadorCPU)) {
			melhorJogada = jogadas.stream().max(MiniMaxEntry::compareTo).get();
		} else {
			melhorJogada = jogadas.stream().min(MiniMaxEntry::compareTo).get();
		}

		print("Melhor Jogada: " + melhorJogada);
		return melhorJogada;
	}

	private void print(String s) {
		if (this.debug) {
			System.out.println(s);
		}
	}

}
