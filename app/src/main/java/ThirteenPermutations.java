import ch.aplu.jcardgame.Card;

import java.util.List;
import Card.*;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class ThirteenPermutations {
    //pure fabrication to help with check if there is 13

    //check if these 2 card can get 13
    public boolean isThirteenCards(Card card1, Card card2) {
        Rank rank1 = (Rank) card1.getRank();
        Rank rank2 = (Rank) card2.getRank();
        return isThirteenFromPossibleValues(rank1.getPossibleSumValues(), rank2.getPossibleSumValues());
    }

    //check if these two list of value can add to 13
    private static boolean isThirteenFromPossibleValues(int[] possibleValues1, int[] possibleValues2) {
        for (int value1 : possibleValues1) {
            for (int value2 : possibleValues2) {
                if (value1 + value2 == 13) {
                    return true;
                }
            }
        }
        return false;
    }

    //check if these two list have a combination to get 13
    public boolean isThirteenMixedCards(List<Card> privateCards, List<Card> publicCards) {
        for (Card privateCard : privateCards) {
            for (Card publicCard : publicCards) {
                if (isThirteenCards(privateCard, publicCard)) {
                    return true;
                }
            }
        }

        return false;
    }


    //option 3 that is the four cards' values can get 13
    public boolean option3(Card privateCard1,Card privateCard2,Card publicCard1,Card publicCard2){
        Rank rank1 = (Rank) privateCard1.getRank();
        Rank rank2 = (Rank) privateCard2.getRank();
        Rank rank3 = (Rank) publicCard1.getRank();
        Rank rank4 = (Rank) publicCard2.getRank();
        int[] possibleValue1 = rank1.getPossibleSumValues();
        int[] possibleValue2 = rank2.getPossibleSumValues();
        int[] possibleValue3 = rank3.getPossibleSumValues();
        int[] possibleValue4 = rank4.getPossibleSumValues();
        for(int value1 : possibleValue1){
            for(int value2 : possibleValue2){
                for(int value3 : possibleValue3){
                    for(int value4 : possibleValue4){
                        if((value1 + value2 + value3 + value4) == 13){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //check if these 4 cards can be 13
    public boolean isThirteenOption3Cards(List<Card> privateCards, List<Card> publicCards){
        Card privateCard1 = privateCards.get(0);
        Card privateCard2 = privateCards.get(1);
        Card publicCard1 = publicCards.get(0);
        Card publicCard2 = publicCards.get(1);
        return option3(privateCard1, privateCard2, publicCard1, publicCard2);
    }
}
