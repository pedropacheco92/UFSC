package main;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Server extends Thread {

	private ServerSocket welcomeSocket;

	private Integer currentClient;

	private Map<Integer, DataOutputStream> clients = new LinkedHashMap<>();

	@Override
	public synchronized void start() {
		super.start();
		System.out.println("Servidor iniciou");
	}

	@Override
	public void run() {
		try {
			this.createServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createServer() throws Exception {
		welcomeSocket = new ServerSocket(99);

		while (true) {
			this.checkClients();
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			String clientSentence = inFromClient.readLine();
			if (clientSentence.equals("token") && nonNull(currentClient)) {
				System.out.println("Servidor: " + currentClient + " saiu da região crítica!");
				currentClient = null;
			} else {
				try {
					Integer client = Integer.parseInt(clientSentence);
					currentClient = client;
					System.out.println("Servidor: " + client + " deseja acessar região crítica!");
					this.sendToken(client);
				} catch (NumberFormatException e) {
					System.out.println("Mensagem desconhecida: " + clientSentence);
				}
			}
		}

	}

	private void sendToken(int port) throws Exception {
		Socket clientSocket = new Socket("localhost", port);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes("token");
		clientSocket.close();
	}

	private void checkClients() throws IOException {
		if (isNull(currentClient)) {
			if (clients.entrySet().iterator().hasNext()) {
				Entry<Integer, DataOutputStream> entry = clients.entrySet().iterator().next();
				currentClient = entry.getKey();
				entry.getValue().writeBytes("token");
				System.out.println("Servidor: enviado token para " + currentClient);
			}
		}
	}

}
