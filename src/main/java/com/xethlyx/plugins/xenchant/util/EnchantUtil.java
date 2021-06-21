package com.xethlyx.plugins.xenchant.util;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static boolean verifyEnchantCompatibility(String enchant, ItemStack item) {
        return verifyEnchantCompatibility(EnchantRegistry.getEnchant(enchant), item);
    }

    public static boolean verifyEnchantCompatibility(Enchant<? extends Listener> enchant, ItemStack item) {
        if (enchant.AllowedItems == null) return true;

        for (Material material : enchant.AllowedItems) {
            if (material == item.getType()) return true;
        }

        return false;
    }

    public static int parseEnchant(String enchantId, ItemStack item) {
        return parseEnchant(EnchantRegistry.getEnchant(enchantId), item);
    }

    public static boolean matchEnchant(String matchString, String enchantName) {
        if (matchString.length() < (enchantName.length() + 2)) return false;

        return matchString.substring(0, enchantName.length() + 2).equals(ChatColor.GRAY + enchantName);
    }

    public static <T extends Listener> int parseEnchant(Enchant<T> enchant, ItemStack item) {
        if (item == null) return 0;
        if (item.getItemMeta() == null) return 0;

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return 0;
        List<String> itemLore = itemMeta.getLore();
        if (itemLore == null) return 0;

        for (String lore : itemLore) {
            if (enchant.Name.length() > lore.length()) {
                continue;
            }

            if (matchEnchant(lore, enchant.Name)) {
                String levelString = lore.substring(enchant.Name.length() + 3);

                // Gracefully fail on invalid enchant level
                if (!RomanNumeralConversion.containsKey(levelString)) return 0;
                return RomanNumeralConversion.get(levelString);
            }
        }

        return 0;
    }

    public static HashMap<Enchant<? extends Listener>, Integer> getEnchants(ItemStack item) {
        HashMap<Enchant<? extends Listener>, Integer> foundEnchants = new HashMap<>();

        if (item == null) return foundEnchants;

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return foundEnchants;

        List<String> itemLore = itemMeta.getLore();
        if (itemLore == null) return foundEnchants;

        for (String lore : itemLore) {
            for (Map.Entry<String, Enchant<? extends Listener>> entry : EnchantRegistry.EnchantList.entrySet()) {
                Enchant<? extends Listener> enchant = entry.getValue();

                if (matchEnchant(lore, enchant.Name)) {
                    int level = RomanNumeralConversion.get(lore.substring(enchant.Name.length() + 3));
                    foundEnchants.put(enchant, level);
                    break;
                }
            }
        }

        return foundEnchants;
    }

    public static <T extends Listener> void modifyEnchant(ItemStack item, Enchant<T> enchant, int newLevel) {
        String enchantLoreString = ChatColor.GRAY + enchant.Name + " " + RomanNumeralConversionRev.get(newLevel);

        ItemMeta meta = item.getItemMeta();
        if (meta == null) System.out.println(item.getType());

        List<String> lore = meta.getLore();

        boolean foundEnchant = false;

        if (lore == null) {
            lore = new ArrayList<>(1);
        } else {
            for (int i = 0; i < lore.size(); i++) {
                if (matchEnchant(lore.get(i), enchant.Name)) {
                    lore.set(i, enchantLoreString);
                    foundEnchant = true;

                    break;
                }
            }
        }

        if (!foundEnchant) {
            lore.add(enchantLoreString);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static <T extends Listener> boolean removeEnchant(ItemStack item, Enchant<T> enchant) {
        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.getLore();

        if (lore == null) {
            return false;
        }

        for (int i = 0; i < lore.size(); i++) {
            if (matchEnchant(lore.get(i), enchant.Name)) {
                lore.remove(i);

                meta.setLore(lore);
                item.setItemMeta(meta);

                return true;
            }
        }

        return false;
    }

    public static ItemStack mergeEnchant(ItemStack first, ItemStack second, ItemStack result) {
        if (first.getType() != second.getType() && second.getType() != Material.ENCHANTED_BOOK) return null;

        HashMap<Enchant<? extends Listener>, Integer> secondEnchants = getEnchants(second);
        if (secondEnchants.size() == 0) return null;

        HashMap<Enchant<? extends Listener>, Integer> newEnchants = getEnchants(first);

        for (Map.Entry<Enchant<? extends Listener>, Integer> entry : secondEnchants.entrySet()) {
            Enchant<? extends Listener> enchant = entry.getKey();
            int level = entry.getValue();

            if (newEnchants.containsKey(enchant)) {
                if (level != newEnchants.get(enchant)) return null;
                level += 1;
                if (level > enchant.MaxLevel) return null;
            }

            newEnchants.put(enchant, level);
        }

        ItemStack mergedResult = result;
        if (mergedResult == null || mergedResult.getType() == Material.AIR) mergedResult = first.clone();

        for (Map.Entry<Enchant<? extends Listener>, Integer> entry : newEnchants.entrySet()) {
            Enchant<? extends Listener> enchant = entry.getKey();
            int level = entry.getValue();

            if (!verifyEnchantCompatibility(enchant, mergedResult)) return null;
            modifyEnchant(mergedResult, enchant, level);
        }

        return mergedResult;
    }
}
