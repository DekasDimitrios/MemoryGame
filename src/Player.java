/**
 *  This class is responsible for handling each players details.
 *
 *  @author Nikas Marios
 *  @author Dekas Dimitrios
 *  @version 2
 */

public class Player {

    private String userName; // Contains the given name of a player.
    private int steps; // Stores the steps of a player.
    private int collectedCards; // Stores the amount of cards a player has revealed and collected.

    /**
     * Constructor sets the name of the player and initializes fields steps and collectedCards to 0.
     * @param userName is the name of the player.
     */
    public Player(String userName) {
        this.userName = userName;
        steps = 0;
        collectedCards = 0;
    }

    /**
     * Returns the name of a player.
     * @return the name of the player.
     */
    public String getUserName() { return userName; }

    /**
     * Assigns field steps a value equal to the methods param.
     * @param steps is the number of steps a player has taken so far.
     */
    public void setSteps(int steps) { this.steps = steps; }

    /**
     * Returns the value of field steps.
     * @return the number of steps a player has taken so far.
     */
    public int getSteps() { return steps; }

    /**
     * Assigns field collectedCards a value equal to the methods param.
     * @param collectedCards is the number of cards a player has collected.
     */
    public void setCollectedCards(int collectedCards) { this.collectedCards = collectedCards; }

    /**
     * Returns the value of field collectedCards.
     * @return the number of cards a player has collected.
     */
    public int getCollectedCards() { return collectedCards; }
}
