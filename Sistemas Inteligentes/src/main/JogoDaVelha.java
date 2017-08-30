package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class JogoDaVelha {

    private Map<Integer, Valor> tabuleiro = new HashMap<>();

    private Valor jogadorCPU, jogadorVerdadeiro, turno;

    private Scanner sc = new Scanner(System.in);

    public JogoDaVelha() {
	inicioJogo();
    }

    private void inicioJogo() {
	// popula o tabuleiro com "-" sendo este o simbolo da casa vazia
	IntStream.rangeClosed(0, 8).forEach(i -> this.tabuleiro.put(i, Valor.VAZIO));

	// seleciona o jogador
	while (true) {
	    System.out.print("Escolha um jogador: X ou O\n");

	    String valor = this.sc.nextLine().toUpperCase();
	    if (Valor.O.getValue().equals(valor)) {
		this.jogadorVerdadeiro = Valor.O;
		this.jogadorCPU = Valor.X;
		break;
	    }

	    if (Valor.X.getValue().equals(valor)) {
		this.jogadorVerdadeiro = Valor.X;
		this.jogadorCPU = Valor.O;
		break;
	    }

	    System.out.println("Valor inválido!");
	}

	System.out.println("jogador Verdadeiro é: " + this.jogadorVerdadeiro.getValue());
	System.out.println("jogador CPU é: " + this.jogadorCPU.getValue());
	System.out.println("\n########################################\n");
	System.out.println(JogoDaVelhaHelper.renderTabuleiro(this.tabuleiro));

	// seta o jogador verdadeiro como jogador inicial
	this.turno = this.jogadorVerdadeiro;

	// realiza o jogo
	jogo();

	// se quiser reiniciar o game
	String valor = this.sc.nextLine().toLowerCase();
	if ("retry".equals(valor)) {
	    inicioJogo();
	} else {
	    // jogo acaba fecha o scanner
	    this.sc.close();
	}
    }

    private void jogo() {
	while (!this.tabuleiro.values().stream().noneMatch(Valor.VAZIO::equals)) {
	    Integer valor;

	    if (this.jogadorCPU.equals(this.turno)) {
		valor = this.tabuleiro.entrySet().parallelStream().filter(entry -> Valor.VAZIO.equals(entry.getValue()))
			.findAny().get().getKey();
	    } else {
		while (true) {
		    System.out.println("Jogador " + this.turno.getValue() + ", escolha uma posição (0..8): ");
		    valor = valorOk();
		    if (valor != null) {
			break;
		    }
		}
	    }

	    this.tabuleiro.replace(valor, this.turno);

	    System.out.println("Jogador " + getNomeJogador(this.turno) + " jogou em: " + valor);
	    System.out.println("\n########################################\n");
	    System.out.println(JogoDaVelhaHelper.renderTabuleiro(this.tabuleiro));

	    if (JogoDaVelhaHelper.ganhou(this.turno, this.tabuleiro)) {
		System.out.println("Jogador " + getNomeJogador(this.turno) + " venceu!");
		return;
	    }

	    trocaTurno();
	}
	// empate
	System.out.println("Empate :/");
    }

    private Integer valorOk() {
	try {
	    String v = this.sc.nextLine();
	    int valor = Integer.valueOf(v);
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
