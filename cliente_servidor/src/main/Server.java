package main;

import static java.util.Objects.isNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
	
	private ServerSocket welcomeSocket;
	
	private List<Integer> clients = new ArrayList<>();
	
	private Integer currentClient;
	
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
		welcomeSocket = new ServerSocket(1);

		while (true) {
			this.checkClients();			
			
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			String clientMsg = inFromClient.readLine();
			
			if (clientMsg.equals("token")) {
				System.out.println("Servidor: " + this.currentClient + " saiu da região crítica!");				
				this.currentClient = null;
			} else {
				Integer client = Integer.parseInt(clientMsg);
				this.clients.add(client);				
				System.out.println("Servidor: " + client + " deseja acessar região crítica!");				
			}
			
		}
	}

	private void checkClients() throws IOException {
		if (!this.clients.isEmpty() && isNull(currentClient)) {
			Integer client = this.clients.remove(0);
			this.currentClient = client;
			this.allowClient(client);			
		}
	}
	
	private void allowClient(Integer client) throws IOException {
		Socket clientSocket = new Socket("localhost", client);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes("token");
		System.out.println("Servidor: enviado token para " + client);
		clientSocket.close();
	}
}
