package moe.orangemc.fishbot.configuration;

import java.util.Map;

public class MySQLConfiguration {
    private String host;
    private int port;
    private String username;
    private String password;
    private String database;

    public MySQLConfiguration(Map<String, Object> configMap) {
        host = configMap.get("host").toString();
        port = (int) configMap.get("port");
        username = configMap.get("username").toString();
        password = configMap.get("password").toString();
        database = configMap.get("database").toString();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }
}
