package com.xethlyx.plugins.xenchant.util;

import com.xethlyx.plugins.xenchant.XEnchant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class EnchantListener implements Listener {
    @EventHandler
    void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();

        ItemStack first = anvilInventory.getItem(0);
        ItemStack second = anvilInventory.getItem(1);

        if (first == null || second == null) return;

        ItemStack newResult = EnchantUtil.mergeEnchant(first, second, event.getResult());
        if (newResult == null) return;

        event.setResult(newResult);
        XEnchant.Instance.getServer().getScheduler().runTask(XEnchant.Instance, () -> anvilInventory.setRepairCost(5));
    }
}
