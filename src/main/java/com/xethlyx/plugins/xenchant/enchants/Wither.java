package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Wither extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant<WitherListeners> witherEnchant = new Enchant<WitherListeners>();

        witherEnchant.Name = "Wither";
        witherEnchant.Description = "Your enemies will sometimes get the wither effect.";

        witherEnchant.MaxLevel = 3;

        List<Material> allowedItems = new ArrayList<>();

        allowedItems.add(Material.DIAMOND_SWORD);
        allowedItems.add(Material.IRON_SWORD);
        allowedItems.add(Material.GOLDEN_SWORD);
        allowedItems.add(Material.STONE_SWORD);
        allowedItems.add(Material.WOODEN_SWORD);

        allowedItems.add(Material.DIAMOND_AXE);
        allowedItems.add(Material.IRON_AXE);
        allowedItems.add(Material.GOLDEN_AXE);
        allowedItems.add(Material.STONE_AXE);
        allowedItems.add(Material.WOODEN_AXE);

        witherEnchant.AllowedItems = allowedItems;

        witherEnchant.EnchantListener = new WitherListeners();

        EnchantRegistry.registerEnchant("wither", witherEnchant);
    }
}
