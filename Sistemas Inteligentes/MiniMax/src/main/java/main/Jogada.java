package main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Jogada implements Comparable<Jogada> {

	private Integer jogada, pontuacao;

	@Override
	public int compareTo(Jogada o) {
		return Integer.compare(getPontuacao(), o.getPontuacao());
	}

}
