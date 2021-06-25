package com.xethlyx.plugins.xenchant.enchants;

import com.xethlyx.plugins.xenchant.XEnchant;
import com.xethlyx.plugins.xenchant.util.EnchantUtil;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ZenithListeners extends BukkitRunnable implements Listener {
    HashMap<Player, List<LivingEntity>> zenithTargets = new HashMap<>();
    HashMap<Player, List<Vex>> zenithSubjects = new HashMap<>();
    HashMap<LivingEntity, LivingEntity> subjectTargets = new HashMap<>();
    HashMap<LivingEntity, Player> subjectOwners = new HashMap<>();

    boolean permitted(Player player) {
        boolean developerModeEnabled = XEnchant.Instance.getConfig().getBoolean("developer-mode");
        if (!developerModeEnabled) return true;

        List<String> developerList = XEnchant.Instance.getConfig().getStringList("developer");

        return developerList.contains(player.getUniqueId().toString());
    }

    LivingEntity getLookingAt(Player player) {
        int searchDistance = 50;

        for (Block lineOfSightBlock : player.getLineOfSight(null, searchDistance)) {
            for (Entity nearbyEntity : lineOfSightBlock.getWorld().getNearbyEntities(lineOfSightBlock.getLocation(), 1, 1, 1)) {
                if (nearbyEntity instanceof LivingEntity && nearbyEntity != player && !subjectTargets.containsKey(nearbyEntity) && !nearbyEntity.isDead())
                    return (LivingEntity) nearbyEntity;
            }
        }

        return null;
    }

    boolean zenithIsLoaded(Player player) {
        return zenithSubjects.containsKey(player);
    }

    boolean subjectCanContinue(Player player, Vex subject) {
        if (player.getWorld() != subject.getWorld()) return false;
        if (player.getLocation().subtract(subject.getLocation()).length() > 100) return false;

        LivingEntity entity = subjectTargets.get(subject);
        if (entity.isDead()) return false;

        return true;
    }

    void loadZenith(Player player) {
        zenithTargets.put(player, new ArrayList<>());
        zenithSubjects.put(player, new ArrayList<>());
    }

    void unloadZenith(Player player) {
        List<Vex> subjectList = zenithSubjects.get(player);
        List<Vex> pendingRemoval = new ArrayList<>(subjectList);
        for (Vex subject : pendingRemoval) {
            despawnSubject(subject);
        }

        zenithTargets.remove(player);
        zenithSubjects.remove(player);
    }

    void spawnZenithSubject(Player player, LivingEntity target) {
        if (blasphemy(player)) return;

        List<LivingEntity> currentTargets = zenithTargets.get(player);
        if (!currentTargets.contains(target)) return;

        List<Vex> currentSubjects = zenithSubjects.get(player);

        Vex subject = (Vex) player.getWorld().spawnEntity(player.getLocation(), EntityType.VEX);
        subject.setTarget(target);
        subject.setCustomName(player.getDisplayName() + ChatColor.GRAY + "'s Zenith");

        AttributeInstance subjectAttribute = subject.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        AttributeInstance playerAttribute = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        subjectAttribute.setBaseValue(playerAttribute.getValue());

        currentSubjects.add(subject);
        subjectTargets.put(subject, target);
        subjectOwners.put(subject, player);
    }

    void despawnSubject(Vex subject) {
        Player player = subjectOwners.get(subject);

        List<Vex> currentSubjects = zenithSubjects.get(player);
        if (currentSubjects != null) currentSubjects.remove(subject);
        subjectOwners.remove(subject);
        subject.remove();
    }

    boolean blasphemy(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        if (EnchantUtil.parseEnchant("zenith", playerInventory.getItemInMainHand()) == 0) return true;
        if (EnchantUtil.parseEnchant("zenith", playerInventory.getChestplate()) == 0) return true;

        return false;
    }

    @EventHandler
    void onDisable(PluginDisableEvent event) {
        Set<Player> players = zenithTargets.keySet();
        for (Player player : players) {
            unloadZenith(player);
        }
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (zenithIsLoaded(player)) {
            unloadZenith(player);
        }
    }

    @EventHandler
    void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if (subjectTargets.containsKey(entity)) {
            despawnSubject((Vex) entity);
            return;
        }

        for (Map.Entry<Player, List<LivingEntity>> entry : zenithTargets.entrySet()) {
            Player player = entry.getKey();
            List<LivingEntity> currentTargets = entry.getValue();

            if (!currentTargets.contains(entity)) continue;
            currentTargets.remove(entity);

            if (!zenithSubjects.containsKey(player)) continue;

            List<Vex> pendingRemoval = new ArrayList<>();
            for (Vex subject : zenithSubjects.get(player)) {
                LivingEntity currentTarget = subjectTargets.get(subject);

                if (currentTarget != entity) continue;
                pendingRemoval.add(subject);
            }

            for (Vex subject : pendingRemoval) {
                despawnSubject(subject);
            }
        }
    }

    @EventHandler
    void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Vex)) return;

        Entity damager = event.getDamager();
        if (subjectOwners.get(entity) != damager) return;

        event.setCancelled(true);
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        Action eventAction = event.getAction();
        if (eventAction != Action.LEFT_CLICK_AIR && eventAction != Action.LEFT_CLICK_BLOCK) return;

        Player player = event.getPlayer();

        if (blasphemy(player)) return;

        if (!permitted(player)) return;

        LivingEntity target = getLookingAt(player);
        if (target == null) return;

        if (!zenithIsLoaded(player)) loadZenith(player);

        if (!zenithTargets.containsKey(player)) zenithTargets.put(player, new ArrayList<>());
        List<LivingEntity> currentTargets = zenithTargets.get(player);
        currentTargets.add(target);

        spawnZenithSubject(player, target);
        spawnZenithSubject(player, target);
    }

    @EventHandler
    void onEntityTarget(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();

        if (!(entity instanceof Vex)) return;
        if (!subjectTargets.containsKey(entity)) return;

        LivingEntity oldTarget = subjectTargets.get(entity);
        if (oldTarget != target) {
            event.setCancelled(true);
            ((Vex) entity).setTarget(oldTarget);
        }
    }

    @Override
    public void run() {
        for (Map.Entry<Player, List<Vex>> entry : zenithSubjects.entrySet()) {
            Player player = entry.getKey();
            List<Vex> subjectList = entry.getValue();

            if (blasphemy(player)) {
                unloadZenith(player);
                continue;
            }

            List<Vex> pendingRemoval = new ArrayList<>();
            for (Vex subject : subjectList) {
                if (subjectCanContinue(player, subject)) continue;
                pendingRemoval.add(subject);
            }

            for (Vex subject : pendingRemoval) {
                despawnSubject(subject);
            }

            List<LivingEntity> targetList = zenithTargets.get(player);
            targetList.removeIf(LivingEntity::isDead);
        }
    }
}
