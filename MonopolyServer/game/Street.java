package MonopolyServer.game;

enum Color {
    Brown,
    LightBlue,
    Pink,
    Orange,
    Red,
    Yellow,
    Green,
    DarkBlue;
}

public class Street extends Property {
    private int houseNum;
    private Color color;

    public Street(int position, String name, BlockType type, Color color, int price) {
        super(position, name, type, price);
        this.houseNum = 0;
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public int getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(int houseNum) {
        this.houseNum = houseNum;
    }
    public void upgradeHouse() {
    	this.houseNum++;
    }
    public void degradeHouse() {
    	this.houseNum--;
    }

    public int getSameColorStreetNum() {
        int num = 0;

        switch (this.color) {
            case Brown:
                num = 2;
                break;
            case DarkBlue:
                num = 2;
                break;
            default:
                num = 3;
                break;
        }
        return num;
    }

    public int getHouseCost() {
        int cost = 0;

        switch (this.getName()) {
            case "Old kent Road": {
                cost = 60;
                break;
            }
            case "Whitechapel Road": {
                cost = 60;
                break;
            }
            case "The angel Islington": {
                cost = 100;
                break;
            }
            case "Euston Road": {
                cost = 100;
                break;
            }
            case "Pentonville Road": {
                cost = 120;
                break;
            }
            case "Pall Mall": {
                cost = 140;
                break;
            }
            case "Whitehall": {
                cost = 140;
                break;
            }
            case "Northumberland Avenue": {
                cost = 160;
                break;
            }
            case "Bow Street": {
                cost = 180;
                break;
            }
            case "Marlborough Street": {
                cost = 180;
                break;
            }
            case "Vine Street": {
                cost = 200;
                break;
            }
            case "The Strand": {
                cost = 220;
                break;
            }
            case "Fleet Street": {
                cost = 220;
                break;
            }
            case "Trafalgar Square": {
                cost = 240;
                break;
            }
            case "Leicester Square": {
                cost = 260;
                break;
            }
            case "Coventry Street": {
                cost = 260;
                break;
            }
            case "Piccadilly": {
                cost = 280;
                break;
            }
            case "Regent Street": {
                cost = 300;
                break;
            }
            case "Oxford Street": {
                cost = 300;
                break;
            }
            case "Bond Street": {
                cost = 320;
                break;
            }
            case "Park Lane": {
                cost = 350;
                break;
            }
            case "Mayfair": {
                cost = 400;
                break;
            }
        }
        return cost;
    }

    public int getStreetRent() {
        int totalRent = 0;

        switch (this.getName()) {
            case "Old Kent Road": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 2;
                        break;
                    case 1:
                        totalRent = 10;
                        break;
                    case 2:
                        totalRent = 30;
                        break;
                    case 3:
                        totalRent = 90;
                        break;
                    case 4:
                        totalRent = 160;
                        break;
                    case 5:
                        totalRent = 250;
                        break;
                }
                break;
            }
            case "Whitechapel Road": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 4;
                        break;
                    case 1:
                        totalRent = 20;
                        break;
                    case 2:
                        totalRent = 60;
                        break;
                    case 3:
                        totalRent = 180;
                        break;
                    case 4:
                        totalRent = 320;
                        break;
                    case 5:
                        totalRent = 450;
                        break;
                }
                break;
            }
            case "The Angel Islington": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 6;
                        break;
                    case 1:
                        totalRent = 30;
                        break;
                    case 2:
                        totalRent = 90;
                        break;
                    case 3:
                        totalRent = 270;
                        break;
                    case 4:
                        totalRent = 400;
                        break;
                    case 5:
                        totalRent = 550;
                        break;
                }
                break;
            }
            case "Euston Road": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 6;
                        break;
                    case 1:
                        totalRent = 30;
                        break;
                    case 2:
                        totalRent = 90;
                        break;
                    case 3:
                        totalRent = 270;
                        break;
                    case 4:
                        totalRent = 400;
                        break;
                    case 5:
                        totalRent = 550;
                        break;
                }
                break;
            }
            case "Pentonville Road": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 8;
                        break;
                    case 1:
                        totalRent = 40;
                        break;
                    case 2:
                        totalRent = 100;
                        break;
                    case 3:
                        totalRent = 300;
                        break;
                    case 4:
                        totalRent = 450;
                        break;
                    case 5:
                        totalRent = 6000;
                        break;
                }
                break;
            }
            case "Pall Mall": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 10;
                        break;
                    case 1:
                        totalRent = 50;
                        break;
                    case 2:
                        totalRent = 150;
                        break;
                    case 3:
                        totalRent = 450;
                        break;
                    case 4:
                        totalRent = 625;
                        break;
                    case 5:
                        totalRent = 750;
                        break;
                }
                break;
            }
            case "Whitehall": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 10;
                        break;
                    case 1:
                        totalRent = 50;
                        break;
                    case 2:
                        totalRent = 150;
                        break;
                    case 3:
                        totalRent = 450;
                        break;
                    case 4:
                        totalRent = 625;
                        break;
                    case 5:
                        totalRent = 750;
                        break;
                }
                break;
            }
            case "Northumberland Avenue": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 12;
                        break;
                    case 1:
                        totalRent = 60;
                        break;
                    case 2:
                        totalRent = 180;
                        break;
                    case 3:
                        totalRent = 500;
                        break;
                    case 4:
                        totalRent = 700;
                        break;
                    case 5:
                        totalRent = 900;
                        break;
                }
                break;
            }
            case "Bow Street": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 14;
                        break;
                    case 1:
                        totalRent = 70;
                        break;
                    case 2:
                        totalRent = 200;
                        break;
                    case 3:
                        totalRent = 550;
                        break;
                    case 4:
                        totalRent = 750;
                        break;
                    case 5:
                        totalRent = 950;
                        break;
                }
                break;
            }
            case "Marlborough Street": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 14;
                        break;
                    case 1:
                        totalRent = 70;
                        break;
                    case 2:
                        totalRent = 200;
                        break;
                    case 3:
                        totalRent = 550;
                        break;
                    case 4:
                        totalRent = 750;
                        break;
                    case 5:
                        totalRent = 950;
                        break;
                }
                break;
            }
            case "Vine Street": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 16;
                        break;
                    case 1:
                        totalRent = 80;
                        break;
                    case 2:
                        totalRent = 220;
                        break;
                    case 3:
                        totalRent = 600;
                        break;
                    case 4:
                        totalRent = 800;
                        break;
                    case 5:
                        totalRent = 1000;
                        break;
                }
                break;
            }
            case "The Strand": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 18;
                        break;
                    case 1:
                        totalRent = 90;
                        break;
                    case 2:
                        totalRent = 250;
                        break;
                    case 3:
                        totalRent = 700;
                        break;
                    case 4:
                        totalRent = 875;
                        break;
                    case 5:
                        totalRent = 1050;
                        break;
                }
                break;
            }
            case "Fleet Street": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 18;
                        break;
                    case 1:
                        totalRent = 90;
                        break;
                    case 2:
                        totalRent = 250;
                        break;
                    case 3:
                        totalRent = 700;
                        break;
                    case 4:
                        totalRent = 875;
                        break;
                    case 5:
                        totalRent = 1050;
                        break;
                }
                break;
            }
            case "Trafalgar Square": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 20;
                        break;
                    case 1:
                        totalRent = 100;
                        break;
                    case 2:
                        totalRent = 300;
                        break;
                    case 3:
                        totalRent = 750;
                        break;
                    case 4:
                        totalRent = 925;
                        break;
                    case 5:
                        totalRent = 1100;
                        break;
                }
                break;
            }
            case "Leicester Square": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 22;
                        break;
                    case 1:
                        totalRent = 110;
                        break;
                    case 2:
                        totalRent = 330;
                        break;
                    case 3:
                        totalRent = 800;
                        break;
                    case 4:
                        totalRent = 975;
                        break;
                    case 5:
                        totalRent = 1150;
                        break;
                }
                break;
            }
            case "Coventry Street": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 22;
                        break;
                    case 1:
                        totalRent = 110;
                        break;
                    case 2:
                        totalRent = 330;
                        break;
                    case 3:
                        totalRent = 800;
                        break;
                    case 4:
                        totalRent = 975;
                        break;
                    case 5:
                        totalRent = 1150;
                        break;
                }
                break;
            }
            case "Piccadilly": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 22;
                        break;
                    case 1:
                        totalRent = 120;
                        break;
                    case 2:
                        totalRent = 360;
                        break;
                    case 3:
                        totalRent = 850;
                        break;
                    case 4:
                        totalRent = 1025;
                        break;
                    case 5:
                        totalRent = 1200;
                        break;
                }
                break;
            }
            case "Regent Street": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 26;
                        break;
                    case 1:
                        totalRent = 130;
                        break;
                    case 2:
                        totalRent = 390;
                        break;
                    case 3:
                        totalRent = 900;
                        break;
                    case 4:
                        totalRent = 1100;
                        break;
                    case 5:
                        totalRent = 1275;
                        break;
                }
                break;
            }
            case "Oxford Street": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 26;
                        break;
                    case 1:
                        totalRent = 130;
                        break;
                    case 2:
                        totalRent = 390;
                        break;
                    case 3:
                        totalRent = 900;
                        break;
                    case 4:
                        totalRent = 1100;
                        break;
                    case 5:
                        totalRent = 1275;
                        break;
                }
                break;
            }
            case "Bond Street": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 28;
                        break;
                    case 1:
                        totalRent = 150;
                        break;
                    case 2:
                        totalRent = 450;
                        break;
                    case 3:
                        totalRent = 1000;
                        break;
                    case 4:
                        totalRent = 1200;
                        break;
                    case 5:
                        totalRent = 1400;
                        break;
                }
                break;
            }
            case "Park Lane": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 35;
                        break;
                    case 1:
                        totalRent = 175;
                        break;
                    case 2:
                        totalRent = 500;
                        break;
                    case 3:
                        totalRent = 1100;
                        break;
                    case 4:
                        totalRent = 1300;
                        break;
                    case 5:
                        totalRent = 1500;
                        break;
                }
                break;
            }
            case "Mayfair": {
                switch (this.houseNum) {
                    case 0:
                        totalRent = 50;
                        break;
                    case 1:
                        totalRent = 200;
                        break;
                    case 2:
                        totalRent = 600;
                        break;
                    case 3:
                        totalRent = 1400;
                        break;
                    case 4:
                        totalRent = 1700;
                        break;
                    case 5:
                        totalRent = 2000;
                        break;
                }
                break;
            }
        }
        return totalRent;
    }
}