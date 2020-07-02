/**
 * This class is responsible for the handling of the different functions during the gameplay.
 *
 * @author Marios Nikas
 * @author Dekas Dimitrios
 * @version 2
 */
public class Gameplay {

    private static int cardsToPick; // The number of cards the user is supposed to pick each turn.
    private int cardsLeft; // Cards left in the board
    private int[][] picks; // Array that stores the user picks every round

    /**
    * Constructor initializes cardsLeft and CardstoPick based on the mode and constructs an object b which is the board of cards used in the game
    * @param mode is a integer which corresponds to the game mode.
    */
    public Gameplay(int mode){
        switch (mode) {
            case 1:
                cardsLeft = 24;
                cardsToPick = 2;
                break;
            case 2:
                cardsLeft = 48;
                cardsToPick = 2;
                break;
            default:
                cardsLeft = 36;
                cardsToPick = 3;
                break;
        }
    }

    /**
    * As long as there are cards on the board users keep on playing.
    * After all the cards are finished we inform them about there performance.
    * @param pArray a table of players,
    * @param b a Board objects that keeps track of the board of cards.
    */
    public void runGame(Player[] pArray,Board b) {
        while (!(cardsLeft == 0)) {
            for (Player p : pArray) {
                nextTurn(p, b);
                if (cardsLeft == 0) { // Checks if the game finished by a player that is not the first one.
                    break;
                }
            }
        }
        for (Player p : pArray) {
            Messages.finalMessage(p.getUserName(),p.getSteps(),p.getCollectedCards());
        }
    }

    /**
     * Runs the turn of each player.
     * @param p the array that stores the users playing.
     * @param b the board.
     * */
    private void nextTurn(Player p,Board b) {
        int same = 1;
        picks = new int[cardsToPick][2]; // Dynamic array bases on the number of cards the user is going to pick each turn.
        for(int i=0;i<cardsToPick;i++) {
            cardSelection(i,picks,b); // User picks an uncollected/in-boundary card.
            if(i>0) {
                for(int j=0;j<i;) {
                    if(picks[i][0] == picks[j][0] && picks[i][1] == picks[j][1]) { // If the card is already picked this turn.
                        Messages.alreadyChosenCard();
                        cardSelection(i,picks,b);
                        j=0; // Check all previous cards again.
                    }
                    else{
                        j++; // If the card is not already picked this turn move on.
                    }
                }
                if (b.getBoard()[picks[i][0]][picks[i][1]].getID() == b.getBoard()[picks[i - 1][0]][picks[i - 1][1]].getID()) {
                    same++;
                }
            }
        }
        turnResults(same,b,p);
        UI.printNextBoard(b.getBoard());
        p.setSteps(p.getSteps()+1);
    }

    /**
     * Handles the results of a round bases on its parameters.
     * @param same the same cards the user chose in the turn.
     * @param b our board.
     * @param p the array with the players.
     */
    private void turnResults(int same,Board b,Player p) {
        if (same==cardsToPick){ // If all the cards the user chose have the same Id.
            for (int i=0;i<cardsToPick;i++){
                b.getBoard()[picks[i][0]][picks[i][1]].collectCard();
            }
            p.setCollectedCards(p.getCollectedCards()+cardsToPick);
            cardsLeft-=cardsToPick;
            Messages.foundMatchingMessage();
        }
        else{
            for (int i=0;i<cardsToPick;i++){
                b.getBoard()[picks[i][0]][picks[i][1]].hideCard();
            }
            Messages.notFoundMatchingMessage();
        }
    }

    /**
     * Reads the row and col the user picks and if the card is not already collected stores the values in the picks array. Also it reveals the card picked and prints the next state of the board.
     * @param i the i-th pick of the user each turn is stored in the i-th - 1 picks row.
     * @param picks the array used to store the picks every turn.
     * @param b the board.
     */
    private void cardSelection(int i,int[][] picks,Board b) {
        int row = UI.rowInput(b.getBoard().length - 1);
        int col = UI.colInput(b.getBoard()[0].length - 1);
        while (b.getBoard()[row][col].isCollected()) {
            Messages.alreadyCollectedCard();
            row = UI.rowInput(b.getBoard().length - 1);
            col = UI.colInput(b.getBoard()[0].length - 1);
        }
        picks[i][0] = row;
        picks[i][1] = col;
        b.getBoard()[row][col].revealCard();
        UI.printNextBoard(b.getBoard());
    }

    /**
     * Returns the number of cards the user picks every turn.
     * @return an int that represents the number of card the user picks each round.
     */
    public static int getCardsToPick() { return cardsToPick ; }
}