package me.kurius.minecraftdiscord;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.kurius.minecraftdiscord.plugin.DiscordCommand;
import me.kurius.minecraftdiscord.plugin.DiscordMinecraftPlugin;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class MinecraftDiscord extends DiscordMinecraftPlugin {
    Shop shop = new Shop();

    ArrayList<Player> activePlayers = new ArrayList<>();

    Map<String, Integer> userPoints = new HashMap<>();

    @Override
    public void onPreload() {
        DiscordCommand helpCommand = new DiscordCommand("help", "Displays help message.");
        registerDiscordCommand(helpCommand);

        DiscordCommand pointsCommand = new DiscordCommand("points", "Get current points.");
        registerDiscordCommand(pointsCommand);

        DiscordCommand buyCommand = new DiscordCommand("buy", "Displays shop.")
                .addOption("effect", OptionType.STRING, "Positive/Negative", true)
                .addOption("points", OptionType.INTEGER, "Points to spend (default: all)", false);
        registerDiscordCommand(buyCommand);

        shop.Init();
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }

    @Override
    public void onDiscordMessage(MessageReceivedEvent event) {
        // Discord message
    }

    @Override
    public void onDiscordCommand(SlashCommandInteractionEvent event) {
        if (!userPoints.containsKey(event.getUser().getId()))
            userPoints.put(event.getUser().getId(), 100);

        getLogger().info(event.getName());

        switch (event.getName()) {
            case "help":
                event.reply("help message").queue();
                break;
            case "points":
                int points = userPoints.get(event.getUser().getId());
                event.reply(String.format("You have %d points", points)).queue();
                break;
            case "buy":
                int pointsSpent;
                try {
                    pointsSpent = event.getOption("points").getAsInt();
                } catch (Exception e) {
                    pointsSpent = userPoints.get(event.getUser().getId());
                }

                boolean positive = event.getOption("effect").getAsString().equalsIgnoreCase("positive");
                int currentPoints = userPoints.get(event.getUser().getId());
                // Get random player in whitelist
                int randPlayer = new Random().nextInt(activePlayers.size());
                Player player = activePlayers.get(randPlayer);
                userPoints.put(event.getUser().getId(), currentPoints - shop.buyItem(pointsSpent, positive, player));
                break;
        }
    }

    @Override
    public void onMinecraftMessage(AsyncChatEvent event) {
//        TextChannel channel = getDiscordBot().getTextChannelById("921979218223562763");
//        channel.sendMessage("**[MINECRAFT] " + event.getPlayer().getName() + ":** " + ((TextComponent) event.message()).content()).queue();
    }
}
