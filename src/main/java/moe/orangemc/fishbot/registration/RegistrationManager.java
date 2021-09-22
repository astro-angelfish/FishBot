package moe.orangemc.fishbot.registration;

import moe.orangemc.fishbot.FishBotPlugin;
import moe.orangemc.fishbot.crypto.PasswordEncryptor;
import moe.orangemc.fishbot.player.PlayerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegistrationManager {
    private final Map<Long, String> registrationMap = new HashMap<>();

    public void put(long qq, String name) {
        registrationMap.put(qq, name);
    }

    public boolean hasRequest(long qq) {
        return registrationMap.containsKey(qq);
    }

    public void processWithPassword(long qq, String password) {
        if (!hasRequest(qq)) {
            return;
        }
        String encrypted = PasswordEncryptor.encryptPassword(registrationMap.get(qq), password);
        PlayerManager playerManager = FishBotPlugin.INSTANCE.getPlayerManager();
        playerManager.registerPlayer(registrationMap.get(qq), encrypted, qq);
        registrationMap.remove(qq);
    }

    public void remove(long qq) {
        registrationMap.remove(qq);
    }

    public boolean hasRequest(String name) {
        AtomicBoolean found = new AtomicBoolean(false);
        registrationMap.forEach((id, checkName) -> {
            if (checkName.equalsIgnoreCase("name")) {
                found.set(true);
            }
        });
        return found.get();
    }
}
