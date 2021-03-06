package com.xethlyx.plugins.xenchant;

import com.xethlyx.plugins.xenchant.commands.XEnchantCommand;
import com.xethlyx.plugins.xenchant.commands.XEnchantTabCompleter;
import com.xethlyx.plugins.xenchant.enchants.*;
import com.xethlyx.plugins.xenchant.util.EnchantListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class XEnchant extends JavaPlugin {
    public static XEnchant Instance;

    @Override
    public void onEnable() {
        Instance = this;

        saveDefaultConfig();

        getLogger().info("Starting initialization of enchants..");

        PluginCommand xenchantCommand = this.getCommand("xenchant");
        assert xenchantCommand != null;

        xenchantCommand.setExecutor(new XEnchantCommand());
        xenchantCommand.setTabCompleter(new XEnchantTabCompleter());

        // Register Enchants

        new Beheading().registerEnchant();
        new Smelting().registerEnchant();
        new Propulsion().registerEnchant();
        new Lifesteal().registerEnchant();
        new Wither().registerEnchant();
        new Strongwill().registerEnchant();
        new Lifesteal().registerEnchant();
        new Insomniac().registerEnchant();
        new Meridian().registerEnchant();
        new Soulbound().registerEnchant();
        new Zenith().registerEnchant();
        
        // Do other stuff

        getServer().getPluginManager().registerEvents(new EnchantListener(), this);

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
