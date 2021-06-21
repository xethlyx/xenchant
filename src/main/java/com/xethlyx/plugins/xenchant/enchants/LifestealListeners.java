package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class LifestealListeners implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return; //ignore hits on armor stands and such

        Entity damagerEntity = event.getDamager();
        if (!(damagerEntity instanceof Player)) return;

        Player damager = (Player) damagerEntity;
        
        int enchantLevel = EnchantUtil.parseEnchant("lifesteal", damager.getInventory().getItemInMainHand());
        if (enchantLevel < 1) return;

        double newHealth = damager.getHealth() + event.getFinalDamage() * (new Random().nextFloat() * 0.1 * enchantLevel); //can be a value between 0-40% of the damage dealt on lifesteal 4
        double damagerMaxHealth = damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (newHealth > damagerMaxHealth) newHealth = damagerMaxHealth;

        damager.setHealth(newHealth);
    }
}
