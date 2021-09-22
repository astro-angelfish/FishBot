package moe.orangemc.fishbot;

import moe.orangemc.fishbot.command.*;
import moe.orangemc.fishbot.configuration.MySQLConfiguration;
import moe.orangemc.fishbot.configuration.PluginConfiguration;
import moe.orangemc.fishbot.player.PlayerDatabase;
import moe.orangemc.fishbot.listener.BotEventListener;
import moe.orangemc.fishbot.permission.PermissionMap;
import moe.orangemc.fishbot.registration.RegistrationManager;
import moe.orangemc.fishbot.player.PlayerManager;
import moe.orangemc.fishbot.util.WhiteList;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FishBotPlugin extends JavaPlugin {
    public static final FishBotPlugin INSTANCE = new FishBotPlugin();
    private final PermissionMap permissionMap;

    private RegistrationManager registrationManager;
    private PlayerManager playerManager;

    public FishBotPlugin() {
        super(new JvmPluginDescriptionBuilder("moe.orangemc.fishbot", "0.0.1").name("FishBot").author("Lucky_fish0w0").build());
        permissionMap = new PermissionMap();
    }

    @Override
    public void onEnable() {
        if (!getConfigFolder().exists()) {
            getConfigFolder().mkdirs();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (FileOutputStream fos = new FileOutputStream(configFile)) {
                try (InputStream is = getResourceAsStream("config.yml")) {
                    IOUtils.copy(is, fos);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        PluginConfiguration config = PluginConfiguration.load(configFile);
        if (config == null) {
            getLogger().warning("Failed to parse configuration");
            return;
        }
        registrationManager = new RegistrationManager();
        playerManager = new PlayerManager(initMySql(config.getMySQLConfiguration()));
        WhiteList.init(config.getWhiteListConfiguration());

        GlobalEventChannel.INSTANCE.registerListenerHost(new BotEventListener());

        initCommands();
    }

    @Override
    public void onDisable() {

    }

    private PlayerDatabase initMySql(MySQLConfiguration mySQLConfiguration) {
        return new PlayerDatabase(mySQLConfiguration.getHost(), mySQLConfiguration.getPort(), mySQLConfiguration.getDatabase(), mySQLConfiguration.getUsername(), mySQLConfiguration.getPassword());
    }

    private void initCommands() {
        CommandManager.INSTANCE.registerCommand(new FishBotVersionCommand(), false);
        CommandManager.INSTANCE.registerCommand(new CancelRegistrationCommand(), false);
        CommandManager.INSTANCE.registerCommand(new RegisterStage1Command(), false);
        CommandManager.INSTANCE.registerCommand(new RegisterStage2Command(), false);
        CommandManager.INSTANCE.registerCommand(new BanCommand(), false);
        CommandManager.INSTANCE.registerCommand(new PardonCommand(), false);
        CommandManager.INSTANCE.registerCommand(new InfoCommand(), false);
    }

    public PermissionMap getPermissionMap() {
        return permissionMap;
    }

    public RegistrationManager getRegistrationManager() {
        return registrationManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
