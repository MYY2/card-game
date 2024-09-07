
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
public abstract class Player{
    private Hand hand;
    public void setHand(Hand hand) {
        this.hand = hand;
    }
    public Hand getHand(){
        return hand;
    }

}
