import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainMenu extends JFrame {

	MainMenu() {
		super("Main Menu");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1200, 1200);

		this.requestFocusInWindow();

		this.setVisible(true);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setSize(this.getSize());
		this.setContentPane(mainPanel);

		JLabel title = new JLabel("Life of Life");
		title.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 50));

		JPanel options = new JPanel();
		JButton singlePlayer = new JButton("Single Player");
		JButton multiPlayer = new JButton("MultiPlayer");
		singlePlayer.addActionListener(new SinglePlayerListener());
		multiPlayer.addActionListener( new MultiPlayerListener());

		options.add(singlePlayer);
		options.add(Box.createRigidArea(new Dimension(60, 0)));
		options.add(multiPlayer);
		singlePlayer.setFont(new Font("Arial", Font.PLAIN, 40));
		multiPlayer.setFont(new Font("Arial", Font.PLAIN, 40));

		mainPanel.add(title);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 500)));
		mainPanel.add(options);

	}
	
	private class SinglePlayerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new SinglePlayerMenu();
			dispose();
		}
	}
	
	private class MultiPlayerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new MultiPlayerMenu();
			dispose();
		}
	}
}
