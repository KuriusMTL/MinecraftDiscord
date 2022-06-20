package me.kurius.minecraftdiscord;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class MinecraftEffects {
    // Map (price, effect)
    public Map<Integer, PotionEffectType> negativeEffects = new HashMap<>();
    public Map<Integer, PotionEffectType> positiveEffects = new HashMap<>();

    // Initialize hashmaps
    public void Init() {
        negativeEffects.put(300, PotionEffectType.SLOW_DIGGING);
        negativeEffects.put(400, PotionEffectType.SLOW);
        negativeEffects.put(500, PotionEffectType.CONFUSION);
        negativeEffects.put(600, PotionEffectType.WEAKNESS);
        negativeEffects.put(700, PotionEffectType.HUNGER);
        negativeEffects.put(800, PotionEffectType.BLINDNESS);
        negativeEffects.put(900, PotionEffectType.LEVITATION);
        negativeEffects.put(1000, PotionEffectType.HARM);
        negativeEffects.put(1100, PotionEffectType.POISON);
    }

    public void negativeEffect(Integer points) {
        // No user is in the minecraft server
        if (!getServer().getOnlinePlayers().iterator().hasNext())
            return;

        // User cannot afford anything
        if (Collections.min(negativeEffects.keySet()) > points)
            return;

        // Create list containing all effects user can afford
        ArrayList<PotionEffectType> canAfford = new ArrayList<>();
        for (int price: negativeEffects.keySet()) {
            if (price < points)
                canAfford.add(negativeEffects.get(price));
        }

        // TEMP : affected player is the first in the list of online players
        Player player = getServer().getOnlinePlayers().iterator().next();
        // Select random effect from the ones user can afford
        PotionEffectType effect = canAfford.get(new Random().nextInt(canAfford.size()));
        // Apply effect
        PotionEffect potionEffect = new PotionEffect(effect, 100, 5);
        player.addPotionEffect(potionEffect);
    }
}
