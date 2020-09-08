import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram
{
    private final static int port = 8888;

    private static final Game game = new Game();

    public static void main(String[] args)
    {
        //game.createPlayer(1, 1001, 0);
        //game.createPlayer(2, 1002, 0);
        //game.createPlayer(3, 1003, 0);
        RunServer();
    }

    private static void RunServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("\nWaiting for incoming connections...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket, game)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
