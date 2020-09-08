# client-server

A socket-based client-server system that allows connected users to pass a virtual ball

## Project Overview

This repository contains a socket-based client-server system that is used to play a virtual ball. Each client application is a player. Once a player receives a ball, the application will prompt the user whom to pass the ball to. The clients can also connect and disconnect from the server at any time. 

This repository contains two versions of the server and the client applications: in C# and in Java. These applications should work with each other seamlessly.

### The Rules of the Game

At any point in time, exactly one player has the virtual ball. This player needs to decide who to pass the ball to. (They are allowed to pass the ball to themselves.) Once the decision is made, they pass the ball to the corresponding player.

New players can join the game at any time. The number of players in the game is unlimited. Every player joining the game has to be assigned a unique ID that will not change until they leave the game and will not be reused after they leave the game. All the players including the one with the ball will immediately learn about new players and, hence, the current ball owner can decide to pass the ball to the player who has just joint the game.

Any player can leave the game at any time (the client application can be closed/killed). If the player with the ball leaves the game, the server passes the ball to one of the remaining players.

If the server is closed or killed, the clients will start a new server application, and reconnect to the new server to continue the game. For the users, this server change is transparent and does not affect the state of the game.

If there are no players in the game, the server waits until someone joins the game in which case the first player to connect receives the ball.

## Client User Interface

Each client will display their own ID, information about all the players currently in the game and the ID of the player currently having the ball.

The server will display main events:
* Player joining the game (display the ID of the new player and the list of players currently in the game).
* Player leaving the game (display the ID of that player and the list of players currently in the game).
* The ball being automatically passed to one of the players after the player with the ball left the game (display the ID of the new player with the ball).
* Player passing the ball to another player (display the IDs of both players).
