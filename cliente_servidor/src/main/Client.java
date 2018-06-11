package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class Client extends Thread {

	private int port;

	private boolean cr = false;

	private ServerSocket welcomeSocket;

	public Client(int port) {
		this.port = port;
	}

	@Override
	public synchronized void start() {
		super.start();
		System.out.println("Cliente " + port + " iniciou");
	}

	@Override
	public void run() {
		try {
			this.createClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createClient() throws Exception {
		welcomeSocket = new ServerSocket(port);

		while (true) {
			sleep(200);
			if (!cr) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, 30);
				if (randomNum % 4 == 0) {
					cr = true;
					this.sendRequest();
					System.out.println("Cliente " + port + " quer entrar na região crítica!");
				}
			}

			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			String token = inFromClient.readLine();
			if (token.equals("token")) {
				System.out.println("Cliente " + port + " recebeu token!");
				sleep(200);
				this.criticalRegion();
				this.sendToken();
				cr = false;
			}
		}

	}

	private void sendRequest() throws Exception {
		Socket clientSocket = new Socket("localhost", 99);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(String.valueOf(port));
		clientSocket.close();
	}

	private void sendToken() throws Exception {
		Socket clientSocket = new Socket("localhost", 99);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes("token");
		clientSocket.close();
	}

	private void criticalRegion() {
		System.out.println("Cliente " + port + " está na região crítica");
	}
}
