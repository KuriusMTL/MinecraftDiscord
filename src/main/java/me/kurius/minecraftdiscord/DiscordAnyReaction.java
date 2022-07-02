package me.kurius.minecraftdiscord;

public class DiscordAnyReaction extends DiscordChallenge {
    public DiscordAnyReaction() {
        channel.sendMessage("React to this message!").queue((message) -> messageID = message.getId());
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
