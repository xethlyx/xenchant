package com.xethlyx.plugins.xenchant.util;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.XEnchant;
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

    public static <U extends Enchant> int parseEnchant(Class<U> enchant, ItemStack item) throws NoSuchFieldException {
        if (item.getItemMeta() == null) {
            return 0;
        }

        List<String> itemLore = item.getItemMeta().getLore();
        if (itemLore == null) {
            return 0;
        }

        for (String lore : itemLore) {
            XEnchant.Instance.getLogger().info(lore);
            XEnchant.Instance.getLogger().info(String.valueOf(enchant.getField("Name")));
            if (lore.substring(0, String.valueOf(enchant.getField("Name")).length()).equals(String.valueOf(enchant.getField("Name")))) {
                // Lore exists, get level
                return RomanNumeralConversion.get(lore.substring(String.valueOf(enchant.getField("Name")).length() + 1));
            }
        }

        return 0;
    }
}
