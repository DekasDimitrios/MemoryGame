import javax.swing.*;

/**
 * It's the class responsible for the creation and the manipulation of each card.
 *
 * @author Dekas Dimitrios
 * @version 5
 * Last Update: 3/1/2019
 */
public class Card extends JButton {

    private static final int FACE_DOWN_CARD = 1;
    private static final int FACE_UP_CARD = 2;
    private static final int COLLECTED_CARD = 3;

    private final ImageIcon BACK_ICON = new ImageIcon(((new ImageIcon("images/cardBack.jpg")).getImage()).getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH));
    private final ImageIcon COLLECTED_ICON = new ImageIcon(((new ImageIcon("images/collected.jpg")).getImage()).getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH));

    private int state;
    private ImageIcon faceIcon;
    private int id; // Cards with same Image has the same id.
    private boolean memorized;
    private boolean clickable;


    /**
     * Sets every card face-down and the Image of each card based on the parameter.
     *
     * @param faceIcon front side Image.
     * @param id       identifier of each card.
     */
    public Card(ImageIcon faceIcon, int id) {
        super();
        this.memorized = false;
        this.clickable = true;
        this.state = FACE_DOWN_CARD;
        this.setIcon(BACK_ICON);
        this.id = id;
        this.faceIcon = new ImageIcon(((faceIcon.getImage()).getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH)));
    }

    /**
     * Returns true only if the card is Collected.
     *
     * @return a boolean value that shows us if the card is already collected or not.
     */
    private boolean isCollected() {
        return state == COLLECTED_CARD;
    }

    /**
     * Returns true only if the card is Face Up.
     *
     * @return a boolean value that shows us if the card is Face Up or not.
     */
    public boolean isFaceUp() {
        return state == FACE_UP_CARD;
    }

    /**
     * Returns true only if the card is Face Down.
     *
     * @return a boolean value that shows us if the card is Face Down or not.
     */
    public boolean isFaceDown() {
        return state == FACE_DOWN_CARD;
    }

    /**
     * Collects a card.
     */
    public void collect() {
        if (this.isCollected() || this.isFaceDown()) {
            return;
        }
        this.setIcon(COLLECTED_ICON);
        this.state = COLLECTED_CARD;
    }

    /**
     * Turns a card Face-Up.
     */
    public void turnUp() {
        if (this.isFaceUp() || this.isCollected()) {
            return;
        }
        this.setIcon(this.faceIcon);
        this.state = FACE_UP_CARD;
    }

    /**
     * Turns a card Face-Down.
     */
    public void turnDown() {
        if (this.isFaceDown() || this.isCollected()) {
            return;
        }
        this.setIcon(BACK_ICON);
        this.state = FACE_DOWN_CARD;
    }

    /**
     * Checks if the icon's of two cards is the same
     *
     * @param other the card we want to check the equality for
     * @return a boolean values the determines the image equality of two cards
     */
    public boolean sameIcon(Card other) {
        if(other == null) {
            return false;
        }
        return other.id == this.id;
    }

    /**
     * Returns true if the card is memorized by the computer's memory
     * @return a boolean value that determines if a card is memorized by the computer AI
     */
    public boolean isMemorized() {
        return memorized;
    }

    /**
     * Determines if the computer AI remembers a card based on the value of the param
     * @param bool a boolean value that sets the param memorized
     */
    public void setMemorized(boolean bool) {
        this.memorized = bool;
    }

    /**
     * Returns the clickable field's value
     *
     * @return a boolean value that is true if the card is clickable or false if its not clickable
     */
    public boolean isClickable() {
        return clickable;
    }

    /**
     * Sets card's clickability to the param's value
     *
     * @param bool a boolean value that will determine whether the card is clickable or not.
     */
    public void setClickable(boolean bool) {
        this.clickable = bool;
    }
}