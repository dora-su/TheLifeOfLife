import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PopUp {

	private ImageIcon icon;

	PopUp(String message, ActionTile tile, Player player1) {

		Image popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");

		JFrame popUp = new JFrame();

		popUp.setUndecorated(true);
		popUp.setResizable(true);
		popUp.setAlwaysOnTop(true);
		Game.gameFrame.setEnabled(false);
		popUp.setBackground(new Color(1.0f,1.0f,1.0f,0.5f)); // the last value, a: is the transparency , 0.0 = full transparency, 1.0 = full opacity
//		popUp.setLocation((int) (Game.screenX / 2) - 200, ((int) (Game.screenY / 2) - 159));
		popUp.setSize(400, 317);
		popUp.setLocationRelativeTo(null);

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
		panel.setOpaque(false);

		//JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html");
		JTextArea messageLabel = new JTextArea(message);
		messageLabel.setEditable(false);
		messageLabel.setSize(messageLabel.getPreferredSize());
		messageLabel.setLineWrap(true);
		messageLabel.setBackground(null);
		messageLabel.setSize(400,250);
		messageLabel.setForeground(Color.black);
		messageLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		messageLabel.setOpaque(false);
		popUp.setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(0, 78)));


		panel.add(messageLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 163)));
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

					player1.move(1, Game.path);
					Game.gameFrame.setEnabled(true);
					popUp.dispose();
				}
			});

			options.add(option1);
			options.add(Box.createRigidArea(new Dimension(50, 0)));
			options.add(option2);

		} else if (tile instanceof SpinToWinTile) {
			JButton spin = new JButton("Spin");
			spin.setFocusPainted(false);
			spin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Game.gameFrame.setEnabled(true);
					popUp.dispose();

				}
			});
			
			
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

			if (tile instanceof LifeTile) {
				int moneyToRemove = 0;
				player1.removeMoney(moneyToRemove);
			} else if (tile instanceof MoneyTile) {
				player1.removeMoney((((MoneyTile) tile).getMoney()));
			}

			close.setAlignmentX(JButton.CENTER_ALIGNMENT);
			options.add(close);
		}

	}

	//pop up for marriage
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
		options.setOpaque(false);

		popUp.setVisible(true);

	}
}