package me.kurius.minecraftdiscord.plugin;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public class DiscordCommandOption {

    private String name;
    private OptionType type;
    private String description;
    private boolean required;

    public DiscordCommandOption(String name, OptionType type, String description, boolean required) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.required = required;
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

}
