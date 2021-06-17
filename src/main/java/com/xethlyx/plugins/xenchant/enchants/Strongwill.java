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
        Enchant<StrongwillListeners> strongwillEnchant = new Enchant<>();

        strongwillEnchant.Name = "Strong Will";
        strongwillEnchant.Description = "Right click any mob with a lead to attach it.";

        strongwillEnchant.MaxLevel = 1;

        List<Material> allowedItems = new ArrayList<>();
        allowedItems.add(Material.NETHERITE_HELMET);
        allowedItems.add(Material.DIAMOND_HELMET);
        allowedItems.add(Material.IRON_HELMET);
        allowedItems.add(Material.GOLDEN_HELMET);
        allowedItems.add(Material.CHAINMAIL_HELMET);
        allowedItems.add(Material.LEATHER_HELMET);
        strongwillEnchant.AllowedItems = allowedItems;

        strongwillEnchant.EnchantListener = new StrongwillListeners();

        EnchantRegistry.registerEnchant("strongwill", strongwillEnchant);
    }
}
