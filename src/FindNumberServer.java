import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class FindNumberServer {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to server side");

        ServerSocket server = null;

        // create server socket
        try {
            server = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Couldn't listen to port 4444");
            System.exit(-1);
        }

        System.out.println("Server " + server.getInetAddress().getHostAddress() + " created!");
        System.out.println("Type: 'exit' to close server");

        //close server on "exit" command
        final ServerSocket finalServers = server;
        new Thread(() -> {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String input;

            try {
                while ((input = in.readLine()) != null) {
                    if (input.equalsIgnoreCase("exit")) break;
                }
            } catch (IOException e) {
                System.out.println("Could not read server command");
            } finally {
                try {
                    finalServers.close();
                } catch (IOException e) {
                    System.out.println("Could not close server safely");
                }
            }
        }).start();

        //get interval
        long interval;
        try {
            interval = Long.parseLong(args[0]);
        } catch (Exception e) {
            interval = 999_999;
        }

        //wait for client forever
        while (true) {
            try {
                System.out.println("Waiting for a new client...");
                Socket client = server.accept();
                FindNumberWorker clientWorker = new FindNumberWorker(client, interval);
                System.out.println("New client " + clientWorker.clientName() + " connected");
                new Thread(clientWorker).start();
            } catch (IOException e) {
                System.out.println("Server closed");
                System.exit(0);
            }
        }
    }

}
