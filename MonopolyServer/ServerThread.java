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
	// temp
	private int inGameId;
	private String name;
	private int uid;

	public ServerThread(Socket client, Connection dbCon, MainServer server, int inGameId) {
		this.client = client;
		this.dbCon = dbCon;
		this.server = server;
		this.inGameId = inGameId;
		try {
			out = new PrintStream(client.getOutputStream());
			in = new Scanner(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getInGameId() {
		return this.inGameId;
	}

	public void setInGameId(int inGameId) {
		this.inGameId = inGameId;
	}
	/**
	 * This is the override method which will be run once start method being called
	 */
	@Override
	public void run() {
		System.out.println("Connecting from : " + this.client.getInetAddress());
		this.listenClient();
	}

	/**
	 * This method parses the message sent by the client The message are in the
	 * following format: <message type> <message content> The first string
	 * represents the message type. All the types are as follows: i: Login ii:
	 * SignUp iii: Ready iv: RollDice v: NickName vi: Buy
	 * 
	 * Followed by message type, the length of the message content varies from their
	 * type
	 * 
	 * i.Login The content of the Login comprises of two bits The first bit
	 * represents the username of the client and the second is the password
	 * 
	 * ii.SignUp The content of the SignUp comprises of two bits The first bit
	 * represents the username of the client and the second is the password
	 * 
	 * iii.Ready The ready message does not have content. It just toggle the
	 * ready status of corresponding player
	 * 
	 * iv.RollDice This message does not have content. Once received this message,
	 * the game will proceed to roll dice
	 * 
	 * v.NickName 
	 * 
	 * @param info
	 * @throws Exception
	 */
	public void parseInfo(String info) throws Exception {
		String[] infos = info.split(" ");
		//Handle Login message
		if (infos[0].equals("Login")) {
			String login = "select uid from users where username = ? and password = ?";
			PreparedStatement loginStatement = dbCon.prepareStatement(login);
			loginStatement.setString(1, infos[1]);
			loginStatement.setString(2, infos[2]);
			ResultSet rs = loginStatement.executeQuery();
			if (rs.next()) {
				this.uid= rs.getInt(1);
				String nickName = "select nickname from users where uid = ?";
				PreparedStatement nickNameStatement = dbCon.prepareStatement(nickName);
				nickNameStatement.setInt(1, this.uid);
				rs = nickNameStatement.executeQuery();
				rs.next();
				this.name = rs.getString(1);
				if(this.name==null) {
					this.send("NickName");
				}
				else
					this.send("Login 1");
			} else
				this.send("Login 0");
		}
		//Handle SignUp message
		else if (infos[0].equals("SignUp")) {
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
		}
		//Handle Ready message
		else if (infos[0].equals("Ready")) {
			if (infos[1].equals("1")) {
				server.getGame().getPlayers().get(this.inGameId).setIsReady(true);
				System.out.println("Player " + this.inGameId + " ready");
				if (server.checkStart())
					new Thread(() -> {
						server.sendAll("PlayerCount "+server.getGame().getPlayers().size());
		 				server.sendAll("Start");
						System.out.println("Game start!");
						while (true) {
							server.getGame().testNextRound();
						}
					}).start();
			}

		}
		//Handle RollDice message
		else if (infos[0].equals("RollDice")) {
			synchronized (server.getGame()) {
				server.getGame().notify();
			}
		}
		//Handle NickName message
		else if (infos[0].equals("NickName")) {
			String nickName = "select count(*) from users where nickname = ?";
			PreparedStatement nickNameStatement = dbCon.prepareStatement(nickName);
			nickNameStatement.setString(1, infos[1]);
			ResultSet rs = nickNameStatement.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) {
				this.name = infos[1];
				this.send("NickName 1");
				String insertNickName = "update users set nickname = ? where uid = ?";
				PreparedStatement insertNickNameStatement = dbCon.prepareStatement(insertNickName);
				insertNickNameStatement.setString(1, infos[1]);
				insertNickNameStatement.setInt(2, this.uid);
				insertNickNameStatement.executeQuery();
				
			}
			else {
				this.send("NickName 0");
			}
			
		}
	}
	/**
	 * This method is to create a new thread to listen to the message sent by the
	 * client
	 */
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
	/**
	 * This method is to close the connection to client
	 */
	public void close() {
		try {
			out.close();
			in.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method sends messages to the server
	 * @param info the message to be sent
	 */
	public void send(String info) {
		out.println(info);
	}

}
