package me.kurius.minecraftdiscord;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Random;

public class DiscordSpecificReaction extends DiscordChallenge {
    private static String[] emojis = {"🍓", "🍑", "🧁", "🥞", "🍰", "🍪", "🍒", "🍩", "🌿", "🍉", "🍑", "🥀", "🍇", "🍶", "🍊", "🌸",
        "♡", "✨", "🌻", "🌌", "🍓", "🍒", "🌼", "🌊", "🦋", "🍭", "🍡", "🍯", "🌙", "☕", "🧸", "🍄", "🌺",
        "🧃", "🍨", "🍦", "🦄", "💖", "🌱", "🎀", "🌷", "🍂", "🧁", "💎", "🌹", "🎨", "💫", "🍩", "🍧"};

    private String emoji;

    public DiscordSpecificReaction() {
        emoji = emojis[new Random().nextInt(emojis.length)];

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Answer to get points", null);
        eb.setColor(Color.blue);
        eb.setDescription(String.format("React to this message with %s!", emoji));

        channel.sendMessageEmbeds(eb.build()).queue((message) -> messageID = message.getId());
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
