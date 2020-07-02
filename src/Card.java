/** It's the class responsible for the creation and the manipulation of the cards.

 *  @author Dekas Dimitrios
 *  @version 2
 */
public class Card {

    private char ID; // The identifier of each card.
    private int state; // The current state of the card in this manner:
                       //     1. Collected Card.
                       //     2. Face-up Card.
                       //     3. Face-down Card.

    /**
     * Sets every card face-down(state = 3) and the ID of each card based on the parameter.
     * @param ID a char value.
     */
    public Card(char ID) {
        this.ID = ID;
        state = 3;
    }

    /**
     * Returns true only if the card's state is equal to 1 so the card is treated as Collected.
     * @return a boolean value that shows us if the card is already collected or not.
     */
    public boolean isCollected() { return state == 1; }

    /**
     * Returns true only if the card's state is equal to 2 so the card is treated as Face Up.
     * @return a boolean value that shows us if the card is Face Up or not.
     */
    public boolean isFaceUp() { return state == 2; }

    /**
     * Sets the state equal to 1 so the card is treated as collected.
     */
    public void collectCard() { state = 1; }

    /**
     * Sets the state equal to 2 so the card is treated as face-up.
     */
    public void revealCard() { state = 2; }

    /**
     *  Sets the state equal to 3 so the card is treated as face-down.
     */
    public void hideCard() { state = 3; }

    /**
     * Returns the ID of the card.
     * @return ID of the card.
     */
    public char getID() { return ID; }
}