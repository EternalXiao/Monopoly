package MonopolyClient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.application.Platform;
import MonopolyServer.game.*;
public class MainClient {
	private Socket client;
	private String serverIP;
	private int port;
	private PrintStream out;
	private Scanner in;
	private ClientGUI gui;
	//temporary
	private Scanner keyIn;
	private Block[] map;
	private LinkedList<Player> players;

	public MainClient(String serverIP, int port) {
		this.serverIP = serverIP;
		this.port = port;
		this.connect();
		//temp
		this.keyIn = new Scanner(System.in);
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
		else if(infos[0].equals("RollDice")) {
			System.out.println("Enter any to roll dice");
			keyIn.nextLine();
			this.send("RollDice");
		}
		else if(infos[0].equals("Dice")) {
			
		}
		else if(infos[0].equals("Update")) {
			if(infos[1].equals("Position")) {
				this.players.get(Integer.parseInt(infos[2])).setCurrentPosition(Integer.parseInt(infos[3]));
			}
		}
		else if(infos[0].equals("Start")) {
			this.initMap();
			this.players.add(new Player(0));
			this.players.add(new Player(1));
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
	public void initMap() {
		map=new Block[40];
		map[0]=new Block(0,"Go",BlockType.Go);
		map[1]=new Street(1,"Old Kent Road",BlockType.Street,60,2);
		map[2]=new Chance(2,"Chance",BlockType.Chance);
		map[3]=new Street(3,"Whitechapel Road",BlockType.Street,60,4);
		map[4]=new Tax(4,"Income Tax",BlockType.Tax,200);
		map[5]=new Railroad(5,"King's Cross Station",BlockType.Railroad,200,25);
		map[6]=new Street(6,"The Angel Islington",BlockType.Street,100,6);
		map[7]=new Chance(7,"Chance",BlockType.Chance);
		map[8]=new Street(8,"Euston Road",BlockType.Street,100,6);
		map[9]=new Street(9,"Pentonville Road",BlockType.Street,120,8);
		map[10]=new Block(10,"Jail",BlockType.Jail);
		map[11]=new Street(11,"Pall Mall",BlockType.Street,140,10);
		map[12]=new Utility(12,"Electric Company",BlockType.Utility,150,4);
		map[13]=new Street(13,"Whitehall",BlockType.Street,140,10);
		map[14]=new Street(14,"Northumberland Avenue",BlockType.Street,160,12);
		map[15]=new Railroad(15,"Marylebone Station",BlockType.Railroad,200,25);
		map[16]=new Street(16,"Bow Street",BlockType.Street,180,14);
		map[17]=new Chance(17,"Chance",BlockType.Chance);
		map[18]=new Street(18,"Marlborough Street",BlockType.Street,180,14);
		map[19]=new Street(19,"Vine Street",BlockType.Street,200,16);
		map[20]=new Block(20,"Free Parking",BlockType.Parking);
		map[21]=new Street(21,"The Strand",BlockType.Street,220,18);
		map[22]=new Chance(22,"Chance",BlockType.Chance);
		map[23]=new Street(23,"Fleet Street",BlockType.Street,220,18);
		map[24]=new Street(24,"Trafalgar Square",BlockType.Street,240,20);
		map[25]=new Railroad(25,"Fenchurch st Station",BlockType.Railroad,200,25);
		map[26]=new Street(26,"Leicester Square",BlockType.Street,260,22);
		map[27]=new Street(27,"Coventry Street",BlockType.Street,260,22);
		map[28]=new Utility(28,"Water Works",BlockType.Utility,150,4);
		map[29]=new Street(29,"Piccadilly",BlockType.Street,280,22);
		map[30]=new Block(30,"Go To Jail",BlockType.GoToJail);
		map[31]=new Street(31,"Regent Street",BlockType.Street,300,26);
		map[32]=new Street(32,"Oxford Street",BlockType.Street,300,26);
		map[33]=new Chance(33,"Chance",BlockType.Chance);
		map[34]=new Street(34,"Bond Street",BlockType.Street,320,28);
		map[35]=new Railroad(35,"Liverpool Street Station",BlockType.Railroad,200,25);
		map[36]=new Chance(36,"Chance",BlockType.Chance);
		map[37]=new Street(37,"Park Lane",BlockType.Street,350,35);
		map[38]=new Tax(38,"Super Tax",BlockType.Tax,100);
		map[39]=new Street(39,"Mayfair",BlockType.Street,400,50);
	}
}
