package MonopolyServer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import MonopolyServer.game.*;
import database.Database;
/**
 * This class create a thread to handle the issue with one client.
 * It is characterised by the following field variables, client, server,
 * out, in, inGameId, name, uid, isLoggedIn, player, inContact and connected
 */
public class ServerThread extends Thread {
	private Socket client;
	private MainServer server;
	private PrintStream out;
	private Scanner in;
	private int inGameId;
	private String name;
	private int uid;
	private boolean isLoggedIn;
	private Player player;
	private boolean inContact;
	private boolean connected;
	/**
	 * Constructor for the ServerThread
	 * @param client the client socket
	 * @param server the main server
	 */
	public ServerThread(Socket client, MainServer server) {
		this.client = client;
		this.server = server;
		this.isLoggedIn = false;
		this.inContact = true;
		this.connected = true;
		try {
			out = new PrintStream(client.getOutputStream());
			in = new Scanner(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getUid() {
		return this.uid;
	}

	public Player getPlayer() {
		return this.player;
	}

	public boolean getLoggedIn() {
		return this.isLoggedIn;
	}

	public int getInGameId() {
		return this.inGameId;
	}

	public void setInGameId(int inGameId) {
		this.inGameId = inGameId;
	}

	/**
	 * This is the override method which will be run once start method being called
	 * A new thread will be created to listen to the client message. Meanwhile, this
	 * thread will send heartbeat message to the client every five second to check client
	 * online status
	 */
	@Override
	public void run() {
		System.out.println("Connecting from : " + this.client.getInetAddress());
		this.listenClient();
		this.sendHeartBeat();
	}

	/**
	 * This method parses the message sent by the client. The message are in the
	 * following format: <message type> <message arguments>. For detailed specification
	 * of the communication protocol, please refer to the report
	 * 
	 * @param info the message sent by the client
	 * @throws Exception
	 */
	public void parseInfo(String info) throws Exception {
		String[] infos = info.split(" ");
		// Handle Login message
		if (infos[0].equals("Login")) {
			Database database = Database.getInstance();
			if (database.login(infos[1], infos[2])) {
				this.uid = database.getUid(infos[1]);
				synchronized (this.server.getGame()) {
					if (this.server.getGame().getIsStart()) {
						this.send("Login 3");
						return;
					}
					if (this.server.getGame().getPlayers().size() == 6) {
						this.send("Login 4");
						return;
					}
					if (this.server.checkOnline(this.uid)) {
						this.send("Login 2");
						return;
					}

					this.name = database.getNickName(this.uid);
					this.server.getConnectedClients().add(this);
					this.isLoggedIn = true;
					this.send("Login 1");
					String lastLogin = database.getlastlogintime(this.uid);
					System.out.println(lastLogin);
					database.updatelogintime(this.uid);
					this.send("LastLogin " + lastLogin);
					this.sendCurrentPlayerProfile();
					this.inGameId = server.getGame().getPlayers().size();
					//this.send("Id " + this.inGameId);
					this.server.getGame().addPlayer(this.name);
					this.server.sendAll("Player " + this.inGameId + " " + this.name);
					this.player = this.server.getGame().getPlayers().get(this.inGameId);
				}
			} else {
				this.send("Login 0");
			}
		}
		// Handle SignUp message
		else if (infos[0].equals("SignUp")) {
			Database database = Database.getInstance();
			String username = infos[1];
			String password = infos[2];
			String nickname = infos[3];
			if (!database.checkUsernameAvailable(username)) {
				this.send("SignUp 0");
			} else if (!database.checkNicknameAvailable(nickname)) {
				this.send("SignUp 1");
			} else {
				database.signUp(username, password, nickname);
				this.send("SignUp 2");
			}
		}
		// Handle Ready message
		else if (infos[0].equals("Ready")) {
			if (infos[1].equals("1")) {
				server.getGame().getPlayers().get(this.inGameId).setIsReady(true);
				server.sendAll("Ready " + this.inGameId + " 1");
				server.sendSystemNormalMessage(this.name, "ready");
				System.out.println("Player " + this.inGameId + " ready");
				if (server.checkStart())
					server.gameStart();
			}

		}
		// Handle RollDice message
		else if (infos[0].equals("RollDice")) {
			synchronized (server.getGame()) {
				server.getGame().notify();
			}
		}
		// Handle Buy message
		else if (infos[0].equals("Buy")) {
			if (infos[1].equals("1")) {
				if (player.buy((Property) server.getGame().getMap()[player.getCurrentPosition()])) {
					server.sendSystemNormalMessage(this.name,
							"bought " + server.getGame().getMap()[player.getCurrentPosition()].getName());
					server.sendUpdateMoney(this.inGameId, player.getMoney());
					server.sendUpdateOwner(player.getCurrentPosition(), player.getInGameId());
				} else {
					this.send("SystemMessage Do not have enough money");
				}
			}
			synchronized (server.getGame()) {
				server.getGame().notify();
			}

		}
		// Handle ChatMessage message
		else if (infos[0].equals("ChatMessage")) {
			server.sendChatMessage(this.name, info.substring(11));
		}
		// Handle Sell Message
		else if (infos[0].equals("Sell")) {
			int sellResult = this.player.sell((Property) this.server.getGame().getMap()[Integer.parseInt(infos[1])]);
			if (sellResult == 0) {
				this.send("SystemMessage This is not your property");
			} else if (sellResult == 1) {
				this.send("SystemMessge You must degrade evenly");
			} else if (sellResult == 2) {
				server.sendSystemNormalMessage(this.name,
						"degraded " + this.server.getGame().getMap()[Integer.parseInt(infos[1])].getName());
				server.sendUpdateMoney(this.inGameId, this.player.getMoney());
				server.sendUpdateLevel(Integer.parseInt(infos[1]),
						((Street) this.server.getGame().getMap()[Integer.parseInt(infos[1])]).getHouseNum());
			} else {
				server.sendSystemNormalMessage(this.name,
						"sold " + this.server.getGame().getMap()[Integer.parseInt(infos[1])].getName());
				server.sendUpdateMoney(this.inGameId, this.player.getMoney());
				server.sendUpdateOwner(Integer.parseInt(infos[1]), -1);
			}
		}
		// Handle Build message
		else if (infos[0].equals("Build")) {
			int buildResult = this.player
					.buildHouse((Street) this.server.getGame().getMap()[Integer.parseInt(infos[1])]);
			if (buildResult == 0) {
				this.send("SystemMessage This is not your property");
			} else if (buildResult == 1) {
				this.server.sendSystemNormalMessage(this.name, "upgraded house");
				this.server.sendUpdateMoney(this.inGameId, this.player.getMoney());
				this.server.sendUpdateLevel(Integer.parseInt(infos[1]),
						((Street) this.server.getGame().getMap()[Integer.parseInt(infos[1])]).getHouseNum());
			} else if (buildResult == 2) {
				this.send("SystemMessage You do not have enough money");
			}else if(buildResult==3) {
				this.send("SystemMessage You should build house evenly");
			}
			else if(buildResult ==4){
				this.send("SystemMessage You do not own this group");
			}else {
				this.send("SystemMessage House is already in max level");
			}
		}
		// Handle EndRound
		else if (infos[0].equals("EndRound")) {
			synchronized (server.getGame()) {
				server.getGame().notify();
			}
		}
		// Handle UpdateFinish
		else if (infos[0].equals("UpdateFinish")) {
			synchronized (server.getGame()) {
				this.server.getGame().notify();
			}
		}
		// Handle Exit
		else if (infos[0].equals("Exit")) {
			if (this.connected) {
				System.out.println(this.name + " exit");
				this.connected = false;
				if (this.server.getGame().getIsStart()) {
					this.server.getGame().getInGameQuit().add(this.inGameId);
					this.server.getGame().clearPlayerProperties(this.inGameId);
					this.player.inDebt();
					this.server.getGame().decreAlivePlayers();
					this.server.sendInDebtPlayer(this.inGameId);
					System.out.println(this.inGameId + " "+this.server.getGame().getCurrentPlayer());
					if (this.server.getGame().getCurrentPlayer() == this.inGameId) {
						synchronized (this.server.getGame()) {
							this.server.getGame().notify();
						}
					}
				} else {
					if (!this.isLoggedIn)
						return;
					this.server.playerExit(this.inGameId);
				}
			}
		} else if (infos[0].equals("HeartBeat")) {
			this.inContact = true;
		}

	}

	/**
	 * This method is to create a new thread to listen to the message sent by the
	 * client
	 */
	public void listenClient() {
		new Thread(() -> {
			while (in.hasNext()) {
				String request = in.nextLine().trim();
				if (!request.equals("HeartBeat"))
					System.out.println("Receiving Client request :" + request);
				try {
					parseInfo(request);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (request.equals("Exit")) {
					this.close();
					break;
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
	 * 
	 * @param info the message to be sent
	 */
	public void send(String info) {
		out.println(info);
	}
	/**
	 * This method is to send all the current player profiles to the client
	 */
	public void sendCurrentPlayerProfile() {
		for (int i = 0; i < this.server.getGame().getPlayers().size(); i++) {
			this.send("Player " + i + " " + this.server.getGame().getPlayers().get(i).getName());
			if(this.server.getGame().getPlayers().get(i).isReady())
				this.send("Ready "+ i+" "+"1");
		}
	}
	/**
	 * This method is to send heart beat message to the client every five seconds
	 * and check whether the client has responded
	 */
	public void sendHeartBeat() {
		while (this.inContact) {
			this.send("HeartBeat");
			this.inContact = false;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			this.parseInfo("Exit");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
