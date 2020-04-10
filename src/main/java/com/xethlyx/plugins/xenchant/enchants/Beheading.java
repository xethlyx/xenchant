package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.entity.Item;

public class Beheading extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant beheadingEnchant = new Enchant<BeheadingListeners>();

        beheadingEnchant.Name = "Beheading";
        beheadingEnchant.Description = "An enchantment that allows you to collect the skulls of victims when they die.";

        beheadingEnchant.MaxLevel = 4;
        beheadingEnchant.AllowedItems = new Item[0];

        beheadingEnchant.EnchantListener = new BeheadingListeners();

        EnchantRegistry.registerEnchant("beheading", beheadingEnchant);
    }
}
