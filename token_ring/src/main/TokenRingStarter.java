package main;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TokenRingStarter {

	public static void main(String[] args) throws Exception {
		int qtThreads = ThreadLocalRandom.current().nextInt(3, 10);
		
		List<TokenRingProcess> process = IntStream.range(1, qtThreads)
				.mapToObj(i -> new TokenRingProcess(i, i == (qtThreads - 1) ? 1 : i + 1))
				.peek(TokenRingProcess::start)
				.collect(Collectors.toList());


		// elei��o: seleciona aleat�rio para come�ar
		  int rnd = ThreadLocalRandom.current().nextInt(process.size());
		  process.get(rnd).sendToken();
	}

}
