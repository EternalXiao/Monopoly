package MonopolyServer.game;

import java.util.LinkedList;

public class Player {
	private int currentPosition;
	private int money;
	private int id;
	private boolean isInJail;
	private boolean isAlive;
	LinkedList<Property> ownedProperties;
	public Player() {
		this.currentPosition=0;
		this.money=1500;
		this.isInJail=false;
		this.isAlive=true;
		this.ownedProperties = new LinkedList<>();
	}
	public Player(int id) {
		this.id=id;
		this.currentPosition=0;
		this.money=1500;
		this.isInJail=false;
		this.isAlive=true;
		this.ownedProperties = new LinkedList<>();
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
}
