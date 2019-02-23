package MonopolyServer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ServerThread extends Thread {
	private Socket client;
	private Connection dbCon;
	private MainServer server;
	private PrintStream out;
	private Scanner in;

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

	@Override
	public void run() {
		System.out.println("Connecting from : " + this.client.getInetAddress());
		this.listenClient();
	}

	public void parseInfo(String info) throws Exception {
		String[] infos = info.split(" ");
		if (infos.length == 3) {
			if (infos[0].equals("Login")) {
				String s = "select count(*) from users where username = '" + infos[1] + "' and password = '"
						+ infos[2] + "'";
				Statement statement = dbCon.createStatement();
				ResultSet rs = statement.executeQuery(s);
				rs.next();
				int result = rs.getInt("count");
				if (result == 1)
					out.println("Login 1");
				else
					out.println("Login 0");
			} else if (infos[0].equals("SignUp")) {
				String s = "select count(*) from users where username = '" + infos[1] + "'";
				Statement statement = dbCon.createStatement();
				ResultSet rs = statement.executeQuery(s);
				rs.next();
				int result = rs.getInt("count");
				if (result == 0) {
					out.println("SignUp 1");
					s = "insert into users (username, password, win,lose,score) values ('" + infos[1] + "','" + infos[2]
							+ "',0,0,0)";
					Statement insertStatement = dbCon.createStatement();
					insertStatement.executeQuery(s);
				} else
					out.println("SignUp 0");
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

}
