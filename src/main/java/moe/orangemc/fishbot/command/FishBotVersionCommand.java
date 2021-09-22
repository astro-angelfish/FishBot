package moe.orangemc.fishbot.command;

import moe.orangemc.fishbot.FishBotPlugin;
import moe.orangemc.fishbot.util.SuffixGenerator;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.SimpleCommand;
import net.mamoe.mirai.console.command.descriptor.CommandArgumentContext;

public class FishBotVersionCommand extends SimpleCommand {
    public FishBotVersionCommand() {
        super(FishBotPlugin.INSTANCE, "fishbot", new String[]{"fb", "fish"}, "显示FishBot版本", FishBotPlugin.INSTANCE.getPermissionMap().getOrCreate("command.version"), CommandArgumentContext.EMPTY);
    }

    @Handler
    public void onCommand(CommandSender sender) {
        try {
            sender.sendMessage("FishBot v0.0.1 由Lucky_fish0w0开发\n" +
                    "基于mirai-console v2.7.0\n" +
                    "使用AGPL v3开源\n" +
                    "https://github.com/Lucky-fish/FishBot\n" +
                    "并感谢mirai开发者Karlatemp在开发过程中的帮助\n\n" +
                    "可用命令：\n" +
                    "/register <名字> - 注册一个帐号\n" +
                    "/confirm <密码> - 完成注册并确定密码\n" +
                    "/cancel - 取消注册\n" +
                    "/info <名字或qq> - 查询一个人的账户信息\n" +
                    "管理员命令：\n" +
                    "/ban <名字或qq> - 封禁一名玩家\n" +
                    "/pardon <名字或qq> - 解封一名玩家");
        } catch (Exception e) {
            sender.sendMessage("如果你看到了这条错误消息，说明这里的错误已经非常离谱了，请通知Lucky_fish0w0删库跑路吧;");
            e.printStackTrace();
        }
    }
}
