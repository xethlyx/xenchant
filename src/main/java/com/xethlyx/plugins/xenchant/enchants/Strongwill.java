package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Strongwill extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant strongwillEnchant = new Enchant<StrongwillListeners>();

        strongwillEnchant.Name = "Strong Will";
        strongwillEnchant.Description = "Right click any mob with a lead to attach it.";

        strongwillEnchant.MaxLevel = 1;

        List<Material> allowedItems = new ArrayList<>();
        allowedItems.add(Material.DIAMOND_HELMET);
        allowedItems.add(Material.IRON_HELMET);
        allowedItems.add(Material.GOLDEN_HELMET);
        allowedItems.add(Material.STONE_HELMET);
        allowedItems.add(Material.WOODEN_HELMET);
        strongwillEnchant.AllowedItems = allowedItems;

        strongwillEnchant.EnchantListener = new StrongwillListeners();

        EnchantRegistry.registerEnchant("strongwill", strongwillEnchant);
    }
}
