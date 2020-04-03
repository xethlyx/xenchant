package com.xethlyx.plugins.xenchant.enchants;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class BeheadingListeners implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // debug
        Entity victim = event.getEntity();

        victim.sendMessage("you died");
    }
}
