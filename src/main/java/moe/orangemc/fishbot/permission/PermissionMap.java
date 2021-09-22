package moe.orangemc.fishbot.permission;

import moe.orangemc.fishbot.FishBotPlugin;
import net.mamoe.mirai.console.permission.Permission;
import net.mamoe.mirai.console.permission.PermissionRegistryConflictException;
import net.mamoe.mirai.console.permission.PermissionService;

import java.util.HashMap;
import java.util.Map;

public class PermissionMap {
    private final Map<String, Permission> map = new HashMap<>();

    public Permission getOrCreate(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        }

        try {
            Permission perm = PermissionService.getInstance().register(FishBotPlugin.INSTANCE.permissionId(name), "", FishBotPlugin.INSTANCE.getParentPermission());
            map.put(name, perm);
            return perm;
        } catch (PermissionRegistryConflictException e) {
            e.printStackTrace();
            return null;
        }
    }
}
