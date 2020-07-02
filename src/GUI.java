import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Class is responsible for the graphic interface with the user
 */
public class GUI {

    private int numOfPlayers;
    private Player[] players;
    private int gamemode;
    private JFrame menuWindow;
    private JButton[] menuButtons = new JButton[4];
    private JLabel langLabel;
    private JTextField[] nameTextField;
    private JCheckBox[] comCheckBox;
    private JComboBox[] memLevComboBox;
    private JButton[] gamemodeButtons = new JButton[4];
    private Game game;
    private Duel duel;
    private JButton langButton;
    private Locale loc;
    private static ResourceBundle bundle;

    /**
     * Creates the starting menu with all the buttons according to the localization setting
     */
    public GUI() {
        //Locale.
        loc = new Locale("en", "En");
        bundle = ResourceBundle.getBundle("MessagesBundle", loc);
        //Games Menu.
        menuWindow = new JFrame(bundle.getString("FrameTitle"));
        menuWindow.setResizable(false);
        menuWindow.setSize(400, 370);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel menuPanel = new JPanel(new BorderLayout());
        JPanel menuButtonsPanel = new JPanel(new GridBagLayout());
        //Menu Header stuff.
        JLabel menuHeader = new JLabel(bundle.getString("MenuHeader1"), SwingConstants.CENTER);
        JLabel menuHeader2 = new JLabel(bundle.getString("MenuHeader2"), SwingConstants.CENTER);
        //Start Game button stuff.
        menuButtons[0] = new JButton(bundle.getString("StartButton"));
        //Scoreboard button stuff.
        menuButtons[1] = new JButton(bundle.getString("ScoreboardButton"));
        //Credits button stuff.
        menuButtons[2] = new JButton(bundle.getString("CreditsButton"));
        //Exit button stuff.
        menuButtons[3] = new JButton(bundle.getString("ExitButton"));
        //Placement stuff.
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 20, 0, 20);
        c.gridx = 0;
        c.gridy = 1;
        menuButtonsPanel.add(menuHeader, c);
        c.insets = new Insets(0, 20, 30, 20);
        c.gridx = 0;
        c.gridy = 2;
        menuButtonsPanel.add(menuHeader2, c);
        for (int i = 0; i < 4; i++) {
            c.insets = new Insets(10, 20, 10, 20);
            c.gridx = 0;
            c.gridy = i + 3;
            menuButtonsPanel.add(menuButtons[i], c);
        }
        FlowLayout langPanelLayout = new FlowLayout();
        langPanelLayout.setAlignment(FlowLayout.LEFT);
        JPanel langPanel = new JPanel(langPanelLayout);
        langLabel = new JLabel(bundle.getString("LangLabel"));
        langPanel.add(langLabel);
        langButton = new JButton(bundle.getString("Language"));
        langPanel.add(langButton);
        menuPanel.add(langPanel, BorderLayout.PAGE_END);
        menuPanel.add(menuButtonsPanel, BorderLayout.CENTER);
        menuWindow.add(menuPanel);
        langButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (langButton.getText().equals("Greek")) {
                    loc = new Locale("gr", "Gr");
                } else {
                    loc = new Locale("en", "En");
                }
                bundle = ResourceBundle.getBundle("MessagesBundle", loc);
                menuWindow.setTitle(bundle.getString("FrameTitle"));
                menuHeader.setText(bundle.getString("MenuHeader1"));
                menuHeader2.setText(bundle.getString("MenuHeader2"));
                menuButtons[0].setText(bundle.getString("StartButton"));
                menuButtons[1].setText(bundle.getString("ScoreboardButton"));
                menuButtons[2].setText(bundle.getString("CreditsButton"));
                menuButtons[3].setText(bundle.getString("ExitButton"));
                langLabel.setText(bundle.getString("LangLabel"));
                langButton.setText(bundle.getString("Language"));
            }
        });
        menuWindow.setVisible(true);
        //Events Handler.
        Handler menuButtonHandler = new Handler();
        for (int i = 0; i < 4; i++) {
            menuButtons[i].addActionListener(menuButtonHandler);
        }
    }

    /**
     * Updates the program based on the input of the user on the player and bot menu
     */
    private void playersUpdate() {
        for (int j = 0; j < numOfPlayers; j++) {
            if (comCheckBox[j].isSelected()) {
                switch ((String) Objects.requireNonNull(memLevComboBox[j].getSelectedItem())) {
                    case "Goldfish":
                        players[j] = new Computer(Computer.GOLDFISH, bundle);
                        break;
                    case "Kangaroo":
                        players[j] = new Computer(Computer.KANGAROO, bundle);
                        break;
                    case "Elephant":
                        players[j] = new Computer(Computer.ELEPHANT, bundle);
                }
            } else {
                players[j] = new Player(nameTextField[j].getText());
            }
        }
    }

    /**
     * Handles the result screen after the game ended
     *
     * @param players a array containing all the players and bots that took part in the game
     * @param mode integer value that determines the gamemode
     * @throws IOException exception to handle file related errors
     */
    public static void showResults(Player[] players, int mode) throws IOException {
        Player winner = players[0];
        for (Player p : players) {
            if (p.getCollectedCards() > winner.getCollectedCards()) {
                winner = p;
            }
        }
        int tie = 0;
        for (Player p : players) {
            if (p.getCollectedCards() == winner.getCollectedCards()) {
                tie++;
            }
        }
        FileManager fmanager = new FileManager(players.length, mode);
        JFrame resultsFrame = new JFrame(bundle.getString("FrameTitle"));
        resultsFrame.setResizable(false);
        resultsFrame.setSize(400, 300);
        resultsFrame.setLocationRelativeTo(null);
        resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel resultsPanel = new JPanel(new GridBagLayout());
        resultsFrame.add(resultsPanel);
        JLabel[] performances = new JLabel[players.length];
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 20, 30, 20);
        c.gridx = 0;
        c.gridy = 0;
        JLabel resultsLabel = new JLabel(bundle.getString("Results"));
        resultsPanel.add(resultsLabel, c);
        c.insets = new Insets(0, 20, 15, 20);
        for (int i = 0; i < players.length; i++) {
            performances[i] = new JLabel();
            performances[i].setText(bundle.getString("User") + " " + players[i].getUserName() + " | " + bundle.getString("MathcesFound") + " " + players[i].getCollectedCards() + " | " + bundle.getString("StepsDone") + " " + players[i].getSteps());
            c.gridy++;
            if (!(players[i] instanceof Computer)) {
                if (players.length == 1) {
                    fmanager.soloFileWriter(players[i].getUserName(), players[i].getSteps(), players[i].getCollectedCards());
                } else {
                    fmanager.multiFileWriter(players[i].getUserName(), players[i].getSteps(), players[i].getCollectedCards(), winner);
                }
            }
            resultsPanel.add(performances[i], c);
        }
        c.gridy += 3;
        if (players.length != 1) {
            if (tie == 1) {
                resultsPanel.add(new JLabel(bundle.getString("Winner") + winner.getUserName()), c);
            } else {
                resultsPanel.add(new JLabel(bundle.getString("Tie")), c);
            }
        }
        JButton resultsOkayButton = new JButton(bundle.getString("OkayButton"));
        resultsOkayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultsFrame.dispose();
                GUI g = new GUI();
            }
        });
        c.insets = new Insets(10, 20, 0, 20);
        c.gridy += 4;
        resultsPanel.add(resultsOkayButton, c);
        resultsFrame.setVisible(true);
    }

    /**
     * Class responsible for the functions of the buttons in the start menu
     */
    private class Handler implements ActionListener {
        /**
         * Depending on what button is pressed a correct action is done.
         *
         * @param event a event that determines what button is pressed
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == menuButtons[0]) {
                getNumberOfPlayers();
            } else if (event.getSource() == menuButtons[1]) {
                try {
                    getScoreBoards().setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event.getSource() == menuButtons[2]) {
                getCredits().setVisible(true);
            } else if (event.getSource() == menuButtons[3]) {
                System.exit(0);
            }
        }
    }

    /**
     * Handles the menu in which the user chooses the number of players
     */
    private void getNumberOfPlayers() {
        JFrame numOfPlayersFrame = new JFrame(bundle.getString("FrameTitle"));
        numOfPlayersFrame.setSize(380, 75);
        numOfPlayersFrame.setLocationRelativeTo(null);
        numOfPlayersFrame.setResizable(false);
        numOfPlayersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Number of Players Panel.
        JPanel numOfPlayersPanel = new JPanel();
        //Number of Players Label.
        JLabel numOfPlayersLabel = new JLabel(bundle.getString("NumOfPlayersLabel"));
        numOfPlayersPanel.add(numOfPlayersLabel);
        numOfPlayersFrame.add(numOfPlayersPanel);
        //Number of Players Buttons.
        JButton[] numOfPlayersButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            numOfPlayersButtons[i] = new JButton("" + (i + 1) + "");
            numOfPlayersButtons[i].setActionCommand("" + (i + 1) + "");
            numOfPlayersPanel.add(numOfPlayersButtons[i]);
            numOfPlayersButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    switch (event.getActionCommand()) {
                        case "" + 1 + "":
                            numOfPlayers = 1;
                            numOfPlayersFrame.dispose();
                            getPlayers();
                            break;
                        case "" + 2 + "":
                            numOfPlayers = 2;
                            numOfPlayersFrame.dispose();
                            getPlayers();
                            break;
                        case "" + 3 + "":
                            numOfPlayers = 3;
                            numOfPlayersFrame.dispose();
                            getPlayers();
                            break;
                        default:
                            numOfPlayers = 4;
                            numOfPlayersFrame.dispose();
                            getPlayers();
                            break;
                    }
                }
            });
        }
        numOfPlayersFrame.setVisible(true);
    }

    /**
     * Handles the menu in which the user inputs the names of the players and chooses whether or not a player is a bot
     */
    private void getPlayers() {
        players = new Player[numOfPlayers];
        //Players Frame.
        JFrame playersFrame = new JFrame(bundle.getString("FrameTitle"));
        playersFrame.setSize(550, 110 + 45 * numOfPlayers);
        playersFrame.setLocationRelativeTo(null);
        playersFrame.setResizable(false);
        playersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Players Panel.
        JPanel playersBasePanel = new JPanel();
        playersBasePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 1;
        c.gridx = 0;
        //Players Label.
        JLabel playersLabel = new JLabel(bundle.getString("PlayersInfo"));
        playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playersBasePanel.add(playersLabel, c);
        //Players subPanels.
        JPanel[] subPanels = new JPanel[numOfPlayers];
        nameTextField = new JTextField[numOfPlayers];
        comCheckBox = new JCheckBox[numOfPlayers];
        memLevComboBox = new JComboBox[numOfPlayers];
        for (int j = 0; j < numOfPlayers; j++) {
            // Player's Name SubPanels
            subPanels[j] = new JPanel();
            subPanels[j].setLayout(new GridBagLayout());
            JLabel nameLabel = new JLabel(bundle.getString("Player") + (j + 1) + ":  ");
            nameTextField[j] = new JTextField(10);
            JLabel comLabel = new JLabel("   " + bundle.getString("PlayerQuestion"));
            comCheckBox[j] = new JCheckBox();
            JLabel memLevLabel = new JLabel("   " + bundle.getString("MemoryLevel") + "   ");
            String[] memOptions = {bundle.getString("Goldfish"), bundle.getString("Kangaroo"), bundle.getString("Elephant")};
            memLevComboBox[j] = new JComboBox<>(memOptions);
            memLevComboBox[j].setEnabled(false);
            final int fj = j; // J must become final in order for Listener to function properly.
            comCheckBox[j].addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (comCheckBox[fj].isSelected()) {
                        nameTextField[fj].setText("");
                        nameTextField[fj].setEditable(false);
                        memLevComboBox[fj].setEnabled(true);
                    }
                    if (!comCheckBox[fj].isSelected()) {
                        nameTextField[fj].setText("");
                        nameTextField[fj].setEditable(true);
                        memLevComboBox[fj].setEnabled(false);
                        memLevComboBox[fj].setSelectedIndex(0);
                    }
                }
            });
            subPanels[j].add(nameLabel);
            subPanels[j].add(nameTextField[j]);
            c.insets = new Insets(20, 0, 0, 50);
            if (j != 0) {
                subPanels[j].add(comLabel);
                subPanels[j].add(comCheckBox[j]);
                subPanels[j].add(memLevLabel);
                subPanels[j].add(memLevComboBox[j]);
                c.insets = new Insets(20, 0, 0, 0);
            } else {
                if (numOfPlayers != 1) {
                    subPanels[j].add(new JLabel("   " + bundle.getString("SoloPlayerEx")));
                }
            }
            c.gridy = j + 2;
            c.gridx = 0;
            playersBasePanel.add(subPanels[j], c);
        }
        // Okay Button Stuff.
        JButton okButton = new JButton(bundle.getString("OkayButton"));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        c.insets = new Insets(20, 0, 5, 0);
        c.gridy = -1;
        c.gridx = 0;
        playersBasePanel.add(okButton, c);
        playersFrame.add(playersBasePanel);
        playersFrame.setVisible(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playersUpdate();
                playersFrame.dispose();
                getGamemode();
            }
        });
    }

    /**
     * Handles the menu in which the user chooses the gamemode and runs the game
     */
    private void getGamemode() {
        //Gamemode Frame.
        JFrame gamemodeFrame = new JFrame(bundle.getString("FrameTitle"));
        gamemodeFrame.setSize(500, 75);
        gamemodeFrame.setLocationRelativeTo(null);
        gamemodeFrame.setResizable(false);
        gamemodeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Gamemode Panel.
        JPanel gamemodePanel = new JPanel();
        //Gamemode Label.
        JLabel gamemodeLabel = new JLabel(bundle.getString("Gamemode"));
        gamemodePanel.add(gamemodeLabel);
        gamemodeFrame.add(gamemodePanel);
        //Gamemode Buttons.
        gamemodeButtons[0] = new JButton(bundle.getString("BasicButton"));
        gamemodeButtons[0].setToolTipText(bundle.getString("BasicToolTip"));
        gamemodeButtons[1] = new JButton(bundle.getString("DoubleButton"));
        gamemodeButtons[1].setToolTipText(bundle.getString("DoubleToolTip"));
        gamemodeButtons[2] = new JButton(bundle.getString("TripleButton"));
        gamemodeButtons[2].setToolTipText(bundle.getString("TripleToolTip"));
        gamemodeButtons[3] = new JButton(bundle.getString("DuelButton"));
        gamemodeButtons[3].setToolTipText(bundle.getString("DuelToolTip"));
        gamemodeButtons[3].setEnabled(numOfPlayers == 2);
        for (int i = 0; i < 4; i++) {
            gamemodeButtons[i].setActionCommand("" + i + "");
            gamemodePanel.add(gamemodeButtons[i]);
            gamemodeFrame.setVisible(true);
            gamemodeButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    switch (event.getActionCommand()) {
                        case "" + 0 + "":
                            gamemode = 0;
                            break;
                        case "" + 1 + "":
                            gamemode = 1;
                            break;
                        case "" + 2 + "":
                            gamemode = 2;
                            break;
                        case "" + 3 + "":
                            gamemode = 3;
                            break;
                        default:
                            System.out.println("Unknown error. Program will now terminate.");
                            System.exit(0);
                    }
                    gamemodeFrame.dispose();
                    menuWindow.dispose();
                    if (gamemode != 3) {
                        Thread gameThread = new Thread() {
                            public void run() {
                                try {
                                    game = new Game(gamemode, loc, players);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        gameThread.start();
                    } else {
                        Thread duelThread = new Thread() {
                            public void run() {
                                try {
                                    duel = new Duel(bundle,players);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        duelThread.start();
                    }
                }
            });
        }
    }

    /**
     * Handles the Scoreboard menu
     *
     * @return a frame which contains the scoreboard
     * @throws IOException exception to handle file related errors
     */
    private JFrame getScoreBoards() throws IOException {
        // Scoreboard Frame stuff
        JFrame scoreboardFrame = new JFrame(bundle.getString("ScoreboardButton"));
        scoreboardFrame.setSize(600, 540);
        scoreboardFrame.setResizable(false);
        scoreboardFrame.setLocationRelativeTo(null);
        scoreboardFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // Tabs Stuff
        JTabbedPane tabs = new JTabbedPane();
        // Solo Tab Stuff
        JPanel soloPanel = new JPanel();
        soloPanel.setLayout(new BorderLayout());
        JTabbedPane soloTabs = new JTabbedPane();
        soloTabs.addTab(bundle.getString("BasicButton"), createPanel(1, 0));
        soloTabs.addTab(bundle.getString("DoubleButton"), createPanel(1, 1));
        soloTabs.addTab(bundle.getString("TripleButton"), createPanel(1, 2));
        soloPanel.add(soloTabs);
        // Multiplayer Tab Stuff
        JPanel multiPanel = new JPanel();
        multiPanel.setLayout(new BorderLayout());
        JTabbedPane multiTabs = new JTabbedPane();
        multiTabs.addTab(bundle.getString("BasicButton"), createPanel(2, 0));
        multiTabs.addTab(bundle.getString("DoubleButton"), createPanel(2, 1));
        multiTabs.addTab(bundle.getString("TripleButton"), createPanel(2, 2));
        multiTabs.addTab(bundle.getString("DuelButton"), createPanel(2, 3));
        multiPanel.add(multiTabs);
        tabs.addTab(bundle.getString("Solo"), soloPanel);
        tabs.addTab(bundle.getString("Multiplayer"), multiPanel);
        scoreboardFrame.add(tabs);
        return scoreboardFrame;
    }

    /**
     * Handles the creation of a panel that is a part of the scoreboard menu
     *
     * @param numOfPlayers integer value that determines the number of players and bots playing the game
     * @param mode integer value that determines the gamemode
     * @return a individual panel that shows the scoreboard date
     * @throws IOException exception to handle file related errors
     */
    private JPanel createPanel(int numOfPlayers, int mode) throws IOException {
        JPanel panel = new JPanel();
        if (numOfPlayers == 1) {
            panel.add(new JScrollPane(createSoloTable(numOfPlayers, mode)));
        } else {
            panel.add(new JScrollPane(createMultiTable(numOfPlayers, mode)));
        }
        return panel;
    }

    /**
     * Handles the creation of a Table containing the information about the solo scoreboard
     *
     * @param numOfPlayers integer value that determines the number of players and bots playing the game
     * @param mode integer value that determines the gamemode
     * @return an individual Jtable that contains information about the scores
     * @throws IOException exceptions to prevent file related errors
     */
    private JTable createSoloTable(int numOfPlayers, int mode) throws IOException {
        FileManager fm = new FileManager(numOfPlayers, mode);
        String[] cols = {bundle.getString("Name"), bundle.getString("Steps"), bundle.getString("CollectedCards")};
        String[][] rows;
        rows = fm.soloFileReader();
        return new JTable(rows, cols) {
            @Override
            public boolean isCellEditable(int row, int cols1) {
                return false;
            }
        };
    }

    /**
     * Handles the creation of a Table containing the information about the multiplayer scoreboard
     *
     * @param numOfPlayers integer value that determines the number of players and bots playing the game
     * @param mode integer value that determines the gamemode
     * @return an individual Jtable that contains information about the scores
     * @throws IOException exceptions to prevent file related errors
     */
    private JTable createMultiTable(int numOfPlayers, int mode) throws IOException {
        FileManager fm = new FileManager(numOfPlayers, mode);
        String[] cols = {bundle.getString("Name"), bundle.getString("Steps"), bundle.getString("CollectedCards"), bundle.getString("Wins")};
        String[][] rows;
        rows = fm.multiFileReader();
        return new JTable(rows, cols) {
            @Override
            public boolean isCellEditable(int row, int cols1) {
                return false;
            }
        };
    }

    /**
     * Handles the creation of the credits menu
     *
     * @return a Jframe which contains the credits of the program
     */
    private JFrame getCredits() {
        JFrame creditsFrame = new JFrame(bundle.getString("CreditsButton"));
        creditsFrame.setSize(300, 200);
        creditsFrame.setResizable(false);
        creditsFrame.setLocationRelativeTo(null);
        creditsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel creditsPanel = new JPanel(new GridBagLayout());
        creditsFrame.add(creditsPanel);
        JLabel creditsLabel = new JLabel(bundle.getString("Creators"), SwingConstants.CENTER);
        JLabel creditsLabel2 = new JLabel(bundle.getString("Dekas"), SwingConstants.CENTER);
        JLabel creditsLabel3 = new JLabel(bundle.getString("Nikas"), SwingConstants.CENTER);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 20, 50, 20);
        c.gridx = 0;
        c.gridy = 1;
        creditsPanel.add(creditsLabel, c);
        c.insets = new Insets(0, 20, 30, 20);
        c.gridx = 0;
        c.gridy = 2;
        creditsPanel.add(creditsLabel2, c);
        c.insets = new Insets(0, 20, 30, 20);
        c.gridx = 0;
        c.gridy = 3;
        creditsPanel.add(creditsLabel3, c);
        return creditsFrame;
    }
}