package MonopolyServer.game;

public class Chance extends Block{

	public Chance(int position, String name, BlockType type) {
		super(position, name, type);
	}
	public int getChance() {
		return 200;
	}
}
