package com.xethlyx.plugins.xenchant;

import org.bukkit.entity.Item;
import org.bukkit.event.Listener;

public class Enchant<T extends Listener> {
    public String Name;
    public String Description;

    public Item[] AllowedItems;

    public T Listener;

    public boolean registerEnchant() {
        return EnchantRegistry.registerEnchant(this);
    }
}
