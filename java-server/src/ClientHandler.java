import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private Game game;
    static int playerId = 1000;
    static boolean disconnected=false;

    public ClientHandler(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    @Override
    public void run() {
        int clientId = 0;
        disconnected=false;

        try (
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            try {

                clientId = Integer.parseInt(scanner.nextLine());
                boolean hasBall = false;

                if (game.getClientIds(clientId).size() == 1) {
                    disconnected=true;
                    throw new Exception("");
                }
                else{
                    List<Integer> players = game.getPlayerIds();

                    for (Integer playerId : players)
                        if(game.getBall(clientId, playerId) == 1){
                            hasBall=true;
                        }

                    if (hasBall) {
                        playerId++;
                        game.createPlayer(clientId, playerId, 0);
                        System.out.println("\nA player has connected. \nClient ID: " + clientId + "\nPlayer ID: " + playerId + "\nPlayer does not have the ball.");
                    }
                    else{
                        playerId++;
                        game.createPlayer(clientId, playerId, 1);
                        System.out.println("\nA player has connected. \nClient ID: " + clientId + "\nPlayer ID: " + playerId + "\nPlayer has the ball.");
                    }
                }

                writer.println("SUCCESS");

                while (true) {
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");
                    switch (substrings[0].toLowerCase()) {
                        case "player":
                            List<Integer> playerIds = game.getPlayer(clientId);
                            writer.println(playerIds.size());
                            for (Integer playerId : playerIds)
                                writer.println(playerId);
                            break;

                        case "players":
                            List<Integer> players = game.getPlayerIds();
                            writer.println(players.size());
                            for (Integer playerId : players)
                                writer.println(playerId);
                            break;

                        case "ball":
                            int player = Integer.parseInt(substrings[1]);
                            writer.println(game.getBall(clientId, player));
                            break;

                        case "giveball":
                            int fromPlayer = Integer.parseInt(substrings[1]);
                            int toPlayer = Integer.parseInt(substrings[2]);
                            int ball = Integer.parseInt(substrings[3]);
                            game.giveBall(clientId, fromPlayer, toPlayer, ball);
                            writer.println("SUCCESS");
                            break;

                        default:
                            throw new Exception("Unknown command: " + substrings[0]);
                    }
                }
            } catch (Exception e) {
                writer.println(e.getMessage());
                socket.close();
            }
        } catch (Exception e) {
        } finally {
            if (!(disconnected)) {
                try {
                    if (game.getBall(clientId, playerId) == 1) {
                        System.out.println("\nA player has disconnected. \nClient ID: " + clientId + "\nPlayer ID: " + playerId + "\nPlayer had the ball.");
                        game.setBall(playerId);
                    } else {
                        System.out.println("\nA player has disconnected. \nClient ID: " + clientId + "\nPlayer ID: " + playerId + "\nPlayer did not have the ball.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
