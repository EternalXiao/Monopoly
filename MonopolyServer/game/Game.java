package MonopolyServer.game;

import java.util.*;

public class Game {
	private Block[] map;
	private LinkedList<Player> players;
	private int alivePlayers;
	//private LinkedList<Player> alivePlayers;
	
	public Game() {
		map = new Block[40];
		players = new LinkedList<>();
		players.add(new Player(1));
		players.add(new Player(2));
		this.alivePlayers=this.players.size();
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

	public void initMap() {
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
		map[35]=new Street(35,"Liverpool Street Station",BlockType.Railroad,200,25);
		map[36]=new Chance(36,"Chance",BlockType.Chance);
		map[37]=new Street(37,"Park Lane",BlockType.Street,350,35);
		map[38]=new Tax(38,"Super Tax",BlockType.Tax,100);
		map[39]=new Street(39,"Mayfair",BlockType.Street,400,50);
	}
	
	public void start() {
		this.initMap();
		Scanner in = new Scanner(System.in);
		while(true) {
			for(Player player:players) {
				if(player.isAlive()) {
					System.out.println("Player "+player.getId()+"' turn");
					System.out.println("Enter any character to roll dice");
					int dice1,dice2;
					in.nextLine();
					dice1 = Dice.rollDice();
					dice2 = Dice.rollDice();
					player.setCurrentPosition(player.getCurrentPosition()+dice1+dice2);
					Block block = map[player.getCurrentPosition()];
					System.out.println("You have reached "+map[block.getPosition()].getName());
					switch(map[block.getPosition()].getType()) {
					case Street:
						Street street = (Street)block;
						if(!street.isOwned()) {
							System.out.println("Enter y to buy, else n (Price "+street.getPrice()+")");
							String decision = in.nextLine().trim();
							if(decision.equalsIgnoreCase("y")) {
								//buy
								street.setOwned(true);
								street.setOwner(player);
								player.getOwnedProperties().add(street);
								//money not enough
								player.setMoney(player.getMoney()-street.getPrice());
							}
						}
						else {
							//money not enough
							player.setMoney(player.getMoney()-street.getInitialRent());
							//pay
							Player anotherPlayer = street.getOwner();
							anotherPlayer.setMoney(anotherPlayer.getMoney()+street.getInitialRent());
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}
	public static void main(String... args) {
		Game game = new Game();
		game.start();
	}
}
