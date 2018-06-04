package main;

import java.io.IOException;

public class TokenRingProcessA extends TokenRing {

	public static void main(String argv[]) throws Exception {
		new TokenRingProcessA().start();
	}

	public TokenRingProcessA() throws IOException {
		this.port = 6789;
		this.next_port = 6790;
		this.reciveToken();
	}

}
