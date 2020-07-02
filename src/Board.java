import java.util.Random;

/**
 * It's the class responsible for the creation and the manipulation of the Board.
 *
 * @author Dekas Dimitrios
 * @author Nikas Marios.
 * @version 3
 */
public class Board {

    private int row; // Number of board's rows.
    private int col; // Number of board's columns.
    private int numOfCardsMatching; // Number of cards matching in our board.
    private Card[][] board; // The board.

    /**
     * Constructor initializes the row, the col and the numOfCardsMatching based on the gameMode selected by the user.
     * After that we use the method createBoard to create a two dimensional array filled with Card objects
     * and we use the UI method printNextBoard to show the user his/her starting gameBoard.
     * @param gameMode Its the gameMode picked by the user through UI.
     */
    public Board(int gameMode) {
        switch (gameMode) {
            case 1:
                row = 4;
                col = 6;
                numOfCardsMatching = 12;
                break;
            case 2:
                row = 6;
                col = 8;
                numOfCardsMatching = 24;
                break;
            case 3:
                row = 6;
                col = 6;
                numOfCardsMatching = 12;
                break;
        }
        createBoard();
        Messages.startingBoardMessage();
        UI.printNextBoard(board);
    }

    /**
     * Creates the Board.
     */
    private void createBoard() {
        // Array with the letters we will need to fill the Board.
        char[] chars = new char[numOfCardsMatching];
        for (int i = 0; i < numOfCardsMatching; i++) {
            chars[i] = (char) (i + 65);
        }
        // Array filled with the empty slots in our Board(e.g. for a 4x6 matrix we have: 00,01,02,03,04,05,10,11,12,13,14,15,20,21 etc.).
        int[] emptySlots = new int[row * col];
        int counter = 0; // Helps us with the changing of the rows.
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                emptySlots[(counter * col) + j] = (i * 10) + j;
            }
            counter++;
            }
        //
        board = new Card[row][col];
        int numOfDelEls = 0; // Number of deleted elements from the emptySlots array.
        for (int i = 0; i < numOfCardsMatching; i++) {
            Random r = new Random(); // Used to access a random emptySlots element.
            for (int j = 0; j < Gameplay.getCardsToPick(); j++) {
                int x = r.nextInt(row * col - numOfDelEls);
                // rRow is the random row generated. We get it by dividing the emptySlots element(e.g. 34) by 10(3).
                int rRow = emptySlots[x] / 10;
                // rCol is the random column generated. We get it by taking the mod of the emptySlots element(34) by 10(4).
                int rCol = emptySlots[x] % 10;
                board[rRow][rCol] = new Card(chars[i]);
                if ((row * col) - (numOfDelEls + 1) - x >= 0)
                    // So we never choose a row and column combination we already placed a letter in.
                    System.arraycopy(emptySlots, x + 1, emptySlots, x, (row * col) - (numOfDelEls + 1) - x);
                numOfDelEls++; // Each time we get a random number(r) the bound is smaller by one.
            }
        }
    }

    /**
     * Method returns the board.
     * @return board : The two dimensional array filled with Card objects.
     */
    public Card[][] getBoard() { return board; }
}