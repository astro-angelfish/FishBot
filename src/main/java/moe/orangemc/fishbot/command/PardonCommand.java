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
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class PardonCommand extends SimpleCommand {
    public PardonCommand() {
        super(FishBotPlugin.INSTANCE, "pardon", new String[]{}, "封禁帐号", FishBotPlugin.INSTANCE.getPermissionMap().getOrCreate("command.pardon"), CommandArgumentContext.EMPTY);
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
            player.setBanned(false);

            MessageChainBuilder mcb = new MessageChainBuilder();
            mcb.add("已解封");
            mcb.add(new At(player.getQq()));
            mcb.add(" -- ");
            mcb.add(player.getName());
            mcb.add(SuffixGenerator.getSuffix());
            sender.sendMessage(mcb.build());
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
            player.setBanned(false);

            MessageChainBuilder mcb = new MessageChainBuilder();
            mcb.add("已解封");
            mcb.add(new At(player.getQq()));
            mcb.add(" -- ");
            mcb.add(player.getName());
            mcb.add(SuffixGenerator.getSuffix());
            sender.sendMessage(mcb.build());
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
