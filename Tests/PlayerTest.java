import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player Gkappas = new Player("gkappas");

    @Test
    public void TestConstructor(){
        assertEquals(0,Gkappas.getSteps());
        assertEquals(0,Gkappas.getCollectedCards());
        assertEquals("gkappas",Gkappas.getUserName());
    }

    @Test
    public void getUserName() {
        assertEquals("gkappas",Gkappas.getUserName());
    }

    @Test
    public void setSteps() {
        Gkappas.setSteps(5);
        assertEquals(5,Gkappas.getSteps());
    }

    @Test
    public void getSteps() {
        assertEquals(0,Gkappas.getSteps());
    }

    @Test
    public void setCollectedCards() {
        Gkappas.setCollectedCards(10);
        assertEquals(10,Gkappas.getCollectedCards());
    }

    @Test
    public void getCollectedCards() {
        assertEquals(0,Gkappas.getCollectedCards());
    }

}