import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.*;

/**
 * This class is responsible for the handling of the different functions during a duel.
 *
 * @author Marios Nikas
 * @author Dekas Dimitrios
 * @version 2
 */
public class Duel extends JFrame implements ActionListener {

    private final JPanel pageStartPanel;
    private final JLabel board1Label;
    private final JLabel board2Label;
    private final JLabel instructionLabel;
    private final Player[] players;
    private int cardsLeft; // Cards left in the board.
    private ResourceBundle bundle;
    private Board b1;
    private Board b2;
    private ArrayList<Card> roundPicks;
    private Timer timer;

    /**
     * Constructor for initializing a duel and running it
     * @param bundle the bundle used during the game selected by the user
     * @param players the players that are going to play the game given as input by the user
     * @throws IOException handles the file exceptions
     */
    public Duel(ResourceBundle bundle, Player[] players) throws IOException {
        super();
        this.bundle = bundle;
        this.b1 = new Board(3);
        b1.setPreferredSize(new Dimension(900, 700));
        b1.setActionListener(this);
        b1.setClickable(true);
        this.b2 = new Board(3);
        b2.setPreferredSize(new Dimension(900, 700));
        b2.setActionListener(this);
        b2.setClickable(false);
        this.setLocationRelativeTo(null);
        this.setTitle(bundle.getString("FrameTitle"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        roundPicks = new ArrayList<>();
        pageStartPanel = new JPanel(new BorderLayout());
        board1Label = new JLabel();
        board2Label = new JLabel();
        instructionLabel = new JLabel("", SwingConstants.CENTER);
        pageStartPanel.add(board1Label, BorderLayout.LINE_START);
        pageStartPanel.add(instructionLabel, BorderLayout.CENTER);
        pageStartPanel.add(board2Label, BorderLayout.LINE_END);
        JPanel duelPanel = new JPanel(new BorderLayout());
        this.add(pageStartPanel, BorderLayout.PAGE_START);
        duelPanel.add(b1, BorderLayout.LINE_START);
        duelPanel.add(new JLabel("   "), BorderLayout.CENTER);
        duelPanel.add(b2, BorderLayout.LINE_END);
        this.cardsLeft = 24;
        this.players = players;
        this.timer = new Timer();
        this.add(duelPanel);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        run(players);
    }

    /**
     *  As long as there are cards on the board users keep on playing.
     *  After all the cards are finished we inform them about there performance.
     *  @param players a table of players,
     *  @throws IOException handles the file exceptions
     */
    private void run(Player[] players) throws IOException {
        if ("US".equals(bundle.getLocale().getCountry())) {
            board1Label.setText("  " + players[0].getUserName() + bundle.getString("DuelPanel"));
            board2Label.setText(players[1].getUserName() + bundle.getString("DuelPanel") + "  ");
        } else {
            board1Label.setText("  " + bundle.getString("DuelPanel") + players[0].getUserName());
            board2Label.setText(bundle.getString("DuelPanel") + players[1].getUserName() + "  ");
        }
        this.setVisible(true);
        int i = 0;
        while (cardsLeft != 0) {
            i = nextTurn(players[i], players[(i + 1) % 2], i) % 2;
        }
        GUI.showResults(players, 3);
        this.dispose();
    }

    /**
     * Runs the next turn of the player given as input
     * @param player1 the player picking a card first this turn
     * @param player2 the player picking a card second this turn
     * @param i the number that says to the program which player is playing, in relation to the other player's
     * @return the turnResults method to figure out the turn's results
     */
    private int nextTurn(Player player1, Player player2, int i) {
        boolean compTurn = false;
        Card finalPick = null;
        if (player1 instanceof Computer) {
            Random r = new Random();
            Card[] cards = b2.getBoard();
            int cardIdx = r.nextInt(cards.length);
            while (!(cards[cardIdx].isFaceDown())) {
                cardIdx = r.nextInt(cards.length);
            }
            pickCard(cards[cardIdx]);
        }
        while (roundPicks.size() < 2) {
            if (roundPicks.size() == 0) {
                instructionLabel.setText(bundle.getString("DuelPick1") + player1.getUserName() + " " + bundle.getString("DuelPick2"));
            }
            if (roundPicks.size() == 1) {
                instructionLabel.setText(bundle.getString("DuelPick1") + player2.getUserName() + " " + bundle.getString("DuelPick2"));
                if (player2 instanceof Computer) {
                    compTurn = true;
                    break;
                }
            }
        }
        if (compTurn) {
            b1.setClickable(false);
            boolean needRandomPick = true;
            Card openCard = null;
            for (Card c : b1.getBoard()) {
                if (c.isFaceUp()) {
                    openCard = c;
                    break;
                }
            }
            for (Card c : ((Computer) player2).getMemory()) {
                if (c.sameIcon(openCard)) {
                    finalPick = c;
                    needRandomPick = false;
                    break;
                }
            }
            if (needRandomPick) {
                Random r = new Random();
                Card[] cards = b2.getBoard();
                int cardIdx = r.nextInt(cards.length);
                while (!(cards[cardIdx].isFaceDown())) {
                    cardIdx = r.nextInt(cards.length);
                }
                finalPick = cards[cardIdx];
            }
        }
        while (roundPicks.size() < 2) {
            pickCard(finalPick);
        }
        return turnResults(player1, player2, i);
    }

    /**
     * Resolves each turn result.
     * @param player1 the player picking a card first this turn
     * @param player2 the player picking a card second this turn
     * @param i the player that is going to be playing next
     * @return an integer informing the program about the next playing player
     */
    private int turnResults(Player player1, Player player2, int i) {
        int same = 1;
        if (roundPicks.get(0).sameIcon(roundPicks.get(1))) {
            same++;
        }
        if (same == 2) {
            instructionLabel.setText(player2.getUserName() + " " + bundle.getString("DuelMatch"));
            for (Card c : roundPicks) {
                c.setSelected(false);
                c.collect();
            }
            cardsLeft--;
            player2.setCollectedCards(player2.getCollectedCards() + 2);
        } else {
            instructionLabel.setText(player2.getUserName() + " " + bundle.getString("NoDuelMatch"));
            for (Card c : roundPicks) {
                c.setSelected(false);
                c.turnDown();
            }
        }
        player1.setSteps(player1.getSteps() + 1);
        player2.setSteps(player2.getSteps() + 1);
        roundPicks.clear();
        b1.setClickable(!(b1.getBoard()[0].isClickable()));
        b2.setClickable(!(b2.getBoard()[0].isClickable()));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                instructionLabel.setText(" ");
            }
        }, 600);
        return i + 1;
    }

    /**
     * Allows a player to pick a card
     *
     * @param c a card that is picked by a player
     */
    private void pickCard(Card c) {
        if (c.isFaceDown() && c.isClickable() && roundPicks.size() < 2) {
            b1.setClickable(!(b1.getBoard()[0].isClickable()));
            b2.setClickable(!(b2.getBoard()[0].isClickable()));
            c.setSelected(true);
            c.turnUp();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    roundPicks.add(c);
                }
            }, 500);
            if (b1.getBoard()[0].isClickable()) {
                for (Player p : players) {
                    if (p instanceof Computer) {
                        if (((Computer) p).getMemoryLevel() == Computer.KANGAROO && Computer.kangarooRemembers()) {
                            ((Computer) p).memorizeCard(c);
                        } else if (((Computer) p).getMemoryLevel() == Computer.ELEPHANT) {
                            ((Computer) p).memorizeCard(c);
                        }
                    }
                }
                Computer.changeKangaroosMemoryState();
            }
        }
    }

    /**
     * Responsible for handling the clicks during the gameplay
     * @param e an event that need to be handled
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Card clickedCard = (Card) e.getSource();
        if (players[1] instanceof Computer && b1.contains(clickedCard) && !b1.hasFaceUpCard()) {
            pickCard(clickedCard);
        }
        if (!(players[1] instanceof Computer)) {
            pickCard(clickedCard);
        }
    }
}
