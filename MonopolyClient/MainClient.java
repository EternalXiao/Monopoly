package MonopolyClient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainClient {
	private Socket client;
	private String serverIP;
	private int port;
	private PrintStream out;
	private Scanner in;

	public MainClient(String serverIP, int port) {
		this.serverIP = serverIP;
		this.port = port;
	}

	public boolean connect() {
		try {
			client = new Socket(this.getServerIP(), this.getPort());
			out = new PrintStream(client.getOutputStream());
			in = new Scanner(client.getInputStream());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Socket getClient() {
		return this.client;
	}

	public String getServerIP() {
		return this.serverIP;
	}

	public int getPort() {
		return this.port;
	}

	public boolean login(String username, String password) {
		String res = "";
		out.println("Login " + username + " " + password);
		if (in.hasNext()) {
			res = in.nextLine().trim();
			System.out.println("Received server response : "+res);
		}
		if(res.equals("1"))
			return true;
		else 
			return false;
	}

	public boolean signUp(String username, String password) {
		return true;
	}

	public void send(String info) {

	}
}
