package me.kurius.minecraftdiscord.plugin;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.ArrayList;

public class DiscordCommandOption {

    private String name;
    private OptionType type;
    private String description;
    private boolean required;
    private Command.Choice[] choices = new Command.Choice[0];

    public DiscordCommandOption(String name, OptionType type, String description, boolean required) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.required = required;
    }

    public DiscordCommandOption(String name, OptionType type, String description, boolean required, Command.Choice[] choices) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.required = required;
        this.choices = choices;
    }

    public String getName() {
        return name;
    }

    public OptionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setChoices(Command.Choice[] choices) { this.choices = choices; }

    public Command.Choice[] getChoices() { return choices; }

}
