import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SinglePlayerMenu extends JFrame {

	private ImageIcon icon;

	SinglePlayerMenu() {
		super("SinglePlayer");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		mainPanel.add(title);


		this.setVisible(true);
	}
	
}
