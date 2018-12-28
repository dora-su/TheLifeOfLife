
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;

class GamePanel extends JFrame { 

  //class variables
  JPanel gamePanel;

  //Constructor - this runs first
   GamePanel() { 
    super("My Game");  

    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

    //Set up the game panel (where we put our graphics)
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());
    
    this.requestFocusInWindow();  
    //this.setUndecorated(true);
    
    this.setVisible(true);
  
  } //End of Constructor


  
  /** --------- INNER CLASSES ------------- **/
  private class GameAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {   
       super.paintComponent(g); //required
       setDoubleBuffered(true); 
       repaint();
    }
  }
  
     
}