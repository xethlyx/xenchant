package com.xethlyx.plugins.xenchant.util;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BukkitCancellingRunnable extends BukkitRunnable {
    private BukkitTask RunningTask;

    public void setTask(BukkitTask task) {
        RunningTask = task;
    }

    public BukkitTask getTask() {
        return RunningTask;
    }

    @Override
    public void run() {
        // this should be overridden
    }
}
