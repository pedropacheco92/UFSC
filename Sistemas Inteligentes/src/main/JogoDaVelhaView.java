package main;

import java.util.Scanner;
import java.util.function.Consumer;

public class JogoDaVelhaView {

    private Consumer<Valor> valorListener;

    private Consumer<String> posicaoListener;

    private Scanner sc = new Scanner(System.in);

    public JogoDaVelhaView(Consumer<Valor> valorListener, Consumer<String> posicaoListener) {
	this.valorListener = valorListener;
	this.posicaoListener = posicaoListener;
    }

    public void selecionaJogador() {
	while (true) {
	    System.out.print("Escolha um jogador: X ou O\n");

	    String valor = this.sc.nextLine().toUpperCase();
	    if (Valor.O.getValue().equals(valor)) {
		this.valorListener.accept(Valor.O);
		return;
	    }

	    if (Valor.X.getValue().equals(valor)) {
		this.valorListener.accept(Valor.X);
		return;
	    }

	    System.out.println("Valor inválido!");
	}
    }

    public void mostraJogadores(String valorVerdadeiro, String valorCPU, String tabuleiro) {
	System.out.println("jogador Verdadeiro é: " + valorVerdadeiro);
	System.out.println("jogador CPU é: " + valorCPU);
	System.out.println("\n########################################\n");
	System.out.println(tabuleiro);

    }

    public void selecionaPosicao(String jogador) {
	System.out.println("Jogador " + jogador + ", escolha uma posição (0..8): ");
	this.posicaoListener.accept(this.sc.nextLine());
    }

    public void mostraJogada(String nomeJogador, Integer v, String tabuleiro) {
	System.out.println("\n########################################\n");
	System.out.println("Jogador " + nomeJogador + " jogou em: " + v + "\n");
	System.out.println(tabuleiro);
    }

    public void mostraVenceu(String nomeJogador) {
	System.out.println("Jogador " + nomeJogador + " venceu!");
    }

    public void mostraEmpate() {
	System.out.println("Empate :/");
    }

}
