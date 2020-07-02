/**
 * It's the class responsible for the messages appearing to the user during the gameplay.
 * The class has no fields or constructor and it is consisted of static methods used to print messages on the user's screen.
 *
 * @author Dekas Dimitrios
 * @version 3
 */
public class Messages {

    /**
     * The message that asks for the user's name.
     */
    public static void nameMessage() { System.out.print("\nWhat's your name?  "); }

    /**
     * The message that appears when the starting board is printed.
     */
    public static void startingBoardMessage() { System.out.println("This is your starting board:");}

    /**
     * The message that asks for the gamemode the user wishes to play.
     */
    public static void gameModeMessage() {
        System.out.println("\nPlease choose the game mode you wish to play by typing the matching number(1-3):");
        System.out.println("1. Basic    (4x6 Board - Pick 2 cards per turn.)");
        System.out.println("2. Double   (6x8 Board - Pick 2 cards per turn.)");
        System.out.println("3. Triple   (6x6 Board - Pick 3 cards per turn.)");
    }

    /**
     * The message that appears when the game is over and informs the user about his score.
     */
    public static void finalMessage(String userName,int steps,int collectedCards) { System.out.println(userName + ", it took you " + steps + " steps to collect " + collectedCards + " cards. Congratulations!"); }

    /**
     * The message that appears when the user makes a mistake on the gamemode input.
     * We ask the user to enter a right input.
     */
    public static void reGameModeMessage() {
        System.out.println("Please choose the game mode you wish to play again. Make sure is is between the limits(1-3):");
        System.out.println("1. Basic    (4x6 Board)");
        System.out.println("2. Double   (6x8 Board)");
        System.out.println("3. Triple   (6x6 Board - Pick 3 cards per turn)");
    }

    /**
     * The message that asks for the row the user wishes to pick a card from.
     */
    public static void rowMessage() { System.out.print("Give me the row you wish to pick a card from: "); }

    /**
     * The message that asks for the column the user wishes to pick a card from.
     */
    public static void colMessage() { System.out.print("Give me the column you wish to pick a card from: "); }

    /**
     * The message that appears when the user finds matching cards in his/her turn.
     */
    public static void foundMatchingMessage() { System.out.println("All the cards you picked share the same latter!!!\n"); }

    /**
     * The message that appears when the user does not find matching cards in his/her turn.
     */
    public static void notFoundMatchingMessage() { System.out.println("Not all the cards you picked share the same letter. Try again!\n"); }

    /**
     * The message that appears to the user when he/she picks the same card twice in the same turn.
     */
    public static void alreadyChosenCard() { System.out.println("You already chose this card. Choose another card.\n"); }

    /**
     * The message that appears to the user when an already collected card is picked.
     */
    public static void alreadyCollectedCard() { System.out.println("This card is already collected. Choose another card.\n"); }

    /**
     * The message that appears to the user when he/she picks a row that does not exist.
     */
    public static void rowBoundaryMessage(int rows) { System.out.print("There are only "+ rows +" rows. Give another row number. Make sure it's positive: "); }

    /**
     * The message that appears to the user when he/she picks a column that does not exist.
     */
    public static void colBoundaryMessage(int cols) { System.out.print("There are only "+ cols +" columns. Give another column number. Make sure it's positive: "); }
}
