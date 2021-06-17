package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Beheading extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant<BeheadingListeners> beheadingEnchant = new Enchant<>();

        beheadingEnchant.Name = "Beheading";
        beheadingEnchant.Description = "An enchantment that allows you to collect the skulls of victims when they die.";

        beheadingEnchant.MaxLevel = 4;

        List<Material> allowedItems = new ArrayList<>();

        allowedItems.add(Material.NETHERITE_SWORD);
        allowedItems.add(Material.DIAMOND_SWORD);
        allowedItems.add(Material.IRON_SWORD);
        allowedItems.add(Material.GOLDEN_SWORD);
        allowedItems.add(Material.STONE_SWORD);
        allowedItems.add(Material.WOODEN_SWORD);

        allowedItems.add(Material.NETHERITE_AXE);
        allowedItems.add(Material.DIAMOND_AXE);
        allowedItems.add(Material.IRON_AXE);
        allowedItems.add(Material.GOLDEN_AXE);
        allowedItems.add(Material.STONE_AXE);
        allowedItems.add(Material.WOODEN_AXE);

        beheadingEnchant.AllowedItems = allowedItems;

        beheadingEnchant.EnchantListener = new BeheadingListeners();

        EnchantRegistry.registerEnchant("beheading", beheadingEnchant);
    }
}
