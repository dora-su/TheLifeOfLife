import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CareerPopUp extends JFrame {

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

		this.setVisible(true);
		
		if(university) {
			 careers = Game.collegeCareers;
		}else {
			 careers = Game.normalCareers;
		}
		

		Random rand = new Random();
		int index = rand.nextInt(careers.size());
		int cycle = rand.nextInt(5) + 1;

		for (int j = 0; j < cycle; j++) {
			for (int i = 0; i < careers.size(); i++) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				image.setIcon(images.get(i));

			}
		}
		
		for(int i = 0; i< index; i++) {
			try {
				Thread.sleep(100 + i * 10);
			} catch (InterruptedException e) {}
			image.setIcon(images.get(i));
		}
		
		player.setCareer(careers.get(index));
		careers.remove(index);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		dispose();

	}

}
