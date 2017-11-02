package fuzzy;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame();

		// second object = new second();

		// f.add(object);

		f.setVisible(true);

		Maze component = new Maze();

		f.add(component);

		f.setVisible(true);

		f.setSize(500, 400);

		f.setLocationRelativeTo(null);

		f.setTitle("Maze Game");

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
