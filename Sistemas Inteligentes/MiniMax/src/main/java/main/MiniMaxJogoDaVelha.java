package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiniMaxJogoDaVelha {

	private Valor jogadorCPU, jogadorVerdadeiro;

	private boolean debug;

	// jogada/pontuacao
	public Jogada minimax(HashMap<Integer, Valor> tabuleiro, Valor jogador) {
		print("minimax de: " + jogador.getValue() + " e: " + tabuleiro);

		// verifica os estados terminais, se ganhou perdeu ou empatou
		// se perdeu retorna -10
		if (JogoDaVelhaHelper.ganhou(this.jogadorVerdadeiro, tabuleiro)) {
			print("-10");
			return new Jogada(null, -10);
		}

		// se ganhou retorna 10
		if (JogoDaVelhaHelper.ganhou(this.jogadorCPU, tabuleiro)) {
			print("10");
			return new Jogada(null, 10);
		}

		List<Integer> casasVazias = JogoDaVelhaHelper.casasVazias(tabuleiro);

		// se empatou retorna 0
		if (casasVazias.isEmpty()) {
			print("0");
			return new Jogada(null, 0);
		}

		// lista para adicionar todas as jogadas possíveis
		List<Jogada> jogadas = new ArrayList<>();

		// para cada casa vazia...
		for (Integer i : casasVazias) {
			print("Casas vazias: " + casasVazias);
			print("Casa: " + i);
			Jogada jogada;

			// coloca no tabuleiro como possivel jogada
			tabuleiro.replace(i, jogador);

			// pega o resultado do minimax para aquela jogada, que dependendo do jogador do
			// turno
			if (jogador.equals(this.jogadorCPU)) {
				Jogada result = minimax(tabuleiro, this.jogadorVerdadeiro);
				jogada = new Jogada(i, result.getPontuacao());
			} else {
				Jogada result = minimax(tabuleiro, this.jogadorCPU);
				jogada = new Jogada(i, result.getPontuacao());
			}

			// reseta a jogada no tabuleiro
			tabuleiro.replace(i, Valor.VAZIO);

			// adiciona a possivel jogada na lista de jogadas
			jogadas.add(jogada);
		}

		// reseta o tabuleiro para o estado original
		casasVazias.stream().forEach(c -> tabuleiro.replace(c, Valor.VAZIO));
		print("Jogadas: " + jogadas);
		Jogada melhorJogada;

		// se for o turno do computador, retorna a melhor jogada como sendo a de MAIOR
		// pontuação
		if (jogador.equals(this.jogadorCPU)) {
			melhorJogada = jogadas.stream().max(Jogada::compareTo).get();
		} else {
			// se for o turno do oponente, retorna a jogada de MENOR pontuação
			melhorJogada = jogadas.stream().min(Jogada::compareTo).get();
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
