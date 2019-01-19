import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
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

	//Declaring class Variables 

	JPanel gameAreaPanel;

	Image map, mango1, popWindow, menuPic;
	Image careerPlaceHolder, housePlaceHolder;
	Image spinPic;
	Player player1;
	ImageIcon icon;

	ArrayList<Player> players = new ArrayList<Player>();
	static ArrayList<ActionTile> path;
	static ArrayList<Career> collegeCareers;
	static ArrayList<Career> normalCareers;
	static ArrayList<Property> properties;

	//variables for spinner
	static boolean finished = false;
	boolean spinText;
	static int rotate;
	static int rollNum = -1;
	static Random rand = new Random();
	static JLabel rollText;
	static Clock c = new Clock();
	JButton close, spin, menu;
	static JButton myCareer, myHouse;

	JFrame careerFrame, houseFrame;
	static JLabel careerLabel;
	static JLabel houseLabel;

	Polygon p;

	static double screenX = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	static double screenY = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	double scale = (screenX * screenY) / (1920 * 1200.0);

	static double scaleX = screenX / 1920.0;
	static double scaleY = screenY / 1200.0;

	// Constructor - this runs first
	Game() {
		super("My Game");
		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize((int) screenX, (int) screenY);
		//setting icon
		icon = new ImageIcon("graphics/icon.png");
		this.setIconImage(icon.getImage());
		System.out.println(screenX + " " + screenY);

		//declaring initial arraylists

		//adding location and function of every tile to scale
		path = new ArrayList<ActionTile>();
		path.add(new MoneyTile((int) (scaleX * 90), (int) (scaleY * 444), null, 0));
		path.add(new ChoiceTile((int) (scaleX * 174), (int) (scaleY * 446), "Would You Like to Go to College", 19));

		//good
		path.add(new MoneyTile((int) (scaleX * 240), (int) (scaleY * 452), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 303), (int) (scaleY * 431), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 321), (int) (scaleY * 371), "a", 0));
		path.add(new PayDayTile((int) (scaleX * 284), (int) (scaleY * 314)));

		//good
		path.add(new MoneyTile((int) (scaleX * 248), (int) (scaleY * 269), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 192), (int) (scaleY * 218), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 144), (int) (scaleY * 176), "a", 0));
		path.add(new PayDayTile((int) (scaleX * 129), (int) (scaleY * 104)));

		//good
		path.add(new MoneyTile((int) (scaleX * 188), (int) (scaleY * 72), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 255), (int) (scaleY * 85), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 318), (int) (scaleY * 110), "a", 0));
		path.add(new PayDayTile((int) (scaleX * 389), (int) (scaleY * 134)));

		//good
		path.add(new MoneyTile((int) (scaleX * 440), (int) (scaleY * 178), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 464), (int) (scaleY * 234), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 480), (int) (scaleY * 298), "a", 0));
		path.add(new PayDayTile((int) (scaleX * 496), (int) (scaleY * 364)));

		//advance to tile 37 from this path
		path.add(new MoneyTile((int) (scaleX * 513), (int) (scaleY * 433), "a", 0));

		//good
		path.add(new MoneyTile((int) (scaleX * 164), (int) (scaleY * 516), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 166), (int) (scaleY * 576), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 171), (int) (scaleY * 642), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 179), (int) (scaleY * 708), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 185), (int) (scaleY * 775), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 210), (int) (scaleY * 835), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 251), (int) (scaleY * 886), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 313), (int) (scaleY * 920), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 379), (int) (scaleY * 941), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 449), (int) (scaleY * 955), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 514), (int) (scaleY * 940), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 556), (int) (scaleY * 884), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 560), (int) (scaleY * 821), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 565), (int) (scaleY * 752), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 560), (int) (scaleY * 688), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 550), (int) (scaleY * 621), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 548), (int) (scaleY * 563), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 535), (int) (scaleY * 492), "a", 0));

		//start of new path(Tile 37)
		path.add(new MoneyTile((int) (scaleX * 575), (int) (scaleY * 419), "a", 0));
		path.add(new PayDayTile((int) (scaleX * 641), (int) (scaleY * 394)));
		path.add(new MoneyTile((int) (scaleX * 669), (int) (scaleY * 336), "a", 0));
		path.add(new MoneyTile((int) (scaleX * 680), (int) (scaleY * 270), "a", 0));
		path.add(new PayDayTile((int) (scaleX * 678), (int) (scaleY * 209)));
		path.add(new Tile(681, 140));

		System.out.println("num Tile " + path.size());

		//creating images for non college careers
		ImageIcon chef = new ImageIcon("graphics/careers/chef.png");
		ImageIcon flightAttendant = new ImageIcon("graphics/careers/flightattendant.png");
		ImageIcon uberDriver = new ImageIcon("graphics/careers/uberDriver.png");
		ImageIcon stripper = new ImageIcon("graphics/careers/stripper.png");
		ImageIcon roadWorker = new ImageIcon("graphics/careers/roadWorker.png");

		//getting images for college careers
		ImageIcon police = new ImageIcon("graphics/careers/police.png");
		ImageIcon math = new ImageIcon("graphics/careers/math.png");
		ImageIcon nurse = new ImageIcon("graphics/careers/nurse.png");
		ImageIcon softwareEngineer = new ImageIcon("graphics/careers/softwareEngineer.png");
		ImageIcon doctor = new ImageIcon("graphics/careers/doctor.png");

		//adding non college careers and setting their salary,name and picture
		normalCareers = new ArrayList<Career>();
		normalCareers.add(new Career("Police", 10000, police));
		normalCareers.add(new Career("Math", 10000, math));
		normalCareers.add(new Career("Nurse", 10000, nurse));
		normalCareers.add(new Career("Doctor", 10000, doctor));
		normalCareers.add(new Career("Software Enginner", 1000000, softwareEngineer));

		//adding college careers and setting their salary name and picture
		collegeCareers = new ArrayList<Career>();
		collegeCareers.add(new Career("Chef", 1000, chef));
		collegeCareers.add(new Career("FlightAttendant", 1000, flightAttendant));
		collegeCareers.add(new Career("UberDriver", 100000000, uberDriver));
		collegeCareers.add(new Career("Stripper", 1000, stripper));
		collegeCareers.add(new Career("RoadWorker", 100000000, roadWorker));

		//getting pictures for the properties in game
		ImageIcon mansion = new ImageIcon("graphics/homes/mansion.png");
		ImageIcon castle = new ImageIcon("graphics/homes/castle.png");
		ImageIcon condo = new ImageIcon("graphics/homes/condo.png");
		ImageIcon detached = new ImageIcon("graphics/homes/detached.png");
		ImageIcon hut = new ImageIcon("graphics/homes/hut.png");
		ImageIcon igloo = new ImageIcon("graphics/homes/igloo.png");
		ImageIcon bungalow = new ImageIcon("graphics/homes/bungalow.png");
		ImageIcon farm = new ImageIcon("graphics/homes/farm.png");

		//adding properties to its arraylist with its name, value and image
		properties = new ArrayList<Property>();
		properties.add(new Property("Mansion", 500, mansion));
		properties.add(new Property("Castle", 500, castle));
		properties.add(new Property("Condo", 500, condo));
		properties.add(new Property("Detached", 500, detached));
		properties.add(new Property("Farm", 500, farm));
		properties.add(new Property("Hut", 500, hut));
		properties.add(new Property("Igloo", 500, igloo));
		properties.add(new Property("bungalow", 500, bungalow));

		// resizing the properties to size based off screen 
		for (int i = 0; i < properties.size(); i++) {
			Property p = properties.get(i);
			ImageIcon pic = p.getImage();
			String name = p.getName().toLowerCase();

			Image image = pic.getImage();
			image = image.getScaledInstance((int) (250 * scaleX), (int) (400 * scaleY), java.awt.Image.SCALE_SMOOTH);
			p.setImage(new ImageIcon(image));
		}
		//getting picture of the map and scaling it 
		map = Toolkit.getDefaultToolkit().getImage("graphics/board.png");
		map = map.getScaledInstance((int) screenX, (int) screenY, Image.SCALE_DEFAULT);

		//getting picture of player and scaling it
		mango1 = Toolkit.getDefaultToolkit().getImage("graphics/mango1.png");
		mango1 = mango1.getScaledInstance((int) (45 * scaleX), (int) (45 * scaleY), Image.SCALE_DEFAULT);

		//getting picture of spinner and scaling it 
		spinPic = Toolkit.getDefaultToolkit().getImage("graphics/spin_button.png");
		spinPic = spinPic.getScaledInstance((int) (149 * scaleX), (int) (149 * scaleY), Image.SCALE_DEFAULT);

		//getting picture of menu bar and scalling it
		menuPic = Toolkit.getDefaultToolkit().getImage("graphics/menu.png");
		menuPic = menuPic.getScaledInstance((int) (154 * scaleX), (int) (130 * scaleY), Image.SCALE_DEFAULT);

		//getting popup window picture 
		popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");

		//getting picture for initial career holder and scaling it
		careerPlaceHolder = Toolkit.getDefaultToolkit().getImage("graphics/careers/career.png");
		careerPlaceHolder = careerPlaceHolder.getScaledInstance((int) (114 * scaleX), (int) (184 * scaleY),
				Image.SCALE_DEFAULT);

		//getting picture for initial career holder and scaling it
		housePlaceHolder = Toolkit.getDefaultToolkit().getImage("graphics/homes/home.png");
		housePlaceHolder = housePlaceHolder.getScaledInstance((int) (114 * scaleX), (int) (184 * scaleY),
				Image.SCALE_DEFAULT);

		//declaring new players and setting their name and money 
		player1 = new Player("Eric", 1200);
		players.add(player1);

		//creating the game area panel
		gameAreaPanel = new GameAreaPanel();
		gameAreaPanel.setLayout(new BoxLayout(gameAreaPanel, BoxLayout.Y_AXIS));

		//creating the interactive bottom panel on the screen
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.setOpaque(false);
		gameAreaPanel.add(Box.createVerticalStrut((int) (1012 * scaleY)));
		gameAreaPanel.add(bottomPanel);

		this.setContentPane(gameAreaPanel);
		this.requestFocusInWindow();
		this.setUndecorated(true);

		//adding mouse listener for interactive features
		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);

		//creating polygon for spinner 
		p = new Polygon();
		p.addPoint((int) (scaleX * 1514), (int) (scaleY * 300));
		p.addPoint((int) (scaleX * 1534), (int) (scaleY * 300));
		p.addPoint((int) (scaleX * 1524), (int) (scaleY * 340));
		Timer timer = new Timer(1, new SpinnerListener());
		timer.start();

		//creating spin button to spin the spinner  
		spin = new JButton(new ImageIcon(spinPic));
		spin.setBackground(null);
		spin.setContentAreaFilled(false);
		spin.setBorderPainted(false);
		spin.setFocusPainted(false);
		spin.addActionListener(new RollListener());
		spin.setVerticalAlignment(JButton.CENTER);

		//creating menu button for user
		menu = new JButton(new ImageIcon(menuPic));
		menu.setContentAreaFilled(false);
		menu.setFocusPainted(false);
		menu.setBorderPainted(false);
		menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// new Menu()

			}

		});

		//adding menu and spin to the bottom panel
		bottomPanel.add(menu);
		bottomPanel.add(spin);
		bottomPanel.add(Box.createHorizontalStrut((int) (scaleX * 205)));

		myHouse = new JButton(new ImageIcon(housePlaceHolder));
		myHouse.setContentAreaFilled(false);
		myHouse.setBorderPainted(false);
		myHouse.setFocusPainted(false);
		myHouse.addMouseListener(new MouseListener() {
			//JFrame houseFrame;
			JFrame frame;

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//	frame.setOpacity(0.5f);

				//creating a new frame in the center of the scree
				houseFrame = new JFrame();
				houseFrame.setSize(250, 400);
				houseFrame.setLocation((int) (Game.screenX / 2) - 125, ((int) (Game.screenY / 2) - 200));
				houseFrame.setUndecorated(true);
				houseFrame.setResizable(false);

				if (player1.getProperty().size() > 0) {
					//if the player owns a property then enlarge the photo 
					JLabel image = new JLabel(new ImageIcon(
							"graphics/homes/" + player1.getProperty().get(0).getName().toLowerCase() + ".png"));
					houseFrame.add(image);

					houseFrame.setVisible(true);
				}
				//houseFrame.setAlwaysOnTop(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//if not hovering over don't show the frame
				houseFrame.dispose();
				//frame.dispose();

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		//adding the my houses to the bottom panel
		bottomPanel.add(myHouse);
		bottomPanel.add(Box.createHorizontalStrut((int) (scaleX * 0)));

		//Jbutton in the bottom bar where player's carrer is shown
		myCareer = new JButton(new ImageIcon(careerPlaceHolder));
		myCareer.setContentAreaFilled(false);
		myCareer.setBorderPainted(false);
		myCareer.setFocusPainted(false);
		myCareer.addMouseListener(new MouseListener() {

			//JFrame frame;

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//				frame = new JFrame();
				//				frame.setSize((int)screenX, (int)screenY);
				//				frame.setUndecorated(true);
				//				frame.setResizable(false);
				//				frame.setVisible(true);
				//frame.setOpacity(0.5f);
				//if the player has been given a career and if the user hovers over it then enlarge the photo
				careerFrame = new JFrame();
				careerFrame.setSize(250, 400);
				careerFrame.setLocation((int) (Game.screenX / 2) - 125, ((int) (Game.screenY / 2) - 200));
				careerFrame.setUndecorated(true);
				careerFrame.setResizable(false);

				if (player1.getCareer() != null) {

					JLabel image = new JLabel(new ImageIcon(
							"graphics/careers/" + player1.getCareer().getCareerName().toLowerCase() + ".png"));
					careerFrame.add(image);
					careerFrame.setVisible(true);
				}
				careerFrame.setAlwaysOnTop(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				careerFrame.dispose();
				//frame.dispose();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		//adding my carrer to the bottom panel
		bottomPanel.add(myCareer);
		bottomPanel.add(Box.createRigidArea(new Dimension((int) (scaleX * 1000), 0)));

		close = new JButton("Close");
		bottomPanel.add(close);
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}

		});
		close.setVerticalAlignment(JButton.CENTER);

		this.setVisible(true);

		player1.move(1, path);
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

			//drawing background map
			g.drawImage(map, 0, 0, null);
			g.drawImage(mango1, path.get(player1.getTile()).getX() - 17, path.get(player1.getTile()).getY() - 17, null);

			// draw bottom game menu image
			g.setFont(new Font("langdon", Font.PLAIN, 100));
			g.drawString(Integer.toString(player1.getMoney()), 995, 1027);

			//drawing player Icon

			Image playerIcon = mango1;
			//playerIcon = playerIcon.getScaledInstance(2*(int) (45 * scaleX), 2*(int) (45 * scaleY), Image.SCALE_DEFAULT);
			g.drawImage(playerIcon, 1800, 980, null);

			// draw player name on screen
			g.setFont(new Font("Arial", Font.PLAIN, 35));
			g.drawString(player1.getName(), (int) (1489 * scaleX), (int) (1090 * scaleY));
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
		double dist, i = 0, vel, accel;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// if currently spinning
			if (running) {
//				c.update();
				// checks if dist is over
				if (vel > 0) {
//					double deltad = vel * c.getElapsedTime();
					rotate -= vel;
					// end dist
				} else {
					for (int j = 0; j < 5; j++) {
						int angle = rotate % 360;
						if (angle < 0) {
							angle += 360;
						}
						angle = 360 - angle;
						if (angle >= j * 72 && angle < (j + 1) * 72) {
							System.out.println(j + 1);
//							player1.move(j + 1, path);
						}
					}
					finished = true;
					running = false;
					spinText = false;
				}
				// delay vel each time
				// so the spinner slows down
				vel += accel;
				// if not running, start running
			} else if (spinText) {
				finished = false;
				dist = rand.nextInt(360) + 5000;
				running = true;
				vel = dist / 80.0;
				accel = -dist / 10000.0;
			}
		}

	}
	class RollListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			spinText = true;
		}
	}

}