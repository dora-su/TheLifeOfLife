import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

class GamePanel extends JFrame {

    // class variables
    JPanel gameAreaPanel;

    Image map, mango1, popWindow;
    Player player1;
    ArrayList<ActionTile> path;

    static double screenX = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static double screenY =  Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    // Constructor - this runs first
    GamePanel() {
        super("My Game");


        
        // Set the frame to full screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize((int)screenX, (int)screenY);
        //this.setSize(1920,1150);
        System.out.println(screenX + " " + screenY);
        double scale = (screenX * screenY) /(1920 * 1150.0) ;
        System.out.println("scale" + scale);
        
        double scaleX =screenX / 1920.0;
        double scaleY = screenY / 1150.0;

        // Set up the game panel (where we put our graphics)
        path = new ArrayList<ActionTile>();

        path.add(new MoneyTile((int)(scaleX* 90),(int)(scaleY* 444), null, 0));
        path.add(new MoneyTile((int)(scaleX* 165),(int)(scaleY* 447), "a", 0));
        path.add(new MoneyTile((int)(scaleX* 232),(int)(scaleY* 449), "a", 0));
        path.add(new MoneyTile((int)(scaleX* 313),(int)(scaleY* 433), "a", 0));

        map = Toolkit.getDefaultToolkit().getImage("graphics/board.jpg");
        map = map.getScaledInstance((int)screenX,(int)screenY, Image.SCALE_DEFAULT);
        mango1 = Toolkit.getDefaultToolkit().getImage("graphics/mango1.png");
        
        mango1 = mango1.getScaledInstance((int)(45*scale),(int)(45*scale), Image.SCALE_DEFAULT);
        
        popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");

        player1 = new Player("Eric", 91, 91, 444);

        gameAreaPanel = new GameAreaPanel();
        this.add(gameAreaPanel);

        this.requestFocusInWindow();
        this.setUndecorated(true);

        this.setVisible(true);

        MyMouseListener mouseListener = new MyMouseListener();
        this.addMouseListener(mouseListener);


        // TILE LOCATION
        // First Path
        /*
         * X: 26 Y: 339 X: 74 Y: 339 X: 129 Y: 339 X: 178 Y: 331
         */

    } // End of Constructor

    /**
     * --------- INNER CLASSES -------------
     **/
    private class GameAreaPanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g); // required
            setDoubleBuffered(true);
            // System.out.println("HI");
            g.drawImage(map, 0, 0, null);
            g.drawImage(mango1, path.get(player1.getTile()).getX() - 17, path.get(player1.getTile()).getY() - 17, null);
            repaint();

            
            //draw bottom game menu image
        }


    }

    private class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("X: " + e.getX() + " Y: " + e.getY());
           // Thread t = new Thread(new Runnable() {
            	//public void run() {
            	
             		player1.move(1, path);
            		
            //	}
         //  });
          //  t.start();
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }

}