package MonopolyServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.Scanner;

public class MainServer {
	private static final String DBDRIVER = "org.postgresql.Driver";
	private static final String DBURL = "jdbc:postgresql://mod-fund-databases.cs.bham.ac.uk:5432/xxm804";
	private static final String USER = "xxm804";
	private static final String PASSWORD = "2xbaqdl41r";

	private int port;
	ServerSocket server;
	Connection dbCon;
	LinkedList<Socket> connectedClients;

	public MainServer(int port) {
		this.port = port;
		this.connectedClients = new LinkedList<>();
	}

	public boolean build() {
		try {
			// Class.forName(DBDRIVER);
			server = new ServerSocket(this.port);
			// dbCon = DriverManager.getConnection(DBURL, USER, PASSWORD);
			System.out.println("Server launched...");
			return true;
		} catch (Exception e) {
			System.out.println("Server failed to launch...");
			return false;
		}
	}

	public void close() throws Exception {
		System.out.println("Server closing...");
		this.server.close();
	}

	public void listen() throws Exception {
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
		while (true) {
			System.out.println("Waiting for client connecting...");
			Socket client = server.accept();
			connectedClients.add(client);
			System.out.println("One client connected...");
			new Thread(new ServerThread(client, dbCon,this)).start();
		}
	}
}
