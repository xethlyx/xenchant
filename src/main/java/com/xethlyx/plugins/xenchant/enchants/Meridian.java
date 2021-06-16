package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.XEnchant;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Meridian extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant<MeridianListeners> meridianEnchant = new Enchant<>();

        meridianEnchant.Name = "Meridian";
        meridianEnchant.Description = "An ancient power that can be channeled through metals";

        meridianEnchant.MaxLevel = 1;

        List<Material> allowedItems = new ArrayList<>();

        allowedItems.add(Material.NETHERITE_SWORD);
        allowedItems.add(Material.DIAMOND_SWORD);
        allowedItems.add(Material.IRON_SWORD);
        allowedItems.add(Material.GOLDEN_SWORD);
        allowedItems.add(Material.PLAYER_HEAD);

        meridianEnchant.AllowedItems = allowedItems;

        meridianEnchant.EnchantListener = new MeridianListeners();
        meridianEnchant.EnchantListener.runTaskTimer(XEnchant.Instance, 0, 1);

        EnchantRegistry.registerEnchant("meridian", meridianEnchant);
    }
}
