package com.xethlyx.plugins.xenchant;

import org.bukkit.entity.Item;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class Enchant<T extends Listener> {
    public static final HashMap<String, Integer> RomanNumeralConversion = new HashMap<String, Integer>() {{
        put("I", 1);
        put("II", 2);
        put("III", 3);
        put("IV", 4);
        put("V", 5);
        put("VI", 6);
        put("VII", 7);
        put("VIII", 8);
        put("IX", 9);
        put("X", 10);
    }};

    public static String Name;
    public static String Description;

    public static int MaxLevel;
    public static Item[] AllowedItems;

    public T Listener;

    public static int parseEnchant(ItemStack item) {
        if (item.getItemMeta() == null) {
            return 0;
        }

        List<String> itemLore = item.getItemMeta().getLore();
        if (itemLore == null) {
            return 0;
        }

        for (String lore : itemLore) {
            XEnchant.Instance.getLogger().info(lore);
            XEnchant.Instance.getLogger().info(Name);
            if (lore.substring(0, Name.length()).equals(Name)) {
                // Lore exists, get level
                return RomanNumeralConversion.get(lore.substring(Name.length() + 1));
            }
        }

        return 0;
    }

    public boolean registerEnchant() {
        EnchantRegistry.EnchantList.add(this);

        return true;
    }
}
