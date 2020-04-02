package com.xethlyx.plugins.xenchant.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class XEnchantCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch(args[0]) {
            case "update": {
                sender.sendMessage("Checking for updates..");
                break;
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
