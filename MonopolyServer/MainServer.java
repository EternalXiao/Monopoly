package MonopolyServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.Scanner;

import MonopolyServer.game.*;
/**
 * This class is the main class for the server which deals with
 * client connection.
 * It has four field variables, port, server, connectedClients and game
 */
public class MainServer {
	private int port;
	ServerSocket server;
	private LinkedList<ServerThread> connectedClients;
	private Game game;
	/**
	 * Constructor for MainServer class
	 * @param port the port of the server to be set up
	 */
	public MainServer(int port) {
		this.port = port;
		this.connectedClients = new LinkedList<>();
	}
	public Game getGame() {
		return this.game;
	}
	public LinkedList<ServerThread> getConnectedClients() {
		return this.connectedClients;
	}
	/**
	 * This method is to set up the server on a specific port
	 * @return true if server successfully builds, else false
	 */
	public boolean build() {
		try {
			server = new ServerSocket(this.port);
			System.out.println("Server launched...");
			server.getInetAddress();
			System.out.println(InetAddress.getLocalHost());
			game = new Game(this);
			return true;
		} catch (Exception e) {
			System.out.println("Server failed to launch...");
			return false;
		}
	}
	/**
	 * This method is to close all the conncetions to the clients and
	 * then close the server socket
	 * @throws Exception
	 */
	public void close() throws Exception {
		System.out.println("Server closing...");
		for(ServerThread st:this.connectedClients) {
			st.close();
		}
		this.server.close();
	}
	/**
	 * This method is to listen the connection of clients.Once the connection
	 * has been built, the server will create a dedicated thread to handle this
	 * client
	 */
	public void listenConnection(){
		while (!server.isClosed()) {
			System.out.println("Waiting for client connecting...");
			try {
				Socket client = server.accept();
				System.out.println("One client connected...");
				ServerThread ST = new ServerThread(client,this);
				ST.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * This method is to check whether all the players are ready
	 * @return true if all are ready, else false
	 */
	public boolean checkStart() {
		if(game.getPlayers().size()<2)
			return false;
		for(Player player:game.getPlayers()) {
			if(!player.isReady())
				return false;
		}
		return true;
	}
	/**
	 * This method takes a message and sends it to all clients 
	 * @param info the message to be sent
	 */
	public void sendAll(String info) {
		for(ServerThread st:this.connectedClients) {
			if(st.getLoggedIn())
				st.send(info);
		}
	}
	/**
	 * This method takes a message and sends it to all clients except
	 * the one specified
	 * @param info the message to be sent
	 * @param playerId the id of the specified client not to send
	 */
	public void sendAllWithout(String info,int playerId) {
		for(ServerThread st:this.connectedClients) {
			if(st.getInGameId()==playerId)
				continue;
			st.send(info);
		}
	}
	/**
	 * This method find the ServerThread with a given id
	 * @param id the id of the ServerThread
	 * @return the ServerThread found
	 */
	public ServerThread searchThread(int id) {
		for(ServerThread st:this.connectedClients) {
			if(st.getInGameId()==id)
				return st;
		}
		return null;
	}
	/**
	 * This method is to start the game
	 */
	public void gameStart() {
		new Thread(() -> {
			this.sendPlayerId();
			sendAll("Start");
			System.out.println("Game start!");
			this.game.setIsStart(true);
			this.game.setAlivePlayers(this.game.getPlayers().size());
			while (!this.game.getIsEnd()) {
				this.game.nextRound();
			}
			sendAll("GameOver");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.game.reset();
		}).start();
	}
	/**
	 * This method is to send the update message of a player's money to all the clients
	 * @param playerId the id of corresponding player to be update
	 * @param money the updated money
	 */
	public void sendUpdateMoney(int playerId,int money) {
		this.sendAll("Update Money "+playerId + " "+money);
	}
	/**
	 * This method is to send player position update message to all clients
	 * @param playerId Id of the player to be updated
	 * @param position player new position
	 */
	public void sendUpdatePosition(int playerId,int position) {
		this.sendAll("Update Position "+playerId+ " "+position);
	}
	/**
	 * This method is to send player alive update message to all clients
	 * @param playerId Id of the player to be updated
	 * @param alive new alive status
	 */
	public void sendUpdateAlive(int playerId,int alive) {
		this.sendAll("Update Alive "+playerId+" "+alive);
	}
	/**
	 * This method is to send street level update message to all clients
	 * @param blockId the block id to be updated
	 * @param level block new level
	 */
	public void sendUpdateLevel(int blockId,int level) {
		this.sendAll("Update BlockLevel "+blockId+" "+level);
	}
	/**
	 * This method is to send street owner update message to all clients
	 * @param blockId the street id to be updated
	 * @param playerId the id of the new street owner
	 */
	public void sendUpdateOwner(int blockId,int playerId) {
		this.sendAll("Update BlockOwner "+blockId+" "+playerId);
	}
	/**
	 * This method is to send chat message to all clients
	 * @param nickname the nickname of message sender
	 * @param message message content
	 */
	public void sendChatMessage(String nickname,String message) {
		this.sendAll("ChatMessage "+nickname+": "+message);
	}
	/**
	 * This method is to send system message to all clients
	 * @param nickname the message subject
	 * @param message the message content
	 */
	public void sendSystemNormalMessage(String nickname,String message) {
		this.sendAll("SystemMessage "+nickname +" "+message);
	}
	/**
	 * This method is to send pay message to all clients
	 * @param payer the nickname of the payer
	 * @param receiver the nickname of the receiver
	 * @param amount the amount of the payment
	 */
	public void sendSystemPay(String payer, String receiver,int amount) {
		this.sendAll("SystemMessage "+payer+" paid "+receiver +" "+amount+"£");
	}
	/**
	 * This method is to send payTax message to all clients
	 * @param payer the nickname of the payer
	 * @param amount the amount of tax
	 */
	public void sendSystemPayTax(String payer,int amount) {
		this.sendAll("SystemMessage "+payer+" paid "+amount+"£");
	}
	/**
	 * This method is to send player reset message to all clients
	 */
	public void sendResetPlayer() {
		this.sendAll("ResetPlayer");
	}
	/**
	 * This method is to send player in debt message to all clients
	 * @param playerId the id of the player in debt
	 */
	public void sendInDebtPlayer(int playerId) {
		this.sendAll("InDebt "+playerId);
	}
	/**
	 * This method is to remove the player to be exit from the current player list
	 * and send new player profile list to all clients
	 * @param playerId the id of player to be exit
	 */
	public void playerExit(int playerId) {
		for(int i=playerId+1;i<this.connectedClients.size();i++) {
			this.connectedClients.get(i).setInGameId(i-1);
			this.connectedClients.get(i).getPlayer().setInGameId(i-1);
		}
		this.game.getPlayers().remove(playerId);
		this.connectedClients.remove(playerId);
		this.sendAllCurrentPlayerProfile();
	}
	/**
	 * This method is to send all online player profiles to all clients
	 */
	public void sendAllCurrentPlayerProfile() {
		this.sendResetPlayer();
		for(ServerThread st:this.connectedClients) {
			st.sendCurrentPlayerProfile();
		}
	}
	/**
	 * This method is to send GameOver message to all clients
	 */
	public void sendGameOver() {
		this.sendAll("GameOver");
	}
	/**
	 * This method is to check whether an account is online
	 * @param uid the uid of an account
	 * @return true if online, else false
	 */
	public boolean checkOnline(int uid) {
		for (ServerThread st : this.getConnectedClients()) {
			if (uid == st.getUid()) {
				return true;
			}
		}
		return false;
	}
	public void sendPlayerId() {
		for(ServerThread st:this.connectedClients) {
			st.send("Id "+st.getInGameId());
		}
	}
}
