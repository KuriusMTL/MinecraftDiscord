package me.eduardanton.minecraftdiscord;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public abstract class DiscordMinecraftPlugin extends JavaPlugin implements Listener {

    private static DiscordBot bot;

    // Abstract methods to implement
    public abstract void onPreload();
    public abstract void onLoad();
    public abstract void onUnload();
    public abstract void onDiscordMessage(MessageReceivedEvent event);
    public abstract void onMinecraftMessage(AsyncChatEvent event);
    public abstract void onMinecraftJoin(PlayerJoinEvent event);
    public abstract void onMinecraftDeath(PlayerDeathEvent event);
    public abstract void onMinecraftQuit(PlayerQuitEvent event);

    @Override
    public void onEnable() {

        // Call abstract method
        onPreload();

        try {
            // Get the discord credentials from the config file.
            getLogger().info("Connecting to Discord services.");

            String token = getPluginConfig().getString("DISCORD_TOKEN");

            if (token == null) {
                // If unable to find the DISCORD_TOKEN in the config file then unload the plugin.
                getLogger().severe("Could not find the DISCORD_TOKEN in config.yml.");
                this.setEnabled(false);
                return;
            }

            // Initialize the discord bot
            bot = new DiscordBot(token);

            // Handle the discord message event
            getDiscordBot().addEventListener(new ListenerAdapter() {
                @Override
                public void onMessageReceived(MessageReceivedEvent event) {
                    // Call abstract method
                    onDiscordMessage(event);
                }
            });

            getLogger().info("Discord bot is online.");
        } catch (Exception e) {

            // Discord login error
            getLogger().severe("Could not connect to the Discord bot.");
            this.setEnabled(false);
            return;
        }

        // Register the Minecraft chat event
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        // Call abstract method
        onLoad();

    }

    @Override
    public void onDisable() {
        getLogger().info("Wumpus from Discord will miss you. Bye.");
        onUnload();
    }

    /**
     * Get the current Discord bot instance.
     * @return JDA : Discord bot instance
     */
    public JDA getDiscordBot() {
        return bot.getJDA();
    }

    /**
     * Similar to the getConfig method from JavaPlugin, getPluginConfig creates in addition a configuration template if no initial config file was found.
     * @return FileConfiguration : the plugin's configurations
     */
    public FileConfiguration getPluginConfig() {
        // Get the config file from plugins
        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            // If the config file does not exist then create a new one.
            // Make sure to set a template variable for DISCORD_TOKEN.
            try {
                getConfig().set("DISCORD_TOKEN", "insert token here");
                getConfig().save(configFile);
                getLogger().info(String.format("Created a config.yml in %s for %s.", getDataFolder(), getName()));
                getLogger().info("Make sure to configure the DISCORD_TOKEN in the config.yml and then restart the server to apply the changes.");
            } catch (IOException e) {
                getLogger().severe(String.format("Could not create a config.yml in %s for %s. Verify the application's IO permissions.", getDataFolder(), getName()));
            }
        }

        return getConfig();
    }
}
