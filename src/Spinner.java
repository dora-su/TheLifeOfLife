import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Spinner extends JFrame {
	
	public static void main(String [] args) {
		new Spinner();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		getRoll();
	
	}

	static Random rand = new Random();
	static JLabel rollText; 
	
  //class variables
	  JPanel gamePanel;

	  //Constructor - this runs first
	  Spinner() { 
	    super("Spinner");  

	    // Set the frame to full screen 
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(600,500);
	    
	    this.requestFocusInWindow();  
	    
	    this.setVisible(true);
	    
	    JPanel panel = new JPanel();
		panel.setSize(new Dimension(500,600));
		panel.setLayout(new BoxLayout(panel, getDefaultCloseOperation()));
		
		this.setContentPane(panel);
		
	    rollText = new JLabel("88");
	    rollText.setFont(new Font("Arial",Font.BOLD,50));
	    panel.add(rollText);

	  } //End of Constructor


		
	
	public static int getRoll() {
		int roll = rand.nextInt(10)+1; 
		for(int i = 0; i<=10; i++) {
			rollText.setText(Integer.toString(i));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
		
		for(int i = 0; i<=roll; i++) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			rollText.setText(Integer.toString(i));
		}
		
		return roll;
	}


	
}
