package moe.orangemc.fishbot.configuration;

import java.util.ArrayList;
import java.util.List;

public class WhiteListConfiguration {
    private final List<Long> whitelistedGroups = new ArrayList<>();

    public WhiteListConfiguration(List<Object> whitelistedGroups) {
        for (Object o : whitelistedGroups) {
            this.whitelistedGroups.add(Long.valueOf(o.toString()));
        }
    }

    public List<Long> getWhitelistedGroups() {
        return whitelistedGroups;
    }
}
