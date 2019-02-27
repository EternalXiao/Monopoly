package MonopolyServer.game;

public class Block {
	private String name;
	private int position;
	private BlockType type;
	public Block(int position,String name,BlockType type) {
		this.position=position;
		this.name=name;
		this.type=type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public BlockType getType() {
		return type;
	}
	public void setType(BlockType type) {
		this.type = type;
	}
}
