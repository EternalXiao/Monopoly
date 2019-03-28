package MonopolyServer.game;
/**
 * This class is a subclass of Block which defines the property block
 * It is characterised by three additional field variables, isOwned, owner and price
 */
public class Property extends Block {
    private boolean isOwned;    
    private Player owner;
    private int price;
    /**
     * Constructor for Property
     * @param position the position of the block
     * @param name the name of the block
     * @param type the tyep of the block
     * @param price the price of the block
     */
    public Property(int position, String name, BlockType type, int price) {
        super(position, name, type);
        this.price = price;
        this.isOwned = false;
        this.owner = null;
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
    /**
     * Overriden method to determine whether two properties are the same
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Property)) {
            return false;
        }
        Property pro = (Property) obj;
        return super.getName().equals(pro.getName()) && super.getType().equals(pro.getType());
    }
}
