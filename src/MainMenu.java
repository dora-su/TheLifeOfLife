import java.awt.*;
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
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setResizable(false);
		this.requestFocusInWindow();

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
				client.setIcon(new ImageIcon("graphics/join_hover.png"));
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

		host.addActionListener(new HostButtonListener());
		client.addActionListener(new ClientButtonListener());
		rules.addActionListener(new RulesListener());
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

			//Thread t1 = new Thread(new Runnable() {
			//public void run() {
			//new Server().go();
			new Client();
			new Server();



			//}
			//});
			//			Thread t2 = new Thread(new Runnable() {
			//				public void run() {
			//					//new Client().login();
			//				}
			//			});
			//			//t1.setPriority(5);
			//			t2.setPriority(10);
			//			//t1.start();
			//			t2.start();
			dispose();

		}
	}

	private class ClientButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Thread(new Runnable() {
				public void run() {
					new Client();
				}
			}).start();
			dispose();
		}
	}

	private class RulesListener implements ActionListener {
		int page = 0;
		ImageIcon[] rule = {new ImageIcon("graphics/rules/1.png"), new ImageIcon("graphics/rules/2.png"), new ImageIcon("graphics/rules/3.png"), new ImageIcon("graphics/rules/4.png"), new ImageIcon("graphics/rules/5.png")};
		JLabel label = new JLabel(new ImageIcon("graphics/rules/1.png"));
		JButton back = new JButton(new ImageIcon("graphics/rules/b.png"));
		JButton next = new JButton(new ImageIcon("graphics/rules/n.png"));
		JButton exit = new JButton(new ImageIcon("graphics/exit.png"));
		JFrame rules = new JFrame("How to Play");
		@Override
		public void actionPerformed(ActionEvent arg0) {

			rules.setSize(1000, 720);
			rules.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			rules.setLayout(null);
			rules.setLocationRelativeTo(null);
			rules.setUndecorated(true);
			label.setSize(1000,720);

			//exit button
			exit.setOpaque(false);
			exit.setContentAreaFilled(false);
			exit.setBorderPainted(false);
			exit.setFocusPainted(false);
			exit.setSize(120,100);
			exit.setLocation(840,560);
			exit.addActionListener(new ExitListener());

			back.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(page>0){
						page--;
						if(page==0){
							back.setVisible(false);
						}
						exit.setVisible(false);
						next.setVisible(true);
						label.setIcon(rule[page]);
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					back.setIcon(new ImageIcon("graphics/rules/b_hover.png"));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					back.setIcon(new ImageIcon("graphics/rules/b.png"));
				}
			});

			next.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(page<4){
						page++;
						if(page==4){
							next.setVisible(false);
							exit.setVisible(true);
						}
						back.setVisible(true);
						label.setIcon(rule[page]);
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					next.setIcon(new ImageIcon("graphics/rules/n_hover.png"));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					next.setIcon(new ImageIcon("graphics/rules/n.png"));
				}
			});
			next.setOpaque(false);
			back.setOpaque(false);
			next.setBorderPainted(false);
			back.setBorderPainted(false);
			next.setContentAreaFilled(false);
			back.setContentAreaFilled(false);
			next.setFocusPainted(false);
			next.setSize(120,120);
			back.setSize(120,120);
			back.setFocusPainted(false);
			next.setLocation(840,540);
			back.setLocation(40,540);

//			rules.add(label);
//			rules.add(next);
////			next.setVisible(true);
////			back.setVisible(false);
			rules.add(exit);
			exit.setVisible(false);
			rules.add(back);
			back.setVisible(false);
			rules.add(next);
			rules.add(label);
			label.setVisible(true);

			rules.setVisible(true);
		}

		private class ExitListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rules.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				rules.dispose();
			}
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
