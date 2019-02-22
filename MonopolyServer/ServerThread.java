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
	private boolean isConnected;

	public ServerThread(Socket client, Connection dbCon, MainServer server) {
		this.client = client;
		this.dbCon = dbCon;
		this.server = server;
		this.isConnected = true;
		try {
			out = new PrintStream(client.getOutputStream());
			in = new Scanner(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void setIsConnected(boolean isConnected) {
		this.isConnected=isConnected;
	}

	@Override
	public void run() {
		System.out.println("Connecting from : " + this.client.getInetAddress());
		this.listen();
	}

	public void parseInfo(String info) throws Exception {
		String[] infos = info.split(" ");
		if (infos.length == 3) {
			if (infos[0].equals("Login")) {
				String s = "select count(*) from persons where lastname = '" + infos[1] + "' and firstname = '"
						+ infos[2] + "'";
				Statement statement = dbCon.createStatement();
				ResultSet rs = statement.executeQuery(s);
				rs.next();
				int result = rs.getInt("count");
				if (result == 1)
					out.println(result);
				else
					out.println(result);
			}
		}
	}

	public void listen() {
		new Thread(() -> {
			while (this.isConnected) {
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

}
