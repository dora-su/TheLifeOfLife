/**
 * Name: MainMenu.java
 * Version: 2.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 5, 2019
 */

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu extends JFrame {

    private ImageIcon icon;
    Image mainMenu;

    /**
     * Constructor
     */
    MainMenu() {
        super("Main Menu");

        this.setSize(911, 561);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setResizable(false);
        this.requestFocusInWindow();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //set icon image
        icon = new ImageIcon("graphics/icon.png");
        this.setIconImage(icon.getImage());

        mainMenu = Toolkit.getDefaultToolkit().getImage("graphics/mainmenu.png");

        //JPanel for the main menu
        JPanel mainPanel = new MainPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(this.getSize());
        this.setContentPane(mainPanel);

        //JPanel for the options given
        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        options.setOpaque(false);
        options.setAlignmentX(LEFT_ALIGNMENT);
        JButton host = new JButton(new ImageIcon("graphics/host.png"));
        host.setContentAreaFilled(false);
        host.setFocusPainted(false);
        host.setBorderPainted(false);
        host.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                host.setIcon(new ImageIcon("graphics/host_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                host.setIcon(new ImageIcon("graphics/host.png"));
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }

        });

        //client join button
        JButton client = new JButton(new ImageIcon("graphics/join.png"));
        client.setContentAreaFilled(false);
        client.setFocusPainted(false);
        client.setBorderPainted(false);
        client.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                client.setIcon(new ImageIcon("graphics/join_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                client.setIcon(new ImageIcon("graphics/join.png"));
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }

        });

        //button to access rules
        JButton rules = new JButton(new ImageIcon("graphics/rules.png"));
        rules.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent arg0) {

            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                rules.setIcon(new ImageIcon("graphics/rules_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                rules.setIcon(new ImageIcon("graphics/rules.png"));
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }
        });

        //adding action listers to buttons
        host.addActionListener(new HostButtonListener());
        client.addActionListener(new ClientButtonListener());
        rules.addActionListener(new RulesListener());
        rules.setContentAreaFilled(false);
        rules.setFocusPainted(false);
        rules.setBorderPainted(false);
        options.add(host);
        options.add(Box.createRigidArea(new Dimension(0, 20)));
        options.add(client);
        options.add(Box.createRigidArea(new Dimension(0, 20)));
        options.add(rules);

        //mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(900, 20)));
        mainPanel.add(options);

        this.setVisible(true);
    }

    /**
     * HostButtonListener
     */
    private class HostButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            new Client();
            new Server();
            dispose();

        }
    }

    /**
     * ClientButtonListener
     */
    private class ClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	//start up client
            new Thread(new Runnable() {
                public void run() {
                    new Client();
                }
            }).start();
            dispose();
        }
    }

    /**
     * RulesListener
     */
    private class RulesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
        	//open rules page
            Rules rules = new Rules();
        }
    }

    /**
     * MainPanel
     */
    private class MainPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g); // required
            this.setDoubleBuffered(true);
            //draw image background
            g.drawImage(mainMenu, 0, 0, null);
            repaint();

        }
    }
}
