import ch.aplu.jcardgame.*;

import java.util.*;
import java.util.List;
import Card.*;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class CleverPlayer extends Player{

    private final calculusFacade facade = new calculusFacade();
    public Card playCard(List<Card> cardsPlayed, List<Card> publicCards,Random random){
        //get a map to know each card wants what card can get 13
        Map<Rank, Rank[]> want = new HashMap<>();
        Rank[] ranksJQK = {Rank.JACK, Rank.QUEEN, Rank.KING};
        Rank[] ranks123 = new Rank[]{Rank.ACE, Rank.TWO, Rank.THREE};
        want.put(Rank.ACE, ranksJQK);
        want.put(Rank.TWO, ranksJQK);
        want.put(Rank.THREE, new Rank[]{Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING});
        want.put(Rank.FOUR, new Rank[]{Rank.NINE});
        want.put(Rank.FIVE, new Rank[]{Rank.EIGHT});
        want.put(Rank.SIX, new Rank[]{Rank.SEVEN});
        want.put(Rank.SEVEN, new Rank[]{Rank.SIX});
        want.put(Rank.EIGHT, new Rank[]{Rank.FIVE});
        want.put(Rank.NINE, new Rank[]{Rank.FOUR});
        want.put(Rank.TEN, new Rank[]{Rank.THREE});
        want.put(Rank.JACK, ranks123);
        want.put(Rank.QUEEN, ranks123);
        want.put(Rank.KING, ranks123);

        //separate three cards in hand to 3 combinations of 2 cards
        Map<Card,List<Card>> threecombinations = new HashMap<>();
        List<Card> TwoThree = new ArrayList<>();
        TwoThree.add(super.getHand().get(1));
        TwoThree.add(super.getHand().get(2));
        List<Card> OneThree = new ArrayList<>();
        OneThree.add(super.getHand().get(0));
        OneThree.add(super.getHand().get(2));
        List<Card> OneTwo = new ArrayList<>();
        OneTwo.add(super.getHand().get(0));
        OneTwo.add(super.getHand().get(1));
        threecombinations.put(super.getHand().get(0),TwoThree);
        threecombinations.put(super.getHand().get(1),OneThree);
        threecombinations.put(super.getHand().get(2),OneTwo);

        //get a map to know how many each card has been played
        Map<Rank,Integer> PlayedCardsFrequency = new HashMap<>();
        for(Card card:cardsPlayed){
            Rank rank = (Rank) card.getRank();
            PlayedCardsFrequency.put(rank,PlayedCardsFrequency.getOrDefault(rank,0)+1);
        }
        for(Card card:publicCards){
            Rank rank = (Rank) card.getRank();
            PlayedCardsFrequency.put(rank,PlayedCardsFrequency.getOrDefault(rank,0)+1);
        }
        for(Card card:super.getHand().getCardList()){
            Rank rank = (Rank) card.getRank();
            PlayedCardsFrequency.put(rank,PlayedCardsFrequency.getOrDefault(rank,0)+1);
        }
        int value = 9999;
        Card fold = null;
        int want_left = 9999;
        //when can get 13,choose the lowest score to fold
        int[] canbe13=new int[3];
        //to know which combinations of 2 cards can get 13 with public cards
        for(int i =0;i<3;i++){
            if(facade.isThereThirteen(threecombinations.get(super.getHand().get(i)),publicCards)){
                canbe13[i]=1;
            }
        }
        //when there is one or more combinations can get 13
        if(canbe13[0]==1||canbe13[1]==1||canbe13[2]==1){
            int highscore = -1;
            for(int i =0;i<3;i++){
                //when the combination can get 13
                if(canbe13[i]==1) {
                    //when the max score of this combination bigger than the highest score in other combination can get 13
                    if(facade.calculateMaxScoreForThirteenPlayer(threecombinations.get(super.getHand().get(i)),publicCards)>highscore){
                        //score of this combination is the highest score now
                        highscore = facade.calculateMaxScoreForThirteenPlayer(threecombinations.get(super.getHand().get(i)),publicCards);
                        //the card out of the 2 card is the card to fold
                        fold = super.getHand().get(i);
                        //when same score with the highest score
                    }else if(facade.calculateMaxScoreForThirteenPlayer(threecombinations.get(super.getHand().get(i)),publicCards)==highscore){
                        Rank rank = (Rank) super.getHand().get(i).getRank();
                        Suit suit = (Suit) super.getHand().get(i).getSuit();
                        Rank foldrank = (Rank) fold.getRank();
                        Suit foldsuit = (Suit) fold.getSuit();
                        //when the single card's score is smaller than the card get to fold before
                        if(rank.getScoreCardValue()*suit.getMultiplicationFactor()<foldrank.getScoreCardValue()*foldsuit.getMultiplicationFactor()){
                            //fold the low card value card
                            fold = super.getHand().get(i);
                        }
                    }
                }
            }
            return fold;
        }else{
            // when no combination can get 13
            //loop the cards in hand
            for(Card handcard: super.getHand().getCardList()){
                for(Card handcard1:super.getHand().getCardList()){
                        Rank rank = (Rank) handcard.getRank();
                        Suit suit = (Suit) handcard.getSuit();
                        Rank rank1 = (Rank) handcard1.getRank();
                        Suit suit1 = (Suit) handcard1.getSuit();
                        //make sure not compare with itself and the cards have same rank or have same cards they want
                        if((rank == rank1 && suit!=suit1)|| (Arrays.equals(want.get(rank),want.get(rank1)) && rank != rank1)){
                            //find the smaller value card and compare with the value of the card found before
                            if(rank.getScoreCardValue()*suit.getMultiplicationFactor()<rank1.getScoreCardValue()*suit1.getMultiplicationFactor()){
                                if(rank.getScoreCardValue()*suit.getMultiplicationFactor()<value){
                                    //new card replace to be the fold card and value
                                    value = rank.getScoreCardValue()*suit.getMultiplicationFactor();
                                    fold = handcard;
                                }
                            }else if(rank.getScoreCardValue()*suit.getMultiplicationFactor()>=rank1.getScoreCardValue()*suit1.getMultiplicationFactor()){
                                if(rank1.getScoreCardValue()*suit1.getMultiplicationFactor()<value){
                                    //new card replace to be the fold card and value
                                    value = rank1.getScoreCardValue()*suit1.getMultiplicationFactor();
                                    fold = handcard1;
                                }
                            }
                        }

                }
            }
            //compare the cards in hand with public cards
            for(Card handcard: super.getHand().getCardList()){
                for(Card publiccard:publicCards){
                    Rank rank = (Rank) handcard.getRank();
                    Suit suit = (Suit) handcard.getSuit();
                    Rank rank1 = (Rank) publiccard.getRank();
                    Suit suit1 = (Suit) publiccard.getSuit();
                    //there is same rank card
                    if(rank == rank1 || Arrays.equals(want.get(rank),want.get(rank1))){
                        //compare with the value of the card found before
                        if(rank.getScoreCardValue()*suit.getMultiplicationFactor()<value){
                            //new card replace to be the fold card and value
                            value = rank.getScoreCardValue()*suit.getMultiplicationFactor();
                            fold = handcard;
                        }
                    }
                }
            }
            //if found a card to fold upon
            if(fold !=null){
                return fold;
            }
            //last loop the cards in hand
            for(Card handcard: super.getHand().getCardList()){
                Rank rank = (Rank) handcard.getRank();
                Rank[] wantlist = want.get(rank);
                int leftttt = 0;
                //find the left cards of each card in hand want that can make them to 13
                for(Rank wantrank:wantlist){
                    leftttt+= (4-PlayedCardsFrequency.getOrDefault(wantrank,0));
                }
                //find the least remain want card
                if(leftttt<want_left){
                    want_left = leftttt;
                    fold = handcard;
                }
            }
            //return the card has the lowest possible in after round to get a card can make it used to get 13
            if(fold !=null) {
                return fold;
            }else{
                //So far cannot find a card to fold,will not happen but to be on the safe side random fold a card
                int randomCard = random.nextInt(super.getHand().getCardList().size());
                return super.getHand().getCardList().get(randomCard);
            }

        }
    }
}
