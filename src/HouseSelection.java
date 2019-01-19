import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HouseSelection extends JFrame {

	// public static void main(String[] args) {
	// new HouseSelection();
	// }

	HouseSelection(Player player) {
		this.setSize((int) (Game.scaleX * 1000), (int) (Game.scaleY * 800));
		this.setResizable(false);
		this.setUndecorated(true);
		// 250, 400
		this.setLocation((int) (Game.screenX / 2) - ((int) (Game.scaleX * 1000) / 2),
				((int) (Game.screenY / 2) - ((int) (Game.scaleY * 800) / 2)));
		this.setFocusable(true);
		JPanel panel = new JPanel();

		this.add(panel);

		panel.setLayout(new GridLayout(2, 4));

		ArrayList<JButton> buttons = new ArrayList<JButton>();

		for (Property p : Game.properties) {
			// Property p = Game.properties.get(i);
			JButton button = new JButton(p.getImage());
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.addProperty(p);
					// setImage to bought

					String[] options = new String[] { "Confirm", "Back" };
					int confirm = JOptionPane.showOptionDialog(null,
							"Are you sure you want to purchase " + p.getName() + " for $" + p.getValue(),
							"CONFIRM YOUR PURCHASE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
							options, options[0]);

					if (confirm != 0) {
						return;
					}

					player.addProperty(p);
					
					ImageIcon pic = p.getImage();
					Image scaledImg = pic.getImage();
					scaledImg = scaledImg.getScaledInstance((int) (114 * Game.scaleX), (int) (184 * Game.scaleY), java.awt.Image.SCALE_SMOOTH);
								
					Game.myHouse.setIcon(new ImageIcon(scaledImg));
//					
					player.removeMoney(p.getValue());
					button.removeActionListener(this);

					
					Image soldImg = Toolkit.getDefaultToolkit()
							.getImage("graphics/homes/" + p.getName().toLowerCase() + "SOLD.png");
					soldImg = soldImg.getScaledInstance((int) (250 * Game.scaleX), (int) (400 * Game.scaleY),
							java.awt.Image.SCALE_SMOOTH);

					button.setIcon(new ImageIcon(soldImg));

//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//					}
					
					dispose();
				}

			});
			buttons.add(button);
			panel.add(button);

		}

		this.setVisible(true);
	}

}