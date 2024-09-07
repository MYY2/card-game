import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.List;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class calculusFacade {
    //facade used to use method
    private IsItThirteen isItThirteen = new IsItThirteen();
    private CalculateEND calculateEnd = new CalculateEND();

    public boolean isThereThirteen(List<Card> hand, List<Card> publicCards){
        return isItThirteen.isThereThirteen(hand,publicCards);
    }

    public int calculateMaxScoreForThirteenPlayer(List<Card> hand,List<Card> publicCards) {
        return calculateEnd.calculateMaxScoreForThirteenPlayer(hand,publicCards);
    }

    public int[] calculateScoreEndOfRound(int[] scores, Hand[] hands , Hand playingArea) {
        return calculateEnd.calculateScoreEndOfRound(scores,hands,playingArea);
    }

    public void initScores(int[] scores) {
        calculateEnd.initScores(scores);
    }
}
