import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    ServerSocket serverSock;
    static boolean running = true;
    private ArrayList<ConnectionHandler> clients = new ArrayList<ConnectionHandler>();

    public static void main(String[] args) {
//        new Server.go();
    }

    private void go() {
        Socket client = null;
        try {
            serverSock = new ServerSocket(5000);
            while (running) {
                client = serverSock.accept();

            }
        } catch (Exception e) {

        }
    }

    private boolean usableName(String name) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getName() != null) {
                if (clients.get(i).getName().equals(name)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void ready() {
        while (true) {
            boolean allReady = true;
            for (ConnectionHandler c : clients) {
                if (!c.ready()) {
                    allReady = false;
                    break;
                }
            }
            if (allReady) {
                for (ConnectionHandler c : clients) {
                    c.launch();
                }
            }
        }
    }

    class ConnectionHandler implements Runnable {
        private PrintWriter output;
        private BufferedReader input;
        private Socket client;
        private boolean running;
        private Server server;
        private String name;
        private boolean ready;

        ConnectionHandler(Socket s, Server server) {
            this.client = s;
            this.server = server;
            try {
                this.output = new PrintWriter(client.getOutputStream());
                InputStreamReader stream = new InputStreamReader(client.getInputStream());
                this.input = new BufferedReader(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            running = true;
        }

        private String getName() {
            return name;
        }

        public boolean ready() {
            return ready;
        }

        public void run() {
            try {
                if (input.ready()) {
                    String name;
                    do {
                        output.println("Enter a valid username");
                        name = input.readLine();
                    } while (!usableName(name));
                    this.name = name;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String msg;
            while (running) {
                try {
                    if (input.ready()) {
                        msg = input.readLine();
                        if (msg.equals("ready")) {
                            ready = true;
                        } else {
                            ready = false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        public void launch() {

        }
    }
}
