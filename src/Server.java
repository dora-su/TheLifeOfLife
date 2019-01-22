/**
 * Name: Server.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 10, 2019
 */
//imports for network communication

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

class Server extends JFrame {

    //Declaring variables
    ServerSocket serverSock;// server socket for connection
    String admin;
    static Boolean running = true; // controls if the server is accepting clients
    public static ArrayList<Client> clientList = new ArrayList<Client>();
    static ArrayList<InetAddress> bannedIps = new ArrayList<InetAddress>();
    static HashMap<String, Client> map = new HashMap<String, Client>();
    static int ready;
    JFrame frame;

    Image background;

    private ImageIcon icon;
    private Font font;

    /**
     * Constructor
     */
    Server() {
        frame = this;

        //set icon image
        icon = new ImageIcon("graphics/icon.png");
        this.setIconImage(icon.getImage());

        this.setSize(911, 561);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);

        JPanel panel = new Panel();

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
        font = font.deriveFont(Font.PLAIN, 50);

        JTextArea port = new JTextArea("5000");
        port.setOpaque(false);
        port.setFont(font);
        port.setForeground(new Color(169, 169, 169));
        // set size
        port.setSize(348, 82);
        port.setLocation(495, 285);
        panel.setLayout(null);
        // start server
        JButton start = new JButton(new ImageIcon("graphics/server.png"));
        start.setContentAreaFilled(false);
        start.setFocusable(false);
        start.setBorderPainted(false);
        start.setBounds(450, 380, 400, 145);
        start.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                // valid port
                if (!port.getText().matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(null, "Port must be a number!");
                    return;

                }
                Thread t1 = new Thread(new Runnable() {
                    public void run() {

                        if (!port.getText().matches("[0-9]+")) {
                            JOptionPane.showMessageDialog(null, "Port must be a number!");
                            return;
                        }
                        frame.dispose();
                        go(port.getText());

                    }
                });
                t1.start();

            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                start.setIcon(new ImageIcon("graphics/server_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                start.setIcon(new ImageIcon("graphics/server.png"));
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }

        });
        // ip address
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // ip address
        String ip = ipAddress.toString();

        ip = ip.substring(ip.indexOf(("/")) + 1);
        System.out.println(ip);
        //set the font size
        font = font.deriveFont(Font.PLAIN, 35);
        // ip label
        JLabel label = new JLabel("" + ip);
        label.setFont(font);
        label.setForeground(new Color(169, 169, 169));
        label.setSize(400, 145);
        // set label location
        label.setLocation(495, 127);
        // add items to panel
        panel.add(Box.createRigidArea(new Dimension(500, 0)));
        panel.add(start);
        panel.add(port);
        panel.add(label);

        this.setResizable(false);
        this.setContentPane(panel);
        this.setVisible(true);
        // background image
        background = Toolkit.getDefaultToolkit().getImage("graphics/server_menu.png");

    }

    /**
     * Starts the server
     *
     * @param portNum the port number
     */
    public void go(String portNum) {

        System.out.println("Waiting for a client connection..");
        // hold the client connection
        Socket client = null;

        try {
            // assigns an port to the server
            serverSock = new ServerSocket(Integer.parseInt(portNum));
            //serverSock.setSoTimeout(30000); // 15 second timeout

            while (running) { // this loops to accept multiple clients
                client = serverSock.accept(); // wait for connection

                //if the user is on the banned list, refuse the connection
                System.out.println("Client connected");
                if (bannedIps.contains(client.getInetAddress())) {
                    System.out.println("Banned ip tried to connect");
                    client.close();
                    continue;
                }
                // full game
                if (clientList.size() == 6) {
                    JOptionPane.showMessageDialog(null, "Full Game!");
                    client.close();
                    continue;
                }
                //establishing input streams
                BufferedReader br;
                InputStreamReader stream = new InputStreamReader(client.getInputStream());
                br = new BufferedReader(stream);
                String userName = br.readLine();
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                //if the userName is already in use close client
                if (map.containsKey(userName)) {
                    System.out.println("User with same name as another user tried to connect.");
                    pw.println("Username exists!");
                    pw.flush();
                    client.close();
                    continue;
                } else {
                    if (clientList.isEmpty()) {
                        admin = userName;
                    }
                    pw.println(admin + " " + clientList.size());
                    pw.flush();
                }

                //add the client to the client list and set as active
                for (Client c : clientList) {
                    pw.println(c.user);
                    pw.println("/status 1000000 0");
                    pw.flush();
                    c.output.println(userName);
                    c.output.println("/status 1000000 0");
                    c.output.flush();
                }

                //add the client to the client list
                clientList.add(new Client(client, userName));
                //				pw.println("");
                //				pw.flush();

                Thread t = new Thread(new ConnectionHandler(client)); // create a thread for the new client and pass in
                // the socket
                t.start(); // start the new thread
            }
        } catch (Exception e) {
            // System.out.println("Error accepting connection");
            // close all and quit
            try {
                client.close();
            } catch (Exception e1) {
                System.out.println("Failed to close socket");
            }
            System.exit(-1);
        }
    }

    // *** Inner class - thread for client connection
    class ConnectionHandler implements Runnable {
        private PrintWriter output; // assign printwriter to network stream
        private BufferedReader input; // Stream for network input
        private Socket client; // keeps track of the client socket
        private boolean running;

        /**
         * ConnectionHandler Constructor
         *
         * @param s socket belonging to this client connection
         */
        ConnectionHandler(Socket s) {
            this.client = s; // constructor assigns client to this
            try { // assign all connections to client
                this.output = new PrintWriter(client.getOutputStream());
                InputStreamReader stream = new InputStreamReader(client.getInputStream());
                this.input = new BufferedReader(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            running = true;
        } // end of constructor

        /**
         * run
         * executed on start of thread
         */
        public void run() {
            // Get a message from the client
            String msg, username;
            // Get a message from the client
            while (running) {
                // loop unit a message is received
                try {
                    if (input.ready()) { // check for an incoming messge
                        username = input.readLine(); //get userName from client
                        msg = input.readLine(); // get a message from the client
//                        System.out.println(msg);
                        //check if the message is a command
                        if (msg.startsWith("/")) {
                            if (msg.startsWith("/stop")) {
                                // tells all clients that the server is closing, only admin can close
                                for (Client c : clientList) {
                                    c.output.println(admin);
                                    c.output.println(msg);
                                    c.output.flush();
                                }
                                System.out.println("Server stopped");
                                // close server
                                serverSock.close();
                            } else if (msg.startsWith("/ban")) { //ban the user
                                // can ban multiple clients at the same time
                                String[] bannedClients = msg.trim().split(" ");
                                for (int i = 1; i < bannedClients.length; i++) {
                                    Client banned = map.get(bannedClients[i]);
                                    // client name is not a real client
                                    if (banned == null) {
                                        continue;
                                    }
                                    // put in ip list
                                    bannedIps.add(banned.client.getInetAddress());

                                    // tell banned client that they have been banned
                                    banned.output.println(admin);
                                    banned.output.println("/ban");
                                    banned.output.flush();
                                    banned.client.close();
                                    // ban user
                                    clientList.remove(banned);
                                    for (Client c : clientList) {
                                        c.output.println(banned.user);
                                        c.output.println("/status -999999999 0");
                                        c.output.flush();
                                    }
                                }
                            } else if (msg.startsWith("/kick")) { //kick the user
                                // Multiple clients can be banned
                                String[] kickedClients = msg.trim().split(" ");
                                for (int i = 1; i < kickedClients.length; i++) {
                                    Client kicked = map.get(kickedClients[i]);
                                    // client name is not a real client
                                    if (kicked == null) {
                                        continue;
                                    }
                                    // tells kicked client that they have been kicked
                                    kicked.output.println(admin);
                                    kicked.output.println("/kick");
                                    kicked.output.flush();
                                    kicked.client.close();
                                    clientList.remove(kicked);
                                    for (Client c : clientList) {
                                        c.output.println(kicked.user);
                                        c.output.println("/status -999999999 0");
                                        c.output.flush();
                                    }
                                }
                            } else if (msg.startsWith("/msg")) { //send private message to a user
                                String tmp = msg;
                                // Seperates /msg and the rest of the command
                                if (tmp.indexOf(" ") >= 0) {
                                    tmp = tmp.substring(tmp.indexOf(" ") + 1);
                                } else {
                                    return;
                                }
                                // seperates receiver and msg
                                String user = "";
                                if (tmp.indexOf(" ") >= 0) {
                                    user = tmp.substring(0, tmp.indexOf(" "));
                                    tmp = tmp.substring(tmp.indexOf(" ") + 1);
                                }
                                // if there is a message
                                if (!tmp.equals("")) {

                                    // makes sure messaged client is valid
                                    Client messaged = map.get(user);
                                    if (messaged == null) {
                                        continue;
                                    }
                                    Client sent = map.get(username);

                                    // sends message
                                    messaged.output.println("FROM " + username);
                                    messaged.output.println(tmp);
                                    messaged.output.flush();

                                    // records sent message
                                    sent.output.println("TO " + user);
                                    sent.output.println(tmp);
                                    sent.output.flush();


                                }
                            } else if (msg.startsWith("/status")) { //change user status

                                // tells all the clients that the user updated their status
                                for (Client c : clientList) {
                                    c.output.println(username);
                                    c.output.println(msg);
                                    c.output.flush();
                                }
                            } else if (msg.equals("/ready")) {
                                // make player ready
                                ready++;
                                if (ready == clientList.size()) {
                                    for (Client c : clientList) {
                                        c.output.println(admin);
                                        c.output.println("/start");
                                        c.output.flush();
                                    }
                                }
                            } else if (msg.equals("/unready")) {
                                // unready user
                                ready--;
                            } else if (msg.startsWith("/spin") || msg.startsWith("/remove") || msg.startsWith("/tile")) {
                                // print username and message to clients
                                for (Client c : clientList) {
                                    c.output.println(username);
                                    c.output.println(msg);
                                    c.output.flush();
                                }
                            }
                        } else {
                            //if not special command send message to everyone
                            for (Client c : clientList) {
                                //output to be received by the client
                                c.output.println(username);
                                c.output.println(msg);
                                c.output.flush();
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Failed to receive msg from the client");
                    e.printStackTrace();
                }
            }

            // close the socket
            try {
                input.close();
                output.close();
                client.close();
            } catch (Exception e) {
                System.out.println("Failed to close socket");
            }
        } // end of run()
    } // end of inner class

    public class Client {
        Socket client;
        private PrintWriter output;
        private BufferedReader input;
        String user;
        // 1 active
        // 2 offline
        // 3 do not disturb

        /**
         * ConnectionHandler Constructor
         *
         * @param s,username socket belonging to this client connection
         */
        Client(Socket s, String userName) {
            user = userName;
            map.put(user, this); // adds user to map
            client = s; // constructor assigns client to this
            try { // assign all connections to client
                output = new PrintWriter(client.getOutputStream());
                InputStreamReader stream = new InputStreamReader(client.getInputStream());
                input = new BufferedReader(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            running = true;
        } // end of constructor

    }

    private class Panel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g); // required
            this.setDoubleBuffered(true);
            g.drawImage(background, 0, 0, null);
            repaint();

        }
    }
} // end of Class