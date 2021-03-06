import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FindNumberWorker implements Runnable {
    private static long start = 1;

    private final Socket client;
    private final long a, b;

    public FindNumberWorker(Socket client, long interval) {
        this.client = client;
        a = start;
        b = a + interval;
        start += interval + 1;
    }

    @Override
    public void run() {
        BufferedReader in;
        PrintWriter out;

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Could not read from client: " + clientName());
            return;
        }

        //send arguments to client
        out.println(a + " " + b);

        //receive answer
        try {
            String answer = in.readLine();
            System.out.println("Client " + clientName() + " send answer:");
            System.out.println("[" + a + ", " + b + "] = " + answer);
        } catch (IOException e) {
            System.out.println("Client " + clientName() + " disconnected");
        }

        //close client
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
            System.out.println("Could not close client " + clientName() + " safely");
        }

    }

    public String clientName() {
        return client.getRemoteSocketAddress().toString();
    }
}
