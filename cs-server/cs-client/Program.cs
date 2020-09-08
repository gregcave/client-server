using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CS_Client
{
    class Program
    {
        static bool printDetails = true;
        static bool newClient = true;

        static void Main(string[] args)
        {
            int clientId = 1;
            while (newClient)
            {
                try
                {
                    Console.WriteLine("Enter Client ID:");
                    clientId = int.Parse(Console.ReadLine());
                    using (Client client = new Client(clientId))
                    {
                        Console.WriteLine("Connected with Client ID: "+ clientId);
                        newClient = false;
                        while (true)
                        {
                            int playerId = client.getPlayerId();
                            var players = client.getPlayers();
                            int fromPlayer;
                            int toPlayer;

                            if (client.getBall(playerId) == 1)
                            {
                                printDetails = true;
                            }

                            else if (printDetails)
                            {
                                foreach (int player in players)
                                {
                                    if (client.getBall(player) == 1)
                                    {
                                        Console.WriteLine("Player " + player + " has the ball");
                                    }
                                    else
                                    {
                                        Console.WriteLine("Player " + player);

                                    }
                                }
                            }

                            if (client.getBall(playerId) == 1)
                            {
                                Console.WriteLine(playerId + ": you have the ball.");
                                fromPlayer = playerId;
                                Console.WriteLine("Enter playerId to pass the ball to:");
                                toPlayer = int.Parse(Console.ReadLine());
                                client.giveBall(fromPlayer, toPlayer, client.getBall(playerId));
                            }
                            else
                            {
                                if (printDetails)
                                {
                                    Console.WriteLine(playerId + ": You dont have the ball");
                                    Console.WriteLine("Waiting to be passed the ball");
                                    printDetails = false;
                                }
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                    clientId++;
                }
            }
        }
    }
}

