import ch.aplu.jcardgame.*;

import java.util.List;
import Card.*;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class TwoPrivateStrategy implements OptionStrategy{
    @Override
    public int optionScore(List<Card> hand, List<Card> publicCards,ThirteenPermutations thirteenPermutations) {
        Card privateCard1 = hand.get(0);
        Card privateCard2 = hand.get(1);
        Rank rank1 = (Rank) privateCard1.getRank();
        Suit suit1 = (Suit) privateCard1.getSuit();
        Rank rank2 = (Rank) privateCard2.getRank();
        Suit suit2 = (Suit) privateCard2.getSuit();
        //if option1 can be 13 return the score
        if (thirteenPermutations.isThirteenCards(privateCard1, privateCard2)) {
            return rank1.getScoreCardValue() * suit1.getMultiplicationFactor() + rank2.getScoreCardValue() * suit2.getMultiplicationFactor();
        }
        return 0;
    }

}
