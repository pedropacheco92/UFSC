package main;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Multicast {

	public static void main(String[] args) throws Exception {
		int qtThreads = ThreadLocalRandom.current().nextInt(3, 10);

		IntStream.range(1, qtThreads).mapToObj(MulticastClient::new).forEach(MulticastClient::start);
	}

}
