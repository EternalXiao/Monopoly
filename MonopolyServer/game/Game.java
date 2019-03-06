package MonopolyServer.game;

import java.util.*;

import MonopolyServer.MainServer;
import javafx.application.Platform;

public class Game {
	private Block[] map;
	private LinkedList<Player> players;
	private int alivePlayers;
	// temporary
	private MainServer server;
	private int currentPlayer;
	Scanner in;

	public Game(MainServer server) {
		map = new GameMap().getMap();
		players = new LinkedList<>();
		this.currentPlayer = 0;
		this.alivePlayers = this.players.size();
		in = new Scanner(System.in);
		this.server = server;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public Block[] getMap() {
		return map;
	}

	public void setMap(Block[] map) {
		this.map = map;
	}

	public LinkedList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(LinkedList<Player> players) {
		this.players = players;
	}

	public int getAlivePlayers() {
		return alivePlayers;
	}

	public void setAlivePlayers(int alivePlayers) {
		this.alivePlayers = alivePlayers;
	}

	public void addPlayer(String name) {
		this.players.add(new Player(this.players.size(),name));
	}

	// temporary
	public synchronized void testNextRound() {
		Player player = this.getPlayers().get(currentPlayer);
		if (player.isAlive()) {
			System.out.println("Player " + player.getInGameId() + "'s turn");
			System.out.println("Enter any character to roll dice");
			int dice1, dice2;
			server.searchThread(this.currentPlayer).send("RollDice");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dice1 = Dice.rollDice();
			dice2 = Dice.rollDice();
			server.sendAll("Update Dice "+dice1+" "+dice2);
			System.out.println("You roll out "+dice1 +" and "+dice2);
			player.setPreviousPosition(player.getCurrentPosition());
			player.setCurrentPosition((player.getCurrentPosition() + dice1 + dice2) % 40);
			server.sendAll("Update Position " + player.getInGameId() + " " + player.getCurrentPosition());
			Block block = map[player.getCurrentPosition()];
			System.out.println(player.getInGameId() + " have reached " + map[block.getPosition()].getName());
		}
		this.currentPlayer = (this.currentPlayer + 1) % this.getPlayers().size();
	}

	public void nextRound() {

		Player player = this.getPlayers().get(currentPlayer);
		if (player.isAlive()) {
			System.out.println("Player " + player.getInGameId() + "'s turn");
			System.out.println("Enter any character to roll dice");
			int dice1, dice2;
			server.getConnectedClients().get(this.currentPlayer).send("RollDice");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dice1 = Dice.rollDice();
			dice2 = Dice.rollDice();
			// server.sendAll("Dice "+dice1+" "+dice2);
			// System.out.println("You roll out "+dice1 +" and "+dice2);
			player.setCurrentPosition((player.getCurrentPosition() + dice1 + dice2) % 40);
			server.sendAll("Update Position " + player.getInGameId() + " " + player.getCurrentPosition());
			// Platform.runLater(()->mp.update());
			Block block = map[player.getCurrentPosition()];
			System.out.println(player.getInGameId() + " have reached " + map[block.getPosition()].getName());
			switch (map[block.getPosition()].getType()) {
			case Street:
				Street street = (Street) block;
				if (!street.isOwned()) {
					System.out.println("Enter y to buy, else n (Price " + street.getPrice() + ")");
					String decision = in.nextLine().trim();
					if (decision.equalsIgnoreCase("y")) {
						if (!player.buy(street)) {
							System.out.println("Not enough money");
						}
					}
				} else {
					// money not enough
					player.pay(street.getOwner(), street.getInitialRent());
				}
				break;
			case Railroad:
				Railroad railroad = (Railroad) block;
				if (railroad.isOwned()) {
					player.pay(railroad.getOwner(), railroad.getTotalRent(railroad.getOwner().getOwnedRailroads()));
				} else {
					System.out.println("Enter y to buy, else n (Price " + railroad.getPrice() + ")");
					String decision = in.nextLine().trim();
					if (decision.equalsIgnoreCase("y")) {
						if (!player.buy(railroad)) {
							System.out.println("Not enough money");
						}
					}
				}
				break;
			case Utility:
				Utility utility = (Utility) block;
				if (utility.isOwned()) {
					player.pay(utility.getOwner(),
							utility.getTotalRent(utility.getOwner().getOwnedRailroads(), dice1 + dice2));
				} else {
					System.out.println("Enter y to buy, else n (Price " + utility.getPrice() + ")");
					String decision = in.nextLine().trim();
					if (decision.equalsIgnoreCase("y")) {
						if (!player.buy(utility)) {
							System.out.println("Not enough money");
						}
					}
				}
				break;
			case Tax:
				player.payMoney(((Tax) block).getTax());
				break;
			case Chance:
				player.receiveMoney(((Chance) block).getChance());
				System.out.println("You got 200");
				break;
			case Go:
				// only step on
				player.receiveMoney(200);
				System.out.println("You got 200 by passing Go");
				break;
			case GoToJail:
				player.setCurrentPosition(10);
				player.setInJail(true);
				break;
			default:
				break;
			}
		}
		this.currentPlayer = (this.currentPlayer + 1) % this.getPlayers().size();
	}

	public void start() {

		while (true) {
			for (Player player : players) {
				if (player.isAlive()) {
					System.out.println("Player " + player.getInGameId() + "' turn");
					System.out.println("Enter any character to roll dice");
					int dice1, dice2;
					in.nextLine();
					dice1 = Dice.rollDice();
					dice2 = Dice.rollDice();
					System.out.println("You roll out " + dice1 + " and " + dice2);
					player.setCurrentPosition((player.getCurrentPosition() + dice1 + dice2) % 40);
					Block block = map[player.getCurrentPosition()];
					System.out.println("You have reached " + map[block.getPosition()].getName());
					switch (map[block.getPosition()].getType()) {
					case Street:
						Street street = (Street) block;
						if (!street.isOwned()) {
							System.out.println("Enter y to buy, else n (Price " + street.getPrice() + ")");
							String decision = in.nextLine().trim();
							if (decision.equalsIgnoreCase("y")) {
								if (!player.buy(street)) {
									System.out.println("Not enough money");
								}
							}
						} else {
							// money not enough
							player.pay(street.getOwner(), street.getInitialRent());
						}
						break;
					case Railroad:
						Railroad railroad = (Railroad) block;
						if (railroad.isOwned()) {
							player.pay(railroad.getOwner(),
									railroad.getTotalRent(railroad.getOwner().getOwnedRailroads()));
						} else {
							System.out.println("Enter y to buy, else n (Price " + railroad.getPrice() + ")");
							String decision = in.nextLine().trim();
							if (decision.equalsIgnoreCase("y")) {
								if (!player.buy(railroad)) {
									System.out.println("Not enough money");
								}
							}
						}
						break;
					case Utility:
						Utility utility = (Utility) block;
						if (utility.isOwned()) {
							player.pay(utility.getOwner(),
									utility.getTotalRent(utility.getOwner().getOwnedRailroads(), dice1 + dice2));
						} else {
							System.out.println("Enter y to buy, else n (Price " + utility.getPrice() + ")");
							String decision = in.nextLine().trim();
							if (decision.equalsIgnoreCase("y")) {
								if (!player.buy(utility)) {
									System.out.println("Not enough money");
								}
							}
						}
						break;
					case Tax:
						player.payMoney(((Tax) block).getTax());
						break;
					case Chance:
						player.receiveMoney(((Chance) block).getChance());
						System.out.println("You got 200");
						break;
					case Go:
						// only step on
						player.receiveMoney(200);
						System.out.println("You got 200 by passing Go");
						break;
					case GoToJail:
						player.setCurrentPosition(10);
						player.setInJail(true);
						break;
					default:
						break;
					}
				}
			}
		}
	}

	public static void main(String... args) {
		// Game game = new Game();
		// game.start();
		while (true) {
			// game.nextRound();
		}
	}
}
