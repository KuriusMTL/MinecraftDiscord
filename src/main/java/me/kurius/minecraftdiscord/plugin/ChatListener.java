package me.kurius.minecraftdiscord.plugin;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

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

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.plugin.onMinecraftQuit(event);
    }

//    @Override
//    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//
//        return false;
//    }
}
