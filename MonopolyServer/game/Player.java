package MonopolyServer.game;

import java.util.LinkedList;

public class Player {
    LinkedList<Property> ownedProperties;
    private int currentPosition;
    private int money;
    private int inGameId;
    private String name;
    private boolean isInJail;
    private boolean isAlive;
    private boolean isReady;

    public Player() {
        this.currentPosition = 0;
        this.money = 1500;
        this.isInJail = false;
        this.isAlive = true;
        this.isReady = false;
        this.ownedProperties = new LinkedList<>();
    }

    public Player(int inGameId,String name) {
        this();
        this.inGameId = inGameId;
        this.name=name;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean isInJail) {
        this.isInJail = isInJail;
    }

    public LinkedList<Property> getOwnedProperties() {
        return ownedProperties;
    }

    public void setOwnedProperties(LinkedList<Property> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }

    public boolean isReady() {
        return this.isReady;
    }

    public int getInGameId() {
        return inGameId;
    }

    public void setInGameId(int inGameId) {
        this.inGameId = inGameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void receiveMoney(int amount) {
        this.money += amount;
    }

    public void payMoney(int amount) {
        this.money -= amount;
    }

    public void pay(Player player, int amount) {
        this.payMoney(amount);
        player.receiveMoney(amount);
        System.out.println("You paid player " + player.getInGameId() + " with " + amount);
        System.out.println("You have " + this.money + " remaining");
    }

    public boolean buy(Property property) {
        if (this.money < property.getPrice()) {
            System.out.println("Not enough money");
            return false;
        } else {
            property.setOwned(true);
            property.setOwner(this);
            this.ownedProperties.add(property);
            this.payMoney(property.getPrice());
            System.out.println("You have " + this.money + " remaining");
            return true;
        }
    }

    public boolean sell(Property property) {
        return true;
    }

    public int getOwnedRailroads() {
        int sum = 0;
        for (Property property : this.ownedProperties) {
            if (property.getType() == BlockType.Railroad)
                sum++;
        }
        return sum;
    }

    public int getOwnedSameColorStreet(Color color) {
        int sum = 0;
        for (Property property : this.ownedProperties) {
            if (property.getType() == BlockType.Street) {
                Street street = (Street) property;
                if (color.equals(street.getColor())) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public int getOwnedUtilities() {
        int sum = 0;
        for (Property property : this.ownedProperties) {
            if (property.getType() == BlockType.Utility)
                sum++;
        }
        return sum;
    }

    public int getMinHouseOnSameColor(Street street) {
        int min = street.getHouseNum();
        for (Property property : this.ownedProperties) {
            if (property.getType() == BlockType.Street) {
                Street otherStreet = (Street) property;
                if (street.getColor().equals(otherStreet.getColor())) {
                    if (otherStreet.getHouseNum() < street.getHouseNum()) {
                        min = otherStreet.getHouseNum();
                    }
                }
            }
        }
        return min;
    }

    public void buildHouse(Street street) {
        if (this.ownedProperties.contains(street)) {
            if (street.getSameColorStreetNum() == getOwnedSameColorStreet(street.getColor()) && street.getHouseNum() - getMinHouseOnSameColor(street) < 1) {
                if (this.money >= street.getHouseCost() && street.getHouseNum() <= 5) {
                    street.setHouseNum(street.getHouseNum() + 1);
                    this.setMoney(this.getMoney() - street.getHouseCost());
                } else {
                    System.out.println("Sorry, you don't have enough money.");
                }
            }
        }
    }
}
