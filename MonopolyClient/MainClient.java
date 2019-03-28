package MonopolyClient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.text.Font;
import MonopolyClient.game.*;
import gui.*;
/**
 * This class is the main class for client to communicate with server
 * and store data.
 * It is characterised by the following field variables, client, serverIP,
 * port, out, in gui, Id, map, players, freeAction, inContact, heartbeat,
 * listener and inGame
 */
public class MainClient {
	private Socket client;
	private String serverIP;
	private int port;
	private PrintStream out;
	private Scanner in;
	private ClientStage gui;
	private int Id;
	private Block[] map;
	private LinkedList<Player> players;
	private boolean freeAction;
	private boolean inContact;
	private boolean heartbeat;
	private Thread listener;
	private boolean inGame;

	/**
	 * Constructor for MainClient class
	 * @param serverIP the server ip to be connecte
	 * @param port the server port to be connect
	 */
	public MainClient(String serverIP, int port) {
		this.map = null;
		this.serverIP = serverIP;
		this.port = port;
		this.players = new LinkedList<>();
		this.freeAction = false;
		this.inGame = false;
	}
	public void setGUI(ClientStage gui) {
		this.gui = gui;
	}
	/**
	 * This method is to build up connection to server
	 * @return true if build successfully, else false
	 */
	public boolean connect() {
		try {
			this.client = new Socket();
			this.client.connect(new InetSocketAddress(this.serverIP, this.port), 1000);
			this.out = new PrintStream(client.getOutputStream());
			this.in = new Scanner(client.getInputStream());
			this.listenServer();
			this.heartbeat = true;
			this.inContact = true;
			this.checkInContact();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public Block[] getMap() {
		return map;
		
	}
	public int getId() {
		return this.Id;
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

	public boolean getInContact() {
		return this.inContact;
	}

	public boolean getFreeAction() {
		return this.freeAction;
	}

	public void setFreeAction(boolean freeAction) {
		this.freeAction = freeAction;
	}

	/**
	 * This method sends login message,including username and password to the server
	 * 
	 * @param username the username provided
	 * @param password the password provided
	 */
	public void login(String username, String password) {
		if (!this.inContact) {
			if (!this.connect()) {
				gui.getLoginPage().connectionTimeout();
				return;
			}
			System.out.println("Connected");
		}
		this.send("Login " + username + " " + password);
	}

	/**
	 * This method sends sign up message, including username, password and nickname
	 * to the server
	 * 
	 * @param username the username provided
	 * @param password the password provided
	 */
	public void signUp(String username, String password, String nickname) {
		if (!this.inContact) {
			if (!this.connect()) {
				gui.getSignUpPage().connectionTimeout();
				return;
			}
		}
		this.send("SignUp " + username + " " + password + " " + nickname);
	}

	/**
	 * This method will create a new thread to listen to the server and parse the
	 * message received
	 */
	public void listenServer() {
		listener = new Thread(() -> {
			while (in.hasNext()) {
				String info = in.nextLine().trim();
				if (!info.equals("HeartBeat"))
					System.out.println("Receiving server information :" + info);
				try {
					if (info.startsWith("Update Position"))
						new Thread(() -> parseInfo(info)).start();
					else
						parseInfo(info);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		listener.setDaemon(true);
		listener.start();
	}

	/**
	 * This method parses the messages sent by the server The messages are in the
	 * following format: <message type> <message arguments>. For detailed specification
	 * of communication protocol, please refer to the report
	 * 
	 * @param info the message sent by the server
	 */
	public void parseInfo(String info) {
		String[] infos = info.split(" ");
		// Handle Login message
		if (infos[0].equals("Login")) {
			if (infos[1].equals("1")) {
				Platform.runLater(() -> gui.setGameDeskPage());
				this.inGame = true;
			} else if (infos[1].equals("2"))
				Platform.runLater(() -> gui.getLoginPage().accountOnline());
			else if (infos[1].equals("3"))
				Platform.runLater(() -> gui.getLoginPage().gameIsStart());
			else if (infos[1].equals("4")) {
				Platform.runLater(() -> gui.getLoginPage().gameDeskFull());
			} else {
				Platform.runLater(() -> gui.getLoginPage().loginFailed());
			}
		}
		// Handle SignUp message
		else if (infos[0].equals("SignUp")) {
			if (infos[1].equals("2"))
				Platform.runLater(() -> gui.getSignUpPage().signUpSuccess());
			else if (infos[1].equals("1"))
				Platform.runLater(() -> gui.getSignUpPage().nicknameUsed());
			else
				Platform.runLater(() -> gui.getSignUpPage().usernameUsed());
		}
		// Handle Ready message
		else if (infos[0].equals("Ready")) {
			if (infos[2].equals("1"))
				this.players.get(Integer.parseInt(infos[1])).setIsReady(true);
			else
				this.players.get(Integer.parseInt(infos[1])).setIsReady(false);
			Platform.runLater(() -> {
				MainGameDesk.displayPlayersInformation();
			});
		}
		// Handle Start message
		else if (infos[0].equals("Start")) {
			this.map = new GameMap().getMap();
			Platform.runLater(() -> {
				MainGameDesk.loadChess();
				MainGameDesk.initialisePlayer();
				MainGameDesk.getReadyButton().setDisable(true);
			});

		}
		// Handle YourTurn message
		else if (infos[0].equals("YourTurn")) {
			Platform.runLater(() -> {
				MainGameDesk.getRollButton().setDisable(false);
			});
		}
		// Handle Buy message
		else if (infos[0].equals("Buy")) {
			MainGameDesk.getBuyButton().setDisable(false);
			MainGameDesk.getSkipButton().setDisable(false);
			// buy button
		}
		// Handle Update message
		else if (infos[0].equals("Update")) {
			if (infos[1].equals("Position")) {
				this.players.get(Integer.parseInt(infos[2]))
						.setPreviousPosition(this.players.get(Integer.parseInt(infos[2])).getCurrentPosition());
				this.players.get(Integer.parseInt(infos[2])).setCurrentPosition(Integer.parseInt(infos[3]));
				MainGameDesk.updatePlayer(Integer.parseInt(infos[2]));
				if (this.Id == Integer.parseInt(infos[2]))
					this.send("UpdateFinish");

			} else if (infos[1].equals("Money")) {
				this.players.get(Integer.parseInt(infos[2])).setMoney(Integer.parseInt(infos[3]));
				Platform.runLater(() -> {
					MainGameDesk.displayPlayersInformation();
				});
				// gui update
			} else if (infos[1].equals("BlockOwner")) {
				if (!infos[3].equals("-1")) {
					((Property) this.map[Integer.parseInt(infos[2])])
							.setOwner(this.players.get(Integer.parseInt(infos[3])));
				}
				else {
					((Property) this.map[Integer.parseInt(infos[2])]).setOwner(null);
				}
				Platform.runLater(() -> {
					MainGameDesk.setBlock(Integer.parseInt(infos[3]) + 1, Integer.parseInt(infos[2]));
				});

				// gui update
			} else if (infos[1].equals("BlockLevel")) {
				((Street) this.map[Integer.parseInt(infos[2])]).setHouseNum(Integer.parseInt(infos[3]));
				Platform.runLater(() -> MainGameDesk.setdHouse(Integer.parseInt(infos[3]), Integer.parseInt(infos[2])));

				// gui update
			} else if (infos[1].equals("Dice")) {
				Platform.runLater(() -> {
					MainGameDesk.setDiceValue(Integer.parseInt(infos[2]), Integer.parseInt(infos[3]));
				});

			}
		}
		// Handle Player message
		else if (infos[0].equals("Player")) {
			this.players.add(new Player(Integer.parseInt(infos[1]), infos[2]));
			
			Platform.runLater(() -> {
				MainGameDesk.displayPlayersInformation();
			});

		}
		// Handle system prompt
		else if (infos[0].equals("ChatMessage")) {
			String tempStr = info.substring(12);
			MainGameDesk.getInformationList().appendText(tempStr + "\n");
		}

		else if (infos[0].equals("SystemMessage")) {
			Platform.runLater(() -> {
				MainGameDesk.getSystemMessage().setFont(Font.font("roman", 20));
				MainGameDesk.getSystemMessage().setText(info.substring(13));
			});
		} else if (infos[0].equals("FreeAction")) {
			this.freeAction = true;
			Platform.runLater(() -> {
				MainGameDesk.getEndButton().setDisable(false);
			});
		} else if (infos[0].equals("Id")) {
			this.Id = Integer.parseInt(infos[1]);
		} else if (infos[0].equals("LastLogin")) {
			if(infos[1].equals("0"))
				MainGameDesk.getInformationList().appendText("Welcome! New player\n");
			else
				MainGameDesk.getInformationList().appendText("Last login: " + info.substring(10) + "\n");
		} else if (infos[0].equals("ResetPlayer")) {
			this.players = new LinkedList<>();
		} else if (infos[0].equals("InDebt")) {
			this.players.get(Integer.parseInt(infos[1])).inDebt();
			Platform.runLater(() -> {
				MainGameDesk.displayPlayersInformation();
				MainGameDesk.removePlayer(Integer.parseInt(infos[1]), this.players.get(Integer.parseInt(infos[1])).getCurrentPosition());
			});
		} else if (infos[0].equals("HeartBeat")) {
			this.send("HeartBeat");
			this.heartbeat = true;
		} else if (infos[0].equals("GameOver")) {
			Platform.runLater(() -> {
				gui.setWinAlert();
			});

		}
	}

	/**
	 * This method is to send message to the server
	 * 
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
	/**
	 * This method is to check whether the client is still in connection to server
	 * by checking the heartbeat every eight seconds
	 */
	public void checkInContact() {
		Thread checkServerStatus = new Thread(() -> {
			while (this.heartbeat) {
				this.heartbeat = false;
				try {
					Thread.sleep(8000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.inContact = false;
			this.listener.interrupt();
			System.out.println("Lost connection");
			if (this.inGame) {
				Platform.runLater(() -> ClientStage.setLoseServer());
			}
			// lost connection
		});
		checkServerStatus.setDaemon(true);
		checkServerStatus.start();

	}
	/**
	 * This method resets the map and players
	 */
	public void reset() {
		this.map = new GameMap().getMap();
		this.resetPlayers();
	}
	/**
	 * This method reset the data of players
	 */
	public void resetPlayers() {
		for(Player player:this.players) {
			player.reset();
		}
	}
}
