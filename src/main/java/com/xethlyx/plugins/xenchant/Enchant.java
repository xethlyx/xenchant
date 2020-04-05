package com.xethlyx.plugins.xenchant;

import org.bukkit.entity.Item;
import org.bukkit.event.Listener;

public class Enchant<T extends Listener> {
    public String Name = "Unnamed Enchant";
    public String Description = "No description";

    public int MaxLevel = 0;
    public Item[] AllowedItems = new Item[0];

    public T Listener;

    public void registerEnchant() {
        EnchantRegistry.EnchantList.add(this);

    }
}
