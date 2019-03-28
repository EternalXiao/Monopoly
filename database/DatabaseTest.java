package database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DatabaseTest {

private  Database d = Database.getInstance();

    @org.junit.Before
    public void setUp() throws Exception {
       // d.signUp("paul2","123qwe","Hereispaul2");
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void login() throws Exception {
        d.signUp("paul5","123qwe","Hereispaul5");
        assertEquals(d.login("paul5","123qwe"),true);
        d.signUp("paul6","123qwe","Hereispaul6");
        assertEquals(d.login("paul6","123qwe"),true);
        d.signUp("paul7","123qwe","Hereispaul7");
        assertEquals(d.login("paul7","123qwe"),true);
        d.signUp("paul8","123qwe","Hereispaul8");
        assertEquals(d.login("paul8","123qwe"),true);

        assertEquals(d.login("paul800","123qwe"),false);
        assertEquals(d.login("paul8","123qwe567"),false);
        assertEquals(d.login("paul800","123qwe567"),false);



    }

    @Test(expected=Exception.class)
    public void signuptest1a() throws Exception {
        d.signUp("paul5","123qwe","Hereispaul5");
        d.signUp("paul6","123qwe","Hereispaul6");
        d.signUp("paul7","123qwe","Hereispaul7");
        d.signUp("paul8","123qwe","Hereispaul8");

    }

    @org.junit.Test
    public void signUp() {
        assertEquals(d.login("paul5","123qwe"),true);
        assertEquals(d.login("paul6","123qwe"),true);
        assertEquals(d.login("paul7","123qwe"),true);
        assertEquals(d.login("paul8","123qwe"),true);

    }



    @org.junit.Test
    public void checkUsernameAvailable() {
        assertEquals(d.checkNicknameAvailable("paul5"),false);
        assertEquals(d.checkNicknameAvailable("paul6"),false);
        assertEquals(d.checkNicknameAvailable("paul7"),false);
        assertEquals(d.checkNicknameAvailable("paul100"),true);
        assertEquals(d.checkNicknameAvailable("paul1000"),true);
        assertEquals(d.checkNicknameAvailable("paul10000"),true);
    }

    @org.junit.Test
    public void checkNicknameAvailable() {
        assertEquals(d.checkNicknameAvailable("Hereispaul5"),false);
        assertEquals(d.checkNicknameAvailable("Hereispaul6"),false);
        assertEquals(d.checkNicknameAvailable("Hereispaul7"),false);
        assertEquals(d.checkNicknameAvailable("Hereispaul100"),true);
        assertEquals(d.checkNicknameAvailable("Hereispaul1000"),true);
        assertEquals(d.checkNicknameAvailable("Hereispaul10000"),true);
    }

    @org.junit.Test
    public void getUid() throws Exception {
        int a=d.getUid("aaa");
        assertEquals(a,13);
    }


}