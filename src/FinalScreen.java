import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FinalScreen extends JFrame {
	
	
	FinalScreen(){
		super("Life");
		
		this.setSize(500,500);
		this.setUndecorated(true);
		this.setVisible(true);
	}
	
	
	private class Panel extends JPanel{
		public void PaintComponet(Graphics g) {
			super.paintComponent(g);
			this.setDoubleBuffered(true);
			
			
		}
	}

}
