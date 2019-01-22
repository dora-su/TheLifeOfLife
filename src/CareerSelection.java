/**
 * Name: CareerSelection.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 3, 2019
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class CareerSelection extends JFrame {

    private ArrayList<Career> careers;
    private int result;
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
        scaledImg = scaledImg.getScaledInstance((int) (114 * Game.scaleX), (int) (184 * Game.scaleY), Image.SCALE_SMOOTH);

        Game.myCareer.setIcon(new ImageIcon(scaledImg));
        // set career
        player.setCareer(careers.get(index));

        //sets ok button for option pane to an image
        final JButton ok = new JButton(new ImageIcon("graphics/yes.png"));

        ok.setFocusPainted(false);
        ok.setOpaque(false);
        ok.setBorderPainted(false);
        ok.setContentAreaFilled(false);

        final JDialog dialog = new JDialog((Dialog) null, "Career", true);

        final JOptionPane optionPane = new JOptionPane();
        ok.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                ok.setIcon(new ImageIcon("graphics/yes_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ok.setIcon(new ImageIcon("graphics/yes.png"));
            }
        });

        Object[] options = {ok};//object array of the joption pane

        //display joption pane
       dialog.getContentPane().add(new JOptionPane("You got the job " + careers.get(index).getCareerName(), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION,null,
                options,
                options[0]));

       dialog.setSize(200,170);
       dialog.setLocationRelativeTo(null);
       dialog.setVisible(true);

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
