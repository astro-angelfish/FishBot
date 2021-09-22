package moe.orangemc.fishbot.command;

import moe.orangemc.fishbot.FishBotPlugin;
import moe.orangemc.fishbot.registration.RegistrationManager;
import moe.orangemc.fishbot.util.SuffixGenerator;
import moe.orangemc.fishbot.util.WhiteList;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.MemberCommandSender;
import net.mamoe.mirai.console.command.SimpleCommand;
import net.mamoe.mirai.console.command.descriptor.CommandArgumentContext;

public class CancelRegistrationCommand extends SimpleCommand {
    public CancelRegistrationCommand() {
        super(FishBotPlugin.INSTANCE, "cancel", new String[]{"c"}, "取消注册帐号", FishBotPlugin.INSTANCE.getPermissionMap().getOrCreate("command.register.cancel"), CommandArgumentContext.EMPTY);
    }

    @Handler
    public void onCommand(CommandSender sender) {
        try {
            if (sender instanceof MemberCommandSender) {
                MemberCommandSender mcs = (MemberCommandSender) sender;
                if (!WhiteList.isGroupWhitelisted(mcs.getGroup().getId())) {
                    sender.sendMessage("发生了不好的事情！请联系Lucky_fish0w0进行错误排查" + SuffixGenerator.getSuffix());
                    return;
                }
            }
            if (sender.getUser() == null) {
                sender.sendMessage("Only group members can execute this command");
                return;
            }

            long qq = sender.getUser().getId();
            RegistrationManager registrationManager = FishBotPlugin.INSTANCE.getRegistrationManager();

            if (!registrationManager.hasRequest(qq)) {
                sender.sendMessage("你没有等待的注册请求" + SuffixGenerator.getSuffix());
                return;
            }

            registrationManager.remove(qq);
            sender.sendMessage("你的注册请求已取消" + SuffixGenerator.getSuffix());
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage("发生了不好的事情！请联系Lucky_fish0w0进行错误排查" + SuffixGenerator.getSuffix());
        }
    }
}
