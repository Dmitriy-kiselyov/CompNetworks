import java.io.*;
import java.net.Socket;

public class FindNumberClient {

    public interface Logger {
        void print(String message);
    }

    public static Logger logger = new Logger() {
        @Override
        public void print(String message) {
            logger.print(message);
        }
    };

    public static void main(String[] args) throws IOException {

        logger.print("Welcome to Client side");

        String ip = args.length > 0 ? args[0] : "localhost";
        String host = args.length > 1 ? args[1] : "4444";

        if (args.length == 0) {
            logger.print("use: client hostname");
            System.exit(-1);
        }

        logger.print("Connecting to " + args[0] + ":" + host);
        Socket client = new Socket(ip, Integer.parseInt(host));
        logger.print("Client address is " + client.getLocalSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        //waiting for arguments
        try {
            String input = in.readLine();
            try {
                logger.print("Received from server: /" + input + "/");

                String[] nums = input.split("\\s+");
                long a = Long.parseLong(nums[0]);
                long b = Long.parseLong(nums[1]);

                int cnt = FindNumber.findInRange(a, b, false);

                logger.print("Answer is " + cnt);
                out.println(cnt);
            } catch (NumberFormatException e) {
                String message = "Error! Arguments should be integer!";
                logger.print(message);
                out.println(message);
            } catch (IndexOutOfBoundsException e) {
                String message = "Error! There should be 2 arguments!";
                logger.print(message);
                out.println(message);
            }
        } catch (Exception e) {
            logger.print("Problem with transferring data!");
        }

        out.close();
        in.close();
        client.close();
    }

}
