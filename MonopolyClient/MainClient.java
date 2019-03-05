package MonopolyClient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.application.Platform;
import MonopolyServer.game.*;
import gui.MainGameDesk;

public class MainClient {
	private Socket client;
	private String serverIP;
	private int port;
	private PrintStream out;
	private Scanner in;
	private ClientGUI gui;
	// temporary
	private Scanner keyIn;
	private Block[] map;
	private LinkedList<Player> players;

	public MainClient(String serverIP, int port) {
		this.serverIP = serverIP;
		this.port = port;
		this.connect();
		// temp
		this.keyIn = new Scanner(System.in);
	}

	public MainClient() {

	}

	public void setGUI(ClientGUI gui) {
		this.gui = gui;
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

	public boolean connect(String serverIP, int port) {
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
	public LinkedList<Player> getPlayers(){
		return this.players;
	}

	public void login(String username, String password) {
		this.send("Login " + username + " " + password);
	}

	public void signUp(String username, String password) {
		this.send("SignUp " + username + " " + password);
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

	/**
	 * This method parses the messages sent by the server The messages are in the
	 * following format: <message type> <message content> The first string
	 * represents the message type. All the types are as follows: i: Login ii:
	 * SignUp iii: Ready iv: Start v: YourTurn vi: Update
	 * 
	 * Followed by message type, the length of the message content varies from their
	 * type
	 * 
	 * i.Login The content of Login only has one bit: 1: Login success 0: Login fail
	 * 
	 * ii.SignUp The content of SignUp only has one bit: 1: SignUp success 0: SignUp
	 * fail
	 * 
	 * iii.Ready Ready represents the ready status of players The content has two
	 * bits. The first bit represent the player id from 0-5 and the second bit
	 * represents ready status: 1: ready 0: not ready
	 * 
	 * iv.Start The Start message does not have content. It is just a notification
	 * from server to initialise the chess position
	 * 
	 * v.YourTurn The YourTurn message does not have content. It is just a
	 * notification from server to enable action
	 * 
	 * vi.Update The Update message is the most complicated one. The first bit in
	 * the content represents the type of data need to update. There are generally n
	 * types of data: 1.Position 2.Money 3.BlockOwner 4.BlockLevel 5.OwnedProperty
	 * 6.Alive 7.InJail 8.Dice 9.NickName
	 * 
	 * @param info the message sent by the server
	 */
	public void parseInfo(String info) {
		String[] infos = info.split(" ");
		if (infos[0].equals("Login")) {
			if (infos[1].equals("1"))
				Platform.runLater(() -> gui.mainPage());
			else
				Platform.runLater(() -> gui.loginFailed());
		} else if (infos[0].equals("SignUp")) {
			if (infos[1].equals("1"))
				Platform.runLater(() -> gui.signUpSuccess());
			else
				Platform.runLater(() -> gui.signUpFail());
		} else if (infos[0].equals("RollDice")) {
			System.out.println("Enter any to roll dice");
			keyIn.nextLine();
			this.send("RollDice");
		} else if (infos[0].equals("YourTurn")) {

		} else if (infos[0].equals("Update")) {
			if (infos[1].equals("Position")) {
				this.players.get(Integer.parseInt(infos[2])).setCurrentPosition(Integer.parseInt(infos[3]));
				Platform.runLater(()->{
					gui.updatePlayer();
				});
				
			} else if (infos[1].equals("Money")) {

			} else if (infos[1].equals("BlockOwner")) {

			} else if (infos[1].equals("BlockLevel")) {

			} else if (infos[1].equals("OwnedProperty")) {

			} else if (infos[1].equals("Alive")) {

			} else if (infos[1].equals("InJail")) {

			} else if (infos[1].equals("Dice")) {
				Platform.runLater(()->{
					gui.toggleDice(gui.getDiceLeft(), Integer.parseInt(infos[2]));
					gui.toggleDice(gui.getDiceRight(), Integer.parseInt(infos[3]));
				});
				
			}
		} else if (infos[0].equals("Start")) {
			this.map = new GameMap().getMap();
			Platform.runLater(()->{
				gui.loadChess();
				gui.updatePlayer();
			});
			
			
		} else if (infos[0].equals("Ready")) {

		} else if (infos[0].equals("NickName")) {
			if (infos.length == 1) {
				Platform.runLater(() -> {
					gui.nickName();
				});
			}
			else if(infos[1].equals("1")) {
				Platform.runLater(()->{
					gui.mainPage();
				});
			}
			else {
				Platform.runLater(()->{
					gui.nickNameFail();
				});
			}
		}
		else if(infos[0].equals("PlayerCount")) {
			this.players = new LinkedList<>();
			for(int i=0;i<Integer.parseInt(infos[1]);i++) {
				this.players.add(new Player(i));
			}
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
