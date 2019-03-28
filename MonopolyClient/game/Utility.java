package MonopolyClient.game;
/**
 * This class is a subclass of Property which defines utility block
 * It does not have additional field variable
 */
public class Utility extends Property {
	/**
	 * Constructor for Utility
	 * @param position the position of the block
	 * @param name the name of the block
	 * @param type the type of the block
	 * @param price the price of the block
	 */
    public Utility(int position, String name, BlockType type, int price) {
        super(position, name, type, price);
    }
    /**
     * This method computes the total rent for this utility
     * @param amount the number of utilities owned
     * @param diceNum the dice number to reach this utility
     * @return the total rent
     */
    public int getTotalRent(int amount, int diceNum) {
        int totalRent = 0;

        switch (amount) {
            case 1:
                totalRent = 4 * diceNum;
                break;
            case 2:
                totalRent = 10 * diceNum;
                break;
        }
        return totalRent;
    }
}
