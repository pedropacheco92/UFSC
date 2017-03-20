package principal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {

		ExecutorService executar_leitor = Executors.newFixedThreadPool(4);
		ScheduledExecutorService executar_escritor = Executors.newScheduledThreadPool(120);
		Buffer sharedLocation = new ImplementacaoBuffer();
		try {
			for (int i = 0; i <= 120; i++) {
				executar_leitor.execute(new Leitor(sharedLocation));
			}
			executar_escritor.scheduleAtFixedRate(new Escritor(sharedLocation), 0, 1, TimeUnit.MILLISECONDS);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		executar_leitor.shutdown();
		executar_escritor.shutdown();
	}
}
