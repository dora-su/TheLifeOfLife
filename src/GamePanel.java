import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

class GamePanel extends JFrame {

	// class variables
	JPanel gamePanel;

	Image map;
	Image mango1;
	Player player1;
	ArrayList<ActionTile> path;

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
		this.setUndecorated(true);

		this.setVisible(true);

		map = Toolkit.getDefaultToolkit().getImage("graphics/boardSize.jpg");
		mango1 = Toolkit.getDefaultToolkit().getImage("graphics/mango1.png");

		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);

		player1 = new Player("Eric", 5000, 26, 317);

		// TILE LOCATION
		// First Path
		/*
		 * X: 26 Y: 339 X: 74 Y: 339 X: 129 Y: 339 X: 178 Y: 331
		 */
		
		path = new ArrayList<ActionTile>();

		path.add(new ActionTile(26,317,null,0));
		path.add(new ActionTile(75,317,"HI",0));
		path.add(new ActionTile(126,317,"B",0));
		path.add(new ActionTile(173,299,"D",0));
		path.add(new ActionTile(183,256,"D",0));
		


		
		
		 

	} // End of Constructor

	/** --------- INNER CLASSES ------------- **/
	private class GameAreaPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			setDoubleBuffered(true);
			// System.out.println("HI");
			g.drawImage(map, 0, 0, null, this);
			g.drawImage(mango1, path.get(player1.getTile()).getX() - 17, path.get(player1.getTile()).getY() - 17, null, this);

			repaint();

		}

	}

	public void move(Player player, int spin) {
		int i;
		for (i = 0; i < spin; i++) {
			if (path.get(player.getTile() + i).getIndex() > 0) {
				//  choice panel
				//  spin
				//move();
				return;
			}else {
				player.setTile(player.getTile() + 1);
				gamePanel.repaint();

				
			}
		}
	}

	private class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("X: " + e.getX() + " Y: " + e.getY());

			move(player1,3);
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