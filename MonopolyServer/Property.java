package MonopolyServer;

public abstract class Property extends Block{
	private boolean isOwned;
	private Player owner;
	private int price;
	private int initialRent;
}
