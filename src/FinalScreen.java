import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FinalScreen extends JFrame {

	public static void main(String[] args) {
		new FinalScreen();
	}

	FinalScreen() {
		super("Life");

		this.setSize(911, 561);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		JPanel panel = new Panel();
		panel.setLayout(null);

		DecimalFormat myFormatter = new DecimalFormat("$###,###.###");
		
		JLabel cash = new JLabel(myFormatter.format(5000));
		cash.setFont(new Font("Arial", Font.PLAIN, 50));
		cash.setForeground(Color.gray);
		cash.setSize(345, 81);
		cash.setLocation(43, 22);

		JLabel kids = new JLabel(myFormatter.format(5000));
		kids.setFont(new Font("Arial", Font.PLAIN, 50));
		kids.setForeground(Color.gray);
		kids.setSize(345, 81);
		kids.setLocation(43, 120);

		JLabel house = new JLabel(myFormatter.format(5000));
		house.setFont(new Font("Arial", Font.PLAIN, 50));
		house.setForeground(Color.gray);
		house.setSize(345, 81);
		house.setLocation(43, 225);

		JLabel startUp = new JLabel(myFormatter.format(5000));
		startUp.setFont(new Font("Arial", Font.PLAIN, 50));
		startUp.setForeground(Color.gray);
		startUp.setSize(345, 81);
		startUp.setLocation(43, 325);

		JPanel rankings = new JPanel();
		rankings.setSize(420,336);
		rankings.setLayout(new BoxLayout(rankings,BoxLayout.Y_AXIS));
		rankings.setAlignmentX(LEFT_ALIGNMENT);
		rankings.setLocation(447,65);
		rankings.add(new JLabel("1"));
		rankings.add(Box.createRigidArea(new Dimension(0, 50)));
		rankings.add(new JLabel("2"));
		
		
		panel.add(cash);
		panel.add(kids);
		panel.add(house);
		panel.add(startUp);
		panel.add(rankings);

		this.setContentPane(panel);

		this.setVisible(true);
	}

	private class Panel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			this.setDoubleBuffered(true);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("graphics/end.png"), 0, 0, null);
			repaint();

		}
	}

}
