package com.xethlyx.plugins.xenchant.commands;

import com.xethlyx.plugins.xenchant.XEnchant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

public class XEnchantCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        switch(args[0]) {
            case "update": {
                sender.sendMessage(ChatColor.GREEN + "Checking for updates..");

                String fileName = new java.io.File(XEnchant.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();

                sender.sendMessage(ChatColor.GRAY + "Current file name is " + fileName);

                try (BufferedInputStream in = new BufferedInputStream(new URL("https://jenkins.xethlyx.com/job/XEnchant/lastSuccessfulBuild/artifact/build/libs/xenchant-1.0-SNAPSHOT.jar").openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(XEnchant.Instance.getDataFolder().getAbsolutePath() + fileName)) {
                    byte dataBuffer[] = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "Update failed! Reason: " + e);
                }

                sender.sendMessage(ChatColor.GREEN + "Downloaded! The update will be applied on the next restart.");

                break;
            }
            case "debug": {
                sender.sendMessage(ChatColor.GREEN + "Changing logging level..");
                Bukkit.getLogger().setLevel(Level.ALL);
                sender.sendMessage(ChatColor.GREEN + "Logging level has been changed to all.");
                break;
            }
            case "reload": {
                sender.sendMessage(ChatColor.RED + "Reloading..");
                break;
            }
            default: {
                sender.sendMessage(ChatColor.GREEN + "Applying enchant " + args[0] + "..");
            }
        }
        return true;
    }

}
