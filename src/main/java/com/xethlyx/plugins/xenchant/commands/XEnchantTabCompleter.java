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
        if (!(command.getName().equalsIgnoreCase("xench") || command.getName().equalsIgnoreCase("xenchant") || command.getName().equalsIgnoreCase("xenchant:xench") || command.getName().equalsIgnoreCase("xenchant:xenchant"))) {
            return new ArrayList<>();
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
                Enchant completedEnchant = EnchantRegistry.getEnchant(args[0]);

                if (completedEnchant == null) {
                    return new ArrayList<>();
                }

                List<String> availableEnchantLevels = new ArrayList<>();

                // Loop to max level
                for (int i = 0; i <= completedEnchant.MaxLevel; i++) {
                    availableEnchantLevels.add(Integer.toString(i));
                }

                return getMatchedAsType(args[1], availableEnchantLevels);
            }
            default: {
                return new ArrayList<>();
            }
        }
    }

    private List<String> getMatchedAsType(String typed, List<String> values) {
        List<String> completions = new ArrayList<>();

        for (String element : values) {
            if (element.startsWith(typed)) {
                completions.add(element);
            }
        }

        return completions;
    }
}
