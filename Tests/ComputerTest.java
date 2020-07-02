import org.junit.Test;

import javax.swing.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class ComputerTest {
    private ResourceBundle K = ResourceBundle.getBundle("MessagesBundle",new Locale("gr","Gr"));
    private Computer A = new Computer(1,K);
    private Computer B = new Computer(3,K);
    private Computer C = new Computer(2,K);
    private ImageIcon kappa = new ImageIcon("images/card11.jpg");
    private ImageIcon keepo = new ImageIcon("images/card2.jpg");
    private Card D = new Card(kappa,3);
    private Card E = new Card(kappa,3);
    private Card F = new Card(keepo,11);
    private Card G = new Card (kappa,3);

    @Test
    public void kangarooRemembers() {
        assertFalse(Computer.kangarooRemembers());
    }

    @Test
    public void changeKangaroosMemoryState() {
        Computer.changeKangaroosMemoryState();
        assertTrue(Computer.kangarooRemembers());
        Computer.changeKangaroosMemoryState();
    }

    @Test
    public void getMemoryLevel() {
        assertEquals(1,A.getMemoryLevel());
    }

    @Test
    public void memorizeCard() {
        A.memorizeCard(D);
        assertTrue(D.isMemorized());
    }

    @Test
    public void getMemoryMatches() {
        A.memorizeCard(D);
        A.memorizeCard(E);
        assertEquals(2,A.getMemoryMatches(2).length);
        assertTrue(A.getMemoryMatches(2)[0].sameIcon(D));
        A.memorizeCard(G);
        assertEquals(3,A.getMemoryMatches(3).length);
        assertTrue(A.getMemoryMatches(3)[2].sameIcon(G));
        B.memorizeCard(D);
        B.memorizeCard(F);
        B.memorizeCard(G);
        assertNull(B.getMemoryMatches(2));
        C.memorizeCard(E);
        C.memorizeCard(D);
        C.memorizeCard(F);
        assertNull(C.getMemoryMatches(3));
    }
}