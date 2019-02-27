package MonopolyClient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javafx.application.Platform;

public class MainClient {
	private Socket client;
	private String serverIP;
	private int port;
	private PrintStream out;
	private Scanner in;
	private ClientGUI gui;

	public MainClient(String serverIP, int port) {
		this.serverIP = serverIP;
		this.port = port;
		this.connect();
	}
	public MainClient() {
		
	}
	public void setGUI(ClientGUI gui) {
		this.gui=gui;
	}

	public boolean connect() {
		try {
			this.client = new Socket(this.getServerIP(), this.getPort());
			this.out = new PrintStream(client.getOutputStream());
			this.in = new Scanner(client.getInputStream());
			this.listenServer();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean connect(String serverIP,int port) {
		try {
			this.client = new Socket(serverIP, port);
			this.out = new PrintStream(client.getOutputStream());
			this.in = new Scanner(client.getInputStream());
			this.listenServer();
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

	public void login(String username, String password) {
		this.send("Login " + username + " " + password);
	}

	public void signUp(String username, String password) {
		this.send("SignUp "+ username +" "+password);
	}
	
	public void listenServer() {
		new Thread(() -> {
			while (client.isConnected()) {
				if (in.hasNext()) {
					String info = in.nextLine().trim();
					System.out.println("Receiving server information :" + info);
					try {
						parseInfo(info);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	public void parseInfo(String info) {
		String[] infos = info.split(" ");
		if(infos[0].equals("Login")) {
			if(infos[1].equals("1"))
				Platform.runLater(()->gui.mainPage());
			else
				Platform.runLater(()->gui.loginFailed());
		}
		else if(infos[0].equals("SignUp")) {
			if(infos[1].equals("1"))
				Platform.runLater(()->gui.signUpSuccess());
			else
				Platform.runLater(()->gui.signUpFail());
		}
	}

	public void send(String info) {
		out.println(info);
	}
	public void close() {
		try {
			this.out.close();
			this.in.close();
			this.client.close();
		} catch (IOException e) {
		}
	}
}
