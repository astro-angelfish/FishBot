package moe.orangemc.fishbot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuffixGenerator {
    private static final List<String> suffixes = new ArrayList<>();
    private static final Random generator = new Random();

    public static String getSuffix() {
        if (generator.nextDouble() < 0.05) {
            return suffixes.get(generator.nextInt(suffixes.size()));
        }
        return "";
    }

    static {
        suffixes.add("喵");
        suffixes.add("0w0");
        suffixes.add("~");
        suffixes.add("=w=");
        suffixes.add("啦~");
        suffixes.add("ovo");
        suffixes.add("www");
    }
}
