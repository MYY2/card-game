import ch.aplu.jcardgame.*;

import java.util.*;
import java.util.List;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */

public class  PlayerFactory {
    private static PlayerFactory factory = null;
    private PlayAdaptor adaptor;
    public static PlayerFactory getInstance(){
        if(factory == null){
            factory = new PlayerFactory();
        }
        return factory;
    }
    public Player createPlayer(String type){
        if(type.equals("random")){
            //randomAdaptor=new RandomAdaptor();
            return new RandomPlayer();
        }
        if(type.equals("basic")){
            return new BasicPlayer();
        }
        if(type.equals("clever")){
            return new CleverPlayer();
        }
        return new RandomPlayer();
    }
    public PlayAdaptor setAdaptor(Player player,Random random,List<Card> allCardPlayed, List<Card> publicCard){
        if (player instanceof BasicPlayer) {
            return new BasicAdaptor(player);
        }
        if (player instanceof RandomPlayer) {
            return new RandomAdaptor(player, random);
        }
        if (player instanceof CleverPlayer) {
            return new CleverAdaptor(player, allCardPlayed, publicCard,random);
        }
        return null;
    }






}
