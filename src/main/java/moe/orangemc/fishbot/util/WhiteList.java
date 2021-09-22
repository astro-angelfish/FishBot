package moe.orangemc.fishbot.util;

import moe.orangemc.fishbot.configuration.WhiteListConfiguration;

import java.util.HashSet;
import java.util.Set;

public class WhiteList {
    private static final Set<Long> WHITE_LISTED_GROUP = new HashSet<>();

    public static void init(WhiteListConfiguration configuration) {
        WHITE_LISTED_GROUP.addAll(configuration.getWhitelistedGroups());
    }

    public static boolean isGroupWhitelisted(long group) {
        return WHITE_LISTED_GROUP.contains(group);
    }
}
