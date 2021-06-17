package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.XEnchant;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Zenith extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant<ZenithListeners> zenithEnchant = new Enchant<>();

        zenithEnchant.Name = "Zenith";
        zenithEnchant.Description = "Puppet master";

        zenithEnchant.MaxLevel = 1;

        List<Material> allowedItems = new ArrayList<>();

        allowedItems.add(Material.TRIDENT);
        allowedItems.add(Material.ELYTRA);

        zenithEnchant.AllowedItems = allowedItems;

        zenithEnchant.EnchantListener = new ZenithListeners();
        zenithEnchant.EnchantListener.runTaskTimer(XEnchant.Instance, 0, 5);

        EnchantRegistry.registerEnchant("zenith", zenithEnchant);
    }
}
