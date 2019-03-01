package MonopolyServer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ServerThread extends Thread {
	private Socket client;
	private Connection dbCon;
	private MainServer server;
	private PrintStream out;
	private Scanner in;
	private int id;

	public ServerThread(Socket client, Connection dbCon, MainServer server) {
		this.client = client;
		this.dbCon = dbCon;
		this.server = server;
		try {
			out = new PrintStream(client.getOutputStream());
			in = new Scanner(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Connecting from : " + this.client.getInetAddress());
		this.listenClient();
	}

	public void parseInfo(String info) throws Exception {
		String[] infos = info.split(" ");
		if (infos[0].equals("Login")) {
			String login = "select count(*) from users where username = ? and password = ?";
			PreparedStatement loginStatement = dbCon.prepareStatement(login);
			loginStatement.setString(1, infos[1]);
			loginStatement.setString(2, infos[2]);
			ResultSet rs = loginStatement.executeQuery();
			rs.next();
			int result = rs.getInt(1);
			if (result == 1)
				this.send("Login 1");
			else
				this.send("Login 0");
		} else if (infos[0].equals("SignUp")) {
			String signUpVeri = "select count(*) from users where username = ?";
			PreparedStatement signUpVeriStatement = dbCon.prepareStatement(signUpVeri);
			signUpVeriStatement.setString(1, infos[1]);
			ResultSet rs = signUpVeriStatement.executeQuery();
			rs.next();
			int result = rs.getInt(1);
			if (result == 0) {
				this.send("SignUp 1");
				String signUp = "insert into users (username, password, win,lose,score) values (?,?,0,0,0)";
				PreparedStatement signUpStatement = dbCon.prepareStatement(signUp);
				signUpStatement.setString(1, infos[1]);
				signUpStatement.setString(2, infos[2]);
				signUpStatement.executeQuery();
			} else
				this.send("SignUp 0");
		} else if (infos[0].equals("Ready")) {
			if (infos[1].equals("1")) {
				server.getGame().getPlayers().get(this.id).setIsReady(true);
				System.out.println(this.id + " ready");
				if (server.checkStart())
					new Thread(() -> {
						while (true) {
							server.sendAll("Start");
							System.out.println("Game start!");
							server.getGame().testNextRound();
						}
					}).start();
			}

		} else if (infos[0].equals("RollDice")) {
			synchronized (server.getGame()) {
				notifyAll();
			}
		}
	}

	public void listenClient() {
		new Thread(() -> {
			while (client.isConnected()) {
				if (in.hasNext()) {
					String request = in.nextLine().trim();
					System.out.println("Receiving Client request :" + request);
					try {
						parseInfo(request);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void close() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String info) {
		out.println(info);
	}

}
