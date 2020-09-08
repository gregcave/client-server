using System;

namespace CS_Server
{
    public class Player
    {
        readonly int clientId;
        readonly int playerId;
        private int _ball;

        public Player(int clientId, int playerId)
        {
            getClientId = clientId;
            getPlayerId = playerId;
        }

        public int getPlayerId { get;  }
        public int getClientId { get; }

        public int hasBall()
        {
            return _ball;
        }
		
        public void giveBall(int newBall)
        {
            _ball = newBall;
        }
    }
}