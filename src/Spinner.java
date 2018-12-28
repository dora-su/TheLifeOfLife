import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Spinner extends JFrame {

//	public static void main(String[] args) {
//		new Spinner();
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//		}
//		getRoll();
//	}

	static Random rand = new Random();
	static JLabel rollText;
	JButton roll;

	//Constructor - this runs first
	Spinner() {
		super("Spinner");

		// Set the frame to full screen 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 500);

		this.requestFocusInWindow();
		//this.setUndecorated(true);

		this.setVisible(true);
		


		JPanel panel = new JPanel();
		panel.setPreferredSize(this.getSize());

		this.setContentPane(panel);


		
		rollText = new JLabel("88");
		rollText.setFont(new Font("Arial", Font.BOLD, 50));

		roll = new JButton("Spin");
		roll.addActionListener(new RollListener());
		
		panel.add(rollText);
		panel.add(roll);
		
		
		
		

	} //End of Constructor

	public static int getRoll() {
		int roll = rand.nextInt(10) + 1;
		int cycle = rand.nextInt(3) + 1;
		for (int j = 0; j < cycle; j++) {
			for (int i = 0; i <= 10; i++) {
				rollText.setText(Integer.toString(i));
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
				}
			}
		}

		for (int i = 0; i <= roll; i++) {
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
			}
			rollText.setText(Integer.toString(i));
		}

		return roll;
	}
	
	private class RollListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			getRoll();
		}
		
	}

}
