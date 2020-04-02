package com.xethlyx.plugins.xenchant;

import java.util.ArrayList;

public abstract class EnchantRegistry {
    public static ArrayList<Enchant> EnchantList;

    public static boolean registerEnchant(Enchant enchant) {
        EnchantList.add(enchant);

        return true;
    }
}
