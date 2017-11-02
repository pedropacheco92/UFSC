package fuzzy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class Maze extends JComponent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle rim2 = new Rectangle(650, 0, 5, 900);
		g2.fill(rim2);
		g2.setColor(Color.BLACK);
		g2.draw(rim2);
		g2.fill(rim2);
	}

}
