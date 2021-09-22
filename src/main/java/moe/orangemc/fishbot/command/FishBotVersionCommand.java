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
                    "并感谢mirai开发者Karlatemp在开发过程中的帮助" + SuffixGenerator.getSuffix());
        } catch (Exception e) {
            sender.sendMessage("如果你看到了这条错误消息，说明这里的错误已经非常离谱了，请通知Lucky_fish0w0删库跑路吧;");
            e.printStackTrace();
        }
    }
}
