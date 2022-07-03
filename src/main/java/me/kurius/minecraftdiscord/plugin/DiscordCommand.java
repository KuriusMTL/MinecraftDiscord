package me.kurius.minecraftdiscord.plugin;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.ArrayList;

public class DiscordCommand {

    private String name;
    private String description;
    private ArrayList<DiscordCommandOption> options = new ArrayList<DiscordCommandOption>();

    public DiscordCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public DiscordCommand addOption(String name, OptionType type, String description, boolean required) {
        options.add(new DiscordCommandOption(name, type, description, required));
        return this;
    }

    public DiscordCommand addOption(String name, OptionType type, String description, boolean required, Command.Choice[] choices) {
        options.add(new DiscordCommandOption(name, type, description, required, choices));
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<DiscordCommandOption> getOptions() {
        return options;
    }

}
