package MonopolyServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.util.Scanner;

public class ServerThread implements Runnable{
	private Socket client;
	private Connection dbCon;
	private MainServer server;
	private PrintWriter out;
	private Scanner in;
	public ServerThread(Socket client,Connection dbCon,MainServer server) {
		this.client=client;
		this.dbCon=dbCon;
		this.server=server;
		try {
			out = new PrintWriter(client.getOutputStream());
			in = new Scanner(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		System.out.println("Connecting from : " + this.client.getInetAddress());
		
	}
	
}
