
/**
 * Name: Client.java
 * Version: 2.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 14, 2019
 */

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Client extends JFrame {

    //declaring variables
    //declaring variables
    private JTextField typeField;
    private JTextArea msgArea;
    private Socket mySocket; // socket for connection
    private BufferedReader input; // reader for network stream
    public PrintWriter output; // printwriter for network output
    private boolean running; // thread status via boolean
    public String userName;
    private String admin;
    private Lobby l;
    public JFrame window1;
    private JPanel panel;
    private JLabel label;
    private JButton button;
    private ArrayList<JPanel> listData;
    private ArrayList<String> blockedUsers;
    HashMap<String, Player> map;
    private Game g;
    private JPanel status;
    private JFrame frame;
    Image background;
    ArrayList<Player> players = new ArrayList<Player>();

    /**
     * Main
     * Runs the client interface
     *
     * @param args parameters from command line
     */
    //	public static void main(String[] args) {
    //		new ChatClient().login();
    //	}

    Client() {

        frame = this;
        JPanel panel = new Panel();
        JTextArea userName = new JTextArea("jason");
        userName.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        userName.setFont(new Font("Arial", Font.PLAIN, 50));
        userName.setSize(339, 80);
        userName.setOpaque(false);
        userName.setForeground(new Color(169, 169, 169));

        JTextArea port = new JTextArea("5000");
        port.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        port.setFont(new Font("Arial", Font.PLAIN, 50));
        port.setSize(339, 80);
        port.setOpaque(false);
        port.setForeground(new Color(169, 169, 169));

        JTextArea ip = new JTextArea("localhost");
        ip.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        ip.setFont(new Font("Arial", Font.PLAIN, 50));
        ip.setSize(339, 80);
        ip.setOpaque(false);
        ip.setForeground(new Color(169, 169, 169));

        this.setSize(911, 561);
        this.setLocation((int) (Game.screenX / 2) - 476, ((int) (Game.screenY / 2) - 281));

        ip.setLocation(494, 85);

        port.setLocation(494, 195);

        userName.setLocation(494, 305);

        panel.setLayout(null);

        JButton start = new JButton(new ImageIcon("graphics/join_server.png"));
        start.setContentAreaFilled(false);
        start.setFocusable(false);
        start.setBorderPainted(false);
        start.setBounds(450, 380, 400, 145);
        start.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Thread t1 = new Thread(new Runnable() {
                    public void run() {
                        frame.dispose();
                        go(userName.getText(), ip.getText(), Integer.parseInt(port.getText()));

                    }
                });
                t1.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                start.setIcon(new ImageIcon("graphics/join_server_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                start.setIcon(new ImageIcon("graphics/join_server.png"));
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

        });
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

            }

        });

        panel.add(Box.createRigidArea(new Dimension(500, 0)));
        panel.add(start);
        panel.add(userName);
        panel.add(ip);
        panel.add(port);

        this.setResizable(false);
        this.setContentPane(panel);
        this.setVisible(true);

        background = Toolkit.getDefaultToolkit().getImage("graphics/client_menu.png");

    }

    /**
     * login
     * Allows the user to choose userName, server IP and the port number
     */
    public void login() {
        //getting user userName
        //		String userName = JOptionPane.showInputDialog("Please enter your username (Without spaces)");
        //		if (userName == null) {
        //			running = false;
        //			return;
        //		}
        //		userName = userName.trim();
        //
        //		//making sure the userName entered is valid
        //		if (!userName.matches("[a-zA-Z0-9]+")) {
        //			JOptionPane.showMessageDialog(null, "Username must only contain alphanumeric characters!");
        //			login();
        //			return;
        //		}
        //
        //		//getting the Ip address that the client want to connect to
        //		String ipAddress = JOptionPane.showInputDialog("Please enter your Ip Address");
        //		if (ipAddress == null) {
        //			running = false;
        //			return;
        //		}
        //
        //		//if the Ip address is localhost convert it to 127.0.0.1 which is the same thing
        //		if (ipAddress.equals("localhost")) {
        //			ipAddress = "127.0.0.1";
        //		}
        //
        //		//making sure the ip address entered is valid
        //		if (!ipAddress.matches("[0-9.]+")) {
        //			JOptionPane.showMessageDialog(null, "IP must only contain digits and periods!");
        //			login();
        //			return;
        //		}
        //
        //		//getting the port num that the user would like to connect to
        //		String portNum = JOptionPane.showInputDialog("Please enter a port number");
        //		if (portNum == null) {
        //			running = false;
        //			return;
        //		}
        //
        //		//making sure the port number entered is valid
        //		if (!portNum.matches("[0-9]+")) {
        //			JOptionPane.showMessageDialog(null, "Port must be a number!");
        //			login();
        //			return;
        //		}

    }

    /**
     * go
     * Runs the chat client UI
     *
     * @param username1, the userName of the user
     * @param ip,        the server Ip address the the client wants to connect to
     * @param port,      the port that client wants to make a connection on
     */
    public void go(String username1, String ip, int port) {
        //Creating the chat client UI
        blockedUsers = new ArrayList<String>();
        map = new HashMap<String, Player>();
        listData = new ArrayList<JPanel>();
        window1 = new JFrame("Chat Client");
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 0));
        JButton sendButton = new JButton("SEND");
        sendButton.addActionListener(new SendButtonListener());
        JButton clearButton = new JButton("QUIT");
        clearButton.addActionListener(new QuitButtonListener());
        JLabel errorLabel = new JLabel("");
        userName = username1;
        typeField = new JTextField(10);

        //creating message area and setting it as not editable
        msgArea = new JTextArea();
        msgArea.setEditable(false);

        //adding scroll to the message area
        JScrollPane scroll = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        typeField.addActionListener(new EnterListener());

        //adding all panel to the main screen
        southPanel.add(typeField);
        southPanel.add(sendButton);
        southPanel.add(errorLabel);
        southPanel.add(clearButton);
        status = new JPanel();
        status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));
        //adding and setting size of scroll wheel
        JScrollPane scrollList = new JScrollPane(status, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setPreferredSize(new Dimension(100, 309));
        scrollList.setMaximumSize(scrollList.getPreferredSize());
        scrollList.setMinimumSize(scrollList.getMinimumSize());

        //set window to be visible
        window1.add(BorderLayout.WEST, scrollList);
        window1.add(BorderLayout.CENTER, scroll);
        window1.add(BorderLayout.SOUTH, southPanel);
        window1.setSize(600, 400);
        //		window1.setVisible(true);
        // call a method that connects to the server
        l = new Lobby(this);
        connect(ip, port);
        running = true;
        // after connecting loop and keep appending[.append()] to the JTextArea
        window1.revalidate();

        readMessagesFromServer();
    }

    /**
     * connect
     * Attempts to connect to the server and creates the socket and streams
     *
     * @param ip   the ip address
     * @param port the port humber
     * @return a socket
     */
    private Socket connect(String ip, int port) {
        System.out.println("Attempting to make a connection..");

        try {
            //attempt socket connection, wait until a connection is made
            mySocket = new Socket(ip, port);

            //steam for network input
            InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream());
            input = new BufferedReader(stream1);
            //assign printwriter to network stream
            output = new PrintWriter(mySocket.getOutputStream());

            //send new userName to server to allow all clients to see new user joining the chat
            msgArea.append(userName + " has joined the chat.\n");
            output.println(userName);
            output.flush();

            //setting status of user to active
            panel = new JPanel();
            label = new JLabel(userName);
            panel.add(label);
            players.add(new Player(userName));
            map.put(userName, new Player(userName));
            button = new JButton("Details");
            button.addActionListener(new InformationActionListener(userName));
            panel.add(button);
            listData.add(panel);
            status.add(panel);
            status.revalidate();
            status.repaint();
            String S = input.readLine();

            // duplicate user
            if (S.equals("Username exists!")) {
                JOptionPane.showMessageDialog(null, "Username already exists");
                window1.dispose();
                login();
                running = false;
                return null;
            }
            admin = S;
            // updates status of everyone else on the server
            //            while (true) {
            //
            //                // get username
            //                String userName = input.readLine();
            //
            //                if (userName == null || userName.equals("")) {
            //                    break;
            //                }
            //                //get the status that the user would like to change to
            //                String money = input.readLine();
            //
            //                //Changed user status based on the user's command
            //                // checks for valid status
            //
            //                //updating the user's new status
            //                label = new JLabel(userName);
            ////                listData.add(label);
            //                status.add(label);
            //                status.revalidate();
            //                status.repaint();
            ////                map.put(userName, money);
            //            }
        } catch (IOException e) { // connection error occurred
            //Show that connection to server failed
            System.out.println("Connection to Server Failed");
            window1.dispose();
            JOptionPane.showMessageDialog(null, "Connection to Server Failed");
            login();
        }

        System.out.println("Connection made.");
        return mySocket;
    }

    /**
     * readMessagesFromServer
     * This method waits for server input and then displays it on the UI
     */
    private void readMessagesFromServer() {

        while (running) { // loop unit a message is received
            try {

                if (input.ready()) { // check for an incoming messge
                    String msg, user, privateMessage = null;
                    user = input.readLine();
                    msg = input.readLine(); // read the message
                    if (user.startsWith("FROM ")) {
                        privateMessage = user.substring(5);
                    }
                    // cannot block admin
                    if ((!blockedUsers.contains(user) && !blockedUsers.contains(privateMessage))
                            || user.equals("admin")) {

                        // admin command
                        System.out.println(msg);
                        if (user.equals(admin) && msg.startsWith("/")) {

                            // user is banned
                            if (msg.equals("/ban")) {

                                // tell user they are banned
                                for (int i = 0; i < 20; i++) {
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    msgArea.append("YOU ARE BANNED\n");
                                }

                                // exit
                                window1.dispose();
                                JOptionPane.showMessageDialog(null, "You have been banned");
                                running = false;
                                System.exit(-1);

                                // user is kicked
                            } else if (msg.equals("/kick")) {

                                // tells user they are kicked
                                for (int i = 0; i < 20; i++) {
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    msgArea.append("YOU ARE KICKED\n");
                                }

                                // exit window
                                window1.dispose();
                                JOptionPane.showMessageDialog(null, "You have been Kicked");
                                running = false;
                                System.exit(-1);
                                //login();

                                // server stop
                            } else if (msg.startsWith("/stop")) {

                                // tells user that the server has stopped
                                for (int i = 0; i < 20; i++) {
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    msgArea.append("SERVER CLOSING\n");
                                }

                                // exit window
                                window1.dispose();
                                JOptionPane.showMessageDialog(null, "Server closed");
                                running = false;
                                System.exit(-1);
                            } else if (msg.equals("/start")) {
                                l.dispose();
                                g = new Game(this, map.keySet(), userName);
                            }
                        }
                        if (msg.equals("")) {
                            msgArea.append(user + " disconnected.\n");

                            // update status
                        } else if (msg.startsWith("/status")) {
                            String money = msg.split(" ")[1];
                            String salary = msg.split(" ")[2];

                            // new user joining
                            if (!map.containsKey(user)) {
                                label = new JLabel(user);
                                Player p = new Player(user);
                                p.setMoney(Integer.parseInt(money));
                                //                                p.setSalary(Integer.parseInt(salary));
                                players.add(p);
                                map.put(user, p);
                                panel = new JPanel();
                                panel.add(label);
                                button = new JButton("Details");
                                button.addActionListener(new InformationActionListener(user));
                                panel.add(button);
                                listData.add(panel);
                                status.add(panel);
                                status.revalidate();
                                status.repaint();
                                msgArea.append(user + " joined the chat.\n");
                            } else {
                                // update status and not new
                                Player p = map.get(user);
                                p.setMoney(Integer.parseInt(money));
                                if (!salary.equals("0")) {
                                    p.getCareer().setSalary((int) (p.getCareer().getSalary() * 1.05));
                                }
                            }
                        } else if (msg.startsWith("/spin")) {
                            Game.spin(Double.parseDouble(msg.split(" ")[1]));
                        } else if (msg.startsWith("/removep")) {
                            map.get(user).addProperty(g.properties.get(Integer.parseInt(msg.split(" ")[1])));
                            g.soldProperties[Integer.parseInt(msg.split(" ")[1])] = true;
                        } else if (msg.startsWith("/removecc")) {
                            map.get(user).setCareer(g.collegeCareers.get(Integer.parseInt(msg.split(" ")[1])));
                            g.collegeCareers.remove(Integer.parseInt(msg.split(" ")[1]));
                        } else if (msg.startsWith("/removec")) {
                            map.get(user).setCareer(g.normalCareers.get(Integer.parseInt(msg.split(" ")[1])));
                            g.normalCareers.remove(Integer.parseInt(msg.split(" ")[1]));
                        } else {
                            // regular user message
                            msgArea.append(user + ": " + msg + "\n");
                        }

                    }
                }

                // failed
            } catch (IOException e) {
                System.out.println("Failed to receive msg from the server");
                e.printStackTrace();
            }
        }
        try { // after leaving the main loop we need to close all the sockets
            input.close();
            output.close();
            mySocket.close();
        } catch (Exception e) {
            System.out.println("Failed to close socket");
        }

    }
    // **** Inner Classes for Action Listeners **

    // send - send msg to server (also flush), then clear the JTextField
    class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // Send a message to the client
            String msg = typeField.getText().trim();

            // is a command
            if (msg.startsWith("/")) {

                // displays all commands
                if (msg.startsWith("/help") || msg.startsWith("/?")) {
                    msgArea.append("/help or /? - displays all available commands\n");
                    msgArea.append("/stop - stops the server");
                    msgArea.append("/ban userName - bans ip from server\n");
                    msgArea.append("/kick userName - kicks user from server\n");
                    msgArea.append("/block userName - ignores all message from user\n");
                    msgArea.append("/msg userName message - sends a private message to user\n");

                    // stops server
                } else if (msg.startsWith("/stop")) {

                    // works only with admin
                    if (userName.equals(admin)) {
                        msgArea.append("Stopping server\n");
                        output.println(userName);
                        output.println(msg);
                    } else {
                        msgArea.append("Do not have the privileges for this\n");
                    }

                    // ban user(s)
                } else if (msg.startsWith("/ban")) {

                    // only works with admin
                    if (userName.equals(admin)) {
                        output.println(userName);
                        output.println(msg);
                    } else {
                        msgArea.append("Do not have the privileges for this\n");
                    }

                    // kick user(s)
                } else if (msg.startsWith("/kick")) {

                    // only works with users
                    if (userName.equals(admin)) {
                        output.println(userName);
                        output.println(msg);
                    } else {
                        msgArea.append("Do no have the privileges for this\n");
                    }

                    // block user(s)
                } else if (msg.startsWith("/block")) {
                    String[] block = msg.trim().split(" ");
                    for (int i = 1; i < block.length; i++) {

                        // blocking twice unblocks
                        if (blockedUsers.contains(block[i])) {
                            blockedUsers.remove(block[i]);
                            msgArea.append("Unblocked " + block[i] + "\n");
                        } else {
                            // block user
                            blockedUsers.add(block[i]);
                            msgArea.append("Blocked " + block[i] + "\n");
                        }

                    }

                    // message another user
                } else if (msg.startsWith("/msg")) {
                    output.println(userName);
                    output.println(msg);
                } else {
                    msgArea.append("Invalid command!\n");
                }
            } else {
                // Message must be entered
                if (msg.equals("")) {
                    return;
                }
                // sends infomration to server
                output.println(userName);
                output.println(msg);
            }

            output.flush();
            typeField.setText("");
        }
    }

    // QuitButtonListener - Quit the program
    class QuitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            // tells everyone user has disconnected
            output.println(userName);
            output.println();
            output.flush();
            running = false;
            window1.dispose();
            //login();
        }
    }

    // EnterListener - Send message
    class EnterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // Send a message to the client
            String msg = typeField.getText().trim();

            // is a command
            if (msg.startsWith("/")) {

                // displays all commands
                if (msg.startsWith("/help") || msg.startsWith("/?")) {
                    msgArea.append("/help or /? - displays all available commands\n");
                    msgArea.append("/stop - stops the server");
                    msgArea.append("/ban userName - bans ip from server\n");
                    msgArea.append("/kick userName - kicks user from server\n");
                    msgArea.append("/block userName - ignores all message from user\n");
                    msgArea.append("/msg userName message - sends a private message to user\n");

                    // stops server
                } else if (msg.startsWith("/stop")) {

                    // works only with admin
                    if (userName.equals(admin)) {
                        msgArea.append("Stopping server\n");
                        output.println(userName);
                        output.println(msg);
                    } else {
                        msgArea.append("Do not have the privileges for this\n");
                    }

                    // ban user(s)
                } else if (msg.startsWith("/ban")) {

                    // only works with admin
                    if (userName.equals(admin)) {
                        output.println(userName);
                        output.println(msg);
                    } else {
                        msgArea.append("Do not have the privileges for this\n");
                    }

                    // kick user(s)
                } else if (msg.startsWith("/kick")) {

                    // only works with users
                    if (userName.equals(admin)) {
                        output.println(userName);
                        output.println(msg);
                    } else {
                        msgArea.append("Do no have the privileges for this\n");
                    }

                    // block user(s)
                } else if (msg.startsWith("/block")) {
                    String[] block = msg.trim().split(" ");
                    for (int i = 1; i < block.length; i++) {

                        // blocking twice unblocks
                        if (blockedUsers.contains(block[i])) {
                            blockedUsers.remove(block[i]);
                            msgArea.append("Unblocked " + block[i] + "\n");
                        } else {
                            // block user
                            blockedUsers.add(block[i]);
                            msgArea.append("Blocked " + block[i] + "\n");
                        }

                    }

                    // message another user
                } else if (msg.startsWith("/msg")) {
                    output.println(userName);
                    output.println(msg);
                } else {
                    msgArea.append("Invalid command!\n");
                }
            } else {
                // Message must be entered
                if (msg.equals("")) {
                    return;
                }
                // sends infomration to server
                output.println(userName);
                output.println(msg);
            }

            output.flush();
            typeField.setText("");
        }
    }

    private class InformationActionListener implements ActionListener {
        String user;

        InformationActionListener(String S) {
            user = S;
            l.addUser(S);
        }

        public void actionPerformed(ActionEvent e) {
            Player p = map.get(user);
            JFrame frame = new JFrame();
            frame.setSize(300, 500);
            JLabel name = new JLabel("Name: " + p.getName());
            JLabel family = new JLabel("Family Size: " + p.family);
            JLabel money = new JLabel("Money: " + p.getMoney());
            JLabel career = new JLabel("Career: " + (p.getCareer() == null ? "NA" : p.getCareer().getCareerName()));
            JLabel salary = new JLabel("Salary: " + (p.getCareer() == null ? "NA" : p.getCareer().getSalary()));
            JLabel house = new JLabel("Property: " + (p.getProperty() == null ? "NA" : p.getProperty().getName()));
            JButton exit = new JButton("Exit");
            exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(name);
            panel.add(family);
            panel.add(money);
            panel.add(career);
            panel.add(salary);
            panel.add(house);
            panel.add(exit);
            frame.setContentPane(panel);
            frame.setVisible(true);
        }
    }
}

class Panel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // required
        this.setDoubleBuffered(true);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("graphics/client_menu.png"), 0, 0, null);
        repaint();

    }
}
