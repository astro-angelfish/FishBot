package moe.orangemc.fishbot.command;

import moe.orangemc.fishbot.FishBotPlugin;
import moe.orangemc.fishbot.player.Player;
import moe.orangemc.fishbot.player.PlayerManager;
import moe.orangemc.fishbot.util.SuffixGenerator;
import moe.orangemc.fishbot.util.WhiteList;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.MemberCommandSender;
import net.mamoe.mirai.console.command.SimpleCommand;
import net.mamoe.mirai.console.command.descriptor.CommandArgumentContext;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class InfoCommand extends SimpleCommand {
    public InfoCommand() {
        super(FishBotPlugin.INSTANCE, "info", new String[]{}, "查看一名玩家的帐号", FishBotPlugin.INSTANCE.getPermissionMap().getOrCreate("command.info"), CommandArgumentContext.EMPTY);
    }

    @Handler
    public void onCommand(CommandSender sender, long qq) {
        try {
            if (sender instanceof MemberCommandSender) {
                MemberCommandSender mcs = (MemberCommandSender) sender;
                if (!WhiteList.isGroupWhitelisted(mcs.getGroup().getId())) {
                    sender.sendMessage("发生了不好的事情！请联系Lucky_fish0w0进行错误排查" + SuffixGenerator.getSuffix());
                    return;
                }
            }
            PlayerManager pm = FishBotPlugin.INSTANCE.getPlayerManager();
            Player player = pm.getPlayer(qq);
            if (player == null) {
                sender.sendMessage("玩家不存在" + SuffixGenerator.getSuffix());
                return;
            }

            MessageChainBuilder mcb = new MessageChainBuilder();
            mcb.add("玩家: ");
            mcb.add(player.getName());
            mcb.add("\n");
            mcb.add("绑定qq: ");
            mcb.add(String.valueOf(player.getQq()));
            mcb.add("\n");
            mcb.add("被封禁: ");
            if (player.isBanned()) {
                mcb.add("是");
                mcb.add("\n");
                mcb.add("封禁原因: ");
                mcb.add(player.getBannedReason());
            } else {
                mcb.add("否");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage("发生了不好的事情！请联系Lucky_fish0w0进行错误排查" + SuffixGenerator.getSuffix());
        }
    }

    @Handler
    public void onCommand(CommandSender sender, String name) {
        try {
            if (sender instanceof MemberCommandSender) {
                MemberCommandSender mcs = (MemberCommandSender) sender;
                if (!WhiteList.isGroupWhitelisted(mcs.getGroup().getId())) {
                    sender.sendMessage("发生了不好的事情！请联系Lucky_fish0w0进行错误排查" + SuffixGenerator.getSuffix());
                    return;
                }
            }
            PlayerManager pm = FishBotPlugin.INSTANCE.getPlayerManager();
            Player player = pm.getPlayer(name);
            if (player == null) {
                sender.sendMessage("玩家不存在" + SuffixGenerator.getSuffix());
                return;
            }

            MessageChainBuilder mcb = new MessageChainBuilder();
            mcb.add("玩家: ");
            mcb.add(player.getName());
            mcb.add("\n");
            mcb.add("绑定qq: ");
            mcb.add(String.valueOf(player.getQq()));
            mcb.add("\n");
            mcb.add("被封禁: ");
            if (player.isBanned()) {
                mcb.add("是");
                mcb.add("\n");
                mcb.add("封禁原因: ");
                mcb.add(player.getBannedReason());
            } else {
                mcb.add("否");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage("发生了不好的事情！请联系Lucky_fish0w0进行错误排查" + SuffixGenerator.getSuffix());
        }
    }

    @Handler
    public void onCommand(CommandSender sender, At at) {
        onCommand(sender, at.getTarget());
    }
}
