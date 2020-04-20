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

public class WitherListeners implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player damager = (Player)event.getDamager();
        if (!(event.getEntity() instanceof LivingEntity)) return;
        LivingEntity damaged = (LivingEntity)event.getEntity();
        int enchantLevel = EnchantUtil.parseEnchant("wither", damager.getInventory().getItemInMainHand());

        if (enchantLevel < 1) return;
        if ((new Random().nextFloat()) > (enchantLevel * 0.1)) return;
        
        damaged.addPotionEffect(PotionEffect(PotionEffectType.WITHER, new Random().nextFloat() * 2 + 4, enchantLevel)); //random length between 4 and 6 seconds
    }
}
