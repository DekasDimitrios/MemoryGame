import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * It's the class responsible for the creation and the manipulation of the Board.
 *
 * @author Dekas Dimitrios
 * @author Nikas Marios.
 * @version 3
 */
public class Board extends JPanel {

    private final int rows;
    private final int cols;
    private final int cardsToPick;
    private final int numOfSameCards;
    private ImageIcon[] cardIcons; // Array for storing the images.
    private Card[] board;


    /**
     * Constructor initializes the row, the col,the numOfSameCards and the cardsToPick based on the gameMode selected by the user.
     *
     * @param gameMode Its the gameMode picked by the user through GUI.
     */
    public Board(int gameMode) {
        super();
        switch (gameMode) {
            case 3:
                rows = 6;
                cols = 4;
                numOfSameCards = 24;
                cardsToPick = 1;
                break;
            case 2:
                rows = 6;
                cols = 6;
                numOfSameCards = 12;
                cardsToPick = 3;
                break;
            case 1:
                rows = 6;
                cols = 8;
                numOfSameCards = 24;
                cardsToPick = 2;
                break;
            default:
                rows = 4;
                cols = 6;
                numOfSameCards = 12;
                cardsToPick = 2;
                break;
        }
        createBoard();
    }

    /**
     * Creates the Board.
     */
    private void createBoard() {
        board = new Card[rows * cols];
        cardIcons = getCardIcons();
        makePanel();
    }

    /**
     * Initializes the images that we will use for the cards
     *
     * @return an array of Images
     */
    private ImageIcon[] getCardIcons() {
        ImageIcon[] icons = new ImageIcon[numOfSameCards];
        for (int i = 0; i < numOfSameCards; i++) {
            String filename = "images/card" + (i + 1) + ".jpg";
            icons[i] = new ImageIcon(filename);
        }
        return icons;
    }

    /**
     * Makes the panel that will be used to facilitate the cards
     */
    private void makePanel() {
        this.setLayout(new GridLayout(rows, cols, 10, 10));
        int[] cardsToAdd = new int[(rows * cols)];
        for (int i = 0; i < numOfSameCards; i++) {
            for (int j = 0; j < cardsToPick; j++) {
                cardsToAdd[cardsToPick * i + j] = i;
            }
        }
        randomizeCardsToAdd(cardsToAdd);
        for (int i = 0; i < cardsToAdd.length; i++) {
            Card newCard = new Card(cardIcons[cardsToAdd[i]], cardsToAdd[i]);
            board[i] = newCard;
            this.add(newCard);
        }
    }

    /**
     * Randomizes the given array.
     *
     * @param array an array of integers
     */
    private void randomizeCardsToAdd(int[] array) {
        Random r = new Random();
        // Do random swaps between elements.
        for (int i = 0; i < array.length; i++) {
            int s = r.nextInt(array.length);
            int temp = array[s];
            array[s] = array[i];
            array[i] = temp;
        }

    }

    /**
     * Sets an action to be done when a card from the board is clicked
     *
     * @param handler an ActionListener for the cards
     */
    public void setActionListener(ActionListener handler) {
        for (Card c : board) {
            c.addActionListener(handler);
        }
    }

    /**
     * Sets all the cards of the board clickable or unclickable
     *
     * @param bool a boolean value that dictates whether the cards of the board will be clickable or not
     */
    public void setClickable(boolean bool) {
        for (Card c : board) {
            c.setClickable(bool);
        }
    }

    /**
     * Getter for the created board
     *
     * @return an array of Cards representing the board
     */
    public Card[] getBoard() {
        return board;
    }

    /**
     * Returns true if the board contains the given card
     *
     * @param card a Card object that we want to determine whether its contained on the board or not.
     * @return a boolean value that represents whether the given card is in the board or not
     */
    public boolean contains(Card card) {
        for (Card c : board) {
            if (card == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the board has already a face-up card
     *
     * @return a boolean value that represents whether or not there is a face-up card in the board
     */
    public boolean hasFaceUpCard() {
        for (Card c : board) {
            if (c.isFaceUp()) {
                return true;
            }
        }
        return false;
    }
}