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

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.ANVIL) return;
        if (event.getRawSlot() != event.getView().convertSlot(event.getRawSlot())) return;

        ItemStack itemToChange = event.getInventory().getItem(0);
        ItemStack itemToAdd = event.getInventory().getItem(1);

        if (itemToChange == null || itemToAdd == null) return;
        if (!EnchantUtil.verifyEnchantCompatibility("lifesteal", itemToChange)) return;
        if (itemToAdd.getType() != Material.WITHER_SKELETON_SKULL) return;

        int enchantLevel = EnchantUtil.parseEnchant("lifesteal", itemToChange);
        enchantLevel += itemToAdd.getAmount();

        if (enchantLevel > EnchantRegistry.getEnchant("lifesteal").MaxLevel) return;

        ItemStack result = itemToChange.clone();
        EnchantUtil.modifyEnchant(result, EnchantRegistry.getEnchant("lifesteal"), enchantLevel);

        if (event.getRawSlot() != 2) {
            return;
        }

        // debug
        event.getClickedInventory().setItem(0, new ItemStack(Material.AIR));
        event.getClickedInventory().setItem(1, new ItemStack(Material.AIR));
        event.getClickedInventory().setItem(2, new ItemStack(Material.AIR));
        event.getWhoClicked().setItemOnCursor(result);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {return;}
        Player damager = (Player)event.getDamager();
        
        int enchantLevel = EnchantUtil.parseEnchant("lifesteal", damager.getInventory().getItemInMainHand());

        if (enchantLevel < 1) {
            return;
        }

        double amountToHeal = event.getFinalDamage() * (new Random().nextFloat() * 0.15 * enchantLevel); //can be a value between 0-60% of the damage dealt on lifesteal 4
        
        if (amountToHeal > damager.getMaxHealth()) {
            amountToHeal = damager.getMaxHealth();
        }
        damager.setHealth(damager.getHealth() + amountToHeal);
    }
}
