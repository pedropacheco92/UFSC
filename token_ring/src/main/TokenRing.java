package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TokenRing extends Thread {

	protected int port;

	protected int next_port;

	private ServerSocket welcomeSocket;

	public void sendToken(Token token) throws IOException {
		Socket clientSocket = new Socket("localhost", this.next_port);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outToServer.writeBytes("token" + '\n');
		String modifiedSentence = inFromServer.readLine();
		System.out.println("DO SERVIDOR: " + modifiedSentence);
		clientSocket.close();
	}

	public void reciveToken() throws IOException {
		String clientSentence;
		String capitalizedSentence;
		this.welcomeSocket = new ServerSocket(this.port);

		while (true) {
			Socket connectionSocket = this.welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			System.out.println("RECEBEU: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		}
	}
}
