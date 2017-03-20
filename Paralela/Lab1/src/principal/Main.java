package principal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {

		ExecutorService threadsLeitoras = Executors.newFixedThreadPool(4);
		ScheduledExecutorService threadsEscritoras = Executors.newScheduledThreadPool(1);
		Buffer sharedLocation = new ImplementacaoBuffer();
		try {
			for (int i = 0; i <= 120; i++) {
				threadsLeitoras.execute(new Leitor(sharedLocation));
				threadsEscritoras.scheduleAtFixedRate(new Escritor(sharedLocation), 0, 1, TimeUnit.MILLISECONDS);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		threadsLeitoras.shutdown();
		threadsEscritoras.shutdown();

		while (!threadsEscritoras.isTerminated()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("Thread: " + Thread.currentThread().getName() + " não finalizada.");
			}
		}
		System.exit(0);
	}
}
