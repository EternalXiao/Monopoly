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

public class MainServer {
	private static final String DBDRIVER = "org.postgresql.Driver";
	private static final String DBURL = "jdbc:postgresql://mod-fund-databases.cs.bham.ac.uk:5432/xxm804";
	private static final String USER = "xxm804";
	private static final String PASSWORD = "2xbaqdl41r";

	private int port;
	ServerSocket server;
	Connection dbCon;
	private LinkedList<ServerThread> connectedClients;
	private Game game;

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
	public boolean build() {
		try {
			Class.forName(DBDRIVER);
			server = new ServerSocket(this.port);
			dbCon = DriverManager.getConnection(DBURL, USER, PASSWORD);
			System.out.println("Server launched...");
			server.getInetAddress();
			System.out.println(InetAddress.getLocalHost());
			//test
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
		this.dbCon.close();
		this.server.close();
	}
	/**
	 * This method is to listen the connection of clients.Once the connection
	 * has been built, the server will create a dedicated thread to handle this
	 * client
	 */
	public void listenConnection(){
		Scanner keyIn = new Scanner(System.in);
		new Thread(() -> {
			while (true) {
				if (keyIn.nextLine().equals("exit")) {
					break;
				}

			}
			keyIn.close();
			try {
				this.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		while (!server.isClosed()) {
			System.out.println("Waiting for client connecting...");
			try {
				Socket client = server.accept();
				System.out.println("One client connected...");
				ServerThread ST = new ServerThread(client, dbCon,this);
				//this.connectedClients.add(ST);
				ST.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean checkStart() {
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
	public ServerThread searchThread(int id) {
		for(ServerThread st:this.connectedClients) {
			if(st.getInGameId()==id)
				return st;
		}
		return null;
	}
	public void gameStart() {
		new Thread(() -> {
			sendAll("Start");
			System.out.println("Game start!");
			this.game.setAlivePlayers(this.game.getPlayers().size());
			while (!this.game.getIsEnd()) {
				this.game.nextRound();
			}
			sendAll("GameOver");
		}).start();
	}
	public void sendUpdateMoney(int playerId,int money) {
		this.sendAll("Update Money "+playerId + " "+money);
	}
	public void sendUpdatePosition(int playerId,int position) {
		this.sendAll("Update Position "+playerId+ " "+position);
	}
	public void sendUpdateAlive(int playerId,int alive) {
		this.sendAll("Update Alive "+playerId+" "+alive);
	}
	public void sendUpdateLevel(int blockId,int level) {
		this.sendAll("Update BlockLevel "+blockId+" "+level);
	}
	public void sendUpdateOwner(int blockId,int playerId) {
		this.sendAll("Update BlockOwner "+blockId+" "+playerId);
	}
	public void sendChatMessage(String nickname,String message) {
		this.sendAll("ChatMessage "+nickname+": "+message);
	}
	public void sendSystemNormalMessage(String nickname,String message) {
		this.sendAll("SystemMessage "+nickname +" "+message);
	}
	public void sendSystemPay(String payer, String receiver,int amount) {
		this.sendAll("SystemMessage "+payer+" paid "+receiver +" "+amount);
	}
	public void sendSystemPayTax(String payer,int amount) {
		this.sendAll("SystemMessage "+payer+" paid "+amount);
	}
}
