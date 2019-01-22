/**
 * Name: Rules.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 10, 2019
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Rules extends JFrame {
    private int page;
    private ImageIcon[] rules;
    private JLabel background;
    private JButton back;
    private JButton next;
    private  JButton exit;

    /**
     * Constructor
     */
    Rules() {
        super("Rules");
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setResizable(false);
        this.requestFocusInWindow();
        this.setUndecorated(true);
        this.setSize(1000, 720);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        rules = new ImageIcon[]{new ImageIcon("graphics/rules/1.png"), new ImageIcon("graphics/rules/2.png"), new ImageIcon("graphics/rules/3.png"), new ImageIcon("graphics/rules/4.png"), new ImageIcon("graphics/rules/5.png")};
        background = new JLabel(new ImageIcon("graphics/rules/1.png"));
        back = new JButton(new ImageIcon("graphics/rules/b.png"));
        next = new JButton(new ImageIcon("graphics/rules/n.png"));
        exit = new JButton(new ImageIcon("graphics/exit.png"));

        background.setSize(1000, 720);

        //exit button
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.setSize(120, 100);
        exit.setLocation(840, 560);
        exit.addActionListener(new ExitListener());

        next.addMouseListener(new NextListener());
        next.setOpaque(false);
        next.setBorderPainted(false);
        next.setContentAreaFilled(false);
        next.setFocusPainted(false);
        next.setSize(128, 128);
        next.setLocation(832, 532);

        back.addMouseListener(new BackListener());
        back.setOpaque(false);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setSize(128, 128);
        back.setLocation(40, 532);

        this.add(exit);
        exit.setVisible(false);

        this.add(back);
        back.setVisible(false);

        this.add(next);
        this.add(background);

        this.setVisible(true);
    }


        /**
         * --------- INNER CLASSES -------------
         **/


        private class ExitListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        }

    private class BackListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (page > 0) {
                page--;
                if (page == 0) {
                    back.setVisible(false);
                }
                exit.setVisible(false);
                next.setVisible(true);
                background.setIcon(rules[page]);
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
            back.setIcon(new ImageIcon("graphics/rules/b_hover.png"));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            back.setIcon(new ImageIcon("graphics/rules/b.png"));
        }
    }

    private class NextListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (page < 4) {
                page++;
                if (page == 4) {
                    next.setVisible(false);
                    exit.setVisible(true);
                }
                back.setVisible(true);
                background.setIcon(rules[page]);
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
            next.setIcon(new ImageIcon("graphics/rules/n_hover.png"));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            next.setIcon(new ImageIcon("graphics/rules/n.png"));
        }
    }
}

