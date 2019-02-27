package MonopolyServer.game;

public class Dice {
	public static int rollDice() {
		return (int)(Math.random()*5)+1;
	}
}
