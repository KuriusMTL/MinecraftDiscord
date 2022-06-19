package me.kurius.minecraftdiscord;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class MinecraftDiscord extends DiscordMinecraftPlugin {

    @Override
    public void onPreload() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }

    @Override
    public void onDiscordMessage(MessageReceivedEvent event) {

        if (event.getAuthor().getId().equals(getDiscordBot().getSelfUser().getId())) {
            return;
        }

        if (!event.getTextChannel().getId().equals("987939568898699295")) {
            return;
        }

        if (event.getMessage().getContentDisplay().equals("effect")) {
            for (Player p : getServer().getOnlinePlayers()) {

                PotionEffect effect = new PotionEffect(PotionEffectType.POISON, 100, 5);
                p.addPotionEffect(effect);
                p.sendMessage("Applied a potion effect.");

            }
        }

    }

    @Override
    public void onMinecraftMessage(AsyncChatEvent event) {
//        TextChannel channel = getDiscordBot().getTextChannelById("921979218223562763");
//        channel.sendMessage("**[MINECRAFT] " + event.getPlayer().getName() + ":** " + ((TextComponent) event.message()).content()).queue();
    }

}
