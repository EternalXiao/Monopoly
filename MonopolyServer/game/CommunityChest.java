package MonopolyServer.game;

import java.util.Random;
/**
 * This class is a subclass of Block which defines the community chest block.
 * It is characterised by two addditional field variables, price and data
 */
public class CommunityChest extends Block{
    private int price;
    private int[] data;
    /**
     * Constructor for CommunityChest
     * @param position the position of the block
     * @param name the name of the block
     * @param type the type of the block
     */
	public CommunityChest(int position, String name, BlockType type) {
		super(position, name, type);
	    data = new int[] {50, 100, 200, 250, 500};
	}
	/**
	 * Get the random money from [50,100,200,250,500] for player
	 * @return the random money
	 */
	public int getPrice() {
		Random rand = new Random();
	    this.price = data[rand.nextInt(data.length)];
	    return this.price;
    }
}
