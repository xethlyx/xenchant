package com.xethlyx.plugins.xenchant;

import org.bukkit.event.Listener;

import java.util.HashMap;

public abstract class EnchantRegistry {
    public static HashMap<String, Enchant<? extends Listener>> EnchantList = new HashMap<>();

    public static void registerEnchant(String enchantId, Enchant<? extends Listener> enchant) {
        EnchantList.put(enchantId, enchant);
    }

    public static Enchant<? extends Listener> getEnchant(String enchantId) {
        return EnchantList.get(enchantId);
    }
}
