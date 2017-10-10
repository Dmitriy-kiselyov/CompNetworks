import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FindNumberClient {

    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to Client side");

        if (args.length == 0) {
            System.out.println("use: client hostname");
            System.exit(-1);
        }

        System.out.println("Connecting to " + args[0]);
        Socket client = new Socket(args[0], 4444);
        System.out.println("Client address is " + client.getLocalSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        //waiting for arguments
        try {
            String input = in.readLine();
            try {
                System.out.println("Received from server: /" + input + "/");

                String[] nums = input.split("\\s+");
                long a = Long.parseLong(nums[0]);
                long b = Long.parseLong(nums[1]);

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
        } catch (Exception e) {
            System.out.println("Problem with transferring data!");
        }

        out.close();
        in.close();
        client.close();
    }

}
