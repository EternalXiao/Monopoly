package MonopolyServer.game;

import java.util.Random;
/**
 * This class is to define the Dice which can generate random number
 * from 1 to 6
 */
public class Dice {
	/**
     * This method is to generate dice number between 1 to 6
     * @return the dice number
     */
    public static int getDiceNum() {
        return new Random().nextInt(6) + 1;
    }
}
