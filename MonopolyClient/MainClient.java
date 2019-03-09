package MonopolyClient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.text.Font;
import MonopolyClient.game.*;
import gui.*;

public class MainClient {
	private Socket client;
	private String serverIP;
	private int port;
	private PrintStream out;
	private Scanner in;
	private ClientStage gui;
	// temporary
	private Block[] map;
	private LinkedList<Player> players;

	public MainClient(String serverIP, int port) {
		this.serverIP = serverIP;
		this.port = port;
		this.connect();
		// temp
		this.players = new LinkedList<>();
	}

	public MainClient() {

	}

	public void setGUI(ClientStage gui) {
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

	public LinkedList<Player> getPlayers() {
		return this.players;
	}
	/**
	 * This method sends login message,including username and password to the server
	 * @param username the username provided
	 * @param password the password provided
	 */
	public void login(String username, String password) {
		this.send("Login " + username + " " + password);
	}
	/**
	 * This method sends sign up message, including username, password and nickname to the server
	 * @param username the username provided
	 * @param password the password provided
	 */
	public void signUp(String username, String password) {
		this.send("SignUp " + username + " " + password);
	}
	/**
	 * This method will create a new thread to listen to the server and
	 * parse the message received
	 */
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
	 * vi.Buy 
	 * 
	 * vii.Update The Update message is the most complicated one. The first bit in
	 * the content represents the type of data need to update. There are generally n
	 * types of data: 1.Position 2.Money 3.BlockOwner 4.BlockLevel 5.OwnedProperty
	 * 6.Alive 7.InJail 8.Dice 9.NickName
	 * 
	 * @param info the message sent by the server
	 */
	public void parseInfo(String info) {
		String[] infos = info.split(" ");
		// Handle Login message
		if (infos[0].equals("Login")) {
			if (infos[1].equals("1"))
				Platform.runLater(() -> gui.setGameDeskPage());
			else
				Platform.runLater(() -> gui.loginFailed());
		}
		// Handle SignUp message
		else if (infos[0].equals("SignUp")) {
			if (infos[1].equals("1"))
				Platform.runLater(() -> gui.signUpSuccess());
			else
				Platform.runLater(() -> gui.signUpFail());
		}
		// Handle Ready message
		else if (infos[0].equals("Ready")) {

		}
		//Handle Start message
		else if (infos[0].equals("Start")) {
			this.map = new GameMap().getMap();
			Platform.runLater(() -> {
				MainGameDesk.loadChess();
				MainGameDesk.initialisePlayer();
				MainGameDesk.getReadyButton().setDisable(true);
			});

		}
		// Handle RollDice message
		else if (infos[0].equals("RollDice")) {
			Platform.runLater(() -> {
				MainGameDesk.getRollButton().setDisable(false);
			});
		}
		//Handle YourTurn message
		else if (infos[0].equals("YourTurn")) {
			Platform.runLater(() -> {
				MainGameDesk.getRollButton().setDisable(false);
			});
		}
		//Handle Buy message
		else if(infos[0].equals("Buy")){
			//buy button
		}
		//Handle Update message
		else if (infos[0].equals("Update")) {
			if (infos[1].equals("Position")) {
				this.players.get(Integer.parseInt(infos[2]))
						.setPreviousPosition(this.players.get(Integer.parseInt(infos[2])).getCurrentPosition());
				this.players.get(Integer.parseInt(infos[2])).setCurrentPosition(Integer.parseInt(infos[3]));
				MainGameDesk.updatePlayer(Integer.parseInt(infos[2]));

			} else if (infos[1].equals("Money")) {
				this.players.get(Integer.parseInt(infos[2])).setMoney(Integer.parseInt(infos[3]));
				//gui update
			} else if (infos[1].equals("BlockOwner")) {
				((Property)this.map[Integer.parseInt(infos[2])]).setOwner(this.players.get(Integer.parseInt(infos[3])));
				//gui update
			} else if (infos[1].equals("BlockLevel")) {
				((Street)this.map[Integer.parseInt(infos[2])]).setLevel(Integer.parseInt(infos[3]));
				//gui update
			} else if (infos[1].equals("OwnedProperty")) {

			} else if (infos[1].equals("Alive")) {
				this.players.get(Integer.parseInt(infos[2])).setAlive(false);
				//update alive
			} else if (infos[1].equals("InJail")) {

			} else if (infos[1].equals("Dice")) {
				Platform.runLater(() -> {
					MainGameDesk.setDiceValue(Integer.parseInt(infos[2]), Integer.parseInt(infos[3]));
				});

			}
		} else if (infos[0].equals("NickName")) {
			if (infos.length == 1) {
				Platform.runLater(() -> {
					gui.nickName();
				});
			} else if (infos[1].equals("1")) {
				Platform.runLater(() -> {
					gui.setGameDeskPage();
				});
			} else {
				Platform.runLater(() -> {
					gui.nickNameFail();
				});
			}
		}
		//Handle Player message
		else if (infos[0].equals("Player")) {
			this.players.add(new Player(Integer.parseInt(infos[1]),infos[2]));
		}
		//Handle system prompt
		else if (infos[0].equals("System")) {
		}
		else if (infos[0].equals("PlayerChat")){
			System.out.println("Client received");
			String tempStr = info.replaceFirst("PlayerChat","");
			MainGameDesk.getInformationList().appendText(tempStr);
		}

		else if (infos[0].equals("SystemCall")){
			MainGameDesk.getSystemCall().setFont(Font.font("roman",20));
			MainGameDesk.getSystemCall().setText(info);

		}
	}
	/**
	 * This method is to send message to the server
	 * @param info the message to be sent
	 */
	public void send(String info) {
		out.println(info);
	}
	/**
	 * This method is to close the connection to the server and io stream
	 */
	public void close() {
		try {
			this.out.close();
			this.in.close();
			this.client.close();
		} catch (IOException e) {
		}
	}
}
