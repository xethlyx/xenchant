package com.xethlyx.plugins.xenchant;

import com.xethlyx.plugins.xenchant.commands.XEnchantCommand;
import com.xethlyx.plugins.xenchant.commands.XEnchantTabCompleter;
import com.xethlyx.plugins.xenchant.enchants.Beheading;
import com.xethlyx.plugins.xenchant.enchants.Smelting;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class XEnchant extends JavaPlugin {
    public static XEnchant Instance;

    @Override
    public void onEnable() {
        Instance = this;

        getLogger().info("Starting initialization of enchants..");
        this.getCommand("xenchant").setExecutor(new XEnchantCommand());
        this.getCommand("xenchant").setTabCompleter(new XEnchantTabCompleter());

        // Register Enchants

        new Beheading().registerEnchant();
        new Smelting().registerEnchant();

        // Do other stuff

        getLogger().info("There are currently " + EnchantRegistry.EnchantList.size() + " enchant(s) registered and pending initialization.");

        for (HashMap.Entry<String, Enchant<? extends Listener>> enchant : EnchantRegistry.EnchantList.entrySet()) {
            getLogger().info("Registering listener for enchantment " + (enchant.getValue().Name + "..."));
            getServer().getPluginManager().registerEvents(enchant.getValue().EnchantListener, this);
        }

        getLogger().info("Enchant initialization finished!");
    }

    @Override
    public void onDisable() {

    }
}
