package me.kurius.minecraftdiscord;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandPointsMultiplier implements CommandExecutor {

    private MinecraftDiscord plugin;

    public CommandPointsMultiplier(MinecraftDiscord plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length != 1) {
            return false;
        }

        try {
            plugin.pointsMultiplier = Integer.parseInt(args[0]);
        } catch (Exception e) {
            return false;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.GREEN + String.format("Set points multiplier to %d.", plugin.pointsMultiplier));
        }
        return true;
    }
}
