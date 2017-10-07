import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FindNumberWorker implements Runnable {
    private final Socket client;

    public FindNumberWorker(Socket client) {
        this.client = client;
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

        System.out.println("Waiting for messages from " + clientName());

        String input;
        try {
            while ((input = in.readLine()) != null) {
                if (input.equalsIgnoreCase("exit")) break;

                System.out.println("Received from " + clientName() + ": " + input);

                try {
                    String[] nums = input.split("\\s+");
                    int a = Integer.parseInt(nums[0]);
                    int b = Integer.parseInt(nums[1]);

                    int cnt = FindNumber.findInRange(a, b, false);

                    System.out.println("Answer is " + cnt);
                    out.println(cnt);
                } catch (NumberFormatException e) {
                    String message = "Error! Arguments should be integer!";
                    System.out.println(message);
                    out.println(message);
                } catch (IndexOutOfBoundsException e) {
                    String message = "Error! There should be 2 arguments!";
                    System.out.println(message);
                    out.println(message);
                }
            }
        } catch (Exception e) {
            System.out.println("Could not read next line from " + clientName());
        }

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
