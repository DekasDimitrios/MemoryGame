import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.*;

/**
 * This class is responsible for the handling of the different functions during the gameplay.
 *
 * @author Dekas Dimitrios
 * @version 2
 */
public class Game extends JFrame implements ActionListener {

    private static int cardsToPick; // The number of cards the user is supposed to pick each turn.
    private static int cardsLeft; // Cards left in the board.
    private static int numOfPlayers; //
    private static int resultsMode;
    private static ArrayList<Card> roundPicks;
    private static Board gameBoard;
    private static JLabel playerLabel;
    private static JLabel cardsToPickLabel;
    private static JLabel resultsLabel;
    private static JPanel pageStartPanel;
    private final Player[] players;
    private Timer timer;
    private ResourceBundle bundle;


    /**
     * Constructor for initializing the game and running it
     * @param mode the gamemode selected by the user
     * @param loc the Locale used during the game selected by the user
     * @param players the players that are going to play the game given as input by the user
     * @throws IOException handles the file exceptions
     */
    public Game(int mode, Locale loc, Player[] players) throws IOException {
        super();
        resultsMode = mode;
        this.bundle = ResourceBundle.getBundle("MessagesBundle", loc);
        switch (mode) {
            case 0:
                cardsLeft = 24;
                cardsToPick = 2;
                break;
            case 1:
                cardsLeft = 48;
                cardsToPick = 2;
                break;
            case 2:
                cardsLeft = 36;
                cardsToPick = 3;
                break;
            default:
                cardsLeft = 4;
                cardsToPick = 2;
        }
        timer = new Timer();
        roundPicks = new ArrayList<>();
        gameBoard = new Board(mode);
        this.setSize(1400, 800);
        this.setLocationRelativeTo(null);
        this.setTitle(bundle.getString("FrameTitle"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(new JPanel(new BorderLayout()));
        this.add(gameBoard, BorderLayout.CENTER);
        playerLabel = new JLabel("");
        cardsToPickLabel = new JLabel("");
        resultsLabel = new JLabel(bundle.getString("InitResultsLabel"), SwingConstants.CENTER);
        pageStartPanel = new JPanel(new BorderLayout());
        pageStartPanel.add(playerLabel, BorderLayout.LINE_START);
        pageStartPanel.add(resultsLabel, BorderLayout.CENTER);
        pageStartPanel.add(cardsToPickLabel, BorderLayout.LINE_END);
        this.add(pageStartPanel, BorderLayout.PAGE_START);
        gameBoard.setActionListener(this);
        if (mode != 0) {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        this.players = players;
        run(players);
    }

    /**
     *  As long as there are cards on the board users keep on playing.
     *  After all the cards are finished we inform them about there performance.
     *  @param players a table of players,
     *  @throws IOException handles the file exceptions
     */
    private void run(Player[] players) throws IOException {
        numOfPlayers = players.length;
        this.setVisible(true);
        int i = 0;
        while (cardsLeft != 0) {
            i = nextTurn(players[i], i) % players.length;
        }
        playerLabel.setText(bundle.getString("OverPlayerLabel"));
        resultsLabel.setText(bundle.getString("OverResultsLabel"));
        cardsToPickLabel.setText(bundle.getString("OverCardsToPickLabel"));
        GUI.showResults(players, resultsMode);
        this.dispose();
    }

    /**
     * Runs the next turn of the player given as input
     * @param p the player that's gonna play this turn
     * @param i the number that says to the program which player is playing, in relation to the other player's
     * @return the turnResults method to figure out the turn's results
     */
    private int nextTurn(Player p, int i) {
        if (p instanceof Computer) {
            cardsToPickLabel.setText(bundle.getString("CardsLeftToPick") + (cardsToPick - roundPicks.size()) + "  ");
            playerLabel.setText("  " + p.getUserName() + bundle.getString("Turn"));
            computerTurn((Computer) p);
        } else {
            while (roundPicks.size() < cardsToPick) {
                cardsToPickLabel.setText(bundle.getString("CardsLeftToPick") + (cardsToPick - roundPicks.size()) + "  ");
                if (numOfPlayers != 1) {
                    playerLabel.setText("  " + p.getUserName() + bundle.getString("Turn"));
                } else {
                    playerLabel.setText("  " + bundle.getString("SoloTurn"));
                }
            }
        }
        return turnResults(p, i);
    }

    /**
     * Runs the turn for the computer players
     * @param comp the computer player that is going to play the turn
     */
    private void computerTurn(Computer comp) {
        Card[] computerPicks = new Card[cardsToPick];
        Card[] cards = gameBoard.getBoard();
        int[] cardsIdx = new int[cardsToPick];
        boolean needRandomTurn = false;
        if (comp.getMemoryLevel() == Computer.ELEPHANT || comp.getMemoryLevel() == Computer.KANGAROO) {
            computerPicks = comp.getMemoryMatches(cardsToPick);
            if (computerPicks == null) {
                needRandomTurn = true;
            }
        }
        if (comp.getMemoryLevel() == Computer.GOLDFISH || needRandomTurn) {
            computerPicks = new Card[cardsToPick];
            for (int j = 0; j < cardsToPick; j++) {
                Random r = new Random();
                cardsIdx[j] = r.nextInt(cards.length);
                if (j == 0) {
                    while (!(cards[cardsIdx[j]].isFaceDown())) {
                        cardsIdx[j] = r.nextInt(cards.length);
                    }
                } else {
                    for (int k = 0; k < j; ) {
                        if (cardsIdx[j] == cardsIdx[k] || !(cards[cardsIdx[j]].isFaceDown())) {
                            cardsIdx[j] = r.nextInt(cards.length);
                            k = 0;
                        } else {
                            k++;
                        }
                    }
                }
                computerPicks[j] = cards[cardsIdx[j]];
            }
        }
        while (roundPicks.size() < cardsToPick) {
            for (Card c : computerPicks) {
                cardsToPickLabel.setText(bundle.getString("CardsLeftToPick") + (cardsToPick - roundPicks.size()) + "  ");
                pickCard(c);
            }
        }
    }

    /**
     * Resolves each turn result.
     * @param p the player that played the turn
     * @param i the player that is going to be playing next
     * @return an integer informing the program about the next playing player
     */
    private int turnResults(Player p, int i) {
        int same = 1;
        for (int j = 1; j < cardsToPick; j++) {
            if (roundPicks.get(j).sameIcon(roundPicks.get(j - 1))) {
                same++;
            }
        }
        if (same == cardsToPick) {
            if (numOfPlayers != 1) {
                resultsLabel.setText(bundle.getString("MultiMatch"));
            } else {
                resultsLabel.setText(bundle.getString("SoloMatch"));
            }
            for (Card c : roundPicks) {
                c.collect();
            }
            cardsLeft -= cardsToPick;
            p.setCollectedCards(p.getCollectedCards() + cardsToPick);
        } else {
            resultsLabel.setText(bundle.getString("NoMatch"));
            for (Card c : roundPicks) {
                c.turnDown();
            }
            i++;
        }
        p.setSteps(p.getSteps() + 1);
        roundPicks.clear();
        gameBoard.setClickable(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                resultsLabel.setText("    ");
                gameBoard.setClickable(true);
            }
        }, 600);
        return i;
    }

    /**
     * Allows a player to pick a card
     *
     * @param c a card that is picked by a player
     */
    private void pickCard(Card c) {
        if (c.isFaceDown() && c.isClickable() && roundPicks.size() < cardsToPick) {
            gameBoard.setClickable(false);
            c.turnUp();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    roundPicks.add(c);
                    gameBoard.setClickable(true);
                }
            }, 500);
            for (Player p : players) {
                if (p instanceof Computer) {
                    if (((Computer) p).getMemoryLevel() == Computer.KANGAROO && Computer.kangarooRemembers()) {
                        ((Computer) p).memorizeCard(c);
                    } else if (((Computer) p).getMemoryLevel() == Computer.ELEPHANT) {
                        ((Computer) p).memorizeCard(c);
                    }
                }
            }
        }
        Computer.changeKangaroosMemoryState();
    }

    /**
     * Responsible for handling the clicks during the gameplay
     * @param e an event that need to be handled
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        pickCard((Card) e.getSource());
    }
}