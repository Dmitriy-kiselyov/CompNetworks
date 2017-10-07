import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FindNumberServer {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Server side");

        ServerSocket server = null;
        Socket client = null;

        // create server socket
        try {
            server = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Couldn't listen to port 4444");
            System.exit(-1);
        }

        try {
            System.out.print("Waiting for a client...");
            client = server.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            System.out.println("Can't accept");
            System.exit(-1);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        System.out.println("Waiting for messages...");

        String input;
        while ((input = in.readLine()) != null) {
            if (input.equalsIgnoreCase("exit")) break;

            System.out.println("Received: " + input);

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

        out.close();
        in.close();
        client.close();
        server.close();
    }

}
