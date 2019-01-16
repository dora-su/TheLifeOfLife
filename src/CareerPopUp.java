import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CareerPopUp extends JFrame {

	CareerPopUp(ArrayList<Career> careers) {
		
		//Images 
		this.add(new CareerPanel());
		
		
		
		
	}
	
	
	private class CareerPanel extends JPanel {
		
		public void PaintComponet(Graphics g) {
			super.paintComponent(g);
		}
	}

}
