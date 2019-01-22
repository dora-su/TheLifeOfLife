
/**
 * Name: Client.java
 * Version: 2.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 14, 2019
 */

// Swing imports
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

// Graphics
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

// Events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;

// IO
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// net
import java.net.Socket;

// data structures
import java.util.ArrayList;
import java.util.HashMap;

public class Client extends JFrame {
    // declaring variables
    private JTextField typeField; // where you type your message
    private JTextArea msgArea; // where messages are declared
    private Socket mySocket; // socket for connection
    private BufferedReader input; // reader for network stream
    public PrintWriter output; // printwriter for network output
    private boolean running; // thread status via boolean
    public String userName; // clients username
    private String admin; // admin username
    private Lobby l; // lobby reference
    public JFrame window1; // chat client window
    private JPanel panel; // panel for status of user
    private JLabel label; // username label
    private JButton button; // details  buton
    private ArrayList<JPanel> listData; // arraylist of panels with user data
    private ArrayList<String> blockedUsers; // blocked users
    HashMap<String, Player> map; // used to find player objects by their username
    private Game g; // game reference
    private JPanel status; // panel that contains list of object
    private JFrame frame; // frame with 
    private Image background; // background image
    ArrayList<Player> players = new ArrayList<Player>(); // list of all players
    private Player p; // reference to current player
    private int idx; // index of the player in the arraylist
    private ImageIcon icon; // icon for the frame
    private Font font; // font for text

    /**
     * Constructor
     */
    Client() {
    	// create reference to current frame
        frame = this;

        // set icon image
        icon = new ImageIcon("graphics/icon.png");
        this.setIconImage(icon.getImage());
        
        JPanel panel = new Panel();

        // get the font
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("graphics/fonts/josefin.ttf"));
        } catch (FontFormatException e) {
            System.out.println("Font format is incorrect.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Font file not found.");
            e.printStackTrace();
        }

        // set the font size
        font = font.deriveFont(Font.PLAIN, 50);
        // username text area
        JTextArea userName = new JTextArea("");
        userName.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        userName.setFont(font);
        userName.setSize(339, 80);
        userName.setOpaque(false);
        userName.setForeground(new Color(169, 169, 169));
        
        // port text area
        JTextArea port = new JTextArea("5000");
        port.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        port.setFont(font);
        port.setSize(339, 80);
        port.setOpaque(false);
        port.setForeground(new Color(169, 169, 169));
        
        // ip address text area
        JTextArea ip = new JTextArea("localhost");
        ip.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        ip.setFont(font);
        ip.setSize(339, 80);
        ip.setOpaque(false);
        ip.setForeground(new Color(169, 169, 169));
        
        // set window size
        this.setSize(911, 561);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        // set locations in window
        ip.setLocation(494, 90);
        port.setLocation(494, 205);
        userName.setLocation(494, 310);

        panel.setLayout(null);
        
        // button to join server
        JButton start = new JButton(new ImageIcon("graphics/join_server.png"));
        start.setContentAreaFilled(false);
        start.setFocusable(false);
        start.setBorderPainted(false);
        start.setBounds(450, 380, 400, 145);
        
        // mouse listener
        start.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Thread t1 = new Thread(new Runnable() {
                    public void run() {
                    	
                    	// makes sure username is not blank and contains numbers or letters
                        if (!userName.getText().matches("[a-zA-Z0-9]+") || userName.getText() == null) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid username");
                            return;
                        }
                        
                        // when ip is localhost it is automatically changed to 127.0.0.1
                        String ipA = "";
                        if (ip.getText().equals("localhost")) {
                            ipA = "127.0.0.1";
                        } else {
                            ipA = ip.getText();
                        }
                        
                        // Checks for valid ip address
                        if (!ipA.matches("[0-9.]+")) {
                            JOptionPane.showMessageDialog(null, "IP must only contain digits and periods!");
                            return;
                        }

                        // port must be a number
                        if (!port.getText().matches("[0-9]+")) {
                            JOptionPane.showMessageDialog(null, "Port must be a number!");
                            return;
                        }
                        
                        // close frame and run the client
                        frame.dispose();
                        go(userName.getText(), ipA, Integer.parseInt(port.getText()));

                    }
                });
                // start thread
                t1.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	//setting hover image
                start.setIcon(new ImageIcon("graphics/join_server_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	//setting hover image
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
        // add boxes to main panel
        panel.add(Box.createRigidArea(new Dimension(500, 0)));
        panel.add(start);
        panel.add(userName);
        panel.add(ip);
        panel.add(port);

        this.setResizable(false);
        this.setContentPane(panel);
        this.setVisible(true);
        // set background image
        background = Toolkit.getDefaultToolkit().getImage("graphics/client_menu.png");

    }

    /**
     * go Runs the chat client UI
     *
     * @param username1, the userName of the user
     * @param ip,        the server Ip address the the client wants to connect to
     * @param port,      the port that client wants to make a connection on
     */
    public void go(String username1, String ip, int port) {
        // Creating the chat client UI
        blockedUsers = new ArrayList<String>();
        map = new HashMap<String, Player>();
        listData = new ArrayList<JPanel>();
        window1 = new JFrame("Chat Client");
        // panels
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 0));
        JButton sendButton = new JButton("SEND");
        sendButton.addActionListener(new SendButtonListener());
        JButton clearButton = new JButton("QUIT");
        clearButton.addActionListener(new QuitButtonListener());
        JLabel errorLabel = new JLabel("");
        userName = username1;
        typeField = new JTextField(10);

        // creating message area and setting it as not editable
        msgArea = new JTextArea();
        msgArea.setEditable(false);

        // adding scroll to the message area
        JScrollPane scroll = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        typeField.addActionListener(new EnterListener());

        // adding all panel to the main screen
        southPanel.add(typeField);
        southPanel.add(sendButton);
        southPanel.add(errorLabel);
        southPanel.add(clearButton);
        status = new JPanel();
        status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));
        // adding and setting size of scroll wheel
        JScrollPane scrollList = new JScrollPane(status, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setPreferredSize(new Dimension(100, 309));
        scrollList.setMaximumSize(scrollList.getPreferredSize());
        scrollList.setMinimumSize(scrollList.getMinimumSize());

        // set window to be visible
        window1.add(BorderLayout.WEST, scrollList);
        window1.add(BorderLayout.CENTER, scroll);
        window1.add(BorderLayout.SOUTH, southPanel);
        window1.setSize(600, 400);
        // window1.setVisible(true);
        // call a method that connects to the server
        l = new Lobby(this);
        connect(ip, port);
        running = true;
        // after connecting loop and keep appending[.append()] to the JTextArea
        window1.revalidate();

        readMessagesFromServer();
    }

    /**
     * connect Attempts to connect to the server and creates the socket and streams
     *
     * @param ip   the ip address
     * @param port the port humber
     * @return a socket
     */
    private Socket connect(String ip, int port) {
        System.out.println("Attempting to make a connection..");

        try {
            // attempt socket connection, wait until a connection is made
            mySocket = new Socket(ip, port);

            // steam for network input
            InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream());
            input = new BufferedReader(stream1);
            // assign printwriter to network stream
            output = new PrintWriter(mySocket.getOutputStream());

            // send new userName to server to allow all clients to see new user joining the
            // chat
            msgArea.append(userName + " has joined the chat.\n");
            output.println(userName);
            output.flush();

            // setting status of user to active
            panel = new JPanel();
            label = new JLabel(userName);
            panel.add(label);
            p = new Player(userName);
            p.setClient(this);
            map.put(userName, p);
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
                l.dispose();
                window1.dispose();
                running = false;
                return null;
            }
            // get admin and index of player
            admin = S.split(" ")[0];
            idx = Integer.parseInt(S.split(" ")[1]);
        } catch (IOException e) { // connection error occurred
            // Show that connection to server failed
            System.out.println("Connection to Server Failed");
            window1.dispose();
            JOptionPane.showMessageDialog(null, "Connection to Server Failed");
        }
        // valid connection
        System.out.println("Connection made.");
        return mySocket;
    }

    /**
     * readMessagesFromServer This method waits for server input and then displays
     * it on the UI
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
                        // System.out.println(msg);
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
                                // login();

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
                                // start game
                                players.add(idx, p);

                                l.dispose();
                                g = new Game(this, userName);
                            }
                        }
                        if (!msg.startsWith("/start")) {
                            if (msg.equals("")) {
                                msgArea.append(user + " disconnected.\n");
                                l.removeUser(user);

                                // update status
                            } else if (msg.startsWith("/status")) {
                                String money = msg.split(" ")[1];
                                String salary = msg.split(" ")[2];

                                // new user joining
                                if (!map.containsKey(user)) {

                                    label = new JLabel(user);
                                    Player p = new Player(user);
                                    p.setMoney(Integer.parseInt(money));
                                    // add to list of players
                                    players.add(p);
                                    map.put(user, p);
                                    panel = new JPanel();
                                    panel.add(label);
                                    // add button to display player details
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
                                    Player pl = map.get(user);
                                    if (!pl.equals(this.p)) {
                                        pl.setMoney(Integer.parseInt(money));
                                        if (!salary.equals("0") && pl.getCareer() != null && p.getCareer() != null) {
                                            pl.getCareer().setSalary((int) (p.getCareer().getSalary() * 1.05));
                                        }
                                    }
                                }
                            } else if (msg.startsWith("/spin")) {
                                // spinning again
                                if (!players.get(g.turn % players.size()).equals(map.get(user))) {
                                    g.turn--;
                                }

                                Game.spin(Double.parseDouble(msg.split(" ")[1]), true);
                            } else if (msg.startsWith("/removep")) {
                                map.get(user).addProperty(g.properties.get(Integer.parseInt(msg.split(" ")[1])));
                                g.soldProperties[Integer.parseInt(msg.split(" ")[1])] = true;
                            } else if (msg.startsWith("/removecc")) {
                                map.get(user).setCareer(g.collegeCareers.get(Integer.parseInt(msg.split(" ")[1])));
                                g.collegeCareers.remove(Integer.parseInt(msg.split(" ")[1]));
                            } else if (msg.startsWith("/removec")) {
                                map.get(user).setCareer(g.normalCareers.get(Integer.parseInt(msg.split(" ")[1])));
                                g.normalCareers.remove(Integer.parseInt(msg.split(" ")[1]));
                            } else if (msg.startsWith("/tile")) {
                                map.get(user).newTile = Integer.parseInt(msg.split(" ")[1]);
                            } else {
                                // regular user message
                                msgArea.append(user + ": " + msg + "\n");
                            }
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
            window1.dispose();
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
            // information about a player
            Player p = map.get(user);
            // jlabels with information
            JFrame frame = new JFrame();
            frame.setSize(300, 500);
            //setting labels for player info
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
            //adding details jlabel for player information
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            // add jlabels to panel
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

//inner class
class Panel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // required
        this.setDoubleBuffered(true);
        //draw client menu background
        g.drawImage(Toolkit.getDefaultToolkit().getImage("graphics/client_menu.png"), 0, 0, null);
        repaint();

    }
}
