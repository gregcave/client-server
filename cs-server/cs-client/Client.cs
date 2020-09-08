using System;
using System.CodeDom;
using System.IO;
using System.Net.Sockets;

namespace CS_Client
{
    class Client : IDisposable
    {
        const int port = 8888;

        private readonly StreamReader reader;
        private readonly StreamWriter writer;

        public Client(int clientId)  
        {
            // Connecting to the server and creating objects for communications

            TcpClient tcpClient = new TcpClient("localhost", port);

            NetworkStream stream = tcpClient.GetStream();
            reader = new StreamReader(stream);
            writer = new StreamWriter(stream);

            // Sending customer ID
            writer.WriteLine(clientId);
            writer.Flush();

            // Parsing the response
            string line = reader.ReadLine();
            if (line.Trim().ToLower() != "inuse")
            {
                throw new Exception(line);
            }

            if (line.Trim().ToLower() != "success")
            {
                throw new Exception(line);
            }
        }

        public int getPlayerId()
        {
            // Sending command
            writer.WriteLine("PLAYER");
            writer.Flush();

            // Reading the players
            string line = reader.ReadLine();
            int numberOfPlayers = int.Parse(line);

            // Reading the player IDs
            int[] players = new int[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++)
            {
                line = reader.ReadLine();
                players[i] = int.Parse(line);
            }
            return players[0];
        }

        public int[] getPlayers()
        {
            writer.WriteLine("PLAYERS");

            String line = reader.ReadLine();
            int numberOfPlayers =  int.Parse(line);

            int[] player = new int[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++)
            {
                line = reader.ReadLine();
                player[i] = int.Parse(line);
            }
            return player;
        }

        public int getBall(int playerId)
        {
            // Writing the command
            writer.WriteLine("Ball " + playerId);
            writer.Flush();

            string line = reader.ReadLine();
            return int.Parse(line);
        }

        public void giveBall(int fromPlayer, int toPlayer, int ball)
        {
            // Writing the command
            writer.WriteLine($"GIVEBALL {fromPlayer} {toPlayer} {ball}");
            writer.Flush();

            // Reading the response
            string line = reader.ReadLine();
            if (line.Trim().ToLower() != "success")
                throw new Exception(line);
        }

        public void Dispose()
        {
            reader.Close();
            writer.Close();
        }       
    }
}