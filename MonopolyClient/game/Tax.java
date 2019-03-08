package MonopolyClient.game;

public class Tax extends Block{
	private int tax;
	public Tax(int position, String name, BlockType type,int tax) {
		super(position, name, type);
		this.tax=tax;
	}
	public int getTax() {
		return tax;
	}
	public void setTax(int tax) {
		this.tax = tax;
	}
}
