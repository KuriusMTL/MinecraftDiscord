package me.kurius.minecraftdiscord;

import java.util.ArrayList;

public class ShopTree <T> {

    ShopTreeNode<T> root;

    public ShopTree() {}

    public void add(int price, T item) {
        if (item instanceof MinecraftEventEffect) ((MinecraftEventEffect) item).setPrice(price);
        if (root == null) {
            root = new ShopTreeNode<T>(item, price);
        } else {
            root.add(price, new ShopTreeNode<T>(item, price));
        }
    }

    public ArrayList<T> getUnder(int targetPrice) {
        ArrayList<T> list = new ArrayList<T>();
        if (root != null) root.getUnder(targetPrice, list);
        return list;
    }
}
