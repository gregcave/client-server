import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Game {
    private final Map<Integer, Player> clients = new TreeMap<>();

    public void createPlayer(int clientId, int playerId, int hasBall)
    {
        Player client = new Player(clientId, playerId);
        client.giveBall(hasBall);
        clients.put(playerId, client);
    }

    public List<Integer> getClientIds(int clientId) {
        List<Integer> result = new ArrayList<Integer>();

        for (Player client : clients.values())
            if (client.getClientId() == clientId)
                result.add(client.getClientId());

        return result;
    }

    public List<Integer> getPlayer(int clientId) {
        List<Integer> result = new ArrayList<Integer>();

        for (Player client : clients.values())
            if (client.getClientId() == clientId)
                result.add(client.getPlayerId());

        return result;
    }

    public List<Integer> getPlayerIds() {
        List<Integer> result = new ArrayList<Integer>();

        for (Player client : clients.values())
            result.add(client.getPlayerId());

        return result;
    }

    public int getBall(int clientId, int playerId) throws Exception {
        return clients.get(playerId).hasBall();
    }

    public void setBall(int player) throws Exception {
        synchronized (clients) {
            clients.get(player).giveBall(0);
        }
    }

    public void giveBall(int clientId, int fromPlayer, int toPlayer, int ball) throws Exception {
        synchronized (clients)
        {
            if (clients.get(fromPlayer).getClientId() != clientId)
                throw new Exception("Player: " + clientId + " is not allowed to pass the ball for " + fromPlayer + ".");
            if (clients.get(fromPlayer).hasBall() < ball)
                throw new Exception(
                        "Player: " + fromPlayer +  " does not have the ball.");
            if (ball <= 0)
                throw new Exception("The amount has to be a positive value.");
            clients.get(fromPlayer).giveBall(clients.get(fromPlayer).hasBall() - ball);
            clients.get(toPlayer).giveBall(clients.get(toPlayer).hasBall() + ball);
        }
    }
}
