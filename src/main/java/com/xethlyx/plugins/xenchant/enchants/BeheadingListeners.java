package com.xethlyx.plugins.xenchant.enchants;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BeheadingListeners implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity victim = event.getEntity();
        Player killer = event.getEntity().getKiller();

        if (killer == null) {
            return;
        }

        int enchantLevel = new Beheading().parseEnchant(killer.getInventory().getItemInMainHand());

        if (enchantLevel < 1) {
            return;
        }
        
        ItemStack skull = null;

        if (victim instanceof Player) {
            skull = new ItemStack(Material.getMaterial("PLAYER_HEAD"));
            SkullMeta meta = (SkullMeta) skull.getItemMeta();

            meta.setOwningPlayer((OfflinePlayer) victim);
        } else if (victim instanceof Zombie) {
            skull = new ItemStack(Material.getMaterial("ZOMBIE_HEAD"));
        } else if (victim instanceof WitherSkeleton) {
            skull = new ItemStack(Material.getMaterial("WITHER_SKELETON_SKULL"));
        } else if (victim instanceof Skeleton) {
            skull = new ItemStack(Material.getMaterial("SKELETON_SKULL"));
        } else if (victim instanceof Creeper) {
            skull = new ItemStack(Material.getMaterial("CREEPER_HEAD"));
        } else if (victim instanceof EnderDragon) {
            skull = new ItemStack(Material.getMaterial("DRAGON_HEAD"));
        }

        if (skull != null) {
            event.getDrops().add(skull);
        }
    }
}
