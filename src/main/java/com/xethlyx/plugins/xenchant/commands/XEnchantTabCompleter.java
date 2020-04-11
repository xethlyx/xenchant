package com.xethlyx.plugins.xenchant.commands;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XEnchantTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(command.getName().equalsIgnoreCase("xench") || command.getName().equalsIgnoreCase("xenchant"))) {
            return null;
        }

        switch (args.length) {
            case 0: {
                List<String> availableEnchantsAndCommands = new ArrayList<>();

                for (Map.Entry<String, Enchant<? extends Listener>> enchant : EnchantRegistry.EnchantList.entrySet()) {
                    availableEnchantsAndCommands.add(enchant.getKey());
                }

                availableEnchantsAndCommands.add("update");
                availableEnchantsAndCommands.add("reload");

                return availableEnchantsAndCommands;
            }

            case 1: {
                if (EnchantRegistry.getEnchant(args[1]) == null) {
                    return null;
                }

                List<String> availableEnchantLevels = new ArrayList<>();

                availableEnchantLevels.add("1");
                availableEnchantLevels.add("2");
                availableEnchantLevels.add("3");
                availableEnchantLevels.add("4");
                availableEnchantLevels.add("5");
                availableEnchantLevels.add("6");
                availableEnchantLevels.add("7");
                availableEnchantLevels.add("8");
                availableEnchantLevels.add("9");
                availableEnchantLevels.add("10");

                return availableEnchantLevels;
            }
            default: {
                return null;
            }
        }
    }
}
