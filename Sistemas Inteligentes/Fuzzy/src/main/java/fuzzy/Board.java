package fuzzy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	Dimension d;
	Font smallfont = new Font("Helvetica", Font.BOLD, 14);

	FontMetrics fmsmall, fmlarge;
	Image ii;
	Color dotcolor = new Color(192, 192, 0);
	Color mazecolor;

	boolean ingame = false;
	boolean dying = false;

	private static final int BLOCK_SIZE = 24;
	private static final int NROFBLOCKS = 15;
	private static final int SCREEN_SIZE = NROFBLOCKS * BLOCK_SIZE;
	private static final int PACMAN_ANIMDELAY = 2;
	private static final int PACMAN_ANIMCOUNT = 4;
	private static final int MAX_GHOSTS = 0;
	private static final int PACMAN_SPEED = 6;

	int pacanimcount = PACMAN_ANIMDELAY;
	int pacanimdir = 1;
	int pacmananimpos = 0;
	int nrofghosts = 0;
	int pacsleft, score;
	int deathcounter;
	int[] dx, dy;
	int[] ghostx, ghosty, ghostdx, ghostdy, ghostspeed;

	Image ghost;
	Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
	Image pacman3up, pacman3down, pacman3left, pacman3right;
	Image pacman4up, pacman4down, pacman4left, pacman4right;

	private int lastDiretion = 0;

	int pacmanx, pacmany, pacmandx, pacmandy;
	int reqdx, reqdy, viewdx, viewdy;

	private static final short LEVEL_DATA[] = { 19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22, 21, 0, 0, 0,
			17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 21, 0,
			0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20, 17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
			17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20, 25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16,
			20, 0, 21, 1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21, 1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16,
			20, 0, 21, 1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21, 1, 17, 16, 16, 16, 16, 20, 0, 17, 16,
			16, 16, 20, 0, 21, 1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21, 1, 17, 16, 16, 16, 16, 16, 16,
			16, 16, 16, 16, 20, 0, 21, 1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20, 9, 8, 8, 8, 8, 8, 8,
			8, 8, 8, 25, 24, 24, 24, 28 };

	private static final int VALID_SPEEDS[] = { 1, 2, 3, 4, 6, 8 };
	private static final int MAX_SPEED = 6;

	int currentspeed = 3;
	short[] screendata;
	Timer timer;

	public Board() {

		getImages();

		addKeyListener(new TAdapter());

		this.screendata = new short[NROFBLOCKS * NROFBLOCKS];
		this.mazecolor = new Color(5, 100, 5);
		setFocusable(true);

		this.d = new Dimension(400, 400);

		setBackground(Color.black);
		setDoubleBuffered(true);

		this.ghostx = new int[MAX_GHOSTS];
		this.ghostdx = new int[MAX_GHOSTS];
		this.ghosty = new int[MAX_GHOSTS];
		this.ghostdy = new int[MAX_GHOSTS];
		this.ghostspeed = new int[MAX_GHOSTS];
		this.dx = new int[4];
		this.dy = new int[4];
		this.timer = new Timer(40, this);
		this.timer.start();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	public void doAnim() {
		this.pacanimcount--;
		if (this.pacanimcount <= 0) {
			this.pacanimcount = PACMAN_ANIMDELAY;
			this.pacmananimpos = this.pacmananimpos + this.pacanimdir;
			if (this.pacmananimpos == PACMAN_ANIMCOUNT - 1 || this.pacmananimpos == 0) {
				this.pacanimdir = -this.pacanimdir;
			}
		}
	}

	public void playGame(Graphics2D g2d) {
		if (this.dying) {
			death();
		} else {
			movePacMan();
			drawPacMan(g2d);
			// moveGhosts(g2d);
			checkMaze();
		}
	}

	public void showIntroScreen(Graphics2D g2d) {

		g2d.setColor(new Color(0, 32, 48));
		g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
		g2d.setColor(Color.white);
		g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

		String s = "Press s to start.";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g2d.setColor(Color.white);
		g2d.setFont(small);
		g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
	}

	public void drawScore(Graphics2D g) {
		int i;
		String s;

		g.setFont(this.smallfont);
		g.setColor(new Color(96, 128, 255));
		s = "Score: " + this.score;
		g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
		for (i = 0; i < this.pacsleft; i++) {
			g.drawImage(this.pacman3left, i * 28 + 8, SCREEN_SIZE + 1, this);
		}
	}

	public void checkMaze() {
		short i = 0;
		boolean finished = true;

		while (i < NROFBLOCKS * NROFBLOCKS && finished) {
			if ((this.screendata[i] & 48) != 0) {
				finished = false;
			}
			i++;
		}

		if (finished) {
			this.score += 50;

			if (this.nrofghosts < MAX_GHOSTS) {
				this.nrofghosts++;
			}
			if (this.currentspeed < MAX_SPEED) {
				this.currentspeed++;
			}
			levelInit();
		}
	}

	public void death() {
		this.pacsleft--;
		if (this.pacsleft == 0) {
			this.ingame = false;
		}
		levelContinue();
	}

	public void moveGhosts(Graphics2D g2d) {
		short i;
		int pos;
		int count;

		for (i = 0; i < this.nrofghosts; i++) {
			if (this.ghostx[i] % BLOCK_SIZE == 0 && this.ghosty[i] % BLOCK_SIZE == 0) {
				pos = this.ghostx[i] / BLOCK_SIZE + NROFBLOCKS * (this.ghosty[i] / BLOCK_SIZE);

				count = 0;
				if ((this.screendata[pos] & 1) == 0 && this.ghostdx[i] != 1) {
					this.dx[count] = -1;
					this.dy[count] = 0;
					count++;
				}
				if ((this.screendata[pos] & 2) == 0 && this.ghostdy[i] != 1) {
					this.dx[count] = 0;
					this.dy[count] = -1;
					count++;
				}
				if ((this.screendata[pos] & 4) == 0 && this.ghostdx[i] != -1) {
					this.dx[count] = 1;
					this.dy[count] = 0;
					count++;
				}
				if ((this.screendata[pos] & 8) == 0 && this.ghostdy[i] != -1) {
					this.dx[count] = 0;
					this.dy[count] = 1;
					count++;
				}

				if (count == 0) {
					if ((this.screendata[pos] & 15) == 15) {
						this.ghostdx[i] = 0;
						this.ghostdy[i] = 0;
					} else {
						this.ghostdx[i] = -this.ghostdx[i];
						this.ghostdy[i] = -this.ghostdy[i];
					}
				} else {
					count = (int) (Math.random() * count);
					if (count > 3) {
						count = 3;
					}
					this.ghostdx[i] = this.dx[count];
					this.ghostdy[i] = this.dy[count];
				}

			}
			this.ghostx[i] = this.ghostx[i] + this.ghostdx[i] * this.ghostspeed[i];
			this.ghosty[i] = this.ghosty[i] + this.ghostdy[i] * this.ghostspeed[i];
			drawGhost(g2d, this.ghostx[i] + 1, this.ghosty[i] + 1);

			if (this.pacmanx > this.ghostx[i] - 12 && this.pacmanx < this.ghostx[i] + 12
					&& this.pacmany > this.ghosty[i] - 12 && this.pacmany < this.ghosty[i] + 12 && this.ingame) {

				this.dying = true;
				this.deathcounter = 64;

			}
		}
	}

	public void drawGhost(Graphics2D g2d, int x, int y) {
		g2d.drawImage(this.ghost, x, y, this);
	}

	public void movePacMan() {
		int pos;
		short ch;

		if (this.reqdx == -this.pacmandx && this.reqdy == -this.pacmandy) {
			this.pacmandx = this.reqdx;
			this.pacmandy = this.reqdy;
			this.viewdx = this.pacmandx;
			this.viewdy = this.pacmandy;
		}
		if (this.pacmanx % BLOCK_SIZE == 0 && this.pacmany % BLOCK_SIZE == 0) {
			pos = this.pacmanx / BLOCK_SIZE + NROFBLOCKS * (this.pacmany / BLOCK_SIZE);
			ch = this.screendata[pos];

			if ((ch & 16) != 0) {
				this.screendata[pos] = (short) (ch & 15);
				this.score++;
			}

			if (this.reqdx != 0 || this.reqdy != 0) {
				if (!(this.reqdx == -1 && this.reqdy == 0 && (ch & 1) != 0
						|| this.reqdx == 1 && this.reqdy == 0 && (ch & 4) != 0
						|| this.reqdx == 0 && this.reqdy == -1 && (ch & 2) != 0
						|| this.reqdx == 0 && this.reqdy == 1 && (ch & 8) != 0)) {
					this.pacmandx = this.reqdx;
					this.pacmandy = this.reqdy;
					this.viewdx = this.pacmandx;
					this.viewdy = this.pacmandy;
				}
			}

			// Check for standstill
			if (this.pacmandx == -1 && this.pacmandy == 0 && (ch & 1) != 0
					|| this.pacmandx == 1 && this.pacmandy == 0 && (ch & 4) != 0
					|| this.pacmandx == 0 && this.pacmandy == -1 && (ch & 2) != 0
					|| this.pacmandx == 0 && this.pacmandy == 1 && (ch & 8) != 0) {
				this.pacmandx = 0;
				this.pacmandy = 0;
			}
		}
		this.pacmanx = this.pacmanx + PACMAN_SPEED * this.pacmandx;
		this.pacmany = this.pacmany + PACMAN_SPEED * this.pacmandy;
	}

	public void drawPacMan(Graphics2D g2d) {
		if (this.viewdx == -1) {
			drawPacManLeft(g2d);
		} else if (this.viewdx == 1) {
			drawPacManRight(g2d);
		} else if (this.viewdy == -1) {
			drawPacManUp(g2d);
		} else {
			drawPacManDown(g2d);
		}
	}

	public void drawPacManUp(Graphics2D g2d) {
		switch (this.pacmananimpos) {
		case 1:
			g2d.drawImage(this.pacman2up, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		case 2:
			g2d.drawImage(this.pacman3up, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		case 3:
			g2d.drawImage(this.pacman4up, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		default:
			g2d.drawImage(this.pacman1, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		}
	}

	public void drawPacManDown(Graphics2D g2d) {
		switch (this.pacmananimpos) {
		case 1:
			g2d.drawImage(this.pacman2down, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		case 2:
			g2d.drawImage(this.pacman3down, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		case 3:
			g2d.drawImage(this.pacman4down, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		default:
			g2d.drawImage(this.pacman1, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		}
	}

	public void drawPacManLeft(Graphics2D g2d) {
		switch (this.pacmananimpos) {
		case 1:
			g2d.drawImage(this.pacman2left, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		case 2:
			g2d.drawImage(this.pacman3left, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		case 3:
			g2d.drawImage(this.pacman4left, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		default:
			g2d.drawImage(this.pacman1, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		}
	}

	public void drawPacManRight(Graphics2D g2d) {
		switch (this.pacmananimpos) {
		case 1:
			g2d.drawImage(this.pacman2right, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		case 2:
			g2d.drawImage(this.pacman3right, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		case 3:
			g2d.drawImage(this.pacman4right, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		default:
			g2d.drawImage(this.pacman1, this.pacmanx + 1, this.pacmany + 1, this);
			break;
		}
	}

	public void drawMaze(Graphics2D g2d) {
		short i = 0;
		int x, y;

		for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
			for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {
				g2d.setColor(this.mazecolor);
				g2d.setStroke(new BasicStroke(2));

				if ((this.screendata[i] & 1) != 0) // draws left
				{
					g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
				}
				if ((this.screendata[i] & 2) != 0) // draws top
				{
					g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
				}
				if ((this.screendata[i] & 4) != 0) // draws right
				{
					g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
				}
				if ((this.screendata[i] & 8) != 0) // draws bottom
				{
					g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
				}
				if ((this.screendata[i] & 16) != 0) // draws point
				{
					g2d.setColor(this.dotcolor);
					g2d.fillRect(x + 11, y + 11, 2, 2);
				}
				i++;
			}
		}
	}

	public void gameInit() {
		this.pacsleft = 3;
		this.score = 0;
		levelInit();
		this.nrofghosts = 6;
		this.currentspeed = 3;
	}

	public void levelInit() {
		int i;
		for (i = 0; i < NROFBLOCKS * NROFBLOCKS; i++) {
			this.screendata[i] = LEVEL_DATA[i];
		}

		levelContinue();
	}

	public void levelContinue() {
		short i;
		int dx2 = 1;
		int random;

		for (i = 0; i < this.nrofghosts; i++) {
			this.ghosty[i] = 4 * BLOCK_SIZE;
			this.ghostx[i] = 4 * BLOCK_SIZE;
			this.ghostdy[i] = 0;
			this.ghostdx[i] = dx2;
			dx2 = -dx2;
			random = (int) (Math.random() * (this.currentspeed + 1));
			if (random > this.currentspeed) {
				random = this.currentspeed;
			}
			this.ghostspeed[i] = VALID_SPEEDS[random];
		}

		this.pacmanx = 7 * BLOCK_SIZE;
		this.pacmany = 11 * BLOCK_SIZE;
		this.pacmandx = 0;
		this.pacmandy = 0;
		this.reqdx = 0;
		this.reqdy = 0;
		this.viewdx = -1;
		this.viewdy = 0;
		this.dying = false;
	}

	public void getImages() {
		this.ghost = new ImageIcon(Board.class.getResource("/res/ghost.png")).getImage();
		this.pacman1 = new ImageIcon(Board.class.getResource("/res/pacman.png")).getImage();
		this.pacman2up = new ImageIcon(Board.class.getResource("/res/up1.png")).getImage();
		this.pacman3up = new ImageIcon(Board.class.getResource("/res/up2.png")).getImage();
		this.pacman4up = new ImageIcon(Board.class.getResource("/res/up3.png")).getImage();
		this.pacman2down = new ImageIcon(Board.class.getResource("/res/down1.png")).getImage();
		this.pacman3down = new ImageIcon(Board.class.getResource("/res/down2.png")).getImage();
		this.pacman4down = new ImageIcon(Board.class.getResource("/res/down3.png")).getImage();
		this.pacman2left = new ImageIcon(Board.class.getResource("/res/left1.png")).getImage();
		this.pacman3left = new ImageIcon(Board.class.getResource("/res/left2.png")).getImage();
		this.pacman4left = new ImageIcon(Board.class.getResource("/res/left3.png")).getImage();
		this.pacman2right = new ImageIcon(Board.class.getResource("/res/right1.png")).getImage();
		this.pacman3right = new ImageIcon(Board.class.getResource("/res/right2.png")).getImage();
		this.pacman4right = new ImageIcon(Board.class.getResource("/res/right3.png")).getImage();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, this.d.width, this.d.height);

		drawMaze(g2d);
		drawScore(g2d);
		doAnim();
		if (this.ingame) {
			playGame(g2d);
		} else {
			showIntroScreen(g2d);
		}

		g.drawImage(this.ii, 5, 5, this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (Board.this.ingame) {
				switch (key) {
				case KeyEvent.VK_LEFT:
					Board.this.lastDiretion = 1;
					Board.this.reqdx = -1;
					Board.this.reqdy = 0;
					break;
				case KeyEvent.VK_RIGHT:
					Board.this.lastDiretion = 3;
					Board.this.reqdx = 1;
					Board.this.reqdy = 0;
					break;
				case KeyEvent.VK_UP:
					Board.this.lastDiretion = 2;
					Board.this.reqdx = 0;
					Board.this.reqdy = -1;
					break;
				case KeyEvent.VK_DOWN:
					Board.this.lastDiretion = 4;
					Board.this.reqdx = 0;
					Board.this.reqdy = 1;
					break;
				case KeyEvent.VK_ESCAPE:
					if (Board.this.timer.isRunning()) {
						Board.this.ingame = false;
					}
					break;
				case KeyEvent.VK_PAUSE:
					if (Board.this.timer.isRunning()) {
						Board.this.timer.stop();
					} else {
						Board.this.timer.start();
					}
					break;
				default:
					break;
				}
			} else {
				if (key == 's' || key == 'S') {
					Board.this.ingame = true;
					gameInit();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == Event.LEFT || key == Event.RIGHT || key == Event.UP || key == Event.DOWN) {
				// Board.this.reqdx = 0;
				// Board.this.reqdy = 0;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		int pos = this.pacmanx / BLOCK_SIZE + NROFBLOCKS * (this.pacmany / BLOCK_SIZE);
		// System.out.println(pos);
		// short ch = this.screendata[pos];
		// System.out.println(ch);
		System.out.println("dist front " + distanceWallFront());
		// System.out.println("dist left " + distanceWallLeft());

	}

	private int distanceWallFront() {
		int pos = this.pacmanx / BLOCK_SIZE + NROFBLOCKS * (this.pacmany / BLOCK_SIZE);
		short ch;

		if (this.lastDiretion == 1) {
			ch = this.screendata[pos];
			if ((ch & 1) == 1) {
				return 100;
			}
			pos--;
			ch = this.screendata[pos];
			if ((ch & 4) == 4) {
				return 100;
			}

		}
		if (this.lastDiretion == 3) {
			ch = this.screendata[pos];
			if ((ch & 4) == 4) {
				return 100;
			}
			pos++;
			ch = this.screendata[pos];
			if ((ch & 1) == 1) {
				return 100;
			}
		}
		if (this.lastDiretion == 4) {
			ch = this.screendata[pos];
			if ((ch & 8) == 8) {
				return 100;
			}

			pos = pos + NROFBLOCKS;
			ch = this.screendata[pos];
			if ((ch & 2) == 2) {
				return 100;
			}
		}
		if (this.lastDiretion == 2) {
			ch = this.screendata[pos];
			if ((ch & 2) == 2) {
				return 100;
			}
			pos = pos - NROFBLOCKS;
			ch = this.screendata[pos];
			if ((ch & 8) == 8) {
				return 100;
			}
		}

		return 0;
	}

	private int distanceWallLeft() {
		int pos = this.pacmanx / BLOCK_SIZE + NROFBLOCKS * (this.pacmany / BLOCK_SIZE);
		short ch;

		if (this.lastDiretion == 1) {
			ch = this.screendata[pos];
			if ((ch & 8) == 8) {
				return 100;
			}
			pos = pos + NROFBLOCKS;
			ch = this.screendata[pos];
			if ((ch & 2) == 2) {
				return 100;
			}

		}
		if (this.lastDiretion == 3) {
			ch = this.screendata[pos];
			if ((ch & 2) == 2) {
				return 100;
			}
			pos = pos - NROFBLOCKS;
			ch = this.screendata[pos];
			if ((ch & 8) == 8) {
				return 100;
			}
		}
		if (this.lastDiretion == 4) {
			ch = this.screendata[pos];
			if ((ch & 4) == 4) {
				return 100;
			}
			pos++;
			ch = this.screendata[pos];
			if ((ch & 1) == 1) {
				return 100;
			}
		}
		if (this.lastDiretion == 2) {
			ch = this.screendata[pos];
			if ((ch & 1) == 1) {
				return 100;
			}
			pos--;
			ch = this.screendata[pos];
			if ((ch & 4) == 4) {
				return 100;
			}
		}

		return 0;
	}

	private int distanceWallRight() {
		int pos = this.pacmanx / BLOCK_SIZE + NROFBLOCKS * (this.pacmany / BLOCK_SIZE);
		short ch;

		if (this.lastDiretion == 1) {
			ch = this.screendata[pos];
			if ((ch & 2) == 2) {
				return 100;
			}
			pos = pos - NROFBLOCKS;
			ch = this.screendata[pos];
			if ((ch & 8) == 8) {
				return 100;
			}

		}
		if (this.lastDiretion == 3) {
			ch = this.screendata[pos];
			if ((ch & 8) == 8) {
				return 100;
			}
			pos = pos + NROFBLOCKS;
			ch = this.screendata[pos];
			if ((ch & 2) == 2) {
				return 100;
			}
		}
		if (this.lastDiretion == 4) {
			ch = this.screendata[pos];
			if ((ch & 1) == 1) {
				return 100;
			}
			pos--;
			ch = this.screendata[pos];
			if ((ch & 4) == 4) {
				return 100;
			}
		}
		if (this.lastDiretion == 2) {
			ch = this.screendata[pos];
			if ((ch & 4) == 4) {
				return 100;
			}
			pos++;
			ch = this.screendata[pos];
			if ((ch & 1) == 1) {
				return 100;
			}
		}

		return 0;
	}

	private int distanceClosestMonster() {

		return 0;
	}

}
