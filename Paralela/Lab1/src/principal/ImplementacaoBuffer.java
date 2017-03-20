package principal;

public class ImplementacaoBuffer implements Buffer {

	private int buffer = 0;

	private boolean occupied = false;

	@Override
	public synchronized void escrever(int value) {
		while (this.occupied) {
			try {
				wait();
			}

			catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}

		this.buffer = value;

		this.occupied = true;

		System.out.print("\n" + "ESCREVEU " + this.buffer + "\n");

		notifyAll();
	}

	@Override
	public synchronized int ler() {
		while (!this.occupied) {
			try {
				if (this.buffer == 0) {
					wait();
				} else {
					notifyAll();
				}
			}

			catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}

		this.occupied = false;

		if (this.buffer != 0) {
			System.out.print("\n" + "LEU " + this.buffer + "\n");
		}

		this.buffer = 0;

		System.out.print("\n" + "LEITOR ALTEROU BUFFER PARA : " + this.buffer + "\n");

		notify();

		return this.buffer;
	}

}