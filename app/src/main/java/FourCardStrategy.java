import ch.aplu.jcardgame.*;
import Card.*;
import java.util.List;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class FourCardStrategy implements OptionStrategy{
    @Override
    public int optionScore(List<Card> hand, List<Card> publicCards,ThirteenPermutations thirteenPermutations) {
        Card privateCard1 = hand.get(0);
        Card privateCard2 = hand.get(1);
        Card publicCard1 = publicCards.get(0);
        Card publicCard2 = publicCards.get(1);
        Rank rank1 = (Rank) privateCard1.getRank();
        Suit suit1 = (Suit) privateCard1.getSuit();
        Rank rank2 = (Rank) privateCard2.getRank();
        Suit suit2 = (Suit) privateCard2.getSuit();
        Rank rank3 = (Rank) publicCard1.getRank();
        Suit suit3 = (Suit) publicCard1.getSuit();
        Rank rank4 = (Rank) publicCard2.getRank();
        Suit suit4 = (Suit) publicCard2.getSuit();
        //if option3 can be 13 return the score
        if(thirteenPermutations.option3(privateCard1, privateCard2, publicCard1, publicCard2)){
            return rank1.getScoreCardValue() * suit1.getMultiplicationFactor() + rank2.getScoreCardValue() * suit2.getMultiplicationFactor()+ rank3.getScoreCardValue() * Suit.PUBLIC_CARD_MULTIPLICATION_FACTOR + rank4.getScoreCardValue() * Suit.PUBLIC_CARD_MULTIPLICATION_FACTOR;
        }
        return 0;
    }
}
