package me.kurius.minecraftdiscord;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class Shop {
    // prices, array index used as unique identifier
//    public ArrayList<Integer> positiveShopItems = new ArrayList<>();
//    public ArrayList<Integer> negativeShopItems = new ArrayList<>();

    public ShopTree<MinecraftEventEffect> negativeShopTree;
    public ShopTree<MinecraftEventEffect> positiveShopTree;

//    MinecraftEffects effects = new MinecraftEffects();
//    MinecraftMobs mobs = new MinecraftMobs();
//    MinecraftOther other = new MinecraftOther();

    // Separate items by category
//    public Map<Integer, PotionEffectType> potionMap = new HashMap<>();
//    public Map<Integer, EntityType> mobMap = new HashMap<>();
//    public Map<Integer, String> othersMaps = new HashMap<>();

    public void Init() {

        // Negative items
        negativeShopTree = new ShopTree<MinecraftEventEffect>();

        negativeShopTree.add(5, new MinecraftPotionEventEffect(PotionEffectType.SLOW_DIGGING, 100, 5));
        negativeShopTree.add(10, new MinecraftPotionEventEffect(PotionEffectType.SLOW, 100, 5));
        negativeShopTree.add(15, new MinecraftPotionEventEffect(PotionEffectType.CONFUSION, 100, 5));
        negativeShopTree.add(20, new MinecraftPotionEventEffect(PotionEffectType.WEAKNESS, 100, 5));
        negativeShopTree.add(25, new MinecraftPotionEventEffect(PotionEffectType.HUNGER, 100, 5));
        negativeShopTree.add(30, new MinecraftPotionEventEffect(PotionEffectType.BLINDNESS, 100, 5));
        negativeShopTree.add(35, new MinecraftPotionEventEffect(PotionEffectType.LEVITATION, 100, 5));
        negativeShopTree.add(40, new MinecraftPotionEventEffect(PotionEffectType.HARM, 100, 5));
        negativeShopTree.add(45, new MinecraftPotionEventEffect(PotionEffectType.POISON, 100, 5));
        negativeShopTree.add(50, new MinecraftPotionEventEffect(PotionEffectType.WITHER, 100, 5));

        negativeShopTree.add(20, new MinecraftMobEventEffect(EntityType.ZOMBIE));
        negativeShopTree.add(20, new MinecraftMobEventEffect(EntityType.SKELETON));
        negativeShopTree.add(30, new MinecraftMobEventEffect(EntityType.WITCH));
        negativeShopTree.add(50, new MinecraftMobEventEffect(EntityType.CREEPER));
        negativeShopTree.add(75, new MinecraftMobEventEffect(EntityType.RAVAGER));
        negativeShopTree.add(80, new MinecraftMobEventEffect(EntityType.WARDEN));

//        othersMaps.put(16, "teleport");
//        othersMaps.put(17, "cobweb");
//        othersMaps.put(18, "fire");
//        othersMaps.put(19, "removeEnchant");
//        othersMaps.put(20, "deleteRandom");
//        othersMaps.put(21, "deleteArmor");
//        othersMaps.put(22, "deleteHeld");

        // Positive items
        positiveShopTree = new ShopTree<MinecraftEventEffect>();

        positiveShopTree.add(5, new MinecraftPotionEventEffect(PotionEffectType.WATER_BREATHING, 100, 5));
        positiveShopTree.add(10, new MinecraftPotionEventEffect(PotionEffectType.NIGHT_VISION, 100, 5));
        positiveShopTree.add(15, new MinecraftPotionEventEffect(PotionEffectType.FAST_DIGGING, 100, 5));
        positiveShopTree.add(20, new MinecraftPotionEventEffect(PotionEffectType.JUMP, 100, 5));
        positiveShopTree.add(25, new MinecraftPotionEventEffect(PotionEffectType.SPEED, 100, 5));
        positiveShopTree.add(30, new MinecraftPotionEventEffect(PotionEffectType.FIRE_RESISTANCE, 100, 5));
        positiveShopTree.add(35, new MinecraftPotionEventEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
        positiveShopTree.add(40, new MinecraftPotionEventEffect(PotionEffectType.ABSORPTION, 100, 5));
        positiveShopTree.add(45, new MinecraftPotionEventEffect(PotionEffectType.SATURATION, 100, 5));
        positiveShopTree.add(50, new MinecraftPotionEventEffect(PotionEffectType.REGENERATION, 100, 5));

        positiveShopTree.add(10, new MinecraftMobEventEffect(EntityType.COW));
        positiveShopTree.add(15, new MinecraftMobEventEffect(EntityType.SHEEP));
        positiveShopTree.add(10, new MinecraftMobEventEffect(EntityType.PIG));
        positiveShopTree.add(5, new MinecraftMobEventEffect(EntityType.CHICKEN));
        positiveShopTree.add(25, new MinecraftMobEventEffect(EntityType.MUSHROOM_COW));
        positiveShopTree.add(50, new MinecraftMobEventEffect(EntityType.VILLAGER));

//        othersMaps.put(39, "killMobs");
//        othersMaps.put(40, "giveItem");
//        othersMaps.put(41, "enchantArmor");
    }

    public int buyItem(int points, boolean positive, Player player) {

        // Nobody in the server
        if (!getServer().getOnlinePlayers().iterator().hasNext()) return 0;

        ShopTree<MinecraftEventEffect> shop = positive ? positiveShopTree : negativeShopTree;

        ArrayList<MinecraftEventEffect> affordableEvents = shop.getUnder(points);

        // Check if the user can afford anything
        if (affordableEvents.size() == 0) return 0;

        System.out.println(String.valueOf(affordableEvents.size()));

        // Randomly pick an effect
        int choice = new Random().nextInt(affordableEvents.size());
        System.out.println(String.valueOf(choice));
        MinecraftEventEffect event = affordableEvents.get(choice);
        event.runEffect(player);

        return event.getPrice();

//        ArrayList<Integer> shopItems = positive ? positiveShopItems : negativeShopItems;
//        int offset = positive ? negativeShopItems.size(): 0;
//
//        // User cannot afford anything
//        if (points < Collections.min(shopItems))
//            return 0;
//
//        // Nobody in the server
//        if (!getServer().getOnlinePlayers().iterator().hasNext())
//            return 0;
//
//        ArrayList<Integer> items = new ArrayList<>();
//
//        // Make list that contains all items user can afford
//        for (int item: shopItems) {
//            if (item <= points) {
//                items.add(shopItems.indexOf(item));
//            }
//        }
//
//        // Randomly pick an item
//        int price = items.get(new Random().nextInt(items.size()));
//        int selection = shopItems.indexOf(price) + offset;
//
//        if (potionMap.containsKey(selection)) {
//            PotionEffectType potion = potionMap.get(selection);
//            effects.giveEffect(potion, player);
//            return price;
//        }
//        if (mobMap.containsKey(selection)) {
//            EntityType entity = mobMap.get(selection);
//            mobs.spawnMob(entity, player);
//            return price;
//        }
//        // selection is in othersMap
//        other.doSomething(othersMaps.get(selection), player);
//        return price;
    }
}
