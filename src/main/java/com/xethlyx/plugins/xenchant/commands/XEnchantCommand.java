package com.xethlyx.plugins.xenchant.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xethlyx.plugins.xenchant.XEnchant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

public class XEnchantCommand implements CommandExecutor {
    public static void updatePlugin() throws Exception {
        final String fileName = new java.io.File(XEnchant.class.getProtectionDomain()
            .getCodeSource()
            .getLocation()
            .getPath())
            .getName();

        final String downloadUrl = "https://jenkins.xethlyx.com/job/XEnchant/lastSuccessfulBuild/artifact/build/libs/xenchant-1.0-SNAPSHOT.jar";
        final String jsonUrl = "https://jenkins.xethlyx.com/job/XEnchant/api/json";
        final String currentVersion = XEnchant.Instance.getDescription().getVersion();

        String latestVersion;
        // First check for updates
        {
            URLConnection request = new URL(jsonUrl).openConnection();
            request.addRequestProperty("User-Agent", "Mozilla");
            request.getHeaderField("cache-control: no-cache");
            request.connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootObj = root.getAsJsonObject();
            latestVersion = rootObj.get("lastSuccessfulBuild").getAsJsonObject().get("number").getAsString();
        }

        if ((currentVersion.substring(0, currentVersion.length() - (latestVersion.length() + 1)) + latestVersion).equals("-" + currentVersion)) {
            throw new Exception("Already up to date!");
        }

        URLConnection downloadConnection = null;

        downloadConnection = new URL(downloadUrl + "?t=" + System.currentTimeMillis()).openConnection();
        downloadConnection.addRequestProperty("User-Agent", "Mozilla");
        downloadConnection.getHeaderField("cache-control: no-cache");
        downloadConnection.setReadTimeout(5000);
        downloadConnection.setConnectTimeout(5000);

        BufferedInputStream in = new BufferedInputStream(downloadConnection.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream("plugins/" + fileName);

        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
    }

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
                sender.sendMessage(ChatColor.GRAY + "Current download path is " + "plugins/" + fileName);

                try {
                    updatePlugin();
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "The plugin could not auto update: " + e);
                    return true;
                }

                sender.sendMessage(ChatColor.GREEN + "Downloaded! Attempting to apply update automatically...");
                sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Current version: " + ChatColor.GREEN + XEnchant.Instance.getDescription().getVersion());
                sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Updated version: " + ChatColor.GREEN + "Latest");
                sender.sendMessage(ChatColor.RED + "We currently cannot update automatically - you will need to restart the plugin manually.");

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
