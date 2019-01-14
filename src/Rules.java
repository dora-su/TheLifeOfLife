import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Rules extends JFrame {

	private static JFrame window;
	private JPanel rulePanel;
	Image background;

	Rules() {
		super("Rules");
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setResizable(false);
		this.requestFocusInWindow();
		//this.setUndecorated(true);
		
		this.setVisible(true);
	

		background = Toolkit.getDefaultToolkit().getImage("graphics/rule_template.png");
		background = background.getScaledInstance((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(), Image.SCALE_DEFAULT);
		
		rulePanel = new RulePanel();
		this.add(rulePanel);
		
	}
	
	
    /**
     * --------- INNER CLASSES -------------
     **/
    private class RulePanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setDoubleBuffered(true);
            g.drawImage(background, 0, 0, null);
            
            //System.out.println("hi");
            repaint();
        }


    }

}
