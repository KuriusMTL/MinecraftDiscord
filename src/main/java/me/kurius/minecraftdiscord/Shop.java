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

        negativeShopTree.add(50, new MinecraftPotionEventEffect(PotionEffectType.SLOW_DIGGING, 300, 1));
        negativeShopTree.add(100, new MinecraftPotionEventEffect(PotionEffectType.SLOW_DIGGING, 200, 3));
        negativeShopTree.add(50, new MinecraftPotionEventEffect(PotionEffectType.SLOW, 600, 1));
        negativeShopTree.add(100, new MinecraftPotionEventEffect(PotionEffectType.SLOW, 150, 3));
        negativeShopTree.add(80, new MinecraftPotionEventEffect(PotionEffectType.CONFUSION, 700, 4));
        negativeShopTree.add(120, new MinecraftPotionEventEffect(PotionEffectType.WEAKNESS, 600, 2));
        negativeShopTree.add(110, new MinecraftPotionEventEffect(PotionEffectType.HUNGER, 150, 5));
        negativeShopTree.add(200, new MinecraftPotionEventEffect(PotionEffectType.BLINDNESS, 100, 5));
        negativeShopTree.add(300, new MinecraftPotionEventEffect(PotionEffectType.BLINDNESS, 1000, 5));
        negativeShopTree.add(300, new MinecraftPotionEventEffect(PotionEffectType.LEVITATION, 100, 5));
        negativeShopTree.add(350, new MinecraftPotionEventEffect(PotionEffectType.LEVITATION, 200, 5));
        negativeShopTree.add(100, new MinecraftPotionEventEffect(PotionEffectType.HARM, 20, 1));
        negativeShopTree.add(200, new MinecraftPotionEventEffect(PotionEffectType.HARM, 20, 2));
        negativeShopTree.add(300, new MinecraftPotionEventEffect(PotionEffectType.POISON, 200, 1));
        negativeShopTree.add(300, new MinecraftPotionEventEffect(PotionEffectType.POISON, 100, 2));
        negativeShopTree.add(420, new MinecraftPotionEventEffect(PotionEffectType.WITHER, 100, 3));
        negativeShopTree.add(800, new MinecraftPotionEventEffect(PotionEffectType.BAD_OMEN, 6000, 1));

        negativeShopTree.add(70, new MinecraftMobEventEffect(EntityType.ZOMBIE));
        negativeShopTree.add(120, new MinecraftMobEventEffect(EntityType.SKELETON));
        negativeShopTree.add(200, new MinecraftMobEventEffect(EntityType.WITCH));
        negativeShopTree.add(200, new MinecraftMobEventEffect(EntityType.PHANTOM));
        negativeShopTree.add(300, new MinecraftMobEventEffect(EntityType.CREEPER));
        negativeShopTree.add(400, new MinecraftMobEventEffect(EntityType.BLAZE));
        negativeShopTree.add(400, new MinecraftMobEventEffect(EntityType.VEX));
        negativeShopTree.add(500, new MinecraftMobEventEffect(EntityType.RAVAGER));
        negativeShopTree.add(600, new MinecraftMobEventEffect(EntityType.WARDEN));

        negativeShopTree.add(120, new MinecraftOtherEventEffect("teleport"));
        negativeShopTree.add(70, new MinecraftOtherEventEffect("spawnWater"));
        negativeShopTree.add(200, new MinecraftOtherEventEffect("cobweb"));
        negativeShopTree.add(200, new MinecraftOtherEventEffect("setNight"));
        negativeShopTree.add(250, new MinecraftOtherEventEffect("fire"));
        negativeShopTree.add(200, new MinecraftOtherEventEffect("removeEnchant"));
        negativeShopTree.add(250, new MinecraftOtherEventEffect("deleteRandom"));
        negativeShopTree.add(300, new MinecraftOtherEventEffect("deleteArmor"));
        negativeShopTree.add(400, new MinecraftOtherEventEffect("spawnTNT"));
        negativeShopTree.add(300, new MinecraftOtherEventEffect("deleteHeld"));
        negativeShopTree.add(500, new MinecraftOtherEventEffect("spawnLava"));

        // Positive items
        positiveShopTree = new ShopTree<MinecraftEventEffect>();

        positiveShopTree.add(50, new MinecraftPotionEventEffect(PotionEffectType.WATER_BREATHING, 1200, 1));
        positiveShopTree.add(150, new MinecraftPotionEventEffect(PotionEffectType.NIGHT_VISION, 1200, 5));
        positiveShopTree.add(150, new MinecraftPotionEventEffect(PotionEffectType.FAST_DIGGING, 600, 1));
        positiveShopTree.add(80, new MinecraftPotionEventEffect(PotionEffectType.JUMP, 100, 5));
        positiveShopTree.add(200, new MinecraftPotionEventEffect(PotionEffectType.JUMP, 300, 5));
        positiveShopTree.add(150, new MinecraftPotionEventEffect(PotionEffectType.SPEED, 300, 1));
        positiveShopTree.add(300, new MinecraftPotionEventEffect(PotionEffectType.SPEED, 300, 3));
        positiveShopTree.add(150, new MinecraftPotionEventEffect(PotionEffectType.FIRE_RESISTANCE, 600, 2));
        positiveShopTree.add(400, new MinecraftPotionEventEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 2));
        positiveShopTree.add(200, new MinecraftPotionEventEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 1));
        positiveShopTree.add(700, new MinecraftPotionEventEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 2));
        positiveShopTree.add(150, new MinecraftPotionEventEffect(PotionEffectType.ABSORPTION, 200, 5));
        positiveShopTree.add(300, new MinecraftPotionEventEffect(PotionEffectType.ABSORPTION, 400, 5));
        positiveShopTree.add(50, new MinecraftPotionEventEffect(PotionEffectType.SATURATION, 100, 5));
        positiveShopTree.add(250, new MinecraftPotionEventEffect(PotionEffectType.REGENERATION, 100, 1));
        positiveShopTree.add(400, new MinecraftPotionEventEffect(PotionEffectType.REGENERATION, 100, 3));
        positiveShopTree.add(600, new MinecraftPotionEventEffect(PotionEffectType.CONDUIT_POWER, 400, 1));

        positiveShopTree.add(100, new MinecraftMobEventEffect(EntityType.COW));
        positiveShopTree.add(150, new MinecraftMobEventEffect(EntityType.SHEEP));
        positiveShopTree.add(50, new MinecraftMobEventEffect(EntityType.PIG));
        positiveShopTree.add(50, new MinecraftMobEventEffect(EntityType.OCELOT));
        positiveShopTree.add(30, new MinecraftMobEventEffect(EntityType.CHICKEN));
        positiveShopTree.add(200, new MinecraftMobEventEffect(EntityType.HORSE));
        positiveShopTree.add(200, new MinecraftMobEventEffect(EntityType.WOLF));
        positiveShopTree.add(300, new MinecraftMobEventEffect(EntityType.MUSHROOM_COW));
        positiveShopTree.add(350, new MinecraftMobEventEffect(EntityType.VILLAGER));
        positiveShopTree.add(500, new MinecraftMobEventEffect(EntityType.IRON_GOLEM));

        positiveShopTree.add(200, new MinecraftOtherEventEffect("killMobs"));
        positiveShopTree.add(200, new MinecraftOtherEventEffect("setDay"));
        positiveShopTree.add(100, new MinecraftOtherEventEffect("ironBlock"));
        positiveShopTree.add(120, new MinecraftOtherEventEffect("emeraldBlock"));
        positiveShopTree.add(300, new MinecraftOtherEventEffect("diamondBlock"));
        positiveShopTree.add(100, new MinecraftOtherEventEffect("giveItem"));
        positiveShopTree.add(250, new MinecraftOtherEventEffect("enchantArmor"));
        positiveShopTree.add(550, new MinecraftOtherEventEffect("findVillage"));
    }

    public BoughtItem buyItem(int points, boolean positive, Player player) {

        // Nobody in the server
        if (!getServer().getOnlinePlayers().iterator().hasNext()) return new BoughtItem("Get friends", 0);

        ShopTree<MinecraftEventEffect> shop = positive ? positiveShopTree : negativeShopTree;

        ArrayList<MinecraftEventEffect> affordableEvents = shop.getUnder(points);

        // Check if the user can afford anything
        if (affordableEvents.size() == 0) return new BoughtItem("You're poor", 0);

        // Randomly pick an effect
        int choice = new Random().nextInt(affordableEvents.size());
        MinecraftEventEffect event = affordableEvents.get(choice);

        return new BoughtItem(event.runEffect(player), event.getPrice());

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
