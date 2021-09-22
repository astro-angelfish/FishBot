package moe.orangemc.fishbot.player;

public class Player {
    private final PlayerDatabase database;
    private final int id;
    private final String name;
    private final String password;
    private final long qq;
    private boolean banned;
    private String bannedReason;

    public Player(PlayerDatabase database, int id, String name, String password, long qq, boolean banned, String bannedReason) {
        this.database = database;
        this.id = id;
        this.name = name;
        this.password = password;
        this.qq = qq;
        this.banned = banned;
        this.bannedReason = bannedReason;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public long getQq() {
        return qq;
    }

    public boolean isBanned() {
        return banned;
    }

    public String getBannedReason() {
        return bannedReason;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
        bannedReason = "原因不明";
        database.updateBan(name, banned, "原因不明");
    }

    public void setBanned(boolean banned, String reason) {
        this.banned = banned;
        bannedReason = reason;
        database.updateBan(name, banned, reason);
    }
}
