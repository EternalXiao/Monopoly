package MonopolyClient.game;
/**
 * This class is a subclass of Property which defines the railroad block
 * It is does not have additional field variable.
 */
public class Railroad extends Property {
	/**
	 * Constructor for Property
	 * @param position the position of the block
	 * @param name the name of the block
	 * @param type the type of the block
	 * @param price the price of the block
	 */
    public Railroad(int position, String name, BlockType type, int price) {
        super(position, name, type, price);

    }
    /**
     * This method computes the total rent for this railroad
     * @param amount the number of railroads owned
     * @return the total rent
     */
    public int getTotalRent(int amount) {
        int totalRent = 0;

        switch (amount) {
            case 1:
                totalRent = 25;
                break;
            case 2:
                totalRent = 50;
                break;
            case 3:
                totalRent = 100;
                break;
            case 4:
                totalRent = 200;
                break;
        }
        return totalRent;
    }
}
