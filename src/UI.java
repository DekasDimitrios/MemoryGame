import java.util.Scanner;

/**
 * It's the class responsible for the interface with the user.
 *
 * @author Dekas Dimitrios
 * @version 5
 */
public class UI {

    private int gameMode; // The gamemode the player chooses at the start of the game.
    private Player[] players; // An array that saves the players currently playing.

    /**
     * Initializes the fields numOfPlayers and players.
     * Asks the user to give us the gameMode he/she wishes to play.
     */
    public UI() {
        Scanner input = new Scanner(System.in);
        int numOfPlayers = 1;
        players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            Messages.nameMessage();
            String name = input.nextLine();
            players[i] = new Player(name);
        }
        Messages.gameModeMessage();
        gameMode = input.nextInt();
        System.out.println();
        while (gameMode < 1 || gameMode > 3) {
            Messages.reGameModeMessage();
            gameMode = input.nextInt();
        }
    }

    /**
     * Used to print the current state of the board.
     * @param board is a two dimensional array filled with Card objects.
     */
    public static void printNextBoard(Card[][] board) {
        System.out.print("\n");
        for (Card[] b : board) {
            for (Card c : b) {
                if (c.isCollected()) {
                    System.out.print("\t");
                    System.out.print(" |   | ");
                    System.out.print("\t");
                }
                else if (c.isFaceUp()) {
                    System.out.print("\t");
                    System.out.print(" | " + c.getID() + " | ");
                    System.out.print("\t");
                }
                else {
                    System.out.print("\t");
                    System.out.print(" | * | ");
                    System.out.print("\t");
                }
            }
            System.out.print("\n");
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * Returns the gameMode.
     * @return an int that represents the gameMode the user has chosen.
     */
    public int getGameMode() { return gameMode; }

    /**
     * Returns the players array.
     * @return an array of Player objects. Each object represents a player in our game.
     */
    public Player[] getPlayers() { return players; }

    /**
     * Used to read and return the row that the user chooses every turn.
     * @param boundary : an int that represents the limits for our input.
     * @return an int that represents the chosen row.
     */
    public static int rowInput(int boundary) {
        Scanner input = new Scanner(System.in);
        Messages.rowMessage();
        int row = input.nextInt() - 1;
        while(row > boundary || row < 0) {
            Messages.rowBoundaryMessage(boundary + 1);
            row = input.nextInt() - 1;
        }
        return row;
    }

    /**
     * Used to read and return the column that the user chooses every turn.
     * @param boundary : an int that represents the limits for our input.
     * @return an int that represents the chosen column.
     */
    public static int colInput(int boundary) {
        Scanner input = new Scanner(System.in);
        Messages.colMessage();
        int col = input.nextInt() - 1;
        while(col > boundary || col < 0) {
            Messages.colBoundaryMessage(boundary + 1);
            col = input.nextInt() - 1;
        }
        return col;
    }
}