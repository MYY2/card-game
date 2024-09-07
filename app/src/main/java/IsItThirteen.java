import ch.aplu.jcardgame.Card;

import java.util.List;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class IsItThirteen {
    private ThirteenPermutations thirteenPermutations = new ThirteenPermutations();
    //to find is there is an option can get to 13
    public boolean isThereThirteen(List<Card> hand, List<Card> publicCards) {
        // option1
        boolean isThirteenPrivate = thirteenPermutations.isThirteenCards(hand.get(0), hand.get(1));
        //option2
        boolean isThirteenMixed = thirteenPermutations.isThirteenMixedCards(hand, publicCards);
        // option3
        boolean isThirteenOption3 = thirteenPermutations.isThirteenOption3Cards(hand, publicCards);
        return isThirteenMixed || isThirteenPrivate || isThirteenOption3;
    }
}
