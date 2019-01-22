
/**
 * Name: PopUp.java
 * Version: 2.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 7, 2019
 */

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PopUp {
	private Font font;
	private ImageIcon icon;
	private JFrame popUp;

	/**
	 * Constructor
	 *
	 * @param message the popup message
	 * @param tile    the popup's tile
	 * @param player1 the player
	 */
	PopUp(String message, ActionTile tile, Player player1) {
		Image popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");

		popUp = new JFrame();
		popUp.setUndecorated(true);
		popUp.setResizable(true);
		popUp.setAlwaysOnTop(true);
		Game.gameFrame.setEnabled(false);
		popUp.setLocation((int) (Game.screenX / 2) - 200, ((int) (Game.screenY / 2) - 159));
		popUp.setSize(400, 317);

		//set icon image
		icon = new ImageIcon("graphics/icon.png");
		popUp.setIconImage(icon.getImage());

		popUp.setVisible(true);

		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				g.drawImage(popWindow, 0, 0, null);

				repaint();
			}
		};

		//get the font
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("graphics/fonts/josefin.ttf"));
		} catch (FontFormatException e) {
			System.out.println("Font format is incorrect.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Font file not found.");
			e.printStackTrace();
		}

		//set the message font size
		font = font.deriveFont(Font.PLAIN,28);

		JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html",
				SwingConstants.CENTER);
		messageLabel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		messageLabel.setSize(400, 250);
		//JLabel messageLabel = new JLabel(message);
		messageLabel.setSize(messageLabel.getPreferredSize());
		//messageLabel.setSize(400,250);
		messageLabel.setForeground(Color.black);
		messageLabel.setFont(font);
		popUp.setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(0, 100)));

		panel.add(messageLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 50)));
		JPanel options = new JPanel();
		options.setOpaque(false);

		popUp.add(options);

		if (tile instanceof ChoiceTile) {
			JButton option1 = new JButton("Yes");
			JButton option2 = new JButton("No");
			option1.setFocusPainted(false);
			option1.setFocusPainted(false);

			option1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(player1.getTile() == 89) {
						player1.setOwnStartUp(true);
						player1.setCareer(Game.startUp);
						Image pic = Toolkit.getDefaultToolkit().getImage("graphics/careers/startup.png");
						pic = pic.getScaledInstance((int) (114 * Game.scaleX), (int) (184 * Game.scaleY), java.awt.Image.SCALE_SMOOTH);
						Game.myCareer.setIcon(new ImageIcon(pic));
					}
					// go to certain index
					Game.gameFrame.setEnabled(true);
					popUp.dispose();
					System.out.println(player1.getName() + " set to " + ((ChoiceTile) tile).getIndex());
					player1.c.output.println(player1.getName());
					player1.c.output.println("/tile " + ((ChoiceTile) tile).getIndex());
					player1.c.output.flush();
					player1.setTile(((ChoiceTile) tile).getIndex());

				}

			});

			option2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// go to new index

					if (player1.getTile() == 1) {
						Thread t = new Thread(new Runnable() {
							public void run() {
								new CareerSelection(true, player1);

							}
						});
						t.start();
					}
					System.out.println(player1.getName() + " set to " + (player1.getTile() + 1));
					player1.c.output.println(player1.getName());
					player1.c.output.println("/tile " + (player1.getTile() + 1));
					player1.c.output.flush();
					player1.setTile(player1.getTile() + 1);
					Game.gameFrame.setEnabled(true);
					popUp.dispose();
				}
			});

			options.add(option1);
			options.add(Box.createRigidArea(new Dimension(50, 0)));
			options.add(option2);

		} else {
			// adding a close button after the message is displayed
			JButton close = new JButton("Close");
			close.setFocusPainted(false);
			close.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Game.gameFrame.setEnabled(true);
					popUp.dispose();

				}
			});

			close.setAlignmentX(JButton.CENTER_ALIGNMENT);
			options.add(close);
		}

	}

	/**
	 * Constructor for marriage
	 *
	 * @param player the player getting married
	 */
	PopUp(Player player) {
		Image popWindow = Toolkit.getDefaultToolkit().getImage("graphics/marriage.png");

		JFrame popUp = new JFrame();

		popUp.setUndecorated(true);
		popUp.setResizable(true);
		popUp.setAlwaysOnTop(true);
		Game.gameFrame.setEnabled(false);
		popUp.setLocation((int) (Game.screenX / 2) - 200, ((int) (Game.screenY / 2) - 150));
		popUp.setSize(400, 250);

		//set icon image
		icon = new ImageIcon("graphics/icon.png");
		popUp.setIconImage(icon.getImage());

		popUp.setVisible(true);

		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				g.drawImage(popWindow, 0, 0, null);

				repaint();
			}
		};

		popUp.setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(Box.createRigidArea(new Dimension(0, 217)));
		JPanel options = new JPanel();
		options.setOpaque(false);

		popUp.add(options);

		JButton close = new JButton("Close");
		close.setFocusPainted(false);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game.gameFrame.setEnabled(true);
				popUp.dispose();

			}
		});

		close.setAlignmentX(JButton.CENTER_ALIGNMENT);
		options.add(close);
	}
}