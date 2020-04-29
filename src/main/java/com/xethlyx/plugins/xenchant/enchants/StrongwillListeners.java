package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.XEnchant;
import com.xethlyx.plugins.xenchant.util.BukkitCancellingRunnable;
import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class StrongwillListeners implements Listener {
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player user = event.getPlayer();
        if (!(event.getRightClicked() instanceof LivingEntity)) return;

        LivingEntity target = (LivingEntity) event.getRightClicked();

        if (EnchantUtil.parseEnchant("strongwill", user.getInventory().getHelmet()) == 0) return;

        ItemStack mainHand = user.getInventory().getItemInMainHand();

        if (mainHand.getType() != Material.LEAD) return;
        if (!(target.setLeashHolder(user) || target instanceof Player)) return; //check for success

        if (target instanceof Player) {
            //TODO: check if player is punished and don't allow punished players to escape
            LivingEntity dummy = (LivingEntity) target.getWorld().spawnEntity(target.getLocation(), EntityType.BAT); //note that this doesn't account for the entity cramming limit

            dummy.setInvulnerable(true);
            dummy.setAI(false);
            dummy.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2 ^ 31, 1, false, false));

            BukkitTask task = null;

            BukkitCancellingRunnable runnable = new BukkitCancellingRunnable() {
                @Override
                public void run() { 
                    if (!dummy.isLeashed() || !((Player)target).isOnline() || !user.isOnline()) { //if the player broke out of leash, or if the leash should not exist
                        if (((Player) target).isOnline() && user.isOnline() && !target.hasPermission("xethlyx.FREE")) {
                            target.teleport(dummy);
                            dummy.setLeashHolder(user);
                        }
                        dummy.setHealth(0);

                        getTask().cancel();
                        return;
                    }

                    dummy.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1, false, false));

                    Vector playerVelocity = user.getVelocity();
                    user.setVelocity(dummy.getVelocity()); //apply force from restraints
                    dummy.setVelocity(playerVelocity); //apply forces from player movement
                    //note that this will create a 1 game tick delay for movement
                }
            };

            task = runnable.runTaskTimer(XEnchant.Instance, 1, 1); // delay so the runnable can be told its task
            runnable.setTask(task);
        }
        mainHand.setAmount(mainHand.getAmount() - 1); //take a lead
    }
}
