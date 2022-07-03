package me.kurius.minecraftdiscord.plugin;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private JDA bot;

    /**
     * Create a new Discord bot instance.
     * @param TOKEN : Secret token of the bot. It can be found or generated on the official Discord developer panel.
     * @throws Exception
     */
    public DiscordBot(@NotNull String TOKEN) throws Exception {

        bot = JDABuilder.createDefault(TOKEN)
                .setActivity(Activity.playing("Minecraft")) // The bot will be playing "Minecraft" as its status
                .build();

        bot.awaitReady();

    }

    public JDA getJDA() {
        return bot;
    }

}
