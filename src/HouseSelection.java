import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HouseSelection extends JFrame {
	
	public static void main(String [] args){
		new HouseSelection();
	}
	
	
	HouseSelection(){
		this.setSize(1200,750);
		this.setResizable(false);
		this.setUndecorated(true);
		
		this.setLocation((int)(Game.screenX/2) - 600, ((int)(Game.screenY/2) - 400));
			
		JPanel panel = new JPanel();
		
		this.add(panel);
		
		panel.setLayout(new GridLayout (2 ,4));
		
		
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		
		buttons.add(new JButton(new ImageIcon("graphics/house1")));
		buttons.add(new JButton(new ImageIcon("graphics/house2")));
		buttons.add(new JButton(new ImageIcon("graphics/house3")));
		buttons.add(new JButton(new ImageIcon("graphics/house4")));
		buttons.add(new JButton(new ImageIcon("graphics/house5")));
		buttons.add(new JButton(new ImageIcon("graphics/house6")));
		buttons.add(new JButton(new ImageIcon("graphics/house7")));
		buttons.add(new JButton(new ImageIcon("graphics/house8")));
		
		for(int i = 0; i<buttons.size(); i++){
			panel.add(buttons.get(i));
		}
		
		this.setVisible(true);
	}
}