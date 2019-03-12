package MonopolyServer.game;

import java.util.ArrayList;
import java.util.Random;

import MonopolyServer.MainServer;

class CardChance {
    private String name;
    private int step;

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

public class Chance extends Block {
    private ArrayList<CardChance> card;
    private Random rand;

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

    public CardChance getCard() {
        return card.get(rand.nextInt(5));
    }

    public void getAction(Player player,MainServer server) {
        switch (this.getCard().getName()) {
            case "forward3":
                player.setCurrentPosition(player.getCurrentPosition() + 3);
                server.sendSystemNormalMessage(player.getName(), "got the chance: Forward 3 blocks");
                break;
            case "goback3":
                player.setCurrentPosition(player.getCurrentPosition() - 3);
                server.sendSystemNormalMessage(player.getName(), "got the chance: Backward 3 blocks");
                break;
            case "gojail":
                player.setCurrentPosition(30);
                server.sendSystemNormalMessage(player.getName(), "got the chance: Go to jail");
                break;
            case "goback2":
                player.setCurrentPosition(player.getCurrentPosition() - 2);
                server.sendSystemNormalMessage(player.getName(), "got the chance: Backward 2 blocks");
                break;
            case "forward2":
                player.setCurrentPosition(player.getCurrentPosition() + 2);
                server.sendSystemNormalMessage(player.getName(), "got the chance: Forward 2 blocks");
                break;
        }
    }
}
