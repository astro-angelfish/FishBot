package moe.orangemc.fishbot.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class PluginConfiguration {
    private final MySQLConfiguration mySQLConfiguration;
    private final WhiteListConfiguration whiteListConfiguration;

    @SuppressWarnings("unchecked")
    private PluginConfiguration(Map<String, Object> configMap) {
        mySQLConfiguration = new MySQLConfiguration((Map<String, Object>) configMap.get("mysql"));
        whiteListConfiguration = new WhiteListConfiguration((List<Object>) configMap.get("whitelist"));
    }

    public MySQLConfiguration getMySQLConfiguration() {
        return mySQLConfiguration;
    }

    public WhiteListConfiguration getWhiteListConfiguration() {
        return whiteListConfiguration;
    }

    public static PluginConfiguration load(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.load(fis);
            System.out.println(map);
            return new PluginConfiguration(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
