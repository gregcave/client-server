using System;
using System.Collections;
using System.Collections.Generic;

namespace CS_Server
{
    class Game
    {
        private readonly Dictionary<int, Player> clients = new Dictionary<int, Player>();

        public void CreatePlayer(int clientId, int playerId, int hasBall)
        {
            Player client = new Player(clientId, playerId);
            client.giveBall(hasBall);
            clients.Add(playerId, client);
        }


        public List<int> getClientId(int clientId)
        {
            List<int> result = new List<int>();

            foreach (Player client in clients.Values)
            {
                if (client.getClientId == clientId)
                {
                    result.Add(client.getClientId);
                }
            }

            return result;

        }
        public List<int> getPlayer(int clientId)
        {
            List<int> result = new List<int>();
            foreach (Player client in clients.Values)
                if (client.getClientId == clientId)
                    result.Add(client.getPlayerId);
            return result;
        }

        public List<int> getPlayerIds()
        {
            List<int> result = new List<int>();
            foreach (Player client in clients.Values)
            {
                result.Add(client.getPlayerId);
            }
            return result;
        }

        public int getBall(int clientId, int playerId)
        {
            if (clients[playerId].getClientId != clientId)
                throw new Exception(
                    $"Client: {clientId} is not allowed to pass the ball for player {playerId}.");

            return clients[playerId].hasBall();
        }

        public void giveBall(int clientId, int fromPlayer, int toPlayer, int ball)
        {
            lock (clients)
            {
                if (clients[fromPlayer].getClientId != clientId)
                    throw new Exception(
                        $"Client: {clientId} is not allowed to pass the ball for player {fromPlayer}");
                if (clients[fromPlayer].hasBall() == 0)
                    throw new Exception(
                        $"You  are not allowed to pass the ball.");
                if (ball <= 0)
                    throw new Exception("");

                if (clients[fromPlayer].hasBall() == 1)
                {
                    clients[toPlayer].giveBall(0);
                }
            }
        }
    }
}
