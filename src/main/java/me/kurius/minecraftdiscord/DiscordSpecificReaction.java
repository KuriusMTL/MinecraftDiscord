package me.kurius.minecraftdiscord;

import java.util.Random;

public class DiscordSpecificReaction extends DiscordChallenge {
    private static String[] emojis = {"🍓", "🍑", "🧁", "🥞", "🍰", "🍪", "🍒", "🍩", "🌿", "🍉", "🍑", "🥀", "🍇", "🍶", "🍊", "🌸",
        "♡", "✨", "🌻", "🌌", "🍓", "🍒", "🌼", "🌊", "🦋", "🍭", "🍡", "🍯", "🌙", "☕", "🧸", "🍄", "🌺",
        "🧃", "🍨", "🍦", "🦄", "💖", "🌱", "🎀", "🌷", "🍂", "🧁", "💎", "🌹", "🎨", "💫", "🍩", "🍧"};

    private String emoji;

    public DiscordSpecificReaction() {
        emoji = emojis[new Random().nextInt(emojis.length)];
        channel.sendMessage(String.format("React to this message with %s!", emoji))
                .queue((message) -> messageID = message.getId());
        multiplier = 1;
    }

    @Override
    public boolean onReact(String reaction) {
        return reaction.equals(emoji);
    }

    @Override
    public boolean onMessage(String message) {
        return false;
    }
}
