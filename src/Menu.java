import java.util.Scanner;
/**
 * Is the class responsible for the menu of our game.
 *
 * @author Dekas Dimitrios
 * @version 3
 */
public class Menu {

    private boolean exit; // Used to stop our game when the user decides to leave.

    // Field exit will initialize as false by default so we do not need a constructor.

    /**
     * Runs our menu.
     */
    public void runMenu() {
        printHeader();
        while(!exit) { // While the user does not want to exit from the game.
            printMenu();
            int selection = getInput();
            performAction(selection);
        }
    }

    /**
     * Prints our header.
     */
    private void printHeader() {
        System.out.println("+------------------------------+");
        System.out.println("|  Welcome to our Memory Game  |");
        System.out.println("|   How good is your memory?   |");
        System.out.println("+------------------------------+");
    }

    /**
     * Informs the user about the available actions in our game.
     */
    private void printMenu() {
        System.out.println("\nPlease, select an option: ");
        System.out.println("1) Start Game");
        System.out.println("2) Credits");
        System.out.println("0) Exit");
    }

    /**
     * Asks,reads and the returns the selection of the user based on our menu.
     * @return the user's selection bases on our menu.
     */
    private int getInput() {
        Scanner input = new Scanner(System.in);
        int selection = -1;
        while(selection < 0 || selection > 2) {
            try {
                System.out.print("\nEnter your selection: ");
                selection = Integer.parseInt(input.nextLine()); // Variable selection is the next line the user will type for us
                if(selection < 0 || selection > 2){
                    System.out.println("Wrong number given. Please select again.");
                }
            }
            catch (NumberFormatException e) { // We are catching the exception the input not being a number.
                System.out.println("Invalid selection. Please try again.");
            }
        }
        return selection;
    }

    /**
     * Performs an action based on the user's selection.
     * @param selection : an in that represents the user's selection from our menu options.
     */
    private void performAction(int selection) {
        switch(selection) {
            case 0:
                exit = true;
                System.out.println("\nThank you for playing our Memory Game!");
                break;
            case 1: // Runs our game.
                UI userInterface = new UI();
                Gameplay gp = new Gameplay(userInterface.getGameMode());
                Board gameBoard = new Board(userInterface.getGameMode());
                gp.runGame(userInterface.getPlayers(),gameBoard);
                break;
            case 2:
                System.out.println("+--------------Creators--------------+");
                System.out.println("|                                    |");
                System.out.println("|  Name: Nikas Marios -- AEM: 3065   |");
                System.out.println("| Name: Dekas Dimitrios -- AEM: 3063 |");
                System.out.println("|                                    |");
                System.out.println("+------------------------------------+");
                break;
            default: // Should be never accessible.
                System.out.println("An unknown error has occurred.");
                break;
        }
    }
}