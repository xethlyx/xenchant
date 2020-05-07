package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.XEnchant;
import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.Random;

public class InsomniacListeners implements Listener {
    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player)) {
            XEnchant.Instance.getLogger().info("Not player");
            return;
        }
        if (!(event.getEntity() instanceof Phantom)) {
            XEnchant.Instance.getLogger().info("Not phantom");
            return;
        }

        // Phantom target player
        Player player = (Player) event.getTarget();
        LivingEntity entity =  (LivingEntity) event.getEntity();

        XEnchant.Instance.getLogger().info("Passed checks");

        int enchantLevel = EnchantUtil.parseEnchant("insomniac", player.getInventory().getHelmet());


        if (enchantLevel == 0) return;
        XEnchant.Instance.getLogger().info("Passed insomniac check");

        float repelChance = Math.min(enchantLevel * 0.4f, 1);

        if ((new Random().nextFloat()) > (repelChance)) return;
        XEnchant.Instance.getLogger().info("Passed rngesus");

        // RNGesus has approved

        XEnchant.Instance.getLogger().info("Killing phantom");
        entity.setHealth(0);
    }
}
