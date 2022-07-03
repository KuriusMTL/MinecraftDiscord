package me.kurius.minecraftdiscord;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class DiscordAnyReaction extends DiscordChallenge {
    public DiscordAnyReaction() {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Answer to get points", null);
        eb.setColor(Color.red);
        eb.setDescription("React with an emoji to this message!");

        channel.sendMessageEmbeds(eb.build()).queue((message) -> messageID = message.getId());

        multiplier = 0.5f;
    }

    @Override
    public boolean onReact(String reaction) {
        return true;
    }

    @Override
    public boolean onMessage(String message) {
        return false;
    }
}
