package me.kurius.minecraftdiscord;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

public class MinecraftOtherEventEffect extends MinecraftEventEffect {
    private String effect;

    public MinecraftOtherEventEffect(String effect) {
        this.effect = effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }

    public String runEffect(Player player) {
        Location loc = player.getLocation();
        PlayerInventory inventory = player.getInventory();

        switch (effect) {
            case "teleport":
                int newX = loc.getBlockX() + (new Random().nextInt(40) - 20);
                int newZ = loc.getBlockZ() + (new Random().nextInt(40) - 20);
                int newY = loc.getWorld().getHighestBlockYAt(newX, newZ);
                Location newLoc = new Location(loc.getWorld(), newX, newY, newZ);

                player.teleport(newLoc);
                return " teleported ";
            case "cobweb":
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z).setType(Material.COBWEB);
                    }
                }
                return " put cobwebs on ";
            case "fire":
                player.setFireTicks(100);
                return " burned ";
            case "removeEnchant":
                for (ItemStack item: inventory) {
                    if (item == null)
                        continue;
                    if (item.getEnchantments().size() > 0) {
                        for (Enchantment e: item.getEnchantments().keySet()) {
                            item.removeEnchantment(e);
                        }
                        return " removed random enchantment from ";
                    }
                }
                return " couldn't remove any enchantments from ";
            case "deleteRandom":
                if (inventory.isEmpty())
                    return " tried to delete an item from ";
                int randInv = new Random().nextInt(inventory.getSize());
                inventory.remove(inventory.getItem(randInv));
                return " deleted an item from ";
            case "deleteArmor":
                ItemStack[] armorsDelete = inventory.getArmorContents();
                for (int i = 0; i < armorsDelete.length; i++) {
                    if (armorsDelete[i] == null)
                        continue;
                    armorsDelete[i] = null;
                    return " deleted an armor piece from ";
                }
                inventory.setArmorContents(armorsDelete);
                return " tried to delete an armor piece from ";
            case "deleteHeld":
                inventory.setItemInMainHand(null);
                inventory.setItemInOffHand(null);
                return " deleted the held item from ";
            case "killMobs":
                for (Entity entity: player.getNearbyEntities(10,10,10)) {
                    if (entity instanceof Mob)
                        ((Mob) entity).setHealth(0);
                }
                return " killed the mobs around ";
            case "giveItem":
                int randMat = new Random().nextInt(Material.values().length);
                Material randItem = Material.values()[randMat];

                ItemStack itemStack = new ItemStack(randItem, 1);
                inventory.addItem(itemStack);
                return String.format(" gave %s to ", randItem.name());
            case "enchantArmor":
                ItemStack[] armorsEnchant = inventory.getArmorContents();
                for (int i = 0; i < armorsEnchant.length; i++) {
                    if (armorsEnchant[i] == null)
                        continue;
                    if (armorsEnchant[i].getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL))
                        continue;
                    armorsEnchant[i].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                }
                return " enchanted the armor of ";
        }
        return "";
    }
}
