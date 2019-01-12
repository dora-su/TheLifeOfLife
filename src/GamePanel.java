import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

class GamePanel extends JFrame {

	// class variables
	JPanel gamePanel;

	Image map,mango1,popWindow;
	Player player1;
	ArrayList<ActionTile> path;
	
	// Constructor - this runs first
	GamePanel() {
		super("My Game");

		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1280, 720);

		// Set up the game panel (where we put our graphics)
		path = new ArrayList<ActionTile>();

		path.add(new ActionTile(26, 317, null, 0));
		path.add(new ActionTile(75, 317, "a", 0));
		path.add(new ActionTile(126, 317, "B", 0));
		path.add(new ActionTile(173, 299, "D", 20));
		path.add(new ActionTile(183, 256, "D", 0));

		map = Toolkit.getDefaultToolkit().getImage("graphics/boardSize.jpg");
		mango1 = Toolkit.getDefaultToolkit().getImage("graphics/mango1.png");
		popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");
		
		player1 = new Player("Eric", 5000, 26, 317);

		gamePanel = new GameAreaPanel();
		this.add(new GameAreaPanel());

		this.requestFocusInWindow();
		this.setUndecorated(true);

		this.setVisible(true);

		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);

		

		// TILE LOCATION
		// First Path
		/*
		 * X: 26 Y: 339 X: 74 Y: 339 X: 129 Y: 339 X: 178 Y: 331
		 */

	} // End of Constructor

	/** --------- INNER CLASSES ------------- **/
	private class GameAreaPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			setDoubleBuffered(true);
			// System.out.println("HI");
			g.drawImage(map, 0, 0, null);
			g.drawImage(mango1, path.get(player1.getTile()).getX() - 17, path.get(player1.getTile()).getY() - 17, null);

			repaint();

		}

	}

	public void move(Player player, int spin) {
		Scanner input = new Scanner(System.in);
		int i;
		for (i = 0; i < spin; i++) {

			if (path.get(player.getTile()).getIndex() == 0) {
				player.setTile(player.getTile() + 1);
				gamePanel.repaint();
			}

			if (path.get(player.getTile()).getIndex() > 0) {
				break;
			}

		}
		if (path.get(player.getTile()).getIndex() > 0) {
			popUp(path.get(player.getTile()).getMessage(), true, path.get(player.getTile()));
			System.out.println("choices");
		}else {
			popUp(path.get(player.getTile()).getMessage(), false, null);
			System.out.println("normal");
		}
	}

	boolean pop;

	private void popUp(String message, boolean choice, ActionTile tile) {
		if (pop) {
			return;
		}
		
		pop = true;
		JFrame popUp = new JFrame();

		popUp.addMouseListener(new MyMouseListener());
		popUp.setUndecorated(true);
		popUp.setResizable(true);
		popUp.setAlwaysOnTop(true);
		popUp.setLocation(640 - 200, 360 - 100);
		popUp.setSize(400, 250);
		popUp.setVisible(true);
		
		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				if(pop) {
					g.drawImage(popWindow,0, 0,null);
				}
				repaint();
			}
		};
		
		JLabel messageLabel = new JLabel(message);
		messageLabel.setForeground(Color.white);
		messageLabel.setFont(new Font("Arial",Font.PLAIN,30));
		popUp.setContentPane(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(0,63)));

		panel.add(messageLabel);
		panel.add(Box.createRigidArea(new Dimension(0,70)));
		JPanel options = new JPanel();
		options.setOpaque(false);;
		popUp.add(options);
		
		if (choice) {
			JButton option1 = new JButton("Option 1");
			JButton option2 = new JButton("Option 2");
			option1.setFocusPainted(false);
			option1.setFocusPainted(false);
			
			
			option1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					//go to certain index
					popUp.dispose();
					pop= false;
				}
				
			});
			
			option2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//go to new index
					popUp.dispose();
					pop= false;
					player1.setTile(tile.getIndex());
				}
			});
			
			options.add(option1);
			options.add(Box.createRigidArea(new Dimension(50,0)));
			options.add(option2);

		} else {
			//adding a close button after the message is displayed
			JButton close = new JButton("Close");
			close.setFocusPainted(false);
			close.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					popUp.dispose();
					pop = false;

				}
			});

			close.setAlignmentX(JButton.CENTER_ALIGNMENT);
			options.add(close);
		}
		
		

	}

	private class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("X: " + e.getX() + " Y: " + e.getY());

			move(player1, 2);
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