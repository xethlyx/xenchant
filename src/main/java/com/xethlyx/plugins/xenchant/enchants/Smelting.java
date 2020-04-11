package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Smelting extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant smeltingEnchant = new Enchant<SmeltingListeners>();

        smeltingEnchant.Name = "Smelting";
        smeltingEnchant.Description = "Any ores when mined will be automatically smelted.";

        smeltingEnchant.MaxLevel = 2;

        List<Material> allowedItems = new ArrayList<>();

        allowedItems.add(Material.DIAMOND_PICKAXE);
        allowedItems.add(Material.IRON_PICKAXE);
        allowedItems.add(Material.GOLDEN_PICKAXE);
        allowedItems.add(Material.STONE_PICKAXE);
        allowedItems.add(Material.WOODEN_PICKAXE);

        smeltingEnchant.AllowedItems = allowedItems;

        smeltingEnchant.EnchantListener = new SmeltingListeners();

        EnchantRegistry.registerEnchant("smelting", smeltingEnchant);
    }
}
