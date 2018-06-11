package main;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class TokenRingStarter {

	public static void main(String[] args) throws Exception {
		int qtThreads = ThreadLocalRandom.current().nextInt(3, 10);

		List<TokenRingProcess> process = IntStream.range(1, qtThreads)
				.mapToObj(i -> new TokenRingProcess(i, i == qtThreads - 1 ? 1 : i + 1))
				.peek(TokenRingProcess::start)
				.collect(toList());

		// eleição: seleciona aleatório para começar
		int rnd = ThreadLocalRandom.current().nextInt(process.size());
		process.get(rnd).sendToken();
	}

}
