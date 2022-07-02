package me.kurius.minecraftdiscord;

import net.dv8tion.jda.api.entities.TextChannel;

public abstract class DiscordChallenge {
    protected static TextChannel channel;
    protected String answer = "";
    public String messageID = "";
    protected long startTime;
    protected float multiplier;

    public DiscordChallenge() {
        startTime = System.currentTimeMillis();
    }

    public long solveTime() {
        return (long) ((System.currentTimeMillis() - startTime) * multiplier);
    }

    public abstract boolean onReact(String reaction);
    public abstract boolean onMessage(String message);
}
