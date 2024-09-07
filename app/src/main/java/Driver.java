import java.util.Properties;
/**
 * 2024s1-project2-mon-17-15-team-03
 * Jieyang Zhu
 * Yueyue Ma
 * RenJieYeo
 */
public class Driver {
    public static final String DEFAULT_PROPERTIES_PATH = "properties/game4.properties";

    public static void main(String[] args) {
        final Properties properties = PropertiesLoader.loadPropertiesFile(DEFAULT_PROPERTIES_PATH);
        String logResult = new LuckyThirdteen(properties).runApp();
        System.out.println("logResult = " + logResult);
    }

}
