package fuzzy;

import java.util.Arrays;

import javax.swing.JFrame;

import net.sourceforge.jFuzzyLogic.FIS;

@SuppressWarnings("serial")
public class PacMan extends JFrame {

	public PacMan(boolean ghost, boolean fuzzy) {
		FIS fis = FIS.load("resources/tipper.fcl");
		add(new Board(fis, ghost, fuzzy));
		setTitle("Pacman");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(380, 420);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		boolean ghost = Arrays.stream(args).filter(s -> s.contains("g")).findAny().isPresent();
		boolean fuzzy = Arrays.stream(args).filter(s -> s.contains("f")).findAny().isPresent();
		new PacMan(ghost, fuzzy);
	}
}