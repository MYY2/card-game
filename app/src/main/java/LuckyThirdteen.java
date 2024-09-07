// LuckyThirteen.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import Card.*;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
@SuppressWarnings("serial")
public class LuckyThirdteen extends CardGame {
    private HashMap<Integer, Player> playerType;
    Font bigFont = new Font("Arial", Font.BOLD, 36);

    private int human;
    private final PlayerFactory playerFactory = PlayerFactory.getInstance();



    final String trumpImage[] = {"bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif"};

    private Properties properties;
//    private StringBuilder logResult = new StringBuilder();
    private List<List<String>> playerAutoMovements = new ArrayList<>();
    private Actor[] scoreActors = {null, null, null, null};
    //private CalculateEND score = new CalculateEND();

    public boolean rankGreater(Card card1, Card card2) {
        return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
    }

    private final String version = "1.0";
    public static final int nbPlayers = 4;
    private final int handWidth = 400;
    private final int trickWidth = 40;
    private static final int THIRTEEN_GOAL = 13;
    private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };
    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(575, 25),
            // new Location(650, 575)
            new Location(575, 575)
    };
    private final Location trickLocation = new Location(350, 350);
    private final Location textLocation = new Location(350, 450);
    private int thinkingTime = 2000;
    private int delayTime = 600;
    private Hand[] hands;
    public void setStatus(String string) {
        setStatusText(string);
    }

    private int[] scores = new int[nbPlayers];

    private int[] autoIndexHands = new int [nbPlayers];
    private boolean isAuto = false;
    private Hand playingArea;

    private final calculusFacade facade = new calculusFacade();
    private Log log = new Log();

    private Deal deal = new Deal();

    private Card selected;

    private void initGame() {
        hands = new Hand[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            hands[i] = new Hand(deck);
        }
        playingArea = new Hand(deck);
        deal.dealingOut(hands, nbPlayers,deck,playingArea,properties);
        playingArea.setView(this, new RowLayout(trickLocation, (playingArea.getNumberOfCards() + 2) * trickWidth));
        playingArea.draw();

        for (int i = 0; i < nbPlayers; i++) {
            hands[i].sort(Hand.SortType.SUITPRIORITY, false);
            //get all the initial card
        }
        playerType = new HashMap<>();
        human = -1;
        for(int i = 0; i < nbPlayers; i++){
            String playerString = "players."+ i;
                if (!properties.getProperty(playerString).isEmpty()) {
                    String type = properties.getProperty(playerString);
                    if (type.equals("human")) {
                        human = i;
                    }else{
                        playerType.put(i, playerFactory.createPlayer(type));
                    }

                }
        }
        //have human player
        if(human != -1){
            // Set up human player for interaction
            CardListener cardListener = new CardAdapter(){
                // Human Player plays card
                public void leftDoubleClicked(Card card) {
                    selected = card;
                    hands[human].setTouchEnabled(false);
                }
            };
            hands[human].addCardListener(cardListener);
        }
        // graphics
        RowLayout[] layouts = new RowLayout[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            // layouts[i].setStepDelay(10);
            hands[i].setView(this, layouts[i]);
            hands[i].setTargetArea(new TargetArea(trickLocation));
            hands[i].draw();
        }
    }

    private void playGame() {
        // End trump suit
        int winner = 0;
        int roundNumber = 1;
        for (int i = 0; i < nbPlayers; i++) updateScore(i);

        List<Card> cardsPlayed = new ArrayList<>();
        log.addRoundInfoToLog(roundNumber);

        int nextPlayer = 0;
        while (roundNumber <= 4) {
            selected = null;
            boolean finishedAuto = false;

            if (isAuto) {
                int nextPlayerAutoIndex = autoIndexHands[nextPlayer];
                List<String> nextPlayerMovement = playerAutoMovements.get(nextPlayer);
                String nextMovement = "";

                if (nextPlayerMovement.size() > nextPlayerAutoIndex) {
                    nextMovement = nextPlayerMovement.get(nextPlayerAutoIndex);
                    nextPlayerAutoIndex++;

                    autoIndexHands[nextPlayer] = nextPlayerAutoIndex;
                    Hand nextHand = hands[nextPlayer];

                    // Apply movement for player
                    selected = deal.applyAutoMovement(nextHand, nextMovement);
                    delay(delayTime);
                    if (selected != null) {
                        selected.removeFromHand(true);
                    } else {
                        selected = deal.getRandomCard(hands[nextPlayer],thinkingTime);
                        selected.removeFromHand(true);
                    }
                } else {
                    finishedAuto = true;
                }
            }
            PlayAdaptor playAdaptor;
            if (!isAuto || finishedAuto) {
                if (human == nextPlayer) {
                    hands[human].setTouchEnabled(true);
                    setStatus("Player" + human + "is playing. Please double click on a card to discard");
                    selected = null;
                    deal.dealACardToHand(hands[human]);
                    while (null == selected) delay(delayTime);
                    selected.removeFromHand(true);
                } else {
                    setStatusText("Player " + nextPlayer + " thinking...");
                    //get another card
                    deal.dealACardToHand(hands[nextPlayer]);
                    playerType.get(nextPlayer).setHand(hands[nextPlayer]);
                    playAdaptor = playerFactory.setAdaptor(playerType.get(nextPlayer),Deal.random,cardsPlayed, playingArea.getCardList());
                    delay(thinkingTime);
                    //choose a card to remove
                    if (playAdaptor != null) {
                        selected = playAdaptor.play();
                    }
                    if (selected != null) {
                        selected.removeFromHand(true);
                    }
                }
            }
            log.addCardPlayedToLog(nextPlayer, hands[nextPlayer].getCardList());
            //collect the cards Players remove
            if (selected != null) {
                cardsPlayed.add(selected);
                selected.setVerso(false);  // In case it is upside down
                delay(delayTime);
                // End Follow
            }
            nextPlayer = (nextPlayer + 1) % nbPlayers;
            if (nextPlayer == 0) {
                roundNumber++;
                log.addEndOfRoundToLog(scores);

                if (roundNumber <= 4) {
                    log.addRoundInfoToLog(roundNumber);
                }
            }
            if (roundNumber > 4) {
                scores = facade.calculateScoreEndOfRound(scores, hands, playingArea);
            }
            delay(delayTime);
        }
    }

    //auto movement read from properties
    private void setupPlayerAutoMovements() {
        String player0AutoMovement = properties.getProperty("players.0.cardsPlayed");
        String player1AutoMovement = properties.getProperty("players.1.cardsPlayed");
        String player2AutoMovement = properties.getProperty("players.2.cardsPlayed");
        String player3AutoMovement = properties.getProperty("players.3.cardsPlayed");

        String[] playerMovements = new String[] {"", "", "", ""};
        if (player0AutoMovement != null) {
            playerMovements[0] = player0AutoMovement;
        }

        if (player1AutoMovement != null) {
            playerMovements[1] = player1AutoMovement;
        }

        if (player2AutoMovement != null) {
            playerMovements[2] = player2AutoMovement;
        }

        if (player3AutoMovement != null) {
            playerMovements[3] = player3AutoMovement;
        }

        for (int i = 0; i < playerMovements.length; i++) {
            String movementString = playerMovements[i];
            if (movementString.equals("")) {
                playerAutoMovements.add(new ArrayList<>());
                continue;
            }
            List<String> movements = Arrays.asList(movementString.split(","));
            playerAutoMovements.add(movements);
        }
    }

    //run the game
    public String runApp() {
        setTitle("LuckyThirteen (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");
        initScore();
        setupPlayerAutoMovements();
        initGame();
        playGame();

        for (int i = 0; i < nbPlayers; i++) updateScore(i);
        int maxScore = 0;
        for (int i = 0; i < nbPlayers; i++) if (scores[i] > maxScore) maxScore = scores[i];
        List<Integer> winners = new ArrayList<Integer>();
        for (int i = 0; i < nbPlayers; i++) if (scores[i] == maxScore) winners.add(i);
        String winText;
        if (winners.size() == 1) {
            winText = "Game over. Winner is player: " +
                    winners.iterator().next();
        } else {
            winText = "Game Over. Drawn winners are players: " +
                    String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toList()));
        }
        addActor(new Actor("sprites/gameover.gif"), textLocation);
        setStatusText(winText);
        refresh();
        log.addEndOfGameToLog(winners,scores);
        return log.getLogResult().toString();
    }

    public LuckyThirdteen(Properties properties) {
        super(700, 700, 30);
        this.properties = properties;
        isAuto = Boolean.parseBoolean(properties.getProperty("isAuto"));
        thinkingTime = Integer.parseInt(properties.getProperty("thinkingTime", "200"));
        delayTime = Integer.parseInt(properties.getProperty("delayTime", "50"));
    }

    private void updateScore(int player) {
        removeActor(scoreActors[player]);
        int displayScore = Math.max(scores[player], 0);
        String text = "P" + player + "[" + String.valueOf(displayScore) + "]";
        scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
        addActor(scoreActors[player], scoreLocations[player]);
    }
    private void initScore() {
        //set the initial score to 0 for each player
        facade.initScores(scores);
        //add score symbol
        for (int i = 0; i < nbPlayers; i++) {
            String text = "[" + String.valueOf(scores[i]) + "]";
            scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
            addActor(scoreActors[i], scoreLocations[i]);
        }
    }


}
