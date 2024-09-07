import ch.aplu.jcardgame.*;
import Card.*;
import java.util.List;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class PrivatePublicStrategy implements OptionStrategy{
    @Override
    public int optionScore(List<Card> hand, List<Card> publicCards,ThirteenPermutations thirteenPermutations) {

        int moreSocre = 0;
        for(Card privateCard:hand){
            for (Card publicCard:publicCards){
                //if a combination of option2 can be 13
                if(thirteenPermutations.isThirteenCards(privateCard, publicCard)) {
                    Rank rank1 = (Rank) privateCard.getRank();
                    Suit suit1 = (Suit) privateCard.getSuit();
                    Rank rank2 = (Rank) publicCard.getRank();
                    Suit suit2 = (Suit) publicCard.getSuit();
                    //find the more score to return
                    if(moreSocre < rank1.getScoreCardValue() * suit1.getMultiplicationFactor() + rank2.getScoreCardValue() * Suit.PUBLIC_CARD_MULTIPLICATION_FACTOR){
                        moreSocre = rank1.getScoreCardValue() * suit1.getMultiplicationFactor() + rank2.getScoreCardValue() * Suit.PUBLIC_CARD_MULTIPLICATION_FACTOR;
                    }
                }
            }
        }
        return moreSocre;
    }
}
