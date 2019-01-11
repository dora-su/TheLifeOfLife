import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Spinner extends JFrame {
	public static void main(String[] args) {
		new Spinner();
	}
	static int rotate;
	static boolean finished = false;
	static int rollNum = -1;
	static Random rand = new Random();
	static JLabel rollText;
	JButton roll;

	//Constructor - this runs first
	Spinner() {
		this.setTitle("Spinner");
		Color[] colors = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue};
		// Set the frame to full screen 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 750);
		this.requestFocusInWindow();
		//this.setUndecorated(true);
		this.setVisible(true);
		// Triangle for spinner
		Polygon p = new Polygon();
        p.addPoint(330, 175);
        p.addPoint(350, 175);
        p.addPoint(340, 225);
        // Initial rotation is 0
		rotate = 0;
		rollText = new JLabel("d");
		rollText.setFont(new Font("Arial", Font.BOLD, 50));
		
		JPanel panel = new JPanel() {
			boolean first = true;
			double dist = 75;
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("graphics/tempSpinner.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				AffineTransform at = AffineTransform.getTranslateInstance(101, 200);
				
				at.rotate(Math.toRadians(rotate),image.getWidth()/2, image.getHeight()/2);
				((Graphics2D)g).drawImage(image, at, null);
				// get color of pointed area
				for (int i = 0; i < 5; i++) {
					int angle = rotate % 360;
					if (angle < 0) angle += 360;
					angle = 360 - angle;
					if (angle >= i * 72 && angle < (i + 1) * 72) {
						rollText.setText(Integer.toString(i + 1));
//						if (i == 0) rollText.setText("5");
					}
				}
				
				// adds pointer
				g.setColor(Color.WHITE);
				g.fillPolygon(p);
				try {
					//Thread.sleep(100);
				} catch (Exception e) {}
				repaint();
			}
		};
		panel.setPreferredSize(this.getSize());
		

		roll = new JButton("Spin");
		roll.addActionListener(new RollListener());
		
		panel.add(rollText);
		panel.add(roll);
		
		// timer to update every 10 milliseconds
		Timer timer = new Timer(1,new UpdateListener());
		timer.start();
		this.setContentPane(panel);
		revalidate();
		
	} //End of Constructor
	
	// Action listener for the spin button
	class RollListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			roll.setText("Spinning");
		}
	}
	
	// Action listener to update the frame
	class UpdateListener implements ActionListener {
		boolean running = false;
		int cycle, i = 0, delay = 0, increase;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// if currently spinning
			if (running) {
				// checks if cycle is over
				if (i <= cycle) {
					rotate -= increase;
					i+=increase;
					
				// end cycle
				} else {
					finished = true;
					running = false;
					roll.setText("Spin");
				}
				
				// delay increase each time
				// so the spinner slows down
				try {
					//Thread.sleep(delay);
					if (i % 20 == 0) increase--;
					increase = Math.max(1,increase);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// if not running, start running
			} else if (roll.getText().equals("Spinning")) {
				finished = false;
				cycle = rand.nextInt(1000) + 1000;
				running = true;
				i = 0;
				delay = 5;
				increase = 20;
			}
		}
	}
}