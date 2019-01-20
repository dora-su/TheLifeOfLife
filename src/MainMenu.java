import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class MainMenu extends JFrame {

	private ImageIcon icon;
	Image mainMenu;

	MainMenu() {
		super("Main Menu");

		this.setSize(911, 561);
		this.setUndecorated(true);
		this.setResizable(false);
		this.requestFocusInWindow();

		//set icon image
		icon = new ImageIcon("graphics/icon.png");
		this.setIconImage(icon.getImage());

		mainMenu = Toolkit.getDefaultToolkit().getImage("graphics/mainmenu.png");

		JPanel mainPanel = new MainPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setSize(this.getSize());
		this.setContentPane(mainPanel);

		//		JLabel title = new JLabel("Life of Life");
		//		title.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		//		title.setHorizontalAlignment(JLabel.CENTER);
		//		title.setFont(new Font("Arial", Font.BOLD, 50));

		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		options.setOpaque(false);
		options.setAlignmentX(LEFT_ALIGNMENT);
		options.setSize(this.size());
		JButton host = new JButton(new ImageIcon("graphics/host.png"));
		host.setContentAreaFilled(false);
		host.setFocusPainted(false);
		host.setBorderPainted(false);
		host.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				host.setIcon(new ImageIcon("graphics/host_hover.png"));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				host.setIcon(new ImageIcon("graphics/host.png"));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});

		JButton client = new JButton(new ImageIcon("graphics/join.png"));
		client.setContentAreaFilled(false);
		client.setFocusPainted(false);
		client.setBorderPainted(false);
		client.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				client.setIcon(new ImageIcon("graphics/join_hover.jpg"));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				client.setIcon(new ImageIcon("graphics/join.png"));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
		host.addActionListener(new HostButtonListener());
		client.addActionListener(new ClientButtonListener());

		JButton rules = new JButton(new ImageIcon("graphics/rules.png"));
		rules.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				rules.setIcon(new ImageIcon("graphics/rules_hover.png"));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				rules.setIcon(new ImageIcon("graphics/rules.png"));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		rules.setContentAreaFilled(false);
		rules.setFocusPainted(false);
		rules.setBorderPainted(false);
		options.add(host);
		options.add(Box.createRigidArea(new Dimension(0, 20)));
		options.add(client);
		options.add(Box.createRigidArea(new Dimension(0, 20)));
		options.add(rules);

		//mainPanel.add(title);
		mainPanel.add(Box.createRigidArea(new Dimension(900, 20)));
		mainPanel.add(options);

		this.setVisible(true);
	}

	private class HostButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Thread t1 = new Thread(new Runnable() {
				public void run() {
					new Server().go();
				}
			});
			Thread t2 = new Thread(new Runnable() {
				public void run() {
					new Client().login();
				}
			});
			t1.setPriority(5);
			t2.setPriority(10);
			t1.start();
			t2.start();
			dispose();
		}
	}

	private class ClientButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Thread(new Runnable() {
				public void run() {
					new Client().login();
				}
			}).start();
			dispose();
		}
	}

	private class RulesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//g.drawImage(rules,0,0,null)

		}

	}

	private class MainPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			this.setDoubleBuffered(true);
			g.drawImage(mainMenu, 0, 0, null);
			repaint();

		}
	}
}
