package moe.orangemc.fishbot.command;

import moe.orangemc.fishbot.FishBotPlugin;
import moe.orangemc.fishbot.player.PlayerManager;
import moe.orangemc.fishbot.registration.RegistrationManager;
import moe.orangemc.fishbot.util.SuffixGenerator;
import moe.orangemc.fishbot.util.WhiteList;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.MemberCommandSender;
import net.mamoe.mirai.console.command.SimpleCommand;
import net.mamoe.mirai.console.command.descriptor.CommandArgumentContext;

public class RegisterStage1Command extends SimpleCommand {
    public RegisterStage1Command() {
        super(FishBotPlugin.INSTANCE, "register", new String[]{"reg"}, "注册一个账户", FishBotPlugin.INSTANCE.getPermissionMap().getOrCreate("command.register.1"), CommandArgumentContext.EMPTY);
    }

    @Handler
    public void onCommand(CommandSender sender, String name) {
        try {
            if (!(sender instanceof MemberCommandSender)) {
                sender.sendMessage("请在群里执行/register指令" + SuffixGenerator.getSuffix());
                return;
            }
            MemberCommandSender mcs = (MemberCommandSender) sender;
            if (!WhiteList.isGroupWhitelisted(mcs.getGroup().getId())) {
                sender.sendMessage("发生了不好的事情！请联系Lucky_fish0w0进行错误处理" + SuffixGenerator.getSuffix());
                return;
            }

            long id = mcs.getUser().getId();

            RegistrationManager registrationManager = FishBotPlugin.INSTANCE.getRegistrationManager();
            PlayerManager playerManager = FishBotPlugin.INSTANCE.getPlayerManager();

            if (playerManager.getPlayer(id) != null) {
                sender.sendMessage("你已经拥有了一个帐号，若需要更换帐号，请联系派克或更换qq帐号" + SuffixGenerator.getSuffix());
                return;
            }
            if (playerManager.getPlayer(name) != null) {
                sender.sendMessage("这个用户名已经被注册，请换个名字" + SuffixGenerator.getSuffix());
                return;
            }
            if (registrationManager.hasRequest(id)) {
                sender.sendMessage("你已经有了一个注册请求, 若需要重新注册, 请输入/cancel" + SuffixGenerator.getSuffix());
                return;
            }
            if (registrationManager.hasRequest(name)) {
                sender.sendMessage("看来有人抢注了你想要的名字，请换个名字" + SuffixGenerator.getSuffix());
                return;
            }

            registrationManager.put(id, name);
            sender.sendMessage("注册第一步完成, 请加好友并私聊FishBot \"/confirm 密码\" 以完成注册" + SuffixGenerator.getSuffix());
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage("发生了不好的事情！请联系Lucky_fish0w0进行错误排查" + SuffixGenerator.getSuffix());
        }
    }
}
