package MonopolyServer;

import java.io.IOException;
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
	LinkedList<ServerThread> connectedClients;
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
//			Class.forName(DBDRIVER);
			server = new ServerSocket(this.port);
//			dbCon = DriverManager.getConnection(DBURL, USER, PASSWORD);
			System.out.println("Server launched...");
			//test
			game = new Game(this);
			return true;
		} catch (Exception e) {
			System.out.println("Server failed to launch...");
			return false;
		}
	}

	public void close() throws Exception {
		System.out.println("Server closing...");
		for(ServerThread st:this.connectedClients) {
			st.close();
		}
		this.dbCon.close();
		this.server.close();
	}

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
				ServerThread ST = new ServerThread(client, dbCon,this,this.connectedClients.size());
				this.connectedClients.add(ST);
				ST.start();
				game.addPlayer();
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
	public void sendAll(String info) {
		for(ServerThread st:this.connectedClients) {
			st.send(info);
		}
	}
}
