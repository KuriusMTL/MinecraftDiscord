package me.kurius.minecraftdiscord;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MinecraftPotionEventEffect extends MinecraftEventEffect {

    private PotionEffectType effect;
    private int duration;
    private int amplifier;
    public int price = 0;

    public MinecraftPotionEventEffect(PotionEffectType effect, int duration, int amplifier) {
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public void setPotionEffect(PotionEffectType potionEffect) {
        this.effect = potionEffect;
    }

    public PotionEffectType getPotionEffect() {
        return effect;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public void runEffect(Player player) {
        PotionEffect potionEffect = new PotionEffect(effect, duration, amplifier);
        player.addPotionEffect(potionEffect);
    }
}
