package moe.orangemc.fishbot.command;

import moe.orangemc.fishbot.FishBotPlugin;
import moe.orangemc.fishbot.registration.RegistrationManager;
import moe.orangemc.fishbot.util.SuffixGenerator;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.FriendCommandSender;
import net.mamoe.mirai.console.command.MemberCommandSender;
import net.mamoe.mirai.console.command.SimpleCommand;
import net.mamoe.mirai.console.command.descriptor.CommandArgumentContext;

public class RegisterStage2Command extends SimpleCommand {
    public RegisterStage2Command() {
        super(FishBotPlugin.INSTANCE, "confirm", new String[]{"creg"}, "确认注册帐号", FishBotPlugin.INSTANCE.getPermissionMap().getOrCreate("command.register.2"), CommandArgumentContext.EMPTY);
    }

    @Handler
    public void onCommand(CommandSender sender, String password) {
        try {
            if (!(sender instanceof FriendCommandSender)) {
                if (sender instanceof MemberCommandSender) {
                    sender.sendMessage("请假好友并私聊执行这条指令，并且请注意你的密码安全，请考虑撤回你的指令（虽然可能有防撤回玩家" + SuffixGenerator.getSuffix());
                } else {
                    sender.sendMessage("请加好友并私聊执行这条指令" + SuffixGenerator.getSuffix());
                }
                return;
            }
            FriendCommandSender fcs = (FriendCommandSender) sender;
            RegistrationManager registrationManager = FishBotPlugin.INSTANCE.getRegistrationManager();
            long qq = fcs.getUser().getId();

            if (!registrationManager.hasRequest(qq)) {
                sender.sendMessage("请在群里输入/register <id>以完成你的id注册" + SuffixGenerator.getSuffix());
                return;
            }

            registrationManager.processWithPassword(qq, password);
            sender.sendMessage("你的注册流程已经完成，享受在服务器的乐趣吧" + SuffixGenerator.getSuffix());
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage("发生了不好的事情！请联系Lucky_fish0w0进行错误排查" + SuffixGenerator.getSuffix());
        }
    }
}
