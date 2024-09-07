import ch.aplu.jcardgame.*;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class BasicAdaptor implements PlayAdaptor {
    private BasicPlayer basicPlayer;
    public BasicAdaptor(Player basicPlayer){
        this.basicPlayer = (BasicPlayer) basicPlayer;
    }
    @Override
    public Card play() {
        return basicPlayer.playCard();
    }
}
