package me.kurius.minecraftdiscord;

import java.util.Random;

public class DiscordMathQuestion extends DiscordChallenge {
    private int n1 = new Random().nextInt(100);
    private int n2 = new Random().nextInt(100);

    public DiscordMathQuestion() {
        answer = String.valueOf(n1 + n2);
        channel.sendMessage(String.format("What's %d + %d?", n1, n2)).queue((message) -> messageID = message.getId());
        multiplier = 1.5f;
    }

    @Override
    public boolean onReact(String reaction) {
        return false;
    }

    @Override
    public boolean onMessage(String message) {
        return message.equals(answer);
    }
}
