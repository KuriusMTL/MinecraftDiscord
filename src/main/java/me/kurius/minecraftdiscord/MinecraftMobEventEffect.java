package me.kurius.minecraftdiscord;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MinecraftMobEventEffect extends MinecraftEventEffect {

    private EntityType entity;

    public MinecraftMobEventEffect(EntityType entity) {
        this.entity = entity;
    }

    public void setEntity(EntityType entity) {
        this.entity = entity;
    }

    public EntityType getEntity() {
        return entity;
    }

    public void runEffect(Player player) {
        // Get player location and spawn entity there
        Location loc = player.getLocation();
        player.getWorld().spawnEntity(loc, entity);
    }
}
