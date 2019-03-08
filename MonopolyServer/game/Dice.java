package MonopolyServer.game;

import java.util.Random;

public class Dice {
    public static int getDiceNum() {
        return new Random().nextInt(6) + 1;
    }
}
