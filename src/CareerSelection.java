/**
 * Name: CareerSelection.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 3, 2019
 */

<<<<<<< HEAD
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
=======

>>>>>>> 98a30285afedaad13e56cd6d8ac30b9cc84096cc
import java.awt.Dialog;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class CareerSelection extends JFrame {

    private ArrayList<Career> careers;
    /**
     * Constructor	
     *
     * @param college if the player has gone through college then true, otherwise false
     * @param player  the player
     */
    CareerSelection(boolean college, Player player) {
        //customize frame + frame settings
        Game.gameFrame.setEnabled(false);
        this.setSize(250, 400);
        this.setLocation((int) (Game.screenX / 2) - 125, ((int) (Game.screenY / 2) - 200));
        this.setUndecorated(true);
        this.setResizable(false);

        // which pool of careers to choose from
        if (college) { //if gone to college, choose from college careers
            careers = Game.collegeCareers;
        } else {
            careers = Game.normalCareers;
        }

        //get image of the career
        JLabel image = new JLabel(careers.get(1).getImage());
        //add image to frame
        this.add(image);

        this.setVisible(true);

        // randomly choose a career
        Random rand = new Random();
        int index = rand.nextInt(careers.size());
        int cycle = rand.nextInt(3) + 1;

        // animate cycling through the careers
        for (int j = 0; j < cycle; j++) {
            for (int i = 0; i < careers.size(); i++) {
                Career c = careers.get(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted.");
                    e.printStackTrace();
                }
                image.setIcon(c.getImage());
            }
        }

        //stop on the career and display the picture on the screen for a short moment
        for (int i = 0; i <= index; i++) {
            Career c = careers.get(i);
            try {
                Thread.sleep(100 + i * 100);
            } catch (InterruptedException e) {
            }
            image.setIcon(c.getImage());
        }

<<<<<<< HEAD
        //Show the career card on the bottom bar
=======

       //scaling and displaying the career icon in the bottom bar 
>>>>>>> 98a30285afedaad13e56cd6d8ac30b9cc84096cc
        ImageIcon pic = careers.get(index).getImage();
        Image scaledImg = pic.getImage();
        //scale the image to fit the bottom bar
        scaledImg = scaledImg.getScaledInstance((int) (114 * Game.scaleX), (int) (184 * Game.scaleY), Image.SCALE_SMOOTH);

        //add the image to the game bar
        Game.myCareer.setIcon(new ImageIcon(scaledImg));
        // set career
        player.setCareer(careers.get(index));

        //sets ok button for option pane to an image
        final JButton ok = new JButton(new ImageIcon("graphics/yes.png"));

        //customize the button
        ok.setFocusPainted(false);
        ok.setOpaque(false);
        ok.setBorderPainted(false);
        ok.setContentAreaFilled(false);

<<<<<<< HEAD
        //Create dialog window to notice players of their new career
=======
        //dialog informing you of the job you received
>>>>>>> 98a30285afedaad13e56cd6d8ac30b9cc84096cc
        final JDialog dialog = new JDialog((Dialog) null, "Career", true);

        //Add a mouselistener to the "ok" button
        ok.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //if button clicked, make dialog close
                dialog.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //when hovered over, change the image
                ok.setIcon(new ImageIcon("graphics/yes_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //not hovered over, change the image back to normal
                ok.setIcon(new ImageIcon("graphics/yes.png"));
            }
        });

        //object array of the joption pane, only one button ("ok")
        Object[] options = {ok};

        //display joption pane on the dialog, display message to players
       dialog.getContentPane().add(new JOptionPane("You got the job " + careers.get(index).getCareerName(), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION,null,
                options,
                options[0]));

       //Set size and location of dialog window
       dialog.setSize(300,170);
       dialog.setLocationRelativeTo(null);
       dialog.setVisible(true);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //send career info to server
        player.c.output.println(player.c.userName);
        if (college) {
            player.c.output.println("/removecc " + index);
        } else {
            player.c.output.println("/removec " + index);
        }
        player.c.output.flush();

        Game.gameFrame.setEnabled(true);
        dispose();
    }

}
