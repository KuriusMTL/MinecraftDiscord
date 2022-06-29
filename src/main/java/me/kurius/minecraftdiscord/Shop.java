package me.kurius.minecraftdiscord;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Shop {
    // prices, array index used as unique identifier
    public ArrayList<Integer> positiveShopItems = new ArrayList<>();
    public ArrayList<Integer> negativeShopItems = new ArrayList<>();

    MinecraftEffects effects = new MinecraftEffects();
    MinecraftMobs mobs = new MinecraftMobs();
    MinecraftOther other = new MinecraftOther();

    // Separate items by category
    public Map<Integer, PotionEffectType> potionMap = new HashMap<>();
    public Map<Integer, EntityType> mobMap = new HashMap<>();
    public Map<Integer, String> othersMaps = new HashMap<>();

    public void Init() {
        // Negative items
        potionMap.put(0, PotionEffectType.SLOW_DIGGING);
        potionMap.put(1, PotionEffectType.SLOW);
        potionMap.put(2, PotionEffectType.CONFUSION);
        potionMap.put(3, PotionEffectType.WEAKNESS);
        potionMap.put(4, PotionEffectType.HUNGER);
        potionMap.put(5, PotionEffectType.BLINDNESS);
        potionMap.put(6, PotionEffectType.LEVITATION);
        potionMap.put(7, PotionEffectType.HARM);
        potionMap.put(8, PotionEffectType.POISON);
        potionMap.put(9, PotionEffectType.WITHER);
        mobMap.put(10, EntityType.ZOMBIE);
        mobMap.put(11, EntityType.SKELETON);
        mobMap.put(12, EntityType.WITCH);
        mobMap.put(13, EntityType.CREEPER);
        mobMap.put(14, EntityType.RAVAGER);
        mobMap.put(15, EntityType.WARDEN);
        othersMaps.put(16, "teleport");
        othersMaps.put(17, "cobweb");
        othersMaps.put(18, "fire");
        othersMaps.put(19, "removeEnchant");
        othersMaps.put(20, "deleteRandom");
        othersMaps.put(21, "deleteArmor");
        othersMaps.put(22, "deleteHeld");

        // Positive items
        potionMap.put(23, PotionEffectType.WATER_BREATHING);
        potionMap.put(24, PotionEffectType.NIGHT_VISION);
        potionMap.put(25, PotionEffectType.FAST_DIGGING);
        potionMap.put(26, PotionEffectType.JUMP);
        potionMap.put(27, PotionEffectType.SPEED);
        potionMap.put(28, PotionEffectType.FIRE_RESISTANCE);
        potionMap.put(29, PotionEffectType.DAMAGE_RESISTANCE);
        potionMap.put(30, PotionEffectType.ABSORPTION);
        potionMap.put(31, PotionEffectType.SATURATION);
        potionMap.put(32, PotionEffectType.REGENERATION);
        mobMap.put(33, EntityType.COW);
        mobMap.put(34, EntityType.SHEEP);
        mobMap.put(35, EntityType.PIG);
        mobMap.put(36, EntityType.CHICKEN);
        mobMap.put(37, EntityType.MUSHROOM_COW);
        mobMap.put(38, EntityType.VILLAGER);
        othersMaps.put(39, "killMobs");
        othersMaps.put(40, "giveItem");
        othersMaps.put(41, "enchantArmor");
    }

    public int buyItem(int points, boolean positive, Player player) {
        ArrayList<Integer> shopItems = positive ? positiveShopItems : negativeShopItems;
        int offset = positive ? negativeShopItems.size(): 0;

        // User cannot afford anything
        if (points < Collections.min(shopItems))
            return 0;

        // Nobody in the server
        if (!getServer().getOnlinePlayers().iterator().hasNext())
            return 0;

        ArrayList<Integer> items = new ArrayList<>();

        // Make list that contains all items user can afford
        for (int item: shopItems) {
            if (item <= points) {
                items.add(shopItems.indexOf(item));
            }
        }

        // Randomly pick an item
        int price = items.get(new Random().nextInt(items.size()));
        int selection = shopItems.indexOf(price) + offset;

        if (potionMap.containsKey(selection)) {
            PotionEffectType potion = potionMap.get(selection);
            effects.giveEffect(potion, player);
            return price;
        }
        if (mobMap.containsKey(selection)) {
            EntityType entity = mobMap.get(selection);
            mobs.spawnMob(entity, player);
            return price;
        }
        // selection is in othersMap
        other.doSomething(othersMaps.get(selection), player);
        return price;
    }
}
