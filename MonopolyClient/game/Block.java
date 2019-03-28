package MonopolyClient.game;

/**
 * This class is the prototype for the block on game board.
 * It defines the name, position and type of the block
 */
public class Block {
    private String name;
    private int position;
    private BlockType type;
    /**
     * Consturctor for Block
     * @param position the block position on the board
     * @param name the name of the block
     * @param type the type of the block
     */
    public Block(int position, String name, BlockType type) {
        this.position = position;
        this.name = name;
        this.type = type;
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
    /**
     * Overriden method to define whether two block object are
     * the same
     */
    @Override
    public boolean equals(Object object) {
    	Block block = (Block)object;
    	if(this.getName().equals(block.getName()))
    		return true;
    	else
    		return false;
    }
}
