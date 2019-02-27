package MonopolyServer.game;

public class Street extends Property{
	private int level;
	public Street(int position, String name, BlockType type, int price, int initialRent) {
		super(position, name, type, price, initialRent);
		this.level=0;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
