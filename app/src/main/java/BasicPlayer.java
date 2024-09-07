import ch.aplu.jcardgame.*;

import java.util.ArrayList;
import Card.*;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class BasicPlayer extends Player{

    public Card playCard(){
        ArrayList<Card> handCards = super.getHand().getCardList();
        int minimum = 9999;
        Card fold = null;
        //loop the cards in hand
        for(Card card:handCards){
            Rank rank = (Rank) card.getRank();
            Suit suit = (Suit) card.getSuit();
            int value = rank.getScoreCardValue()*suit.getMultiplicationFactor();
            //find the smaller value card
            if(value < minimum){
                fold = card;
                minimum = value;
            }
        }
        return fold;
    }
}
