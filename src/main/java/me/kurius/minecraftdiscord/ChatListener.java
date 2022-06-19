package me.kurius.minecraftdiscord;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {

    private DiscordMinecraftPlugin plugin;

    public ChatListener(DiscordMinecraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        this.plugin.onMinecraftMessage(event);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.plugin.onMinecraftJoin(event);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        this.plugin.onMinecraftDeath(event);
    }

    @EventHandler void onPlayerQuit(PlayerQuitEvent event) {
        this.plugin.onMinecraftQuit(event);
    }

}
