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

public class StrongwillListeners implements Listener {
    private void updatePlayerLeash(Player user, Player target, LivingEntity dummy) {
        if (!dummy.isLeashed() || !target.isOnline() || !user.isOnline()) { //if the player broke out of leash, or if the leash should not exist
            dummy.setHealth(0);
            return;
        }
        dummy.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1, false, false));
        Vector playerVelocity = player.getVelocity();
        player.setVelocity(dummy.getVelocity()); //apply force from restraints
        dummy.setVelocity(playerVelocity); //apply forces from player movement
        //note that this will create a 1 game tick delay for movement
        new updatePlayerLeash(user, target, dummy).runTaskLater(user, target, dummy, 1);
    }
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
      Player user = event.getPlayer();
      if (!(event.clickedEntity instanceof LivingEntity)) return;
      LivingEntity target = (LivingEntity)event.clickedEntity; //not a method; clickedEntity is a field
      if (EnchantUtil.parseEnchant("strongwill", user.getInventory().getHelmet()) == 0) return;
      ItemStack mainHand = user.getItemInMainHand();
      if (mainHand.getType() != Material.LEAD) return;
      if (!(target.setLeashHolder(user) || target instanceof Player)) return; //check for success
      if (target instanceof Player) {
          //TODO: check if player is punished and don't allow punished players to escape
          LivingEntity dummy = (LivingEntity)target.getWorld().spawnEntity(target.getLocation(), EntityType.BAT); //note that this doesn't account for the entity cramming limit
          dummy.setInvulnerable(true);
          dummy.setAI(false);
          dummy.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1, false, false));
          new updatePlayerLeash(user, target, dummy).runTaskLater(user, target, dummy, 1);
      }
      mainHand.setAmount(mainHand.getAmount() - 1); //take a lead
    }
}
