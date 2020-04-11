package com.xethlyx.plugins.xenchant.util;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class EnchantUtil {
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

    public static final HashMap<Integer, String> RomanNumeralConversionRev = new HashMap<Integer, String>() {{
        put(1, "I");
        put(2, "II");
        put(3, "III");
        put(4, "IV");
        put(5, "V");
        put(6, "VI");
        put(7, "VII");
        put(8, "VIII");
        put(9, "IX");
        put(10, "X");
    }};

    public static int parseEnchant(String enchantId, ItemStack item) {
        return parseEnchant(EnchantRegistry.getEnchant(enchantId), item);
    }

    public static boolean matchEnchant(String matchString, String enchantName) {
        return matchString.substring(0, enchantName.length() + 2).equals("\u00A77" + enchantName);
    }

    public static int parseEnchant(Enchant enchant, ItemStack item) {
        if (item.getItemMeta() == null) {
            return 0;
        }

        List<String> itemLore = item.getItemMeta().getLore();
        if (itemLore == null) {
            return 0;
        }

        for (String lore : itemLore) {
            if (enchant.Name.length() > lore.length()) {
                continue;
            }

            if (matchEnchant(lore, enchant.Name)) {
                return RomanNumeralConversion.get(lore.substring(enchant.Name.length() + 3));
            }
        }

        return 0;
    }
}
