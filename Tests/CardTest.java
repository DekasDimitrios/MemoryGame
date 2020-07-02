import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class CardTest {
    private ImageIcon kappa = new ImageIcon("images/cardBack.jpg");
    private Card A = new Card (kappa,1);

    @Test
    public void isFaceDown() {
        assertTrue(A.isFaceDown());
        A.turnUp();
        assertFalse(A.isFaceDown());
    }

    @Test
    public void isSelected() {
        assertFalse(A.isSelected());
        A.setSelected(true);
        assertTrue(A.isSelected());
    }
    
    @Test
    public void turnUp() {
        A.turnUp();
        assertTrue(A.isFaceUp());
    }

    @Test
    public void turnDown() {
        A.turnUp();
        A.turnDown();
        assertTrue(A.isFaceDown());
    }

    @Test
    public void setSelected() {
        A.setSelected(true);
        assertTrue(A.isSelected());
    }

    @Test
    public void sameIcon() {
        ImageIcon keepo = new ImageIcon("images/card16.jpg");
        Card B = new Card (keepo,1);
        assertTrue(A.sameIcon(B));
    }

    @Test
    public void setClickable() {
        A.setClickable(false);
        assertFalse(A.isClickable());
    }

    @Test
    public void isClickable() {
        assertTrue(A.isClickable());
        A.setClickable(false);
        assertFalse(A.isClickable());
    }

    @Test
    public void isFaceUp(){
        assertFalse(A.isFaceUp());
        A.turnUp();
        assertTrue(A.isFaceUp());
    }
}