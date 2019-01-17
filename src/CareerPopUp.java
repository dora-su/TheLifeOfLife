import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CareerPopUp extends JFrame {

	public static void main(String [] args) {
		new CareerPopUp(true, new Player("jason",200,1,1));
	}
	
	ArrayList<Career> careers;
	CareerPopUp(boolean university, Player player) {

		//Images 
		this.setSize(250, 400);
		this.setLocation((int) (Game.screenX / 2) - 150, ((int) (Game.screenY / 2) - 200));

		ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();

		images.add(new ImageIcon("graphics/mango1.png"));
		images.add(new ImageIcon("graphics/blue_pp.png"));
		images.add(new ImageIcon("graphics/pink_pp.png"));

		this.setUndecorated(true);
		this.setResizable(false);

		JLabel image = new JLabel(images.get(0));

		this.add(image);

		careers = new ArrayList<Career>();
		careers.add(new Career("Police", 1000));
		careers.add(new Career("Dancer", 1000));
		careers.add(new Career("Stripper", 100000000));
		
//		
//		if(university) {
//			 careers = Game.collegeCareers;
//		}else {
//			 careers = Game.normalCareers;
//		}
		
		this.setVisible(true);
		
		Random rand = new Random();
		int index = rand.nextInt(careers.size() + 1);
		int cycle = rand.nextInt(5) + 1;

		for (int j = 0; j < cycle; j++) {
			for (int i = 0; i < careers.size(); i++) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				image.setIcon(images.get(i));
				//System.out.println("CHANGE");
			}
		}
		
		for(int i = 0; i< index; i++) {
			try {
				Thread.sleep(100 + i * 10);
			} catch (InterruptedException e) {}
			image.setIcon(images.get(i));
		}
		
//		player.setCareer(careers.get(index));
//		careers.remove(index);
//		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		
		//dispose();
		
		

	}

}
