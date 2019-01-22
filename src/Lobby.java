/**
 * Name: Lobby.java
 * Version: 2.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 6, 2019
 */

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Lobby extends JFrame {

    private MyKeyListener keyListener;
    private ImageIcon icon;

    private JPanel lobbyPanel = new JPanel();
    Image lobby;
    private Client c;
    private boolean ready = false;
    private String[] playerList;
    private int playerCount;
    private Font font;

    /**
     * Constructor
     *
     * @param c the client
     */
    Lobby(Client c) {
        super("Lobby");
        playerCount = 0;
        this.c = c;
        this.setSize(911, 561);
        this.setUndecorated(true);
        this.setResizable(false);
        this.requestFocusInWindow();
        this.setLocationRelativeTo(null);
        //this.setUndecorated(true);
        keyListener = new MyKeyListener();
        this.addKeyListener(keyListener); //adds the keylistener

        //set icon image
        icon = new ImageIcon("graphics/icon.png");
        this.setIconImage(icon.getImage());

        // ready box
        playerList = new String[6];
        for (int i = 0; i < 6; i++) {
            playerList[i] = "";
        }
        lobby = Toolkit.getDefaultToolkit().getImage("graphics/lobby.png");

        JPanel mainPanel = new MainPanel();
        mainPanel.setLayout(null);
        mainPanel.setSize(this.getSize());
        this.setContentPane(mainPanel);

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        options.setOpaque(false);
        options.setAlignmentX(LEFT_ALIGNMENT);
        options.setSize(this.size());
        options.setLocation(450, 200);

        JButton imready = new JButton(new ImageIcon("graphics/ready.png"));
        imready.setContentAreaFilled(false);
        imready.setFocusPainted(false);
        imready.setBorderPainted(false);
        imready.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (ready) {
                    imready.setIcon(new ImageIcon("graphics/ready_hover.png"));
                } else {
                    imready.setIcon(new ImageIcon("graphics/ready.png"));
                }
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                if (!ready) {
                    imready.setIcon(new ImageIcon("graphics/ready_hover.png"));
                } else {
                    imready.setIcon(new ImageIcon("graphics/ready.png"));
                }
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                if (ready) {
                    imready.setIcon(new ImageIcon("graphics/ready_hover.png"));
                } else {
                    imready.setIcon(new ImageIcon("graphics/ready.png"));
                }
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }

        });

        imready.addActionListener(new ReadyButtonListener());
//        for (int i = 0; i < 6; i++) {
//            players.add(playerList[i]);
//        }
        options.add(Box.createRigidArea(new Dimension(0, 170)));
        options.add(imready);

        mainPanel.add(Box.createRigidArea(new Dimension(900, 100)));
        mainPanel.add(options);

        this.setVisible(true);
        this.add(lobbyPanel);
    }

    /**
     * Adds a user
     *
     * @param user the username
     */
    public void addUser(String user) {
        System.out.println(user + " added!");
        for (int i = 0; i < playerList.length; i++) {
            if (playerList[i].equals("")) {
                playerList[i] = user;
                return;
            }
        }
    }

    /**
     * Removes a user
     *
     * @param user the username
     */
    public void removeUser(String user) {
        for (int i = 0; i < playerList.length; i++) {
            if (playerList[i].equals(user)) {
                playerList[i] = "";
                return;
            }
        }
    }

    /**
     * private class for the keylistener to check for key presses
     */
    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        /**
         * check for key presses
         *
         */
        public void keyPressed(KeyEvent e) {
            //exit if esc is pressed
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                dispose();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    /**
     * MainPanel
     */
    private class MainPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g); // required
            this.setDoubleBuffered(true);
            g.drawImage(lobby, 0, 0, null);
            g.setColor(new Color(169,169,169));

            //get the font
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, new File("graphics/fonts/josefin.ttf"));
            } catch (FontFormatException e) {
                System.out.println("Font format is incorrect.");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Font file not found.");
                e.printStackTrace();
            }

            //set the font size
            font = font.deriveFont(Font.PLAIN,20);
            //set the font for use in graphics g
            g.setFont(font);

            //set text color
            g.setColor(new Color(130,130,130));
            for (int i = 0; i < playerList.length; i++) {

                g.drawString(playerList[i], 500, 110 + 20 * i);
            }
            repaint();
        }
    }

    /**
     * ReadyButtonListener
     */
    private class ReadyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            c.output.println(c.userName);
            ready = !ready;
            if (ready) {
                c.output.println("/ready");
            } else {
                c.output.println("/unready");
            }
            c.output.flush();
        }
    }

}
