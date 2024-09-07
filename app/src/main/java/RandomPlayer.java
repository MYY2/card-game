import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class RandomPlayer extends Player{

    public Card playCard( Random random){
        //get a random number from the size of cards in hand
        int randomCard = random.nextInt(super.getHand().getCardList().size());
        return super.getHand().getCardList().get(randomCard);
    }

}
