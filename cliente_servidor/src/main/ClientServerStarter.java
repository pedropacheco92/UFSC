package main;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ClientServerStarter {

	public static void main(String[] args) {
		Server server = new Server();

		server.start();

		int qtThreads = ThreadLocalRandom.current().nextInt(3, 10);

		IntStream.range(1, qtThreads).mapToObj(Client::new).forEach(Client::start);
	}

}
