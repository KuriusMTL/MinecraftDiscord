package me.kurius.minecraftdiscord;

import org.bukkit.*;
import org.bukkit.entity.*;

public class MinecraftMobs {

    public void spawnMob(EntityType entity, Player player) {
        // Get player location and spawn entity there
        Location loc = player.getLocation();
        player.getWorld().spawnEntity(loc, entity);
    }
}
