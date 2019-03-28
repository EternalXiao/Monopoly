package MonopolyClient.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is to define the chance card of the chance block.
 * It defines two field variable name and step.
 */
class CardChance {
    private String name;
    private int step;
    /**
     * Constructor for CardChance
     * @param name the name of the chance card
     * @param step the move relative to current block
     */
    public CardChance(String name, int step) {
        this.name = name;
        this.step = step;
    }

    public String getName() {
        return this.name;
    }

    public int getStep() {
        return this.step;
    }
}
/**
 * This class is a subclass of Block which defines the chance block on board It
 * is characterised by two additional field variablesl, card and rand
 */
public class Chance extends Block {
    private ArrayList<CardChance> card;
    private Random rand;
	/**
	 * Constructor for Chance
	 * 
	 * @param position the position of this block
	 * @param name     the name of the block
	 * @param type     the type of the block
	 */
    public Chance(int position, String name, BlockType type) {
        super(position, name, type);
        this.card = new ArrayList<>();
        this.rand = new Random();

        card.add(new CardChance("forward3", 3));
        card.add(new CardChance("goback3",-3));
        card.add(new CardChance("gojail", 23));
        card.add(new CardChance("goback2", -2));
        card.add(new CardChance("forward2", 2));
    }
	/**
	 * This method is to randomly get chance in card pool
	 * @return a random chance
	 */
    public CardChance getCard() {
        return card.get(rand.nextInt(5));
    }
}
