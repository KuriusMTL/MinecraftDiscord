package me.kurius.minecraftdiscord;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.kurius.minecraftdiscord.plugin.DiscordCommand;
import me.kurius.minecraftdiscord.plugin.DiscordMinecraftPlugin;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class MinecraftDiscord extends DiscordMinecraftPlugin {
    Shop shop = new Shop();

    ArrayList<Player> activePlayers = new ArrayList<>();

    Map<String, Integer> userPoints = new HashMap<>();

    ArrayList<DiscordChallenge> challenges = new ArrayList<>();

    TextChannel focusChannel;

    Timer challengeTimer = new Timer();

    int pointsMultiplier = 1;

    @Override
    public void onPluginPreload() {

        // Initialize the Discord slash commands

        // Help command provides a basic help message to the user
        DiscordCommand helpCommand = new DiscordCommand("help", "Displays a help message.");
        registerDiscordCommand(helpCommand);

        // Points command returns the amount of points available for the user who invoked this command
        DiscordCommand pointsCommand = new DiscordCommand("points", "Get current points.");
        registerDiscordCommand(pointsCommand);

        // Buy command allows the user to spend points on game effects
        // It is required to specify the effect (either positive or negative)
        // and the amount of points to gamble. The greater the points, the greater the in game effect
        DiscordCommand buyCommand = new DiscordCommand("buy", "Displays shop.")
                .addOption("effect", OptionType.STRING, "Positive/Negative effect", true, new Command.Choice[]{
                        new Command.Choice("Positive", "Positive"),
                        new Command.Choice("Negative", "Negative")
                }).addOption("points", OptionType.INTEGER, "Points to spend (default: all)", false);
        registerDiscordCommand(buyCommand);

        // Initialize Minecraft commands
        this.getCommand("crowdcontrol").setExecutor(new CommandCrowdControl(this));
        this.getCommand("pointsmultiplier").setExecutor(new CommandPointsMultiplier(this));

        // Initialize the shop list
        shop.Init();
    }

    @Override
    public void onPluginLoad() {

        // Get the focus channel to send messages
        try {
            focusChannel = getDiscordBot().getGuildById(getConfig().getString("FOCUS_SERVER")).getTextChannelById(getConfig().getString("FOCUS_CHANNEL"));
            DiscordChallenge.channel = focusChannel;
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
            getLogger().severe(String.format("config.yml for %s appears to be corrupted. Please remove the file and restart the plugin.", getName()));
        }

    }

    @Override
    public void onPluginUnload() {
        // Nothing to implement
    }

    @Override
    public void onDiscordMessage(MessageReceivedEvent event) {
        if (!userPoints.containsKey(event.getAuthor().getId()))
            userPoints.put(event.getAuthor().getId(), 0);
        int currentPoints = userPoints.get(event.getAuthor().getId());

        for (DiscordChallenge challenge: challenges) {
            if (challenge.onMessage(event.getMessage().getContentDisplay())) {
                int points = (int) (1000 * challenge.multiplier / Math.sqrt(challenge.solveTime())) * pointsMultiplier;
                userPoints.put(event.getAuthor().getId(), currentPoints + points);
                challenges.remove(challenge);
                event.getChannel().sendMessage(String.format("%s got %d points", event.getAuthor().getName(), points));
                focusChannel.deleteMessageById(challenge.messageID).queue();
                break;
            }
        }
    }

    @Override
    public void onMinecraftMessage(AsyncChatEvent event) {
        // Nothing to implement
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

                if (activePlayers.size() == 0) {
                    event.reply("No players are connected to the server yet.").queue();
                    return;
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
                    int points = (int) (1000 * challenge.multiplier / Math.sqrt(challenge.solveTime())) * pointsMultiplier;
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
    public void onMinecraftJoin(PlayerJoinEvent event) {
        // Get the player name that just joined the game
        String playerName = event.getPlayer().getName();

        // Determine if the player is on the whitelist from the configs
        // If so, add them to the activePlayers list
        try {
            for (String name : (ArrayList<String>) getConfig().get("PLAYERS")) {
                if (name.equals(playerName)) {
                    activePlayers.add(event.getPlayer());
                    break;
                }
            }
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
            getLogger().severe(String.format("config.yml for %s appears to be corrupted. Please remove the file and restart the plugin.", getName()));
        }

    }

    @Override
    public void onMinecraftQuit(PlayerQuitEvent event) {
        // Get the player name that just joined the game
        String playerName = event.getPlayer().getName();

        // Determine if the player is on the activePlayers list
        // If so, remove them from that list
        for (Player player : activePlayers) {
            if (player.getName().equals(playerName)) {
                activePlayers.remove(player);
                break;
            }
        }
    }

    public void startCrowdcontrol() {
        challengeTimer.schedule(new TimerTask() {
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
        }, 1000, 10000);
    }

    public void stopCrowdcontrol() {
        challengeTimer.cancel();
    }

}

