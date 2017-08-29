package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.Setter;

public class MiniMaxJogoDaVelha {
	
	/**
	 * Mapa indica o tabuleiro, come�ando em "-" vazio e com indices indo de 0 a 8
	 * com os possiveis valores das casas sendo X ou O
	 * 
	 *  - | - | -    0 | 1 | 2
	 * -----------  -----------
	 *  - | - | -    3 | 4 | 5
	 * -----------  -----------
	 *  - | - | - 	 6 | 7 | 8
	 *  
	 */	
	private Map<Integer, Valor> tabuleiro = new HashMap<>();
	
	private List<List<Integer>> valoresGanhadores = new ArrayList<>();
	
	@Getter
	@Setter
	private Valor jogadorCPU, jogadorVerdadeiro;
	
	public MiniMaxJogoDaVelha() {
		// popula o tabuleiro com "-" sendo este o simbolo da casa vazia
		IntStream.rangeClosed(0, 8).forEach(i -> tabuleiro.put(i, Valor.VAZIO));
		
		// insere as linhas que se estiverem com os valores iguais ganham o jogo
		valoresGanhadores.add(Arrays.asList(0, 1, 2));
		valoresGanhadores.add(Arrays.asList(3, 4, 5));
		valoresGanhadores.add(Arrays.asList(6, 7, 8));
		valoresGanhadores.add(Arrays.asList(0, 3, 6));
		valoresGanhadores.add(Arrays.asList(1, 4, 7));
		valoresGanhadores.add(Arrays.asList(2, 5, 8));
		valoresGanhadores.add(Arrays.asList(0, 4, 8));
		valoresGanhadores.add(Arrays.asList(2, 5, 6));		
	}
	
	// a função principal do miniMax
	private int miniMax(Valor jogador) {
		
		// lista de casas vazios
		List<Integer> casasVazias = tabuleiro.entrySet()
				.stream()
				.filter(e -> Valor.VAZIO.equals(e.getValue()))
				.map(Entry::getKey)
				.collect(Collectors.toList());
		
		// verifica se empata
		if (casasVazias.isEmpty()) {
			return 0;
		}
		
		// verifica se o jogador verdadeiro ganhou, se sim, return -10, senao a cpu ganhou e retorna +10
		return ganhou(jogadorVerdadeiro) ? -10 : 10;		
	}
	
	private boolean ganhou(Valor jogador) {		
		for (List<Integer> l : valoresGanhadores) {
			if (l.stream().map(tabuleiro::get).allMatch(jogador::equals)) {
				return true;
			}
		}
		
		return false;		
	}

}
