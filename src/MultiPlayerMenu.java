import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MultiPlayerMenu extends JFrame {

	int numPlayers;
	MultiPlayerMenu() {
		super("MultiPlayer");

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

		mainPanel.add(title);

	}
	
}
