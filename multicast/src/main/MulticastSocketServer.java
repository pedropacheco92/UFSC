package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MulticastSocketServer {

	final static String INET_ADDR = "224.0.0.3";

	final static int PORT = 8888;

	public static void main(String[] args) throws UnknownHostException, InterruptedException {

		InetAddress addr = InetAddress.getByName(INET_ADDR);

		try (DatagramSocket serverSocket = new DatagramSocket()) {

			for (int i = 0; i < 5; i++) {
				String msg = "Enviando msg numero: " + i;
				DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, addr, PORT);

				serverSocket.send(msgPacket);
				System.out.println("Server enviou pacote com a msg: " + msg);
				Thread.sleep(2000);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
