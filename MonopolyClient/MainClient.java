package MonopolyClient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainClient {
	private Socket client;
	private String serverIP;
	private int port;

	public MainClient(String serverIP, int port) {
		this.serverIP = serverIP;
		this.port = port;
	}

	public boolean connect() {
		try {
			client = new Socket(this.getServerIP(), this.getPort());
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
	public boolean login(String username,String password) {
		return false;
	}
}
