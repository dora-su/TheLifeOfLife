
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;

class GamePanel extends JFrame {

	// class variables
	JPanel gameAreaPanel;

	Image map, mango1, popWindow;
	Player player1;
	ArrayList<ActionTile> path;
	ArrayList<Career> collegeCareers;
	ArrayList<Career> normalCareers;

	static int rotate;
	static boolean finished = false;
	static int rollNum = -1;
	static Random rand = new Random();
	static JLabel rollText;
	JButton roll;

	Polygon p;

	static double screenX = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	static double screenY = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	double scale = (screenX * screenY) / (1920 * 1150.0);

	double scaleX = screenX / 1920.0;
	double scaleY = screenY / 1150.0;

	// Constructor - this runs first
	GamePanel() {
		super("My Game");

		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize((int) screenX, (int) screenY);
		// this.setSize(1920,1150);
		System.out.println(screenX + " " + screenY);

		// Set up the game panel (where we put our graphics)
		path = new ArrayList<ActionTile>();

		path.add(new MoneyTile((int) (scaleX * 90), (int) (scaleY * 444), null, 0));
		path.add(new MoneyTile((int) (scaleX * 165), (int) (scaleY * 447), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 232), (int) (scaleY * 449), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 313), (int) (scaleY * 433), "a", 0));
		
		
		collegeCareers = new ArrayList<Career>();
		collegeCareers.add(new Career("Lawyer", 10000));
		collegeCareers.add(new Career("Doctor", 10000));
		collegeCareers.add(new Career("Software Enginner", 1000000));
		
		normalCareers = new ArrayList<Career>();
		normalCareers.add(new Career("Police", 1000));
		normalCareers.add(new Career("Dancer", 1000));
		normalCareers.add(new Career("Stripper", 100000000));
		

		map = Toolkit.getDefaultToolkit().getImage("graphics/board.jpg");
		map = map.getScaledInstance((int) screenX, (int) screenY, Image.SCALE_DEFAULT);
		mango1 = Toolkit.getDefaultToolkit().getImage("graphics/mango1.png");

		mango1 = mango1.getScaledInstance((int) (45 * scale), (int) (45 * scale), Image.SCALE_DEFAULT);

		popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");

		player1 = new Player("Eric", 91, 91, 444);

		gameAreaPanel = new GameAreaPanel();
		this.add(gameAreaPanel);

		roll = new JButton("Spin");
		gameAreaPanel.add(roll);
		roll.addActionListener(new RollListener());

		this.requestFocusInWindow();
		this.setUndecorated(true);

		this.setVisible(true);

		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);

		p = new Polygon();
		p.addPoint((int) (scaleX * 1514), (int) (scaleY * 300));
		p.addPoint((int) (scaleX * 1534), (int) (scaleY * 300));
		p.addPoint((int) (scaleX * 1524), (int) (scaleY * 340));
		Timer timer = new Timer(1, new SpinnerListener());
		timer.start();
	} // End of Constructor

	/**
	 * --------- INNER CLASSES -------------
	 **/
	private class GameAreaPanel extends JPanel {
		boolean first = true;
		double dist = 75;

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			setDoubleBuffered(true);
			// System.out.println("HI");
			g.drawImage(map, 0, 0, null);
			g.drawImage(mango1, path.get(player1.getTile()).getX() - 17, path.get(player1.getTile()).getY() - 17, null);

			// draw bottom game menu image
			g.drawString(Integer.toString(player1.getMoney()), 1745, 1013);

			// spinner
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("graphics/tempSpinner.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			AffineTransform at = AffineTransform.getTranslateInstance(scaleX * 1362, scaleY * 323);

			at.rotate(Math.toRadians(rotate), image.getWidth() / 2, image.getHeight() / 2);
			((Graphics2D) g).drawImage(image, at, null);

			// adds pointer
			g.setColor(Color.WHITE);
			g.fillPolygon(p);
			try {
				// Thread.sleep(100);
			} catch (Exception e) {
			}

			repaint();
		}
	}

	private class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("X: " + e.getX() + " Y: " + e.getY());
			// Thread t = new Thread(new Runnable() {
			// public void run() {

			player1.move(1, path);

			// }
			// });
			// t.start();

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class SpinnerListener implements ActionListener {

		boolean running = false;
		int cycle, i = 0, delay = 0, increase, j;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// if currently spinning
			if (running) {
				// checks if cycle is over
				if (i <= cycle) {
					rotate -= increase;
					i += increase;

					// end cycle
				} else {
					for (j = 0; j < 5; j++) {
						int angle = rotate % 360;
						if (angle < 0) {
							angle += 360;
						}
						angle = 360 - angle;
						if (angle >= j * 72 && angle < (j + 1) * 72) {
//							Thread t = new Thread(new Runnable() {
//								public void run() {
									player1.move(j + 1, path);

//								}
//							});
//							t.start();
						}
					}
					finished = true;
					running = false;
					roll.setText("Spin");
				}

				// delay increase each time
				// so the spinner slows down
				try {
					Thread.sleep(delay);
					if (i % 20 == 0)
						increase--;
					increase = Math.max(1, increase);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// if not running, start running
			} else if (roll.getText().equals("Spinning")) {
				finished = false;
				cycle = rand.nextInt(360) + 2000;
				running = true;
				i = 0;
				delay = 5;
				increase = 20;
			}
		}

	}

	class RollListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			roll.setText("Spinning");
		}
	}

}