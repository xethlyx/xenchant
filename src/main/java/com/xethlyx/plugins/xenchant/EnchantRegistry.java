package com.xethlyx.plugins.xenchant;

import java.util.HashMap;

public abstract class EnchantRegistry {
    public static HashMap<String, Enchant> EnchantList = new HashMap<>();

    public static void registerEnchant(String enchantId, Enchant enchant) {
        EnchantList.put(enchantId, enchant);
    }

    public static Enchant getEnchant(String enchantId) {
        return EnchantList.get(enchantId);
    }
}
