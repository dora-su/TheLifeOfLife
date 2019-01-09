import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Image;

class GamePanel extends JFrame {

	// class variables
	JPanel gamePanel;

	Image map;
	Image mango1; 
	Player player1; 

	// Constructor - this runs first
	GamePanel() {
		super("My Game");

		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1280, 720);

		// Set up the game panel (where we put our graphics)
		gamePanel = new GameAreaPanel();
		this.add(new GameAreaPanel());

		this.requestFocusInWindow();
		// this.setUndecorated(true);

		this.setVisible(true);

		map = Toolkit.getDefaultToolkit().getImage("graphics/boardSize.jpg");
		mango1 = Toolkit.getDefaultToolkit().getImage("graphics/mango1.png");

		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);
		
		player1 = new Player("Eric",5000, 26,339);

		// TILE LOCATION
		// First Path
		/*
		 * X: 26  Y: 339 
		 * X: 74  Y: 339 
		 * X: 129 Y: 339 
		 * X: 178 Y: 331
		 */

	} // End of Constructor

	/** --------- INNER CLASSES ------------- **/
	private class GameAreaPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			setDoubleBuffered(true);
			// System.out.println("HI");
			g.drawImage(map, 0, 0, null, this);
			g.drawImage(mango1, 26, 339, null, this);
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

}