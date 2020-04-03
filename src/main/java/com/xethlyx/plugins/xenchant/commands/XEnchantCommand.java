package com.xethlyx.plugins.xenchant.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

public class XEnchantCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        switch(args[0]) {
            case "update": {
                sender.sendMessage("Checking for updates..");
                break;
            }
            case "debug": {
                Bukkit.getLogger().setLevel(Level.ALL);
                sender.sendMessage(ChatColor.GREEN + "Logging level has been changed to all.");
            }
            case "reload": {
                sender.sendMessage("Reloading..");
                break;
            }
            default: {
                sender.sendMessage("Applying enchant " + args[0] + "..");
            }
        }
        return true;
    }

}
