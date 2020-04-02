package com.xethlyx.plugins.xenchant;

import com.xethlyx.plugins.xenchant.commands.XEnchantCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class XEnchant extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("xenchant").setExecutor(new XEnchantCommand());
    }

    @Override
    public void onDisable() {

    }
}
