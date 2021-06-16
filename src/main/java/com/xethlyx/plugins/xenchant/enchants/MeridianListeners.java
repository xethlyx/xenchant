package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.Console;
import java.util.*;

public class MeridianListeners extends BukkitRunnable implements Listener {
    Map<Player, ArmorStand> activeMeridian = new HashMap<>();
    Map<Player, Vector> activeMeridianVelocity = new HashMap<>();
    Map<Player, List<LivingEntity>> targetingEntities = new HashMap<>();

    float accelerationSpeed = 1f;

    LivingEntity getLookingAt(Player player) {
        int searchDistance = 50;

        for (Block lineOfSightBlock : player.getLineOfSight(null, searchDistance)) {
            for (Entity nearbyEntity : lineOfSightBlock.getWorld().getNearbyEntities(lineOfSightBlock.getLocation(), 1, 1, 1)) {
                if (nearbyEntity instanceof LivingEntity && !(nearbyEntity instanceof ArmorStand) && nearbyEntity != player)
                    return (LivingEntity) nearbyEntity;
            }
        }

        return null;
    }

    void generateMeridian(Player player) {
        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        armorStand.setSmall(true);

        EntityEquipment playerInventory = player.getEquipment();
        ItemStack mainHand = playerInventory.getItemInMainHand();

        EntityEquipment equipment = armorStand.getEquipment();
        equipment.setHelmet(mainHand.clone());

        activeMeridian.put(player, armorStand);
        activeMeridianVelocity.put(player, new Vector(0, 0, 0));
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        Action eventAction = event.getAction();
        if (eventAction != Action.LEFT_CLICK_AIR && eventAction != Action.LEFT_CLICK_BLOCK) return;

        Player player = event.getPlayer();

        int enchantLevel = EnchantUtil.parseEnchant("meridian", player.getInventory().getItemInMainHand());
        if (enchantLevel < 1) return;

        if (!targetingEntities.containsKey(player)) targetingEntities.put(player, new ArrayList<>());

        LivingEntity entity = getLookingAt(player);
        if (entity != null) {
            targetingEntities.get(player).add(entity);
        }

        if (!activeMeridian.containsKey(player)) {
            generateMeridian(player);
        }
    }

    void despawnMeridian(Player player) {
        ArmorStand meridian = activeMeridian.get(player);
        meridian.remove();
        activeMeridian.remove(player);
        activeMeridianVelocity.remove(player);
        targetingEntities.remove(player);
    }

    @EventHandler
    void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ArmorStand meridian = activeMeridian.get(player);
        if (meridian == null) return;

        despawnMeridian(player);
    }

    @EventHandler
    void onDisable(PluginDisableEvent event) {
        for (Map.Entry<Player, ArmorStand> entry : activeMeridian.entrySet()) {
            Player player = entry.getKey();
            despawnMeridian(player);
        }
    }

    Location lerp(Location locationA, Location locationB, float factor) {
        return locationA.multiply(1 - factor).add(locationB.multiply(factor));
    }

    boolean shouldBeActive(Player player, ArmorStand meridian) {
        if (meridian.isDead()) {
            return false;
        }

        if (meridian.getWorld() != player.getWorld()) {
            return false;
        }

        if (meridian.getLocation().subtract(player.getLocation()).length() > 100) {
            return false;
        }

        EntityEquipment playerInventory = player.getEquipment();
        if (playerInventory == null) {
            return false;
        }

        EntityEquipment meridianEquipment = meridian.getEquipment();
        if (meridianEquipment == null) {
            return false;
        }

        ItemStack playerItem = playerInventory.getItemInMainHand();
        ItemStack meridianItem = meridianEquipment.getHelmet();

        if (!playerItem.isSimilar(meridianItem)) {
            return false;
        }

        return true;
    }

    @Override
    public void run() {
        for (Map.Entry<Player, ArmorStand> entry : activeMeridian.entrySet()) {
            Player player = entry.getKey();
            ArmorStand meridian = entry.getValue();

            if (!shouldBeActive(player, meridian)) {
                despawnMeridian(player);
            }

            List<LivingEntity> currentTargetingEntities = targetingEntities.get(player);
            currentTargetingEntities.removeIf(Entity::isDead);

            AttributeInstance damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            double attackDamage = damage.getValue();

            for (Entity entity : meridian.getNearbyEntities(2, 2, 2)) {
                if (!(entity instanceof LivingEntity)) continue;
                if (!currentTargetingEntities.contains(entity)) continue;

                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(attackDamage, player);
            }

            if (currentTargetingEntities.isEmpty()) {
                activeMeridianVelocity.put(player, new Vector(0, 0, 0));

                Location restingPosition = player.getEyeLocation().add(0, 1, 0);
                meridian.teleport(lerp(meridian.getLocation(), restingPosition, accelerationSpeed));
                return;
            }

            LivingEntity currentTarget = currentTargetingEntities.get(0);

            Vector direction = currentTarget.getLocation().subtract(meridian.getLocation()).toVector().normalize();
            Vector currentVelocity = activeMeridianVelocity.get(player).multiply(0.5);
            activeMeridianVelocity.put(player, currentVelocity.add(direction.multiply(accelerationSpeed)));

            meridian.teleport(meridian.getLocation().add(activeMeridianVelocity.get(player)));
        }
    }
}
