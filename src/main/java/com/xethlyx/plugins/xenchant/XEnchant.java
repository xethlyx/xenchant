package com.xethlyx.plugins.xenchant;

import com.xethlyx.plugins.xenchant.commands.XEnchantCommand;
import com.xethlyx.plugins.xenchant.enchants.Beheading;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class XEnchant extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting initialization of enchants..");
        this.getCommand("xenchant").setExecutor(new XEnchantCommand());

        Beheading beheadingEnchant = new Beheading();
        EnchantRegistry.registerEnchant(beheadingEnchant);

        Bukkit.broadcastMessage("There are currently " + EnchantRegistry.EnchantList.size() + " enchants registered.");
        getLogger().info("There are currently " + EnchantRegistry.EnchantList.size() + " enchants registered.");

        for (Enchant enchant : EnchantRegistry.EnchantList) {
            getLogger().info("Registering listener for enchantment " + enchant.Name + "...");
            getServer().getPluginManager().registerEvents(enchant.Listener, this);
        }
    }

    @Override
    public void onDisable() {

    }
}
