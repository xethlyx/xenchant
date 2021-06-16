package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

class ItemData {
    public int location;
    public ItemStack item;

    public ItemData(int newLocation, ItemStack newItem) {
        location = newLocation;
        item = newItem;
    }
}

public class SoulboundListeners implements Listener {
    HashMap<UUID, ArrayList<ItemData>> soulbound = new HashMap<>();

    @EventHandler
    void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Inventory playerInventory = player.getInventory();

        ArrayList<ItemData> soulboundItems = new ArrayList<>();

        for (int i = 0; i < playerInventory.getSize(); i++) {
            ItemStack item = playerInventory.getItem(i);

            int enchantLevel = EnchantUtil.parseEnchant("soulbound", item);
            if (enchantLevel < 1) continue;

            event.getDrops().remove(item);
            soulboundItems.add(new ItemData(i, item));
        }

        if (soulboundItems.size() == 0) return;

        soulbound.put(player.getUniqueId(), soulboundItems);
    }

    @EventHandler
    void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Inventory playerInventory = player.getInventory();

        if (!soulbound.containsKey(player.getUniqueId())) return;
        ArrayList<ItemData> soulboundItems = soulbound.get(player.getUniqueId());

        for (ItemData itemData : soulboundItems) {
            System.out.println("soulbound restore");
            playerInventory.setItem(itemData.location, itemData.item);
        }

        soulbound.remove(player.getUniqueId());
    }
}
