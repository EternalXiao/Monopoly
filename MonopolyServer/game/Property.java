package MonopolyServer.game;

public abstract class Property extends Block{
	private boolean isOwned;
	private Player owner;
	private int price;
	private int initialRent;
	public Property(int position, String name, BlockType type,int price,int initialRent) {
		super(position, name, type);
		this.price=price;
		this.initialRent=initialRent;
		this.isOwned=false;
		this.owner=null;
	}
	public boolean isOwned() {
		return isOwned;
	}
	public void setOwned(boolean isOwned) {
		this.isOwned = isOwned;
	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getInitialRent() {
		return initialRent;
	}
	public void setInitialRent(int initialRent) {
		this.initialRent = initialRent;
	}
	
}
