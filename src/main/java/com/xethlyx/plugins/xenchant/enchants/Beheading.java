package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import org.bukkit.entity.Item;

public class Beheading extends Enchant<BeheadingListeners> {
    public static String Name = "Beheading";
    public static String Description = "An enchantment that allows you to collect the skulls of victims when they die.";

    public static int MaxLevel;
    public static Item[] AllowedItems;

    public Beheading() {
        super();
        Listener = new BeheadingListeners();
    }
}
