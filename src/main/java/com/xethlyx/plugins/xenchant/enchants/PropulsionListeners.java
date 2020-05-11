package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PropulsionListeners implements Listener {
    @EventHandler
    public void onEntityToggleGlide(EntityToggleGlideEvent event) {
        if (!event.isGliding()) return;

        if (!(event.getEntity() instanceof Player)) return;
        Player eventEntity = (Player) event.getEntity();
        if (!eventEntity.isSprinting()) return;

        ItemStack entityChestplate = eventEntity.getInventory().getChestplate();

        if (entityChestplate.getType() != Material.ELYTRA) return;

        if (EnchantUtil.parseEnchant("propulsion", entityChestplate) > 0) {
            eventEntity.setVelocity(eventEntity.getLocation().getDirection().multiply(1.5));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR) return;
        if (!event.getPlayer().isGliding()) return;

        int enchantLevel = EnchantUtil.parseEnchant("propulsion", event.getPlayer().getInventory().getChestplate());

        if (enchantLevel < 2) return;

        int extraMult = 1;

        if (enchantLevel == 3) extraMult *= 2;

        event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(extraMult));
    }
}
