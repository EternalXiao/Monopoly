package MonopolyServer.game;

public class Utility extends Property{

	public Utility(int position, String name, BlockType type, int price, int initialRent) {
		super(position, name, type, price, initialRent);
	}
	public int getTotalRent(int amount,int diceNum) {
		int totalRent = 0;
		switch (amount) {
		case 1:
			totalRent = 4*diceNum;
			break;
		case 2:
			totalRent = 10*diceNum;
			break;
		}
		return totalRent;
	}
}
