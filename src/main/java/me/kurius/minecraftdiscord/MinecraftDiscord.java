package me.kurius.minecraftdiscord;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.kurius.minecraftdiscord.plugin.DiscordCommand;
import me.kurius.minecraftdiscord.plugin.DiscordMinecraftPlugin;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class MinecraftDiscord extends DiscordMinecraftPlugin {
    Shop shop = new Shop();

    ArrayList<Player> activePlayers = new ArrayList<>();

    Map<String, Integer> userPoints = new HashMap<>();

    ArrayList<DiscordChallenge> challenges = new ArrayList<>();

    TextChannel focusChannel;

    @Override
    public void onPluginPreload() {
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
    public void onPluginLoad() {
        focusChannel = getDiscordBot().getGuildById("692133317520261120").getTextChannelById("904198381734330378");
        DiscordChallenge.channel = focusChannel;
        new BukkitRunnable() {
            @Override
            public void run() {
                int pick = new Random().nextInt(3);
                switch (pick) {
                    case 0:
                        challenges.add(new DiscordAnyReaction());
                        return;
                    case 1:
                        challenges.add(new DiscordSpecificReaction());
                        return;
                    case 2:
                        challenges.add(new DiscordMathQuestion());
                        return;
                }
            }
        }.runTaskTimer(this, 20, 200);
    }

    @Override
    public void onPluginUnload() {

    }

    @Override
    public void onDiscordMessage(MessageReceivedEvent event) {
        if (!userPoints.containsKey(event.getAuthor().getId()))
            userPoints.put(event.getAuthor().getId(), 0);
        int currentPoints = userPoints.get(event.getAuthor().getId());

        for (DiscordChallenge challenge: challenges) {
            if (challenge.onMessage(event.getMessage().getContentDisplay())) {
                int points = (int) (1000 / Math.sqrt(challenge.solveTime()));
                userPoints.put(event.getAuthor().getId(), currentPoints + points);
                challenges.remove(challenge);
                event.getChannel().sendMessage(String.format("%s got %d points", event.getAuthor().getName(), points));
                focusChannel.deleteMessageById(challenge.messageID).queue();
                break;
            }
        }
    }

    @Override
    public void onDiscordCommand(SlashCommandInteractionEvent event) {
        if (!userPoints.containsKey(event.getUser().getId()))
            userPoints.put(event.getUser().getId(), 0);

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

                if (pointsSpent <= 0) {
                    event.reply("You do not have any points").queue();
                    break;
                }

                boolean positive = event.getOption("effect").getAsString().equalsIgnoreCase("positive");
                int currentPoints = userPoints.get(event.getUser().getId());
                // Get random player in whitelist
                int randPlayer = new Random().nextInt(activePlayers.size());
                Player player = activePlayers.get(randPlayer);
                BoughtItem boughtItem = shop.buyItem(pointsSpent, positive, player);

                if (boughtItem.getPrice() == 0) {
                    event.reply(boughtItem.getMessage());
                    break;
                }


                userPoints.put(event.getUser().getId(), currentPoints - boughtItem.getPrice());
                int spentPoints = currentPoints - userPoints.get(event.getUser().getId());

                String message = (positive ? ChatColor.GREEN : ChatColor.RED) + event.getUser().getName() +
                        boughtItem.getMessage() + player.getName();

                for (Player p: getServer().getOnlinePlayers()) {
                    p.sendMessage(message);
                }

                event.reply(String.format("You spent %d points", spentPoints)).queue();

                break;
        }
    }

    @Override
    public void onDiscordReaction(MessageReactionAddEvent event) {
        if (!userPoints.containsKey(event.getUserId()))
            userPoints.put(event.getUserId(), 0);
        int currentPoints = userPoints.get(event.getUserId());

        for (DiscordChallenge challenge: challenges) {
            if (event.getMessageId().equals(challenge.messageID)) {
                if (challenge.onReact(event.getReaction().getReactionEmote().getEmoji())) {
                    int points = (int) challenge.solveTime() / 100;
                    userPoints.put(event.getUserId(), currentPoints + points);
                    focusChannel.deleteMessageById(challenge.messageID).queue();
                    challenges.remove(challenge);
                    event.getChannel().sendMessage(String.format("%s got %d points", event.getUser().getName(), points));
                }
                break;
            }
        }
    }

    @Override
    public void onMinecraftMessage(AsyncChatEvent event) {
        activePlayers.add(event.getPlayer());
//        TextChannel channel = getDiscordBot().getTextChannelById("921979218223562763");
//        channel.sendMessage("**[MINECRAFT] " + event.getPlayer().getName() + ":** " + ((TextComponent) event.message()).content()).queue();
    }
}
