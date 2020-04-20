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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class LifestealListeners implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player damager = (Player)event.getDamager();
        
        int enchantLevel = EnchantUtil.parseEnchant("lifesteal", damager.getInventory().getItemInMainHand());

        if (enchantLevel < 1) return;

        double newHealth = damager.getHealth() (event.getFinalDamage() * (new Random().nextFloat() * 0.15 * enchantLevel)); //can be a value between 0-60% of the damage dealt on lifesteal 4
        
        if (newHealth > damager.getMaxHealth())
            newHealth = damager.getMaxHealth();
        damager.setHealth(newHealth);
    }
}
