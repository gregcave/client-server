import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements AutoCloseable {
    final int port = 8888;

    private final Scanner reader;
    private final PrintWriter writer;

    public Client(int clientId) throws Exception {
        // Connecting to the server and creating objects for communications
        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream());

        // Automatically flushes the stream with every command
        writer = new PrintWriter(socket.getOutputStream(), true);

        // Sending client ID
        writer.println(clientId);
        // Parsing the response
        String line = reader.nextLine();

        if (line.trim().compareToIgnoreCase("inuse") != 0)


        if (line.trim().compareToIgnoreCase("success") != 0)
            throw new Exception(line);
    }

    public int getPlayerId() {
        // Sending command
        writer.println("PLAYER");

        // Reading the number of players
        String line = reader.nextLine();
        int numberOfPlayers = Integer.parseInt(line);

        // Reading the player ID
        int[] players = new int[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            line = reader.nextLine();
            players[i] = Integer.parseInt(line);
        }

        return players[0];
    }

    public int[] getPlayers() {
        // Sending command
        writer.println("PLAYERS");

        // Reading the number of players
        String line = reader.nextLine();
        int numberOfPlayers = Integer.parseInt(line);

        // Reading the player IDs
        int[] players = new int[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            line = reader.nextLine();
            players[i] = Integer.parseInt(line);
        }

        return players;
    }

    public int getBall(int playerId) {
        // Writing the command
        writer.println("BALL " + playerId);

        // Reading the ball
        String line = reader.nextLine();
        return Integer.parseInt(line);
    }

    public void giveBall(int fromPlayer, int toPlayer, int ball) throws Exception {
        // Writing the command
        writer.println("GIVEBALL " + fromPlayer + " " + toPlayer + " " + ball);

        // Reading the response
        String line = reader.nextLine();
        if (line.trim().compareToIgnoreCase("success") != 0)
            throw new Exception(line);
    }

    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }
}
