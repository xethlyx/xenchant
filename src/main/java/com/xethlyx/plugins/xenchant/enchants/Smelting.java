package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.entity.Item;

public class Smelting extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant smeltingEnchant = new Enchant<SmeltingListeners>();

        smeltingEnchant.Name = "Smelting";
        smeltingEnchant.Description = "Any ores when mined will be automatically smelted.";

        smeltingEnchant.MaxLevel = 2;
        smeltingEnchant.AllowedItems = new Item[0];

        smeltingEnchant.EnchantListener = new SmeltingListeners();

        EnchantRegistry.registerEnchant("smelting", smeltingEnchant);
    }
}
