package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class TokenRingProcess extends Thread {

	private int port;

	private int next_port;

	private ServerSocket welcomeSocket;
	
	private boolean wantsCriticalRegion = false;

	public TokenRingProcess(int port, int next_port) {
		this.port = port;
		this.next_port = next_port;
		print("iniciado");
	}

	@Override
	public void run() {
		try {
			this.reciveToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendToken() throws IOException, InterruptedException {
		sleep(1000);
		Socket clientSocket = new Socket("localhost", this.next_port);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes("token");
		print("enviado token para: " + this.next_port);
		clientSocket.close();
	}

	public void reciveToken() throws IOException, InterruptedException {
		String token;
		this.welcomeSocket = new ServerSocket(this.port);

		while (true) {
			if (!this.wantsCriticalRegion) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
				if (randomNum % 2 == 0) {
					this.wantsCriticalRegion = true;
				}				
			}
			
			Socket connectionSocket = this.welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			token = inFromClient.readLine();
			if (token.equals("token")) {
				print("recebeu token");
				sleep(1000);
				if (this.wantsCriticalRegion) {
					this.criticalRegion();
					this.wantsCriticalRegion = false;
				}
				sendToken();
			}
		}
	}

	private void print(String s) {
		System.out.println("Processo " + this.port + ": " + s);
	}

	private void criticalRegion() {
		print("Fazendo coisas na região crítica");
	}
}
