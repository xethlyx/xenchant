package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.EnchantRegistry;
import com.xethlyx.plugins.xenchant.XEnchant;
import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Random;

public class BeheadingListeners implements Listener {

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        ItemStack itemToChange = event.getInventory().getItem(0);
        if (event.getResult() != null && event.getResult().getType() != Material.AIR) {
            itemToChange = event.getResult();
        }

        ItemStack itemToAdd = event.getInventory().getItem(1);

        if (itemToChange == null || itemToAdd == null) return;
        if (!EnchantUtil.verifyEnchantCompatibility("beheading", itemToChange)) return;
        if (itemToAdd.getType() != Material.WITHER_SKELETON_SKULL) return;

        int enchantLevel = EnchantUtil.parseEnchant("beheading", itemToChange);
        enchantLevel += itemToAdd.getAmount();

        if (enchantLevel > EnchantRegistry.getEnchant("beheading").MaxLevel) return;

        ItemStack result = itemToChange.clone();
        EnchantUtil.modifyEnchant(result, EnchantRegistry.getEnchant("beheading"), enchantLevel);

        event.getInventory().setRepairCost(5 * itemToAdd.getAmount());
        event.getInventory().setItem(2, result);
        event.setResult(result);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.ANVIL) return;
        XEnchant.Instance.getLogger().info("Is anvil " + event.getSlot() + " " + event.getRawSlot());
        if (event.getRawSlot() != event.getView().convertSlot(event.getRawSlot())) return;
        XEnchant.Instance.getLogger().info("Is in anvil at " + event.getRawSlot());
        if (event.getRawSlot() != 2) return;
        XEnchant.Instance.getLogger().info("Is result");

        ItemStack itemToChange = event.getInventory().getItem(0);
        ItemStack itemToAdd = event.getInventory().getItem(1);

        if (itemToChange == null || itemToAdd == null) return;
        if (!EnchantUtil.verifyEnchantCompatibility("beheading", itemToChange)) return;
        if (itemToAdd.getType() != Material.WITHER_SKELETON_SKULL) return;

        XEnchant.Instance.getLogger().info("pass stage 1");

        int enchantLevel = EnchantUtil.parseEnchant("beheading", itemToChange);
        enchantLevel += itemToAdd.getAmount();

        if (enchantLevel > EnchantRegistry.getEnchant("beheading").MaxLevel) return;
        XEnchant.Instance.getLogger().info("pass stage 2");

        ItemStack result = itemToChange.clone();
        EnchantUtil.modifyEnchant(result, EnchantRegistry.getEnchant("beheading"), enchantLevel);
        XEnchant.Instance.getLogger().info("set res");

        event.setCurrentItem(result);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity victim = event.getEntity();
        Player killer = event.getEntity().getKiller();

        if (killer == null) {
            return;
        }

        int enchantLevel = EnchantUtil.parseEnchant("beheading", killer.getInventory().getItemInMainHand());

        if (enchantLevel < 1) {
            return;
        }

        if (new Random().nextFloat() > enchantLevel * 0.1) {
            // RNGesus did not say yes
            return;
        }
        
        ItemStack skull = null;

        if (victim instanceof Player) {
            skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();

            meta.setOwningPlayer((OfflinePlayer) victim);
            skull.setItemMeta(meta);
        } else if (victim instanceof Zombie) {
            skull = new ItemStack(Material.ZOMBIE_HEAD);
        } else if (victim instanceof WitherSkeleton) {
            skull = new ItemStack(Material.WITHER_SKELETON_SKULL);
        } else if (victim instanceof Skeleton) {
            skull = new ItemStack(Material.SKELETON_SKULL);
        } else if (victim instanceof Creeper) {
            skull = new ItemStack(Material.CREEPER_HEAD);
        } else if (victim instanceof EnderDragon) {
            skull = new ItemStack(Material.DRAGON_HEAD);
        }

        if (skull != null) {
            event.getDrops().add(skull);
        }
    }
}
