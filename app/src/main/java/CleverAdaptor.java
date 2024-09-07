import ch.aplu.jcardgame.*;

import java.util.List;
import java.util.Random;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class CleverAdaptor implements PlayAdaptor {
    private Hand hand;
    private List<Card> cardsPlayed;
    private List<Card> publicCards;
    private CleverPlayer cleverPlayer;

    private Random random;

    public CleverAdaptor(Player cleverPlayer, List<Card> cardsPlayed, List<Card> publicCards,Random random){
        this.cleverPlayer = (CleverPlayer) cleverPlayer;
        this.cardsPlayed = cardsPlayed;
        this.publicCards = publicCards;
        this.random= random;
    }

    @Override
    public Card play() {
        return cleverPlayer.playCard(cardsPlayed, publicCards,random);
    }
}
