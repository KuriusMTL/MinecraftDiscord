package me.kurius.minecraftdiscord;

import org.bukkit.entity.Player;

public abstract class MinecraftEventEffect {
    private int price = 0;
    public int getPrice() { return price ;}
    public void setPrice(int price) { this.price = price; }
    public abstract void runEffect(Player player);
}
