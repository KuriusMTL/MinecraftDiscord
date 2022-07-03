package me.kurius.minecraftdiscord;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandCrowdControl implements CommandExecutor {

    private MinecraftDiscord plugin;

    public CommandCrowdControl(MinecraftDiscord plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length != 1) {
            return false;
        }

        if (args[0].equals("start")) {
            plugin.startCrowdcontrol();
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("Crowdcontrol has now begun.");
                player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1.0f, 1.0f);
            }

        } else if (args[0].equals("stop")) {
            plugin.stopCrowdcontrol();
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("Crowdcontrol has now ended.");
            }

        } else {
            return false;
        }

        return true;
    }
}
