package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SmeltingListeners implements Listener {
    HashMap<Material, Material> SmeltConversionTable = new HashMap<Material, Material>() {{
        put(Material.IRON_ORE, Material.IRON_INGOT);
        put(Material.GOLD_ORE, Material.GOLD_INGOT);
    }};

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        int enchantLevel = EnchantUtil.parseEnchant("smelting", player.getInventory().getItemInMainHand());

        if (enchantLevel < 1) return;
        if (!event.isDropItems()) return;

        Material replacement = SmeltConversionTable.get(event.getBlock().getType());

        if (replacement == null) return;

        event.setDropItems(false);
        event.getBlock().getWorld().dropItemNaturally(
                event.getBlock().getLocation().add(0.5, 0.5, 0.5),
                new ItemStack(replacement)
        );
    }
}
