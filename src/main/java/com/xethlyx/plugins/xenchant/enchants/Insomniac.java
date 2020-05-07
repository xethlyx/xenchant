package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Insomniac extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant insomniacEnchant = new Enchant<InsomniacListeners>();

        insomniacEnchant.Name = "Insomniac";
        insomniacEnchant.Description = "An enchantment that makes phantoms less annoying.";

        insomniacEnchant.MaxLevel = 3;

        List<Material> allowedItems = new ArrayList<>();

        allowedItems.add(Material.DIAMOND_HELMET);
        allowedItems.add(Material.IRON_HELMET);
        allowedItems.add(Material.GOLDEN_HELMET);
        allowedItems.add(Material.CHAINMAIL_HELMET);
        allowedItems.add(Material.LEATHER_HELMET);

        insomniacEnchant.AllowedItems = allowedItems;

        insomniacEnchant.EnchantListener = new InsomniacListeners();

        EnchantRegistry.registerEnchant("insomniac", insomniacEnchant);
    }
}
