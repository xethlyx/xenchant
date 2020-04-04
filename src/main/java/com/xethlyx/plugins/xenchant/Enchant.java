package com.xethlyx.plugins.xenchant;

import org.bukkit.entity.Item;
import org.bukkit.event.Listener;

public class Enchant<T extends Listener> {
    public String Name;
    public String Description;

    public int MaxLevel;
    public Item[] AllowedItems;

    public T Listener;

    public boolean checkEnchant(Item item) {
        return false;
    }

    public boolean registerEnchant() {
        EnchantRegistry.EnchantList.add(this);

        return true;
    }
}
