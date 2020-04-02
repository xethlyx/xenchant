package com.xethlyx.plugins.xenchant.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XEnchantCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch(args[0]) {
            case "update": {
                sender.sendMessage("Checking for updates..");
            }
            case "reload": {
                sender.sendMessage("Reloading..");
            }
            default: {
                sender.sendMessage("Applying enchant " + args[0]);
            }
        }
        return true;
    }

}
