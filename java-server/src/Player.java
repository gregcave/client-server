public class Player {
    private final int clientId;
    private final int playerId;
    private int ball;

    public Player(int clientId, int playerId) {
        this.clientId = clientId;
        this.playerId = playerId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int hasBall() {
        return ball;
    }

    public void giveBall(int newBall) {
        ball = newBall;
    }
}
