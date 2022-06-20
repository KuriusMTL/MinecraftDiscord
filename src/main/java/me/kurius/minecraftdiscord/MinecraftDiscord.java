package me.kurius.minecraftdiscord;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class MinecraftDiscord extends DiscordMinecraftPlugin {
    MinecraftEffects effects = new MinecraftEffects();

    String[] commands = {"help", "shop", "effect", "points"};
    Map<String, Integer> userPoints = new HashMap<>();

    long initialTime;

    @Override
    public void onPreload() {

    }

    @Override
    public void onLoad() {
        effects.Init();
    }

    @Override
    public void onUnload() {

    }

    @Override
    public void onDiscordMessage(MessageReceivedEvent event) {
        // Message sent by bot
        if (event.getAuthor().getId().equals(getDiscordBot().getSelfUser().getId()))
            return;

        // Event channel
        TextChannel channel = getDiscordBot().getTextChannelById("904198381734330378");

        // Message is in another channel
        if (!event.getTextChannel().equals(channel))
            return;

        // Message sent
        String message = event.getMessage().getContentDisplay();
        // First word of message is the command name
        String command = message.split(" ")[0];

        // If the message is not in the known commands we ignore it
        boolean isCmd = false;
        for (String cmd: commands) {
            if (command.equals(cmd)) {
                isCmd = true;
                break;
            }
        }
        if (!isCmd)
            return;

        String userID = event.getAuthor().getId();

        // Initialize user points if not there yet
        if (!userPoints.containsKey(userID)) {
            userPoints.put(userID, 0);
        }
        int points = userPoints.get(userID);

        // Effect command
        if (command.equals("effect")) {
            // Needs to specify "positive" or "negative"
            if (message.split(" ").length < 2)
                return;

            // Points spent are either the 3rd parameter or defaulted to current user balance
            int pointsSpent;
            try {
                if (message.split(" ").length < 3)
                    pointsSpent = points;
                else
                    pointsSpent = Integer.parseInt(message.split(" ")[2]);
            } catch (Exception e) {
                pointsSpent = points;
            }

            // Select and apply negative effect
            if (message.split(" ")[1].equals("negative")) {
                effects.negativeEffect(pointsSpent);
            }
        }

        // Points commands : Outputs current user balance
        if (message.equals("points")) {
            channel.sendMessage(String.format("You have %d points", userPoints.get(event.getAuthor().getId()))).queue();
            return;
        }

        // Shop command : Displays all available effects
        if (message.equals("shop")) {
            String msg = "```";
            msg += "SHOP:\nNegative Effects:\n";
            for (int val: effects.negativeEffects.keySet()) {
                msg += String.format("%s: %d points\n", effects.negativeEffects.get(val).getName(), val);
            }
            msg += "```";

            channel.sendMessage(msg).queue();
        }

    }

    @Override
    public void onMinecraftMessage(AsyncChatEvent event) {
//        TextChannel channel = getDiscordBot().getTextChannelById("921979218223562763");
//        channel.sendMessage("**[MINECRAFT] " + event.getPlayer().getName() + ":** " + ((TextComponent) event.message()).content()).queue();
    }



}
