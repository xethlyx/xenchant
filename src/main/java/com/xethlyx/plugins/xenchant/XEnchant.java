package com.xethlyx.plugins.xenchant;

import com.xethlyx.plugins.xenchant.commands.XEnchantCommand;
import com.xethlyx.plugins.xenchant.enchants.Beheading;
import org.bukkit.plugin.java.JavaPlugin;

public class XEnchant extends JavaPlugin {
    public static XEnchant Instance;

    @Override
    public void onEnable() {
        Instance = this;

        getLogger().info("Starting initialization of enchants..");
        this.getCommand("xenchant").setExecutor(new XEnchantCommand());

        new Beheading().registerEnchant();

        getLogger().info("There are currently " + EnchantRegistry.EnchantList.size() + " enchant(s) registered and pending initialization.");

        for (Object enchant : EnchantRegistry.EnchantList) {
            getLogger().info("Registering listener for enchantment " + ((Enchant)enchant).Name + "...");
            getServer().getPluginManager().registerEvents(((Enchant)enchant).Listener, this);
        }

        getLogger().info("Enchant initialization finished!");
    }

    @Override
    public void onDisable() {

    }
}
