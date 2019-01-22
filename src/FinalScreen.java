import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FinalScreen extends JFrame {

	Player p;
	FinalScreen(Player p) {
		super("Life");
		this.p = p;
		this.setSize(911, 561);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		JPanel panel = new Panel();
		panel.setLayout(null);

		DecimalFormat myFormatter = new DecimalFormat("$###,###.###");
		
		JLabel cash = new JLabel(myFormatter.format(p.getMoney()));
		cash.setFont(new Font("Arial", Font.PLAIN, 50));
		cash.setForeground(Color.gray);
		cash.setSize(345, 81);
		cash.setLocation(43, 22);

		JLabel kids = new JLabel(myFormatter.format(p.getChild() * 1000));
		kids.setFont(new Font("Arial", Font.PLAIN, 50));
		kids.setForeground(Color.gray);
		kids.setSize(345, 81);
		kids.setLocation(43, 120);

		JLabel house = new JLabel(myFormatter.format((p.getProperty() == null ? 0 : p.getProperty().getValue())));
		house.setFont(new Font("Arial", Font.PLAIN, 50));
		house.setForeground(Color.gray);
		house.setSize(345, 81);
		house.setLocation(43, 225);

		JLabel startUp = new JLabel(myFormatter.format(p.startup));
		startUp.setFont(new Font("Arial", Font.PLAIN, 50));
		startUp.setForeground(Color.gray);
		startUp.setSize(345, 81);
		startUp.setLocation(43, 325);

//		JPanel rankings = new JPanel();
//		rankings.setSize(420,336);
//		rankings.setLayout(new BoxLayout(rankings,BoxLayout.Y_AXIS));
//		rankings.setAlignmentX(LEFT_ALIGNMENT);
//		rankings.setLocation(447,65);
//		rankings.add(new JLabel("1"));
//		rankings.add(Box.createRigidArea(new Dimension(0, 50)));
//		rankings.add(new JLabel("2"));
		Image mainMenu = Toolkit.getDefaultToolkit().getImage("graphics/mainmenu_button.png");
		Image mainMenuHover = Toolkit.getDefaultToolkit().getImage("graphics/mainmenu_button_hover.png");
		JButton quit = new JButton(new ImageIcon(mainMenu));
		quit.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				new MainMenu();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				quit.setIcon(new ImageIcon(mainMenuHover));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				quit.setIcon(new ImageIcon(mainMenu));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		quit.setLocation(550,450);
		quit.setSize(250,100);
		quit.setContentAreaFilled(false);
		quit.setBorderPainted(false);
		quit.setFocusPainted(false);
		panel.add(cash);
		panel.add(kids);
		panel.add(house);
		panel.add(startUp);
		panel.add(quit);

		this.setContentPane(panel);

		this.setVisible(true);
	}

	private class Panel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			this.setDoubleBuffered(true);
			Player[] A = (Player[]) p.c.players.toArray();
			Arrays.sort(A);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("graphics/end.png"), 0, 0, null);
			for (int i = 0; i < A.length; i++) {
				g.drawString((i + 1) + ". " + ((Player)A[i]).getName(), 500, 100 + i * 40);
			}
			repaint();

		}
	}

}
