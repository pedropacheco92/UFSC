package fuzzy;

import javax.swing.JFrame;

import net.sourceforge.jFuzzyLogic.FIS;

@SuppressWarnings("serial")
public class PacMan extends JFrame {

	public PacMan() {
		FIS fis = FIS.load("resources/tipper.fcl");
		add(new Board(fis));
		setTitle("Pacman");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(380, 420);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new PacMan();
	}
}