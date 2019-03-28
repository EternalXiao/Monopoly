package MonopolyClient.game;
/**
 * This class is a subclass of Block, which defines tax block
 * It is characterised by an additional field variable tax
 */
public class Tax extends Block {
    private int tax;
    /**
     * Constructor for Tax
     * @param position the position of the block
     * @param name the name of the block
     * @param type the type of the block
     * @param tax the tax of the block
     */
    public Tax(int position, String name, BlockType type, int tax) {
        super(position, name, type);
        this.tax = tax;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }
}
