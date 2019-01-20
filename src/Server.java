//imports for network communication
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
	/**
	 * Main
	 * Runs the server
	 * @param args parameters from command line
	 */
//	public static void main(String[] args) {
//		new ChatServer().go(); // start the server
//	}

	Server(){
		
		frame = this;
		JPanel panel = new Panel();
		JTextArea port = new JTextArea("5000");
		
		this.setSize(911, 561);
		this.setLocation((int) (Game.screenX / 2) - 476, ((int) (Game.screenY / 2) - 281));
		port.setSize(200,30);
		port.setLocation(500, 50);
		panel.setLayout(null);
		
		
		JButton start = new JButton("Start");
		start.setBounds(500, 300,120,30);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread t1 = new Thread(new Runnable() {
					public void run() {
						//new Server().go();
						go(port.getText());
					}
				});
				t1.start();
				
			}
			
		});
		
		panel.add(Box.createRigidArea(new Dimension(500, 0)));
		panel.add(start);
		panel.add(port);
		
		
		this.setResizable(false);
		this.setContentPane(panel);
		this.setVisible(true);
		
	
		background = Toolkit.getDefaultToolkit().getImage("graphics/mainmenu.png");
		
		
		
	}
	/**
	 * Go 
	 * Starts the server
	 */
	public void go(String portNum) {
		
		//allowing the user to choose the port to connect to 
		//String portNum = "";
//		while (!portNum.matches("[0-9]+")) {
//			portNum = JOptionPane.showInputDialog("Please enter the port number");
//			System.out.println("Server started on port " + portNum);
//		}
		
	
		//displaying the server Ip for clients to connect to
		Thread t1 = new Thread(new displayServer());
		t1.start();

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
				if (clientList.size() == 6) {
					System.out.println("Full Game");
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
					pw.println(admin);
					pw.flush();
				}
				
				System.out.println(userName + " joined.");

				//add the client to the client list and set as active
				for (Client c : clientList) {
					pw.println(c.user);
					pw.println(c.status = 1000000);
					c.output.println(userName);
					c.output.println("/status 1000000");
					c.output.flush();
				}

				//add the client to the client list
				clientList.add(new Client(client, userName));
				pw.println("");
				pw.flush();

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

	// ***** Inner class - thread for client connection
	class ConnectionHandler implements Runnable {
		private PrintWriter output; // assign printwriter to network stream
		private BufferedReader input; // Stream for network input
		private Socket client; // keeps track of the client socket
		private boolean running;

		/**
		 * ConnectionHandler Constructor
		 * 
		 * @param the socket belonging to this client connection
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
						System.out.println(msg);
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
									System.out.println("Banned " + banned.user + ":" + banned.client.getInetAddress());
									// tell banned client that they have been banned
									banned.output.println(admin);
									banned.output.println("/ban");
									banned.output.flush();
									banned.client.close();
									clientList.remove(banned);
									for (Client c : clientList) {
										c.output.println(banned.user);
										c.output.println("/status -999999999");
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
									System.out.println("Kicked " + kicked.user) ;
									// tells kicked client that they have been kicked
									kicked.output.println(admin);
									kicked.output.println("/kick");
									kicked.output.flush();
									kicked.client.close();
									clientList.remove(kicked);
									for (Client c : clientList) {
										c.output.println(kicked.user);
										c.output.println("/status -999999999");
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
									
									System.out.println("Message from " + username + " to " + user + " : " + tmp);
								}
							} else if (msg.startsWith("/status")) { //change user status
								String[] read = msg.split(" ");
								Client current = map.get(username);
								
								// gets status and updates it
								current.status = Integer.parseInt(read[1]);
								
								System.out.println(username + " status updated to " + current.status);
								// tells all the clients that the user updated their status
								for (Client c : clientList) {
									c.output.println(username);
									c.output.println(msg);
									c.output.flush();
								}
							} else if (msg.equals("/ready")) {
								ready++;
								if (ready == clientList.size()) {
									for (Client c : clientList) {
										c.output.println(admin);
										c.output.println("/start");
										c.output.flush();
									}
								}
							} else if (msg.equals("/unready")) {
								ready--;
							} else if (msg.startsWith("/spin") || msg.startsWith("/remove")) {
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
							System.out.println(username + " : " + msg);
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
		int status;
		// 1 active
		// 2 offline
		// 3 do not disturb

		/**
		 * ConnectionHandler Constructor
		 * 
		 * @param the socket belonging to this client connection
		 */
		Client(Socket s, String userName) {
			user = userName;
			map.put(user, this); // adds user to map
			status = 1; // intially status is active
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
	
	// displays the ip
	public class displayServer implements Runnable {
		public void run() {
			try {
				// get ip and display
				InetAddress ip = InetAddress.getLocalHost();
				JOptionPane.showMessageDialog(null, "Server IP: " + ip);
			} catch (UnknownHostException e2) {
				// problem with getting ip
				JOptionPane.showMessageDialog(null, "Error receiving Ip Address");
			}
			return;
		}
	}
	
	private class Panel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			this.setDoubleBuffered(true);
			g.drawImage(background, 0, 0, null);
			repaint();

		}
	}
} // end of Class
