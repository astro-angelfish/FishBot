package moe.orangemc.fishbot.player;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerDatabase {
    private final Connection connection;

    private final Map<SqlStatement, PreparedStatement> preparedStatementMap = new HashMap<>();

    public PlayerDatabase(String host, int port, String database, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8", username, password);
            initStatements();
            initTables();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initStatements() {
        try {
            for (SqlStatement sqlStatement : SqlStatement.values()) {
                preparedStatementMap.put(sqlStatement, connection.prepareStatement(sqlStatement.getStringStatement()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initTables() {
        try {
            preparedStatementMap.get(SqlStatement.INITIALIZE_TABLE).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Player getPlayerByQQ(long qq) {
        try {
            PreparedStatement ps = preparedStatementMap.get(SqlStatement.SELECT_USER_BY_QQ);
            ps.setLong(1, qq);
            ResultSet rs = ps.executeQuery();
            return new Player(this, rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getLong("bound_qq"), rs.getBoolean("banned"), rs.getString("ban_reason"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Player getPlayerByName(String name) {
        try {
            PreparedStatement ps = preparedStatementMap.get(SqlStatement.SELECT_USER_BY_NAME);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return new Player(this, rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getLong("bound_qq"), rs.getBoolean("banned"), rs.getString("ban_reason"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateBan(String name, boolean banned, String reason) {
        try {
            PreparedStatement ps = preparedStatementMap.get(SqlStatement.BAN_USER_BY_NAME);
            ps.setBoolean(1, banned);
            ps.setString(2, reason);
            ps.setString(3, name);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertPlayer(String name, String password, long qq) {
        try {
            PreparedStatement ps = preparedStatementMap.get(SqlStatement.REGISTER_USER);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setLong(3, qq);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private enum SqlStatement {
        INITIALIZE_TABLE("create table if not exists players (" +
                "`id` int not null auto_increment," +
                "`name` varchar(16) not null," +
                "`password` varchar(1024) not null," +
                "`bound_qq` long not null," +
                "`banned` boolean default false," +
                "`ban_reason` varchar(512) default null," +
                "primary key(id)" +
                ");"),
        REGISTER_USER("insert into `players` (name,password,bound_qq) values (?,?,?);"),
        BAN_USER_BY_QQ("update players set banned=?, ban_reason=? where bound_qq=?;"),
        BAN_USER_BY_NAME("update players set banned=?, ban_reason=? where name=?;"),
        SELECT_USER_BY_QQ("select * from players where bound_qq=?;"),
        SELECT_USER_BY_NAME("select * from players where name=?;");

        private final String stringStatement;

        SqlStatement(String stringStatement) {
            this.stringStatement = stringStatement;
        }

        public String getStringStatement() {
            return stringStatement;
        }
    }
}
