import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainMenu extends JFrame {

	private ImageIcon icon;

	MainMenu() {
		super("Main Menu");

//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1200, 1200);

		this.requestFocusInWindow();

		//set icon image
		icon = new ImageIcon("graphics/icon.png");
		this.setIconImage(icon.getImage());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setSize(this.getSize());
		this.setContentPane(mainPanel);

		JLabel title = new JLabel("Life of Life");
		title.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 50));

		JPanel options = new JPanel();
		JButton host = new JButton("Host Server");
		JButton client = new JButton("Join Server");
		host.addActionListener(new SinglePlayerListener());
		client.addActionListener( new MultiPlayerListener());

		options.add(host);
		options.add(Box.createRigidArea(new Dimension(60, 0)));
		options.add(client);
		client.setFont(new Font("Arial", Font.PLAIN, 40));
		host.setFont(new Font("Arial", Font.PLAIN, 40));

		mainPanel.add(title);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 500)));
		mainPanel.add(options);


		this.setVisible(true);
	}

	private class SinglePlayerListener implements ActionListener {

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

	private class MultiPlayerListener implements ActionListener {

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
}
