package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class Client extends Thread {
	
	private int port;
	
	private boolean wantsCriticalRegion = false;

	private ServerSocket socket;
	
	public Client(int port) {
		this.port = port;
	}
	
	@Override
	public synchronized void start() {
		super.start();
		System.out.println("Cliente " + port + " iniciou");
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		while(true) {
			int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
			if (randomNum % 2 == 0 && !wantsCriticalRegion) {
				System.out.println("Cliente " + this.port + " quer entrar na região crítica!");
				Socket clientSocket = new Socket("localhost", 1);
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				outToServer.writeBytes(String.valueOf(port));
				clientSocket.close();
				this.wantsCriticalRegion = true;
			}
			
			if (this.wantsCriticalRegion) {
				socket = new ServerSocket(this.port);
				Socket connectionSocket = socket.accept();				
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				
				String clientMsg = inFromClient.readLine();
				System.out.println(clientMsg + "aaaaaaaaa");
				if (clientMsg.equals("token")) {
					critialRegion();
					DataOutputStream outToServer = new DataOutputStream(connectionSocket.getOutputStream());
					outToServer.writeBytes("token");
					connectionSocket.close();
					wantsCriticalRegion = false;
				}
				socket.close();
			}
			
			
			
		}		
	}
	
	private void critialRegion() {
		System.out.println("Cliente " + this.port + " está na região crítica");
	}
}
