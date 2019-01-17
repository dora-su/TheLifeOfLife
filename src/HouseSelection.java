import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HouseSelection extends JFrame {

	public static void main(String[] args) {
		new HouseSelection();
	}

	HouseSelection() {
		this.setSize(1200, 750);
		this.setResizable(false);
		this.setUndecorated(true);

		this.setLocation((int) (Game.screenX / 2) - 600, ((int) (Game.screenY / 2) - 400));

		JPanel panel = new JPanel();

		this.add(panel);

		panel.setLayout(new GridLayout(2, 4));

		ArrayList<JButton> buttons = new ArrayList<JButton>();

		for (Property p : Game.properties) {
			JButton button = new JButton(p.getImage());
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println(p.getName());
					
				}
				
			});
			buttons.add(button);
			panel.add(button);

		}

		this.setVisible(true);
	}

	private class HouseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			

		}

	}
}