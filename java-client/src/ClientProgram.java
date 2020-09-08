import java.util.Scanner;

public class ClientProgram {
    static boolean printDetails = true;
    static boolean newClient = true;

    public static void main(String[] args) {
        int clientId = 1;
        while (newClient) {
            try {
                //System.out.println("Enter Client ID:");
                Scanner in = new Scanner(System.in);
                //int clientId = Integer.parseInt(in.nextLine());


                try (Client client = new Client(clientId)) {

                    System.out.printf("\nConnected with client ID: %2d.\n", clientId);
                    newClient=false;

                    while (true) {
                        int playerId = client.getPlayerId();
                        var players = client.getPlayers();
                        int fromPlayer;
                        int toPlayer;

                        if (client.getBall(playerId) == 1) {
                            printDetails = true;
                        }

                        if (printDetails) {
                            for (int player : players)
                                if (client.getBall(player) == 1) {
                                    System.out.printf("Player %2d: This player has the ball.\n", player);
                                } else {
                                    System.out.printf("Player %2d\n", player);
                                }
                        }

                        if (client.getBall(playerId) == 1) {
                            System.out.printf("%nPlayer %2d: You have the ball.\n", playerId);
                            fromPlayer = playerId;
                            System.out.println("Enter player ID to pass the ball to: (or pass to yourself)");
                            toPlayer = Integer.parseInt(in.nextLine());
                            client.giveBall(fromPlayer, toPlayer, client.getBall(playerId));
                        } else {
                            if (printDetails) {
                                System.out.printf("\nPlayer %2d: You don't have the ball.\n", playerId);
                                System.out.println("Waiting to be passed the ball...");
                                printDetails = false;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                clientId++;
            }
        }
    }
}
