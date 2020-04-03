package com.xethlyx.plugins.xenchant;

import com.xethlyx.plugins.xenchant.commands.XEnchantCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class XEnchant extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting initialization of enchants..");
        this.getCommand("xenchant").setExecutor(new XEnchantCommand());

        // new Beheading().registerEnchant();

        getLogger().info("There are currently " + EnchantRegistry.EnchantList.size() + " enchants registered.");

        for (Object enchant : EnchantRegistry.EnchantList) {
            getLogger().info("Registering listener for enchantment " + ((Enchant)enchant).Name + "...");
            getServer().getPluginManager().registerEvents(((Enchant)enchant).Listener, this);
        }
    }

    @Override
    public void onDisable() {

    }
}
