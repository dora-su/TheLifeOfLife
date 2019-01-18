import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
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

class Game extends JFrame {

	// class variables
	JPanel gameAreaPanel;

	Image map, mango1, popWindow;
	Player player1;
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<ActionTile> path;
	static ArrayList<Career> collegeCareers;
	static ArrayList<Career> normalCareers;
	static ArrayList<Property> properties;

	static int rotate;
	static boolean finished = false;
	static int rollNum = -1;
	static Random rand = new Random();
	static JLabel rollText;
	static Clock c = new Clock();
	JButton close, spin;

	Polygon p;

	static double screenX = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	static double screenY = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	double scale = (screenX * screenY) / (1920 * 1200.0);

	static double scaleX = screenX / 1920.0;
	static double scaleY = screenY / 1200.0;

	private ImageIcon icon;

	// Constructor - this runs first
	// Constructor - this runs first
	Game() {
		super("My Game");

		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize((int) screenX, (int) screenY);
		icon = new ImageIcon("graphics/icon.png");
		this.setIconImage(icon.getImage());
		System.out.println(screenX + " " + screenY);

		// Set up the game panel (where we put our graphics)
		path = new ArrayList<ActionTile>();
		properties = new ArrayList<Property>();

		path.add(new MoneyTile((int) (scaleX * 90), (int) (scaleY * 444), null, 0));
		path.add(new MoneyTile((int) (scaleX * 165), (int) (scaleY * 447), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 232), (int) (scaleY * 449), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 313), (int) (scaleY * 433), "a", 0));

		ImageIcon chef = new ImageIcon("graphics/careers/chef.png");
		ImageIcon flightAttendant = new ImageIcon("graphics/careers/flightattendant.png");
		ImageIcon uberDriver = new ImageIcon("graphics/careers/uberDriver.png");
		ImageIcon stripper = new ImageIcon("graphics/careers/stripper.png");
		ImageIcon roadWorker = new ImageIcon("graphics/careers/roadWorker.png");

		ImageIcon police = new ImageIcon("graphics/careers/police.png");
		ImageIcon math = new ImageIcon("graphics/careers/math.png");
		ImageIcon nurse = new ImageIcon("graphics/careers/nurse.png");
		ImageIcon softwareEngineer = new ImageIcon("graphics/careers/softwareEngineer.png");
		ImageIcon doctor = new ImageIcon("graphics/careers/doctor.png");

		normalCareers = new ArrayList<Career>();
		normalCareers.add(new Career("Police", 10000, police));
		normalCareers.add(new Career("Math", 10000, math));
		normalCareers.add(new Career("Nurse", 10000, nurse));
		normalCareers.add(new Career("Doctor", 10000, doctor));
		normalCareers.add(new Career("Software Enginner", 1000000, softwareEngineer));

		collegeCareers = new ArrayList<Career>();
		collegeCareers.add(new Career("Chef", 1000, chef));
		collegeCareers.add(new Career("FlightAttendant", 1000, flightAttendant));
		collegeCareers.add(new Career("UberDriver", 100000000, uberDriver));
		collegeCareers.add(new Career("Stripper", 1000, stripper));
		collegeCareers.add(new Career("RoadWorker", 100000000, roadWorker));

		ImageIcon mansion = new ImageIcon("graphics/homes/mansion.png");
		ImageIcon castle = new ImageIcon("graphics/homes/castle.png");
		ImageIcon condo = new ImageIcon("graphics/homes/condo.png");
		ImageIcon detached = new ImageIcon("graphics/homes/detached.png");
		ImageIcon hut = new ImageIcon("graphics/homes/hut.png");
		ImageIcon igloo = new ImageIcon("graphics/homes/igloo.png");
		ImageIcon bungalow = new ImageIcon("graphics/homes/bungalow.png");
		ImageIcon farm = new ImageIcon("graphics/homes/farm.png");
		
		properties.add(new Property("Mansion", 500, mansion));
		properties.add(new Property("Castle", 500, castle));
		properties.add(new Property("Condo", 500, condo));
		properties.add(new Property("Detached", 500, detached));
		properties.add(new Property("Farm", 500, farm));
		properties.add(new Property("Hut", 500, hut));
		properties.add(new Property("Igloo", 500, igloo));
		properties.add(new Property("bungalow", 500, bungalow));
		
		//resizing the properties
		for(int i = 0; i<properties.size(); i++) {
			Property p = properties.get(i);
			ImageIcon pic = p.getImage();
			String name = p.getName().toLowerCase();
			
			Image image = pic.getImage(); 
			image = image.getScaledInstance((int)(250 * scaleX), (int)(400 * scaleY), java.awt.Image.SCALE_SMOOTH); 
			p.setImage(new ImageIcon(image));
		}

		map = Toolkit.getDefaultToolkit().getImage("graphics/board.png");
		map = map.getScaledInstance((int) screenX, (int) screenY, Image.SCALE_DEFAULT);
		mango1 = Toolkit.getDefaultToolkit().getImage("graphics/mango1.png");

		mango1 = mango1.getScaledInstance((int) (45 * scaleX), (int) (45 * scaleY), Image.SCALE_DEFAULT);

		popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");

		player1 = new Player("Eric", 1200, 91, 444);
		players.add(player1);
		gameAreaPanel = new GameAreaPanel();
		gameAreaPanel.setLayout(new BoxLayout(gameAreaPanel, BoxLayout.Y_AXIS));

		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.setOpaque(false);
		gameAreaPanel.add(Box.createVerticalStrut((int) (1018 * scaleY)));
		gameAreaPanel.add(p1);
		this.setContentPane(gameAreaPanel);

		close = new JButton("Close");
		p1.add(close);
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}

		});

		this.requestFocusInWindow();
		this.setUndecorated(true);

		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);

		p = new Polygon();
		p.addPoint((int) (scaleX * 1514), (int) (scaleY * 300));
		p.addPoint((int) (scaleX * 1534), (int) (scaleY * 300));
		p.addPoint((int) (scaleX * 1524), (int) (scaleY * 340));
		Timer timer = new Timer(1, new SpinnerListener());
		timer.start();

		spin = new JButton("spin");
		spin.addActionListener(new RollListener());

		//button.setAlignmentY(JButton.CENTER_ALIGNMENT);
		spin.setVerticalAlignment(JButton.CENTER);
		close.setVerticalAlignment(JButton.CENTER);
		p1.add(Box.createHorizontalStrut((int) (scaleX * 1750)));
		p1.add(spin);
		revalidate();
		this.setVisible(true);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				loop();
			}
		});
		// t.start();
	} // End of Constructor

	private void loop() {
		while (true) {
			for (Player p : players) {
				if (p.getTile() != p.getDestination()) {
					p.move(p.getDestination() - p.getTile(), path);
				}
			}
		}
	}

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
			g.setFont(new Font("Arial",Font.PLAIN, 100));
			g.drawString(Integer.toString(player1.getMoney()), 995, 1027);

			// spinner
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("graphics/tempSpinner.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			AffineTransform at = AffineTransform.getTranslateInstance(scaleX * 1362, scaleY * 323);
			at.scale(scaleX, scaleY);
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

			//player1.move(1, path);

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
		int j;
		double dist, i = 0, vel, accel;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// if currently spinning
			if (running) {
				c.update();
				// checks if dist is over
				if (vel > 0) {
					double deltad = vel * c.getElapsedTime();
					rotate -= deltad;
					i += deltad;

					// end dist
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
							//player1.move(j + 1, path);
							player1.move(3, path);
							//player1.(player1.getTile() + 2);
							//								}
							//							});
							//							t.start();
						}
					}
					finished = true;
					running = false;
					spin.setText("Spin");
				}

				// delay vel each time
				// so the spinner slows down
				vel += accel * c.getElapsedTime();
				// if not running, start running
			} else if (spin.getText().equals("Spinning")) {
				finished = false;
				dist = rand.nextInt(360) + 5000;
				running = true;
				i = 0;
				vel = dist / 2.0;
				accel = -dist / 8.0;
			}
		}

	}

	class RollListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			spin.setText("Spinning");
		}
	}

}