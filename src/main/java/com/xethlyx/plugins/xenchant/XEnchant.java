package com.xethlyx.plugins.xenchant;

import com.xethlyx.plugins.xenchant.commands.XEnchantCommand;
import com.xethlyx.plugins.xenchant.enchants.Beheading;
import org.bukkit.plugin.java.JavaPlugin;

public class XEnchant extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("xenchant").setExecutor(new XEnchantCommand());

        EnchantRegistry.registerEnchant(new Beheading());

        getLogger().warning("There are currently " + EnchantRegistry.EnchantList.size() + " enchants registered.");

        for (Enchant enchant : EnchantRegistry.EnchantList) {
            getLogger().warning("Registering listener for enchantment " + enchant.Name + "...");
            getServer().getPluginManager().registerEvents(enchant.Listener, this);
        }
    }

    @Override
    public void onDisable() {

    }
}
