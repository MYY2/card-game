import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class OptionsManagement {
    private OptionStrategy strategy;
    private ThirteenPermutations thirteenPermutations = new ThirteenPermutations();
    public int calculateScore(List<Card> hand, List<Card> publicCards){
        return strategy.optionScore(hand, publicCards,thirteenPermutations);
    }
    //reset the strategy
    public void setOptionStrategy(OptionStrategy strategy){
        this.strategy = strategy;
    }
    //initial the strategy
    public OptionsManagement(OptionStrategy strategy){
        this.strategy = strategy;
    }
}
