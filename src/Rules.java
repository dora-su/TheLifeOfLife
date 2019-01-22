/**
 * Name: Rules.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 10, 2019
 */

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Rules extends JFrame {
    private int page; //rule page
    private ImageIcon[] rules; //array of all the rule images
    private JLabel background; //where the rule picture displays

    //buttons
    private JButton back; //go to previous page
    private JButton next; //go to next page
    private  JButton exit; //exit the window

    /**
     * Constructor
     */
    public Rules() {
        super("Rules");

        //customize frame + set size
        this.setResizable(false);
        this.requestFocusInWindow();//sets this in focus on top of the other window
        this.setUndecorated(true);
        this.setSize(1000, 720);
        this.setLayout(null);
        this.setLocationRelativeTo(null); //appear in the centre of the screen
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//make sure it disposes and stops when closed

        //initialize variables with icon images
        rules = new ImageIcon[]{new ImageIcon("graphics/rules/1.png"), new ImageIcon("graphics/rules/2.png"), new ImageIcon("graphics/rules/3.png"), new ImageIcon("graphics/rules/4.png"), new ImageIcon("graphics/rules/5.png")};
        background = new JLabel(new ImageIcon("graphics/rules/1.png"));
        back = new JButton(new ImageIcon("graphics/rules/b.png"));
        next = new JButton(new ImageIcon("graphics/rules/n.png"));
        exit = new JButton(new ImageIcon("graphics/exit.png"));

        //set size of the rules images
        background.setSize(1000, 720);

        //exit button
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.setSize(120, 100);
        exit.setLocation(840, 560);
        exit.addActionListener(new ExitListener());

        //next button
        //remove background of button
        next.setOpaque(false);
        next.setBorderPainted(false);
        next.setContentAreaFilled(false);
        next.setFocusPainted(false);
        next.setSize(128, 128);
        next.setLocation(832, 532);
        next.addMouseListener(new NextListener());

        //back button
        //remove background of button
        back.setOpaque(false);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setSize(128, 128);
        back.setLocation(40, 532);
        back.addMouseListener(new BackListener());

        //add exit to the frame, only visible on last rule image
        this.add(exit);
        exit.setVisible(false);

        //add back to the frame, not visible on the first rule image
        this.add(back);
        back.setVisible(false);

        //add next to the frame
        this.add(next);
        this.add(background);

        this.setVisible(true);
    }


    /**
     * --------------------------------- INNER CLASSES -----------------------------------
     **/
    private class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            dispose(); // make sure the window closes when exit is clicked
        }
    }

    /**
     * Private MouseListener for the back button
     */
    private class BackListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) { //when button is clicked
            if (page > 0) { //when it is not the first rule image
                page--; //go to the previous image
                if (page == 0) { //if on the first rule image
                    back.setVisible(false); //back button shouldn't exist on the first image
                }
                exit.setVisible(false); //exit should not be visible, only visible on the last image
                next.setVisible(true); //next should be visible
                background.setIcon(rules[page]); //change the image
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //if hovered over, change the image
            back.setIcon(new ImageIcon("graphics/rules/b_hover.png"));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //non hover, change the image back
            back.setIcon(new ImageIcon("graphics/rules/b.png"));
        }
    }

    private class NextListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (page < 4) { //if not on the last image
                page++; //go to the next image/page
                if (page == 4) { //if last image
                    next.setVisible(false); //on the last page, next button shouldn't be there
                    exit.setVisible(true); //exit should be shown
                }
                back.setVisible(true); //after going forward, back is always an option so make the back button visible
                background.setIcon(rules[page]); //change the image
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //if hovered over, change image
            next.setIcon(new ImageIcon("graphics/rules/n_hover.png"));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //non hover, change the image back
            next.setIcon(new ImageIcon("graphics/rules/n.png"));
        }
    }
}

