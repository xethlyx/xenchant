package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Propulsion extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant propulsionEnchant = new Enchant<PropulsionListeners>();

        propulsionEnchant.Name = "Propulsion";
        propulsionEnchant.Description = "An enchantment that allows you to lift off using an elytra.";

        propulsionEnchant.MaxLevel = 2;

        List<Material> allowedItems = new ArrayList<>();
        allowedItems.add(Material.ELYTRA);
        propulsionEnchant.AllowedItems = allowedItems;

        propulsionEnchant.EnchantListener = new PropulsionListeners();

        EnchantRegistry.registerEnchant("propulsion", propulsionEnchant);
    }
}
