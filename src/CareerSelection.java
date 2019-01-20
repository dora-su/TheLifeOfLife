/**
 * Name: CareerSelection.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 3, 2019
 */

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

public class CareerSelection extends JFrame {

    private ArrayList<Career> careers;

    /**
     * Constructor
     *
     * @param college if the player has gone through college
     * @param player  the player
     */
    CareerSelection(boolean college, Player player) {

        //Images
        Game.gameFrame.setEnabled(false);
        this.setSize(250, 400);
        this.setLocation((int) (Game.screenX / 2) - 125, ((int) (Game.screenY / 2) - 200));
        this.setUndecorated(true);
        this.setResizable(false);

        // which pool of careers to choose from
        if (college) {
            careers = Game.collegeCareers;
        } else {
            careers = Game.normalCareers;
        }

        JLabel image = new JLabel(careers.get(1).getImage());

        this.add(image);

        this.setVisible(true);
        // randomly choose a career
        Random rand = new Random();
        int index = rand.nextInt(careers.size());
        int cycle = rand.nextInt(3) + 1;
        // animation
        for (int j = 0; j < cycle; j++) {
            for (int i = 0; i < careers.size(); i++) {
                Career c = careers.get(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                image.setIcon(c.getImage());
            }
        }
        // final animation
        for (int i = 0; i <= index; i++) {
            Career c = careers.get(i);
            try {
                Thread.sleep(100 + i * 100);
            } catch (InterruptedException e) {
            }
            image.setIcon(c.getImage());
        }


        ImageIcon pic = careers.get(index).getImage();
        Image scaledImg = pic.getImage();
        scaledImg = scaledImg.getScaledInstance((int) (114 * Game.scaleX), (int) (184 * Game.scaleY), java.awt.Image.SCALE_SMOOTH);

        Game.myCareer.setIcon(new ImageIcon(scaledImg));
        // set career
        player.setCareer(careers.get(index));

        JOptionPane.showMessageDialog(null, "You got the job " + careers.get(index).getCareerName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
