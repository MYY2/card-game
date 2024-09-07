import ch.aplu.jcardgame.*;
import java.util.*;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class RandomAdaptor implements PlayAdaptor {
    private RandomPlayer randomPlayer;
    private Random random;
    public RandomAdaptor(Player randomPlayer, Random random){
        this.randomPlayer= (RandomPlayer) randomPlayer;
        this.random= random;
    }



    @Override
    public Card play() {
        return randomPlayer.playCard(random);
    }
}
