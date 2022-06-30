package me.kurius.minecraftdiscord;

import java.util.ArrayList;

public class ShopTreeNode <T> {

    public T data;
    public int price;
    public ShopTreeNode<T> left;
    public ShopTreeNode<T> right;

    public ShopTreeNode(T data, int price) {
        this.data = data;
        this.price = price;
    }

    public ShopTreeNode<T> getUnder(int targetPrice, ArrayList<T> list) {
        if (price <= targetPrice) {
            list.add(this.data);
        }
        if (right != null && right.price <= targetPrice) {
            right.getUnder(targetPrice, list);
        }
        if (left != null) {
            return left.getUnder(targetPrice, list);
        }
        return null;
    }

    public void add(int price, ShopTreeNode<T> item) {
        if (price <= this.price) {
            if (left != null) {
                left.add(price, item);
            } else {
                left = item;
            }
        } else {
            if (right != null) {
                right.add(price, item);
            } else {
                right = item;
            }
        }
    }
}
