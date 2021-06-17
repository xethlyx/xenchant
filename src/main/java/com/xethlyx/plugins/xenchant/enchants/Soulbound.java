package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.util.EnchantRegister;

public class Soulbound extends EnchantRegister {
    @Override
    public void registerEnchant() {
        Enchant<SoulboundListeners> soulboundEnchant = new Enchant<>();

        soulboundEnchant.Name = "Soulbound";
        soulboundEnchant.Description = "Soulbound items don't leave you on death";

        soulboundEnchant.MaxLevel = 1;

        soulboundEnchant.AllowedItems = null;

        soulboundEnchant.EnchantListener = new SoulboundListeners();

        EnchantRegistry.registerEnchant("soulbound", soulboundEnchant);
    }
}
