import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CareerPopUp extends JFrame {
	
	public static void main(String [] args) {
		new CareerPopUp();
	}

	CareerPopUp() {
		
		//Images 
		this.setSize(250, 400);
		this.setLocation((int)(GamePanel.screenX/2) - 150, ((int)(GamePanel.screenY/2) - 200));

		
		
		ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
		
		ImageIcon lawyer = new ImageIcon("blue_pp.png");
		images.add(new ImageIcon("blue_pp.png"));
		images.add(new ImageIcon("pink_pp.png"));
		

    	//this.pack();
		this.setUndecorated(true);
		this.setResizable(false);
		
		
    	JLabel image = new JLabel(lawyer);
    	
    	this.add(image);
		this.setVisible(true);
    	
    	
    	

	}
	
	
	

}
