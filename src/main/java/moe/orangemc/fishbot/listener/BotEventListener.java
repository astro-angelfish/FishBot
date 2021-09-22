package moe.orangemc.fishbot.listener;

import moe.orangemc.fishbot.FishBotPlugin;
import moe.orangemc.fishbot.player.Player;
import moe.orangemc.fishbot.player.PlayerManager;
import moe.orangemc.fishbot.util.SuffixGenerator;
import moe.orangemc.fishbot.util.WhiteList;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.event.events.NudgeEvent;

public class BotEventListener implements ListenerHost {
    @EventHandler
    public void onFriendRequest(NewFriendRequestEvent event) {
        event.accept();
    }

    @EventHandler
    public void onGroupInvite(BotInvitedJoinGroupRequestEvent event) {
        event.accept();
        Group joinedGroup = event.getBot().getGroup(event.getGroupId());
        if (joinedGroup != null) {
            joinedGroup.sendMessage("FishBot joined" + SuffixGenerator.getSuffix());
        }
    }

    @EventHandler
    public void onMemberQuit(MemberLeaveEvent event) {
        if (!WhiteList.isGroupWhitelisted(event.getGroupId())) {
            return;
        }
        PlayerManager pm = FishBotPlugin.INSTANCE.getPlayerManager();
        Player player = pm.getPlayer(event.getUser().getId());
        if (player != null) {
            player.setBanned(true, "擅自退群");
        }
    }

    @EventHandler
    public void onNudge(NudgeEvent event) {
        if (event.getFrom() instanceof Bot) {
            return;
        }
        event.getFrom().nudge();
    }
}
