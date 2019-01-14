import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PopUp {


    PopUp(String message, ActionTile tile,Player player1) {

        Image popWindow = Toolkit.getDefaultToolkit().getImage("graphics/optionPane.png");

        JFrame popUp = new JFrame();

        popUp.setUndecorated(true);
        popUp.setResizable(true);
        popUp.setAlwaysOnTop(true);
        popUp.setLocation(640 - 200, 360 - 100);
        popUp.setSize(400, 250);
        popUp.setVisible(true);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                    g.drawImage(popWindow, 0, 0, null);
                
                repaint();
            }
        };

        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.white);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        popUp.setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 78)));

        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 96)));
        JPanel options = new JPanel();
        options.setOpaque(false);
        ;
        popUp.add(options);

        if (tile instanceof ChoiceTile) {
            JButton option1 = new JButton("Option 1");
            JButton option2 = new JButton("Option 2");
            option1.setFocusPainted(false);
            option1.setFocusPainted(false);

            option1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // go to certain index
                    popUp.dispose();
                }

            });

            option2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // go to new index
                    popUp.dispose();
                    player1.setTile(((ChoiceTile) tile).getIndex());
                }
            });

            options.add(option1);
            options.add(Box.createRigidArea(new Dimension(50, 0)));
            options.add(option2);

        } else {
            // adding a close button after the message is displayed
            JButton close = new JButton("Close");
            close.setFocusPainted(false);
            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    popUp.dispose();

                }
            });

            close.setAlignmentX(JButton.CENTER_ALIGNMENT);
            options.add(close);
        }

    }

}