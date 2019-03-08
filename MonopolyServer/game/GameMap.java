package MonopolyServer.game;

public class GameMap {
    private Block[] map;

    public GameMap() {
        map = new Block[40];
        initMap();
    }

    public Block[] getMap() {
        return this.map;
    }

    private void initMap() {
        map[0] = new Block(0, "Go", BlockType.Go);
        map[1] = new Street(1, "Old Kent Road", BlockType.Street, Color.Brown, 60);
        map[2] = new CommunityChest(2, "CommunityChest", BlockType.CommunityChest);
        map[3] = new Street(3, "Whitechapel Road", BlockType.Street, Color.Brown, 60);
        map[4] = new Tax(4, "Income Tax", BlockType.Tax, 200);
        map[5] = new Railroad(5, "King's Cross Station", BlockType.Railroad, 200);
        map[6] = new Street(6, "The Angel Islington", BlockType.Street, Color.LightBlue, 100);
        map[7] = new Chance(7, "Chance", BlockType.Chance);
        map[8] = new Street(8, "Euston Road", BlockType.Street, Color.LightBlue, 100);
        map[9] = new Street(9, "Pentonville Road", BlockType.Street, Color.LightBlue, 120);
        map[10] = new Block(10, "Jail", BlockType.Jail);
        map[11] = new Street(11, "Pall Mall", BlockType.Street, Color.Pink,140);
        map[12] = new Utility(12, "Electric Company", BlockType.Utility, 150);
        map[13] = new Street(13, "Whitehall", BlockType.Street, Color.Pink, 140);
        map[14] = new Street(14, "Northumberland Avenue", BlockType.Street, Color.Pink, 160);
        map[15] = new Railroad(15, "Marylebone Station", BlockType.Railroad, 200);
        map[16] = new Street(16, "Bow Street", BlockType.Street, Color.Orange,180);
        map[17] = new CommunityChest(17, "CommunityChest", BlockType.CommunityChest);
        map[18] = new Street(18, "Marlborough Street", BlockType.Street, Color.Orange, 180);
        map[19] = new Street(19, "Vine Street", BlockType.Street, Color.Orange, 200);
        map[20] = new Block(20, "Free Parking", BlockType.Parking);
        map[21] = new Street(21, "The Strand", BlockType.Street, Color.Red, 220);
        map[22] = new Chance(22, "Chance", BlockType.Chance);
        map[23] = new Street(23, "Fleet Street", BlockType.Street, Color.Red, 220);
        map[24] = new Street(24, "Trafalgar Square", BlockType.Street, Color.Red, 240);
        map[25] = new Railroad(25, "Fenchurch st Station", BlockType.Railroad, 200);
        map[26] = new Street(26, "Leicester Square", BlockType.Street, Color.Yellow, 260);
        map[27] = new Street(27, "Coventry Street", BlockType.Street, Color.Yellow, 260);
        map[28] = new Utility(28, "Water Works", BlockType.Utility, 150);
        map[29] = new Street(29, "Piccadilly", BlockType.Street, Color.Yellow, 280);
        map[30] = new Block(30, "Go To Jail", BlockType.GoToJail);
        map[31] = new Street(31, "Regent Street", BlockType.Street, Color.Green, 300);
        map[32] = new Street(32, "Oxford Street", BlockType.Street, Color.Green, 300);
        map[33] = new CommunityChest(33, "CommunityChest", BlockType.CommunityChest);
        map[34] = new Street(34, "Bond Street", BlockType.Street, Color.Green, 320);
        map[35] = new Railroad(35, "Liverpool Street Station", BlockType.Railroad, 200);
        map[36] = new Chance(36, "Chance", BlockType.Chance);
        map[37] = new Street(37, "Park Lane", BlockType.Street, Color.DarkBlue,  350);
        map[38] = new Tax(38, "Super Tax", BlockType.Tax, 100);
        map[39] = new Street(39, "Mayfair", BlockType.Street, Color.DarkBlue, 400);
    }
}
