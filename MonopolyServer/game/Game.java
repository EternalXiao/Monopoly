package MonopolyServer.game;

import java.util.*;

import MonopolyServer.MainServer;
import MonopolyServer.ServerThread;

public class Game {
	private Block[] map;
	private LinkedList<Player> players;
	private int countRound;
	private int jailRound;
	private boolean isEnd;
	private int alivePlayers;
	// temporary
	private MainServer server;
	private int currentPlayer;

	public Game(MainServer server) {
		this.map = new GameMap().getMap();
		this.players = new LinkedList<>();
		this.currentPlayer = 0;
		this.server = server;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public int getCountRound() {
		return this.countRound;
	}

	public boolean getIsEnd() {
		return this.isEnd;
	}

	public Block[] getMap() {
		return map;
	}

	public void setMap(Block[] map) {
		this.map = map;
	}

	public int getAlivePlayers() {
		return this.alivePlayers;
	}

	public void setAlivePlayers(int alivePlayers) {
		this.alivePlayers = alivePlayers;
	}

	public LinkedList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(LinkedList<Player> players) {
		this.players = players;
	}

	public void addPlayer(String name) {
		this.players.add(new Player(this.players.size(), name));
	}

	public void action(Player player, Block block, int diceNum,ServerThread currentPlayerThread) {
		switch (map[player.getCurrentPosition()].getType()) {
		case Street: {
			Street street = (Street) block;
			if (!street.isOwned()) {
				System.out.println("Do you want to buy " + street.getName() + " ?");
				currentPlayerThread.send("Buy");
				this.waitDecision();
			} else if(!player.getOwnedProperties().contains(street)){
				player.pay(street.getOwner(), street.getStreetRent());
				server.sendAll("Update Money "+ player.getInGameId()+" "+player.getMoney());
				server.sendAll("Update Money" + street.getOwner().getInGameId()+" "+street.getOwner().getMoney());
			}
			break;
		}
		case Railroad: {
			Railroad railroad = (Railroad) block;
			if (!railroad.isOwned()) {
				System.out.println("Do you want to buy " + railroad.getName() + "?");
				if (player.getDecision().equalsIgnoreCase("YES")) {
					player.buy(railroad);
				} else {
					break;
				}
			} else {
				if (player.getOwnedProperties().contains(railroad)) {
					break;
				} else {
					player.pay(railroad.getOwner(), railroad.getTotalRent(railroad.getOwner().getOwnedRailroads()));
				}
			}
			break;
		}
		case Utility: {
			Utility utility = (Utility) block;
			if (!utility.isOwned()) {
				System.out.println("Do you want to buy " + utility.getName() + "?");
				if (player.getDecision().equalsIgnoreCase("YES")) {
					player.buy(utility);
				} else {
					break;
				}
			} else {
				if (player.getOwnedProperties().contains(utility)) {
					break;
				} else {
					player.pay(utility.getOwner(),
							utility.getTotalRent(utility.getOwner().getOwnedUtilities(), diceNum));
				}
			}
			break;
		}
		case Chance: { // might have problem;
			Chance chance = (Chance) block;
			chance.getAction(player);
			action(player, map[player.getCurrentPosition()], diceNum,currentPlayerThread);
			break;
		}
		case CommunityChest: {
			CommunityChest communityChest = (CommunityChest) block;
			player.setMoney(player.getMoney() + communityChest.getPrice());
			break;
		}
		case Tax: {
			Tax tax = (Tax) block;
			player.payMoney(tax.getTax());
			break;
		}
		case GoToJail: {
			player.setInJail(true);
		}
		default:
			break;
		}
	}

	public void nextRound() {
		Player player = this.getPlayers().get(currentPlayer);
		int dice1, dice2, diceNum;

		if (this.getAlivePlayers() >= 2 && countRound <= 100) {
			this.countRound++;
		} else {
			this.isEnd = true;
			System.out.println("Game Over");
			return;
		}

		if (player.isAlive()) {
			if (player.isInJail()) {
				player.setInJail(false);
				System.out.println("You are in jail, skip a round.");
				this.currentPlayer++;
				return;
			} else {
				System.out.println("Player " + player.getInGameId() + "'s turn");
				ServerThread currentPlayerThread = server.searchThread(this.currentPlayer);
				currentPlayerThread.send("YourTurn");
				currentPlayerThread.send("RollDice");
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				dice1 = Dice.getDiceNum();
				dice2 = Dice.getDiceNum();
				diceNum = dice1 + dice2;
				server.sendAll("Update Dice " + dice1 + " " + dice2);
				System.out.println(player.getInGameId() + " roll out " + diceNum);
				boolean passGo = false;
				if ((player.getCurrentPosition() + diceNum) / 40 == 1) {
					passGo = true;
				}
				player.setCurrentPosition((player.getCurrentPosition() + diceNum) % 40);
				server.sendAll("Update Position " + player.getInGameId() + " " + player.getCurrentPosition());
				
				if (passGo) {
					System.out.println(player.getInGameId()+" got 200 pound.");
					player.setMoney(player.getMoney() + 200);
					server.sendAll("Update Money "+this.currentPlayer+" "+player.getMoney());
				}
				Block block = map[player.getCurrentPosition()];
				System.out.println(player.getInGameId() + " have reached " + map[block.getPosition()].getName());
				action(player, block, diceNum,currentPlayerThread);

				if (player.getMoney() < 0) {
					player.setAlive(false);
					this.alivePlayers--;
				}
			}
		}
	}

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
			dice1 = Dice.getDiceNum();
			dice2 = Dice.getDiceNum();
			server.sendAll("Update Dice " + dice1 + " " + dice2);
			System.out.println(player.getInGameId() + " roll out " + dice1 + " and " + dice2);
			player.setCurrentPosition((player.getCurrentPosition() + dice1 + dice2) % 40);
			server.sendAll("Update Position " + player.getInGameId() + " " + player.getCurrentPosition());
			Block block = map[player.getCurrentPosition()];
			System.out.println(player.getInGameId() + " have reached " + map[block.getPosition()].getName());
		}
		this.currentPlayer = (this.currentPlayer + 1) % this.getPlayers().size();
	}
	public synchronized void waitDecision() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
