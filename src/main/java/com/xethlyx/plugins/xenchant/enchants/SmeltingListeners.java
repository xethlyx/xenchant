package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.XEnchant;
import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SmeltingListeners implements Listener {
    HashMap<Material, Material> SmeltConversionTable = new HashMap<Material, Material>() {{
        put(Material.IRON_ORE, Material.IRON_INGOT);
        put(Material.GOLD_ORE, Material.GOLD_INGOT);
    }};

    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        int enchantLevel = EnchantUtil.parseEnchant("smelting", player.getInventory().getItemInMainHand());

        if (enchantLevel == 0) return;
        if (!event.isDropItems()) return;

        XEnchant.Instance.getLogger().info("Can drop");

        event.setDropItems(false);

        for (int i = 0; i < event.getBlock().getDrops().size(); i++) {
            Material block = (Material) event.getBlock().getDrops().toArray()[i];
            Material replacement = SmeltConversionTable.get(block);

            XEnchant.Instance.getLogger().info("Searching drops");

            if (replacement == null) continue;
            
            XEnchant.Instance.getLogger().info("Dropping");

            event.getBlock().setType(Material.AIR);

            event.getBlock().getWorld().dropItem(
                event.getBlock().getLocation(),
                new ItemStack(replacement)
            );
        }
    }
}
