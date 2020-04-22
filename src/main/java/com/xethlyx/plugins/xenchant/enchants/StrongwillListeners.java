package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.XEnchant;
import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.inventory.ItemStack;

import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class StrongwillListeners implements Listener {
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
      Player user = event.getPlayer();
      Entity target = event.clickedEntity; //not a method; clickedEntity is a field
      if (EnchantUtil.parseEnchant("strongwill", user.getInventory().getHelmet()) == 0) return;
      ItemStack mainHand = user.getItemInMainHand();
      if (mainHand.getType() != Material.LEAD) return;
      if (!user.setLeashHolder(target)) return; //check for success
      mainHand.setAmount(mainHand.getAmount() - 1); //take a lead
    }
}
