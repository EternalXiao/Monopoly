package MonopolyServer.game;

import java.util.LinkedList;
/**
 * This class is to define the player in the game.
 * It is characterised by the following field variables, 
 * ownedProperties, currentPosition, money, inGameId, name,
 * isInJail, isAlive and isReady
 */
public class Player {
	LinkedList<Property> ownedProperties;
	private int currentPosition;
	private int money;
	private int inGameId;
	private String name;
	private boolean isInJail;
	private boolean isAlive;
	private boolean isReady;
	/**
	 * Constructor for Player without arguments
	 */
	public Player() {
		this.init();
	}
	/**
	 * Constructor for Player with two arguments
	 * @param inGameId the player id in the game
	 * @param name the player nickname 
	 */
	public Player(int inGameId, String name) {
		this();
		this.inGameId = inGameId;
		this.name = name;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean isInJail() {
		return isInJail;
	}

	public void setInJail(boolean isInJail) {
		this.isInJail = isInJail;
	}

	public LinkedList<Property> getOwnedProperties() {
		return ownedProperties;
	}

	public void setOwnedProperties(LinkedList<Property> ownedProperties) {
		this.ownedProperties = ownedProperties;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void setIsReady(boolean isReady) {
		this.isReady = isReady;
	}

	public boolean isReady() {
		return this.isReady;
	}

	public int getInGameId() {
		return inGameId;
	}

	public void setInGameId(int inGameId) {
		this.inGameId = inGameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * This method initialise the field variables for player
	 */
	public void init() {
		this.currentPosition = 0;
		this.money = 1500;
		this.isInJail = false;
		this.isAlive = true;
		this.isReady = false;
		this.ownedProperties = new LinkedList<>();
	}
	/**
	 * This method allows player to receive a certain amount of money
	 * @param amount the amount of money to receive
	 */
	public void receiveMoney(int amount) {
		this.money += amount;
	}
	/**
	 * This method allows player to pay money to system
	 * @param amount the amount of money to pay
	 */
	public void payMoney(int amount) {
		this.money -= amount;
	}
	/**
	 * This method allows player to pay another player money
	 * @param player the object of another player
	 * @param amount the amount of money to pay
	 */
	public void pay(Player player, int amount) {
		this.payMoney(amount);
		player.receiveMoney(amount);
		System.out.println("You paid player " + player.getInGameId() + " with " + amount);
		System.out.println("You have " + this.money + " remaining");
	}
	/**
	 * This method allows player to buy a property
	 * @param property the property that the player desires to buy
	 * @return true if the player bought the property successfully, else false
	 */
	public boolean buy(Property property) {
		if (this.money < property.getPrice()) {
			System.out.println("Not enough money");
			return false;
		} else {
			property.setOwned(true);
			property.setOwner(this);
			this.ownedProperties.add(property);
			this.payMoney(property.getPrice());
			System.out.println("You have " + this.money + " remaining");
			return true;
		}
	}
	/**
	 * This method allows player to sell his property
	 * @param property the property that the player want to sell
	 * @return the sell status
	 */
	public int sell(Property property) {
		if (property.getOwner() != this) {
			return 0; // does not own property
		} else {
			switch (property.getType()) {
			case Street: {
				Street street = (Street) property;
				if (street.getSameColorStreetNum() == getOwnedSameColorStreet(street.getColor())) {
					if(street.getHouseNum() > 0 && getMaxHouseOnSameColor(street) - street.getHouseNum() >= 1) {
						return 1; // degrade unevenly
					}
					else if (street.getHouseNum() > 0 && getMaxHouseOnSameColor(street) - street.getHouseNum() < 1) {
						street.degradeHouse();
						this.receiveMoney(street.getHouseCost() / 2);
						return 2; // degrade successful
					} else if (street.getHouseNum() == 0 && getMaxHouseOnSameColor(street) - street.getHouseNum() < 1) {
						street.setOwned(false);
						street.setOwner(null);
						this.receiveMoney(street.getHouseCost() / 2);
					}
				} else {
					street.setOwned(false);
					street.setOwner(null);
					this.receiveMoney(street.getHouseCost() / 2);
				}
				break;
			}
			case Utility: {
				Utility utility = (Utility) property;
				utility.setOwned(false);
				utility.setOwner(null);
				this.receiveMoney(utility.getPrice() / 2);
				break;
			}
			case Railroad: {
				Railroad railroad = (Railroad) property;
				railroad.setOwned(false);
				railroad.setOwner(null);
				this.receiveMoney(railroad.getPrice() / 2);
				break;
			}
			default:
				break;
			}
			return 3; // sell successful
		}
	}
	/**
	 * This method calculates the total number of railroads owned by
	 * the player
	 * @return the owned number of railroads
	 */
	public int getOwnedRailroads() {
		int sum = 0;
		for (Property property : this.ownedProperties) {
			if (property.getType() == BlockType.Railroad)
				sum++;
		}
		return sum;
	}
	/**
	 * This method computes the number of streets with same colour
	 * for the player
	 * @param color the street colour
	 * @return the number of same colour street
	 */
	public int getOwnedSameColorStreet(Color color) {
		int sum = 0;
		for (Property property : this.ownedProperties) {
			if (property.getType() == BlockType.Street) {
				Street street = (Street) property;
				if (color.equals(street.getColor())) {
					sum++;
				}
			}
		}
		return sum;
	}
	/**
	 * This method calculates the total number of utilities
	 * owned by the player
	 * @return the total number of utilities owned
	 */
	public int getOwnedUtilities() {
		int sum = 0;
		for (Property property : this.ownedProperties) {
			if (property.getType() == BlockType.Utility)
				sum++;
		}
		return sum;
	}
	/**
	 * This method computes the minimum level of street in the same group
	 * @param street the current street block
	 * @return the minimum level in the same group
	 */
	public int getMinHouseOnSameColor(Street street) {
		int min = street.getHouseNum();
		for (Property property : this.ownedProperties) {
			if (property.getType() == BlockType.Street) {
				Street otherStreet = (Street) property;
				if (street.getColor().equals(otherStreet.getColor())) {
					if (otherStreet.getHouseNum() < street.getHouseNum()) {
						min = otherStreet.getHouseNum();
					}
				}
			}
		}
		return min;
	}
	/**
	 * This method calculates the maximum elvel of street in the same group
	 * @param street the current street block
	 * @return the maximum elvel in the same group
	 */
	public int getMaxHouseOnSameColor(Street street) {
		int max = street.getHouseNum();
		for (Property property : this.ownedProperties) {
			if (property.getType() == BlockType.Street) {
				Street otherStreet = (Street) property;
				if (street.getColor().equals(otherStreet.getColor())) {
					if (otherStreet.getHouseNum() > street.getHouseNum()) {
						max = otherStreet.getHouseNum();
					}
				}
			}
		}
		return max;
	}
	/**
	 * This method allows player to build house on a street
	 * @param street the street that the player want to build house
	 * @return build result status 
	 */
	public int buildHouse(Street street) {
		if (street.getOwner() != this) {
			return 0; // does not own street
		} else if (street.getSameColorStreetNum() == getOwnedSameColorStreet(street.getColor())
				&& street.getHouseNum() - getMinHouseOnSameColor(street) < 1) {
			if (this.money >= street.getHouseCost()) {
				if(street.getHouseNum()==5)
					return 5; // street in max level
				street.upgradeHouse();
				this.payMoney(street.getHouseCost());
				return 1; // build successful
			} else {
				System.out.println("Sorry, you don't have enough money.");
				return 2; // not enough money
			}
		}
		else if(street.getSameColorStreetNum() == getOwnedSameColorStreet(street.getColor()))
			return 3; // build unevenly
		else
			return 4; // does not own this group
	}
	/**
	 * This method set the player in debt by changing the
	 * alive status, clear all the owned properties and change
	 * them into unowned
	 */
	public void inDebt() {
		this.setAlive(false);
		for(Property property:this.ownedProperties) {
			property.setOwned(false);
			property.setOwner(null);
		}
		this.ownedProperties.clear();
	}
	/**
	 * This method reset the player data
	 */
	public void reset() {
		this.init();
	}
}
