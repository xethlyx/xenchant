package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import org.bukkit.entity.Item;

public class Beheading extends Enchant<BeheadingListeners> {
    public String Name = "Beheading";
    public String Description = "An enchantment that allows you to collect the skulls of victims when they die.";

    public int MaxLevel = 1;
    public Item[] AllowedItems = new Item[0];

    public Beheading() {
        Listener = new BeheadingListeners();
    }
}
