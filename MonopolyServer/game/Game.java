package MonopolyServer.game;

import java.util.*;

import MonopolyServer.MainServer;
import MonopolyServer.ServerThread;
/**
 * This class is to define the game flow of Monopoly.
 * It is characterised by the following field variables,
 * map, players, countRound, isEnd, isStart, alivePlayers, server
 * and currentPlayer
 */
public class Game {
	private Block[] map;
	private LinkedList<Player> players;
	private int countRound;
	private boolean isEnd;
	private boolean isStart;
	private int alivePlayers;
	private MainServer server;
	private int currentPlayer;
	private ArrayList<Integer> inGameQuit;
	/**
	 * Constructor for Game class
	 * @param server the main server
	 */
	public Game(MainServer server) {
		this.map = new GameMap().getMap();
		this.players = new LinkedList<>();
		this.currentPlayer = 0;
		this.server = server;
		this.inGameQuit = new ArrayList<>();
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
	public boolean getIsStart() {
		return this.isStart;
	}
	public void setIsStart(boolean isStart) {
		this.isStart=isStart;
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
		this.alivePlayers=alivePlayers;
	}
	public ArrayList<Integer> getInGameQuit(){
		return this.inGameQuit;
	}
	/**
	 * This method is to decrement the number of current alive players
	 */
	public void decreAlivePlayers() {
		this.alivePlayers--;
	}

	public LinkedList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(LinkedList<Player> players) {
		this.players = players;
	}
	/**
	 * This method is to add a new player to the game
	 * @param name the nickname of the new player
	 */
	public void addPlayer(String name) {
		this.players.add(new Player(this.players.size(), name));
	}
	/**
	 * This method is to process the action for a player at a specific block
	 * @param player the object of current player 
	 * @param block the object of the block that the player currently position
	 * @param diceNum the dice number rolled out to reach this block
	 * @param currentPlayerThread the server thread that handles the current player
	 */
	public void action(Player player, Block block, int diceNum, ServerThread currentPlayerThread) {
		switch (map[player.getCurrentPosition()].getType()) {
		case Street: {
			Street street = (Street) block;
			if (!street.isOwned()) {
				currentPlayerThread.send("SystemMessage Do you want to buy " + street.getName() + " ?");
				System.out.println("Do you want to buy " + street.getName() + " ?");
				currentPlayerThread.send("Buy");
				this.waitDecision();
				if(!player.isAlive()) {
					return;
				}
			} else if (!player.getOwnedProperties().contains(street)) {
				server.sendSystemPay(player.getName(), street.getOwner().getName(), street.getStreetRent());
				player.pay(street.getOwner(), street.getStreetRent());
				server.sendUpdateMoney(player.getInGameId(), player.getMoney());
				server.sendUpdateMoney(street.getOwner().getInGameId(), street.getOwner().getMoney());
			}else {
				System.out.println("You have own this property");
			}
			break;
		}
		case Railroad: {
			Railroad railroad = (Railroad) block;
			if (!railroad.isOwned()) {
				currentPlayerThread.send("SystemMessage Do you want to buy " + railroad.getName() + " ?");
				System.out.println("Do you want to buy " + railroad.getName() + "?");
				currentPlayerThread.send("Buy");
				this.waitDecision();
				if(!player.isAlive()) {
					return;
				}
			} else if (!player.getOwnedProperties().contains(railroad)) {
				server.sendSystemPay(player.getName(), railroad.getOwner().getName(), railroad.getTotalRent(railroad.getOwner().getOwnedRailroads()));
				player.pay(railroad.getOwner(), railroad.getTotalRent(railroad.getOwner().getOwnedRailroads()));
				server.sendUpdateMoney(player.getInGameId(), player.getMoney());
				server.sendUpdateMoney(railroad.getOwner().getInGameId(), railroad.getOwner().getMoney());
			}
			break;
		}
		case Utility: {
			Utility utility = (Utility) block;
			if (!utility.isOwned()) {
				currentPlayerThread.send("SystemMessage Do you want to buy " + utility.getName() + " ?");
				System.out.println("Do you want to buy " + utility.getName() + "?");
				currentPlayerThread.send("Buy");
				this.waitDecision();
				if(!player.isAlive()) {
					return;
				}
			} else if (!player.getOwnedProperties().contains(utility)) {
				server.sendSystemPay(player.getName(), utility.getOwner().getName(), utility.getTotalRent(utility.getOwner().getOwnedUtilities(), diceNum));
				player.pay(utility.getOwner(), utility.getTotalRent(utility.getOwner().getOwnedUtilities(), diceNum));
				server.sendUpdateMoney(player.getInGameId(), player.getMoney());
				server.sendUpdateMoney(utility.getOwner().getInGameId(), utility.getOwner().getMoney());
			}
			break;
		}
		case Chance: { // might have problem;
			Chance chance = (Chance) block;
			chance.getAction(player,server);
			server.sendUpdatePosition(player.getInGameId(), player.getCurrentPosition());
			this.waitDecision(); //waiting for update complete
			if(!player.isAlive()) {
				return;
			}
			action(player, map[player.getCurrentPosition()], diceNum, currentPlayerThread);
			break;
		}
		case CommunityChest: {
			CommunityChest communityChest = (CommunityChest) block;
			int money = communityChest.getPrice();
			server.sendSystemNormalMessage(player.getName(), "got "+money +"£");
			player.receiveMoney(money);
			server.sendUpdateMoney(player.getInGameId(), player.getMoney());
			break;
		}
		case Tax: {
			Tax tax = (Tax) block;
			player.payMoney(tax.getTax());
			server.sendSystemPayTax(player.getName(), tax.getTax());
			server.sendUpdateMoney(player.getInGameId(), player.getMoney());
			break;
		}
		case GoToJail: {
			player.setCurrentPosition(10);
			server.sendSystemNormalMessage(player.getName(), "is sent to jail (Skip a round)");
			server.sendUpdatePosition(player.getInGameId(), player.getCurrentPosition());
			this.waitDecision(); //waiting for update complete
			if(!player.isAlive()) {
				return;
			}
			player.setInJail(true);
		}
		default:
			break;
		}
	}
	/**
	 * This method is to execute the game flow of next round
	 */
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
				server.sendSystemNormalMessage(player.getName(), "is in Jail. Skip a round.");
				System.out.println("You are in jail, skip a round.");
				this.currentPlayer=(this.currentPlayer+1)%this.players.size();
				return;
			} else {
				System.out.println("Player " + player.getInGameId() + "'s turn");
				server.sendSystemNormalMessage(player.getName(), "turn to roll dice.");
				ServerThread currentPlayerThread = server.searchThread(this.currentPlayer);
				currentPlayerThread.send("YourTurn");
				this.waitDecision();
				if(!player.isAlive()) {
					return;
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
				server.sendUpdatePosition(player.getInGameId(), player.getCurrentPosition());
				this.waitDecision(); //waiting for update complete
				if(!player.isAlive()) {
					return;
				}
				if (passGo) {
					server.sendSystemNormalMessage(player.getName(), "passed go. Got 200£.");
					System.out.println(player.getInGameId() + " got 200 pound.");
					player.setMoney(player.getMoney() + 200);
					server.sendAll("Update Money " + this.currentPlayer + " " + player.getMoney());
				}
				Block block = map[player.getCurrentPosition()];
				System.out.println(player.getInGameId() + " have reached " + map[block.getPosition()].getName());
				action(player, block, diceNum, currentPlayerThread);
				if(!player.isAlive()) {
					return;
				}
				currentPlayerThread.send("FreeAction");
				this.waitDecision();
				if(!player.isAlive()) {
					return;
				}
				if (player.getMoney() < 0) {
					player.inDebt();
					this.clearPlayerProperties(player.getInGameId());
					this.server.sendInDebtPlayer(player.getInGameId());
					this.decreAlivePlayers();
				}
			}
		}
		this.currentPlayer=(this.currentPlayer+1)%this.players.size();
	}
	/**
	 * This method is to force the current thread into wait status for client to make actions
	 */
	public synchronized void waitDecision() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method is to clear the properties of a specific player
	 * @param id thd id of the player
	 */
	public void clearPlayerProperties(int id) {
		for(Property property:this.players.get(id).getOwnedProperties()) {
			this.server.sendUpdateOwner(property.getPosition(), -1);
			if(property.getType()==BlockType.Street)
				this.server.sendUpdateLevel(property.getPosition(), 0);
		}
	}
	public synchronized void reset() {
		for(int id:this.inGameQuit) {
			this.server.playerExit(id);
		}
		this.resetPlayers();
		this.map = new GameMap().getMap();
		this.countRound =0;
		this.currentPlayer=0;
		this.isEnd=false;
		this.isStart=false;
		this.inGameQuit = new ArrayList<>();
	}
	public void resetPlayers() {
		for(Player player:this.players) {
			player.reset();
		}
	}
}
