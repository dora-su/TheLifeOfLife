
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUp {

	private ImageIcon icon;

	/**
	 * Constructor
	 *
	 * @param message the popup message
	 * @param tile    the popup's tile
	 * @param player1 the player
	 */
	PopUp(String message, ActionTile tile, Player player1) {

		Image popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");

		JFrame popUp = new JFrame();

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

		JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html", SwingConstants.CENTER);
		messageLabel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		messageLabel.setSize(400, 250);
		//JLabel messageLabel = new JLabel(message);
		messageLabel.setSize(messageLabel.getPreferredSize());
		//messageLabel.setSize(400,250);
		messageLabel.setForeground(Color.black);
		messageLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		popUp.setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(0, 78)));

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
					// go to certain index
					Game.gameFrame.setEnabled(true);
					popUp.dispose();
					
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
					player1.setTile(player1.getTile() + 1);
					Game.gameFrame.setEnabled(true);
					popUp.dispose();
				}
			});

			options.add(option1);
			options.add(Box.createRigidArea(new Dimension(50, 0)));
			options.add(option2);

		} else if (tile instanceof SpinToWinTile || tile instanceof RollAgainTile) {
			JButton spin = new JButton("Spin");
			spin.setFocusPainted(false);

			//spin and add the money accordingly
			if (tile instanceof SpinToWinTile) {
				spin.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Game.spin.doClick();
						Game.gameFrame.setEnabled(true);
						popUp.dispose();

					}
				});
			} else {
				//re spin
				spin.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Game.spin.doClick();
						Game.gameFrame.setEnabled(true);
						popUp.dispose();

					}
				});
			}

			options.add(spin)

			;

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