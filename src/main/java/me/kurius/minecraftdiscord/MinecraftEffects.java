package me.kurius.minecraftdiscord;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MinecraftEffects {

    public void giveEffect(PotionEffectType potion, Player player) {
        // Apply effect
        PotionEffect potionEffect = new PotionEffect(potion, 100, 5);
        player.addPotionEffect(potionEffect);
    }
}
