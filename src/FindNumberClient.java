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
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Waiting for input...");

        String input;
        while ((input = userInput.readLine()) != null) {
            out.println(input);

            if (input.equalsIgnoreCase("exit")) break;

            String answer = in.readLine();
            System.out.println("Answer: " + answer);
        }

        out.close();
        in.close();
        userInput.close();
        client.close();
    }

}
