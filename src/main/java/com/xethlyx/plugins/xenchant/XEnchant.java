package com.xethlyx.plugins.xenchant;

import com.xethlyx.plugins.xenchant.commands.XEnchantCommand;
import com.xethlyx.plugins.xenchant.enchants.Beheading;
import org.bukkit.plugin.java.JavaPlugin;

public class XEnchant extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("xenchant").setExecutor(new XEnchantCommand());

        EnchantRegistry.registerEnchant(new Beheading());

        for (Enchant enchant : EnchantRegistry.EnchantList) {
            getServer().getPluginManager().registerEvents(enchant.Listener, this);
        }
    }

    @Override
    public void onDisable() {

    }
}
