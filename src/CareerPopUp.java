import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CareerPopUp extends JFrame {
	
	ArrayList<Career> careers;
	CareerPopUp(boolean college, Player player) {

		//Images 
		this.setSize(250, 400);
		this.setLocation((int) (Game.screenX / 2) - 125, ((int) (Game.screenY / 2) - 200));
		this.setUndecorated(true);
		this.setResizable(false);

		
		if(college) {
			 careers = Game.collegeCareers;
		}else {
			 careers = Game.normalCareers;
		}
		
		JLabel image = new JLabel(careers.get(1).getImage());
		
		this.add(image);
		
		this.setVisible(true);
		
		Random rand = new Random();
		int index = rand.nextInt(careers.size());
		int cycle = rand.nextInt(3) + 1 ;

		System.out.println(index);
		for (int j = 0; j < cycle; j++) {
			for (int i = 0; i < careers.size(); i++) {
				Career c = careers.get(i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				image.setIcon(c.getImage());
				//System.out.println("CHANGE");
			}
		}
		
		for(int i = 0; i<=index; i++) {
			Career c = careers.get(i);
			try {
				Thread.sleep(100 + i * 100);
			} catch (InterruptedException e) {}
			image.setIcon(c.getImage());
		}
		
		
		player.setCareer(careers.get(index));
		
		JOptionPane.showMessageDialog(null,"You got the job " + careers.get(index).getCareerName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		careers.remove(index);
		
		dispose();
	
		

	}

}
