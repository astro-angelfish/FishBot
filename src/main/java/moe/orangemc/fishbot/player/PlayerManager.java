package moe.orangemc.fishbot.player;

public class PlayerManager {
    private PlayerDatabase playerDatabase;

    public PlayerManager(PlayerDatabase playerDatabase) {
        this.playerDatabase = playerDatabase;
    }

    public Player getPlayer(String name) {
        return playerDatabase.getPlayerByName(name);
    }

    public Player getPlayer(long qq) {
        return playerDatabase.getPlayerByQQ(qq);
    }

    public void registerPlayer(String name, String password, long qq) {
        playerDatabase.insertPlayer(name, password, qq);
    }
}
