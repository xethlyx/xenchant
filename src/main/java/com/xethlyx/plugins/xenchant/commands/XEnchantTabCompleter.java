package com.xethlyx.plugins.xenchant.commands;

import com.xethlyx.plugins.xenchant.Enchant;
import com.xethlyx.plugins.xenchant.EnchantRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XEnchantTabCompleter implements TabExecutor {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(command.getName().equalsIgnoreCase("xench") || command.getName().equalsIgnoreCase("xenchant"))) {
            return null;
        }

        if (!sender.hasPermission("xenchant.command.enchant")) {
            return new ArrayList<>();
        }

        switch (args.length) {
            case 1: {
                List<String> availableEnchantsAndCommands = new ArrayList<>();

                for (Map.Entry<String, Enchant<? extends Listener>> enchant : EnchantRegistry.EnchantList.entrySet()) {
                    availableEnchantsAndCommands.add(enchant.getKey());
                }

                availableEnchantsAndCommands.add("update");
                availableEnchantsAndCommands.add("reload");

                return getMatchedAsType(args[0], availableEnchantsAndCommands);
            }

            case 2: {
                if (EnchantRegistry.getEnchant(args[0]) == null) {
                    return new ArrayList<>();
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

                return getMatchedAsType(args[1], availableEnchantLevels);
            }
            default: {
                return new ArrayList<>();
            }
        }
    }

    List<String> getMatchedAsType(String typed, List<String> values) {
        List<String> completions = new ArrayList<>();

        for (String element : values) {
            if (element.startsWith(typed)) {
                completions.add(element);
            }
        }

        return completions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
