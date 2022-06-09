package me.eduardanton.minecraftdiscord;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class MinecraftDiscord extends DiscordMinecraftPlugin {

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

        if (event.getAuthor().getId().equals(getDiscordBot().getSelfUser().getId())) {
            return;
        }

        if (!event.getTextChannel().getId().equals("921979218223562763")) {
            return;
        }

        for (Player p : getServer().getOnlinePlayers()) {
            p.sendMessage("[DISCORD] " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        }

    }

    @Override
    public void onMinecraftMessage(AsyncChatEvent event) {
        TextChannel channel = getDiscordBot().getTextChannelById("921979218223562763");
        channel.sendMessage("**[MINECRAFT] " + event.getPlayer().getName() + ":** " + ((TextComponent) event.message()).content()).queue();
    }

    @Override
    public void onMinecraftJoin(PlayerJoinEvent event) {

    }

    @Override
    public void onMinecraftDeath(PlayerDeathEvent event) {

    }

    @Override
    public void onMinecraftQuit(PlayerQuitEvent event) {

    }
}
