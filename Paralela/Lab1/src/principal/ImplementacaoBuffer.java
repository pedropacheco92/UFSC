package principal;

public class ImplementacaoBuffer implements Buffer {

	private boolean occupied = false;
	private int mensagem = 0;
	private int threadLeitor = 0;

	@Override
	public synchronized void escrever(int value) {
		while (this.occupied) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("A thread: " + Thread.currentThread().getName() + " não escreveu a mensagem.");
			}
		}
		this.mensagem = value;
		this.occupied = true;
		String nomethread = Thread.currentThread().getName();
		System.out.println(nomethread + "-" + "MENSAGEM ENVIADA: " + value);
		notifyAll();
	}

	@Override
	public synchronized int ler() {
		String nomethread = Thread.currentThread().getName();
		while (!this.occupied) {
			try {
				if (this.mensagem == 0) {
					wait();
				}
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}

		this.occupied = false;

		if (this.mensagem != 0) {
			if (this.threadLeitor < 4) {
				int msgRecebida = this.mensagem;
				this.threadLeitor++;
				System.out.println(nomethread + "-" + "Mensagem recebida: " + msgRecebida);
			}
			if (this.threadLeitor == 4) {
				this.occupied = false;
				this.threadLeitor = 0;
				notify();
			}
		} else {
			System.out.println("A thread: " + Thread.currentThread().getName() + " não leu a mensagem.");
		}

		this.mensagem = 0;
		System.out.println(nomethread + "-" + "Buffer alterado para 0");

		notify();

		return this.mensagem;

	}
}