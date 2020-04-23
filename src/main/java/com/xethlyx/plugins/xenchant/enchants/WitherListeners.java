package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class WitherListeners implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (event.getCause() == DamageCause.THORNS) return;
        Player damager = (Player)event.getDamager();
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (event.isCancelled()) return;

        Player damager = (Player) event.getDamager();
        LivingEntity damaged = (LivingEntity) event.getEntity();

        int enchantLevel = EnchantUtil.parseEnchant("wither", damager.getInventory().getItemInMainHand());

        if (enchantLevel < 1) return;
        if ((new Random().nextFloat()) > (enchantLevel * 0.1)) return;

        //random length between 4 and 6 seconds
        damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, new Random().nextInt(20*2) + (4*20), enchantLevel));
    }
}
