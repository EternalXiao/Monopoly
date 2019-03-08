package MonopolyServer.game;

public class Railroad extends Property {
    public Railroad(int position, String name, BlockType type, int price) {
        super(position, name, type, price);

    }

    public int getTotalRent(int amount) {
        int totalRent = 0;

        switch (amount) {
            case 1:
                totalRent = 25;
                break;
            case 2:
                totalRent = 50;
                break;
            case 3:
                totalRent = 100;
                break;
            case 4:
                totalRent = 200;
                break;
        }
        return totalRent;
    }
}
