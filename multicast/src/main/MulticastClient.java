package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class MulticastClient extends Thread {

	final static String INET_ADDR = "224.0.0.3";

	final static int PORT = 8888;

	private int id;

	public MulticastClient(int id) {
		this.id = id;
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

		InetAddress address = InetAddress.getByName(INET_ADDR);

		byte[] buf = new byte[256];

		DatagramSocket serverSocket = new DatagramSocket();

		Thread t = new Thread(() -> {
			MulticastSocket clientSocket;
			try {
				clientSocket = new MulticastSocket(PORT);
				clientSocket.joinGroup(address);
				while (true) {
					DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
					clientSocket.receive(msgPacket);
					String msg = new String(buf, 0, buf.length);

					System.out.println(id + " recebeu msg: " + msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		t.start();
		Thread.sleep(1000);

		while (true) {
			Thread.sleep(2000);
			int randomNum = ThreadLocalRandom.current().nextInt(1, 30);
			if (randomNum % 6 == 0) {
				String msg = Long.toString(new Date().getTime());
				DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, PORT);

				serverSocket.send(msgPacket);
				System.out.println(id + " quer regição crítica com tempo: " + msg);
			}
		}
	}
}