import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The class responsible for the creation of the Computer Players
 */
public class Computer extends Player {

    public static final int GOLDFISH = 1;
    public static final int KANGAROO = 2;
    public static final int ELEPHANT = 3;
    private static int comNum; // Number of active Computer User's. Starts at zero
    private static boolean kangarooMemoryState; // Starts with false as a value
    private int memoryLevel;
    private ArrayList<Card> memory;

    /**
     * Initializes the computer player
     *
     * @param memoryLevel an int that represents the memory level of the computer player.
     * @param bundle      The language bundle used
     */
    public Computer(int memoryLevel, ResourceBundle bundle) {
        super(bundle.getString("ComputerUsername") + (++comNum));
        this.memoryLevel = memoryLevel;
        this.memory = new ArrayList<>();
    }

    /**
     * Returns true if the Kangaroo AI will remember the next card
     *
     * @return Boolean value that determines whether or not the AI will remember the next card that is revealed
     */
    public static boolean kangarooRemembers() {
        return kangarooMemoryState;
    }

    /**
     * Changes the state of the kangaroo memory which determines whether kangaroo bots will remember the next card opened or not.
     */
    public static void changeKangaroosMemoryState() {
        kangarooMemoryState = !kangarooMemoryState;
    }

    /**
     * Returns the memory level of the computer AI
     *
     * @return integer value that determines the level of the AI's memory (1 = Goldfish, 2 = Kangaroo, 3 = Elephant)
     */
    public int getMemoryLevel() {
        return memoryLevel;
    }

    /**
     * Return the memory of a computer player
     *
     * @return an ArrayList that contains all the memorized cards.
     */
    public ArrayList<Card> getMemory() {
        return memory;
    }

    /**
     * Memorizes a card (if it's not already memorized) adding it to the array memory.
     *
     * @param c the card that the AI will attempt to memorize
     */
    public void memorizeCard(Card c) {
        if (c.isMemorized()) {
            return;
        }
        memory.add(c);
        c.setMemorized(true);
    }

    /**
     * Checks if the computer finds any matched in its memory.
     *
     * @param cardsToPick the number of cards that are picked each turn
     * @return Array of cards that contains the cards that matched
     */
    public Card[] getMemoryMatches(int cardsToPick) {
        Card[] memoryMatches = new Card[cardsToPick];
        int cardPicked;
        for (int j = 0; j < memory.size(); j++) {
            cardPicked = 1;
            if (memory.get(j).isFaceDown()) {
                memoryMatches[0] = memory.get(j);
            }
            for (int k = j + 1; k < memory.size() && memoryMatches[0] != null; k++) {
                if (memory.get(j).sameIcon(memory.get(k)) && memory.get(k).isFaceDown()) {
                    memoryMatches[cardPicked] = memory.get(k);
                    cardPicked++;
                    if (cardPicked == cardsToPick) {
                        return memoryMatches;
                    }
                }
            }
        }
        return null;
    }
}
