package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.commons.lang3.time.StopWatch;

public class JogoDaVelha {

	private HashMap<Integer, Valor> tabuleiro = new HashMap<>();

	private Valor jogadorCPU, jogadorVerdadeiro, turno;

	private JogoDaVelhaView view;

	private MiniMaxJogoDaVelha miniMax;

	private boolean debug;

	public JogoDaVelha(String[] agrs) {
		this.debug = Arrays.asList(agrs).stream().filter(s -> s.contains("debug")).findAny().isPresent();
		this.view = new JogoDaVelhaView(this::onValorSelecionado, this::onCasaSelecionada);
		inicioJogo();
	}

	private void onCasaSelecionada(String casa) {
		Integer v = valorOk(casa);
		if (v == null) {
			this.view.selecionaPosicao(this.turno.getValue());
		} else {
			this.tabuleiro.replace(v, this.turno);
			this.view.mostraJogada(getNomeJogador(this.turno), v, JogoDaVelhaHelper.renderTabuleiro(this.tabuleiro), 0);
		}
	}

	private void onValorSelecionado(Valor valor) {
		this.jogadorVerdadeiro = valor;
		this.jogadorCPU = Valor.contrario(valor);
	}

	private void inicioJogo() {
		// popula o tabuleiro com valor vazio
		IntStream.rangeClosed(0, 8).forEach(i -> this.tabuleiro.put(i, Valor.VAZIO));

		// seleciona o jogador
		this.view.selecionaJogador();

		// mostra o jogador selecionado
		this.view.mostraJogadores(this.jogadorVerdadeiro.getValue(), this.jogadorCPU.getValue(),
				JogoDaVelhaHelper.renderTabuleiro(this.tabuleiro));

		// seta o jogador verdadeiro como jogador inicial
		this.turno = this.jogadorVerdadeiro;

		// inicializa Minimax
		this.miniMax = new MiniMaxJogoDaVelha(this.jogadorCPU, this.jogadorVerdadeiro, this.debug);

		// realiza o jogo
		jogo();
	}

	private void jogo() {
		// enquanto tiver casas vazias no jogo
		while (!this.tabuleiro.values().stream().noneMatch(Valor.VAZIO::equals)) {
			// se o turno for do jogador cpu, pega uma casa aleatoria
			if (this.jogadorCPU.equals(this.turno)) {
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				Integer valor = this.miniMax.minimax(this.tabuleiro, this.turno, Integer.MIN_VALUE, Integer.MAX_VALUE)
						.getJogada();
				stopWatch.stop();
				this.tabuleiro.replace(valor, this.turno);
				this.view.mostraJogada(getNomeJogador(this.turno), valor,
						JogoDaVelhaHelper.renderTabuleiro(this.tabuleiro), stopWatch.getTime(TimeUnit.MILLISECONDS));
			} else {
				// se nao é o turno do verdadeiro e seleciona um valor
				this.view.selecionaPosicao(this.turno.getValue());
			}

			// se ganhou sai do jogo e mostra que venceu
			if (JogoDaVelhaHelper.ganhou(this.turno, this.tabuleiro)) {
				this.view.mostraVenceu(getNomeJogador(this.turno));
				return;
			}

			// senao troca o turno
			trocaTurno();
		}
		// se acamar as casas e ninguem ganhar, da empate
		this.view.mostraEmpate();
	}

	private Integer valorOk(String v) {
		try {
			int valor = Integer.valueOf(v);
			// se o valor for entre 0 e 8 e não estiver sido selecionado ele está ok,
			// senao
			// volta nulo
			if (valor >= 0 && valor <= 8 && Valor.VAZIO.equals(this.tabuleiro.get(valor))) {
				return valor;
			} else {
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private String getNomeJogador(Valor v) {
		if (v.equals(this.jogadorVerdadeiro)) {
			return "Verdadeiro";
		}

		return "CPU";
	}

	private void trocaTurno() {
		if (this.jogadorVerdadeiro.equals(this.turno)) {
			this.turno = this.jogadorCPU;
		} else {
			this.turno = this.jogadorVerdadeiro;
		}
	}

}
