package MonopolyClient.game;
/**
 * This class is to define the player in the game.
 * It is characterised by the following field variables, 
 * currentPosition, money, inGameId, name,
 * isInJail, isAlive and isReady
 */
public class Player {
	private int currentPosition;
	private int previousPosition;
	private int money;
	private int inGameId;
	private String name;
	private boolean isAlive;
	private boolean isReady;
	/**
	 * Constructor for Player with two arguments
	 * @param inGameId the player id in the game
	 * @param name the player nickname 
	 */
	public Player(int inGameId,String name) {
		this.inGameId=inGameId;
		this.name=name;
		this.currentPosition=0;
		this.previousPosition=0;
		this.money=1500;
		this.isAlive=true;
		this.isReady=false;
	}
	public int getCurrentPosition() {
		return currentPosition;
	}
	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	public int getPreviousPosition() {
		return previousPosition;
	}
	public void setPreviousPosition(int previousPosition) {
		this.previousPosition = previousPosition;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setIsReady(boolean isReady) {
		this.isReady=isReady;
	}
	public boolean isReady() {
		return this.isReady;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
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
	 * This method set the player in debt by changing the
	 * alive status, clear all the owned properties and change
	 * them into unowned
	 */
	public void inDebt() {
		this.setAlive(false);
	}
	/**
	 * This method reset the player data
	 */
	public void reset() {
		this.currentPosition=0;
		this.previousPosition=0;
		this.money=1500;
		this.isAlive=true;
		this.isReady=false;
	}
}
