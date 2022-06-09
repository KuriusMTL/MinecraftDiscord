package me.eduardanton.minecraftdiscord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.entity.Player;

public final class MinecraftDiscord extends DiscordMinecraftPlugin {

    /**
     * NOTE: DiscordMinecraftPlugin extends JavaPlugin. Hence, all JavaPlugin methods are available in this class.
     *
     * OTHER METHODS:
     *
     * @Override
     * public void onPreload(): Runs once the plugin is enabled, but before the Discord bot starts connecting.
     *
     * @Override
     * public void onLoaded(): Runs once the plugin is enabled, but after the Discord bot is connected.
     *
     * @Override
     * public void onUnloaded(): Runs once the plugin is disabled.
     *
     * @Override
     * public onDiscordMessage(MessageReceivedEvent event): Event for Discord messages.
     *
     * public void JDA getDiscordBot(): Returns the Discord bot instance.
     */

    @Override
    public void onPreload() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }

    @Override
    public void onDiscordMessage(MessageReceivedEvent event) {

        for (Player p : getServer().getOnlinePlayers()) {
            p.sendMessage(event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        }

    }
}
