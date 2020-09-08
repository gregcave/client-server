using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace CS_Server
{
    class ServerProgram
    {
        private const int port = 8888;

        private static readonly Game game = new Game();
        private static int playerId = 1001;
        static void Main(string[] args)
        {
           
            RunServer();
        }

        private static void RunServer()
        {
            TcpListener listener = new TcpListener(IPAddress.Loopback, port);
            listener.Start();
            Console.WriteLine("Waiting for incoming connections...");
            while (true)
            {
                TcpClient tcpClient = listener.AcceptTcpClient();
                new Thread(HandleIncomingConnection).Start(tcpClient);
            }
        }

        private static void HandleIncomingConnection(object param)
        {
            TcpClient tcpClient = (TcpClient)param;
            using (Stream stream = tcpClient.GetStream())
            {
                StreamWriter writer = new StreamWriter(stream);
                StreamReader reader = new StreamReader(stream);
               
                int clientId = 1;
                try
                {   
                    Console.WriteLine($"New connection; client ID: {clientId}");
                    if (game.getClientId(clientId).Count == 1)
                    {
                       
                        throw new Exception("");
                    }
                    else
                    {
                        if (clientId == 1)
                        {
                            game.CreatePlayer(clientId, playerId, 1);
                            Console.WriteLine("Player: {playerId} created with ball.");
                            clientId++;
                        }
                        else
                        {
                            playerId++;
                            
                            game.CreatePlayer(clientId, playerId, 0);
                            Console.WriteLine("Player: {playerId} created without ball.");
                            clientId++;
                        }
                    }

                    Console.WriteLine("New connection " + clientId);
                    writer.WriteLine("SUCCESS");
                    writer.Flush();

                    
                    while (true)
                    {
                        string line = reader.ReadLine();
                        string[] substrings = line.Split(' ');
                        switch (substrings[0].ToLower())
                        {
                            case "player":
                                List<int> playerId = game.getPlayer(clientId);
                                writer.WriteLine(playerId.Count);

                                foreach (int playerIds in playerId)
                                {
                                    writer.WriteLine(playerIds);
                                }
                                break;


                            case "players":
                                List<int> players = game.getPlayerIds();
                                writer.WriteLine(players.Count);
                                foreach (int player in players)
                                    writer.WriteLine(player);
                                writer.Flush();
                                break;

                            case "ball":
                                int client = int.Parse(substrings[1]);
                                writer.WriteLine(game.getBall(clientId, client));
                                writer.Flush();
                                break;

                            case "giveBall":
                                int fromPlayer = int.Parse(substrings[1]);
                                int toPlayer = int.Parse(substrings[2]);
                                int ball = int.Parse(substrings[3]);
                                game.giveBall(clientId, fromPlayer, toPlayer, ball);
                                writer.WriteLine("SUCCESS");
                                writer.Flush();
                                break;


                            default:
                                throw new Exception($"Unknown command: {substrings[0]}.");
                        }
                    }
                }
                catch (Exception e)
                {
                    try
                    {
                        writer.WriteLine("ERROR " + e.Message);
                        writer.Flush();
                        tcpClient.Close();
                    }
                    catch
                    {
                        Console.WriteLine("Failed to send error message.");
                    }
                }
                finally
                {
                    Console.WriteLine($"Client: {clientId} disconnected.");
                }
            }
        }
    }
}
