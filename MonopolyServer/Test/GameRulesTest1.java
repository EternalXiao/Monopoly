package MonopolyServer.Test;
import MonopolyServer.MainServer;
import MonopolyServer.game.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * The purpose of these tests are to test the game rules
 */

public class GameRulesTest1 {
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    private CommunityChest communitychest1;

    @Before
    public void setUpBefore() throws Exception {
        player1 = new Player();
        player2 = new Player();
        player3 = new Player();
        player4 = new Player();
    }

    //test initial state
    @Test
    public void test1() {
        int expected = 0;
        LinkedList E = new LinkedList();
        int actual1 = player1.getOwnedRailroads();
        int actual2 = player1.getOwnedUtilities();
        LinkedList actual3 = player1.getOwnedProperties();
        assertEquals(expected, actual1);
        assertEquals(expected, actual2);
        assertEquals(E, actual3);
        System.out.print(player1.getOwnedProperties());
    }

    //test initial balance
    @Test
    public void test2() {
        int expectedBalance = 1500;
        int actualBalance = player1.getMoney();
        assertEquals(expectedBalance, actualBalance);
    }

    //test throwing dice
    @Test
    public void test3(){
        int dice1 = Dice.getDiceNum();
        int dice2 = Dice.getDiceNum();
        List <Integer> a3 = Arrays.asList(1,2,3,4,5,6);
        assertTrue(a3.contains(dice1));
        assertTrue(a3.contains(dice2));

    }

    //test community chest
    @Test
    public void test4(){
        communitychest1 = new CommunityChest(0, "name", BlockType.CommunityChest);
        int money1 = communitychest1.getPrice();
        player1.receiveMoney(money1);
        int money2 = player1.getMoney();
        List <Integer> a4 = Arrays.asList(1550, 1600, 1700, 1750, 2000);
        assertTrue(a4.contains(money2));
    }

    //test final balance and winner; not yet finished in the main program
    @Test
    public void test5(){
        player1.setMoney(1500);
        player2.setMoney(2000);
        player3.setMoney(2000);
        player4.setMoney(0);
    }

}
