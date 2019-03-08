package MonopolyServer.game;

import java.util.Random;

public class CommunityChest extends Block{
    private int price;
    private int[] data;

	public CommunityChest(int position, String name, BlockType type) {
		super(position, name, type);
	    data = new int[] {50, 100, 200, 250, 500};
	}

	public int getPrice() {
		Random rand = new Random();
	    this.price = data[rand.nextInt(data.length)];
	    return this.price;
    }
}
