import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import Card.*;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */

public class CalculateEND{
    private IsItThirteen isItThirteen = new IsItThirteen();

    //calculate the max score of one player when more than one get 13
    public int calculateMaxScoreForThirteenPlayer(List<Card> hand,List<Card> publicCards) {
        OptionsManagement management = new OptionsManagement(new TwoPrivateStrategy());
        int maxScore = 0;
        //strategy for option1
        if (maxScore < management.calculateScore(hand,publicCards)) {
                maxScore = management.calculateScore(hand,publicCards);
        }
        //strategy for option2
        management.setOptionStrategy(new PrivatePublicStrategy());
        if (maxScore < management.calculateScore(hand,publicCards)) {
            maxScore = management.calculateScore(hand,publicCards);
        }
        //strategy for option3
        management.setOptionStrategy(new FourCardStrategy());
        if (maxScore < management.calculateScore(hand,publicCards)) {
            maxScore = management.calculateScore(hand,publicCards);
        }
        return maxScore;
    }

    //calculate one card value in hand
    public int getScorePrivateCard(Card card) {
        Rank rank = (Rank) card.getRank();
        Suit suit = (Suit) card.getSuit();

        return rank.getScoreCardValue() * suit.getMultiplicationFactor();
    }


    //calculate each player's score
    public int[] calculateScoreEndOfRound(int[] scores, Hand[] hands ,Hand playingArea) {
        List<Boolean> isThirteenChecks = Arrays.asList(false, false, false, false);
        //check who can get 13
        for (int i = 0; i < hands.length; i++) {
            isThirteenChecks.set(i, isItThirteen.isThereThirteen(hands[i].getCardList(),playingArea.getCardList()));
        }
        //get the index of player get 13
        List<Integer> indexesWithThirteen = new ArrayList<>();
        for (int i = 0; i < isThirteenChecks.size(); i++) {
            if (isThirteenChecks.get(i)) {
                indexesWithThirteen.add(i);
            }
        }
        long countTrue = indexesWithThirteen.size();
        Arrays.fill(scores, 0);
        //case1 only one player get 13
        if (countTrue == 1) {
            int winnerIndex = indexesWithThirteen.get(0);
            scores[winnerIndex] = 100;
            //case2 more than one player get 13
        } else if (countTrue > 1) {
            for (Integer thirteenIndex : indexesWithThirteen) {
                scores[thirteenIndex] = calculateMaxScoreForThirteenPlayer(hands[thirteenIndex].getCardList(),playingArea.getCardList());
            }

        } else {
            //nobody get 13
            for (int i = 0; i < scores.length; i++) {
                scores[i] = getScorePrivateCard(hands[i].getCardList().get(0)) +
                        getScorePrivateCard(hands[i].getCardList().get(1));
            }
        }
        return scores;
    }
    public void initScores(int[] scores) {
        Arrays.fill(scores, 0);
    }

}
