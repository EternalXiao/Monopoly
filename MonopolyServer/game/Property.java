package MonopolyServer.game;

public class Property extends Block {
    private boolean isOwned;    // if it has owner
    private Player owner;
    private int price;

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
