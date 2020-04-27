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

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class StrongwillListeners implements Listener {
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player user = event.getPlayer();
        if (!(event.getRightClicked() instanceof LivingEntity)) return;
        LivingEntity target = (LivingEntity) event.getRightClicked();
        if (EnchantUtil.parseEnchant("strongwill", user.getInventory().getHelmet()) == 0) return;
        ItemStack mainHand = user.getInventory().getItemInMainHand();
        if (mainHand.getType() != Material.LEAD) return;
        if (!(target.setLeashHolder(user) || target instanceof Player)) return; //check for success
        if (target instanceof Player) {
            //TODO: check if player is punished and don't allow punished players to escape
            LivingEntity dummy = (LivingEntity) target.getWorld().spawnEntity(target.getLocation(), EntityType.BAT); //note that this doesn't account for the entity cramming limit
            dummy.setInvulnerable(true);
            dummy.setAI(false);
            dummy.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2 ^ 31, 1, false, false));
            BukkitTask task;
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() { 
                    if (!dummy.isLeashed() || !target.isOnline() || !user.isOnline()) { //if the player broke out of leash, or if the leash should not exist
                        if (target.isOnline() && user.isOnline() && !target.hasPermission("xethlyx.FREE")) {
                            target.teleport(dummy);
                            dummy.setLeashHolder(user);
                        }
                        dummy.setHealth(0);
                        task.cancel();
                        return;
                    }
                    dummy.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1, false, false));
                    Vector playerVelocity = user.getVelocity();
                    user.setVelocity(dummy.getVelocity()); //apply force from restraints
                    dummy.setVelocity(playerVelocity); //apply forces from player movement
                    //note that this will create a 1 game tick delay for movement
                }
            }
            task = runnable.runTaskTimer(this.plugin, 0, 1);
        }
        mainHand.setAmount(mainHand.getAmount() - 1); //take a lead
    }
}
