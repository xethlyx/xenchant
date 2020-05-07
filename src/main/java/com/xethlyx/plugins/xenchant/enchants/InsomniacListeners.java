package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.Random;

public class InsomniacListeners implements Listener {
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player)) return;
        if (!(event.getEntity() instanceof Phantom)) return;

        // Phantom target player
        Player player = (Player) event.getEntity();
        LivingEntity entity =  (LivingEntity) event.getEntity();

        int enchantLevel = EnchantUtil.parseEnchant("insomniac", player.getInventory().getHelmet());

        if (enchantLevel == 0) return;

        float repelChance = Math.min(enchantLevel * 0.4f, 1);

        if ((new Random().nextFloat()) > (repelChance)) return;

        // RNGesus has approved

        entity.setHealth(0);
    }
}
