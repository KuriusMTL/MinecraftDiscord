package me.kurius.minecraftdiscord;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandEffectsMultiplier implements CommandExecutor {

    private MinecraftDiscord plugin;

    public CommandEffectsMultiplier(MinecraftDiscord plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length != 2) {
            return false;
        }

        if (args[0].equals("time")) {

            try {
                plugin.timeMultiplier = Float.parseFloat(args[1]);
            } catch (Exception e) {
                commandSender.sendMessage(ChatColor.RED + String.format("Invalid time multiplier with value \"%s\".", args[1]));
                return false;
            }

            // Restart the timer
            plugin.stopCrowdcontrol();
            plugin.startCrowdcontrol();

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.GREEN + String.format("Set time multiplier to %s.", args[1]));
            }

        } else if (args[0].equals("points")) {

            try {
                plugin.pointsMultiplier = Float.parseFloat(args[1]);
            } catch (Exception e) {
                commandSender.sendMessage(ChatColor.RED + String.format("Invalid points multiplier with value \"%s\".", args[1]));
                return false;
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.GREEN + String.format("Set points multiplier to %s.", args[1]));
            }

        } else {
            return false;
        }

        return true;
    }
}
