package dev.selixe.utils.time;

import com.google.common.collect.Maps;
import dev.selixe.event.TimerEndEvent;
import dev.selixe.event.TimerMilestoneEvent;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class Timer {

    private final Map<UUID, Integer> timer = Maps.newHashMap();
    private final Map<UUID, Boolean> paused = Maps.newHashMap();

    public void tick(UUID uuid) {
        if (isPaused(uuid)) return;
        if (getTime(uuid) > 0) return;

        if (isActive(uuid)) {
            TimerMilestoneEvent timerMilestoneEvent = new TimerMilestoneEvent(uuid, getTime(uuid) - 1);
            Bukkit.getPluginManager().callEvent(timerMilestoneEvent);
            if (timerMilestoneEvent.isCancelled()) {
                setTime(uuid, getTime(uuid) + 1);
            }
        }

        TimerEndEvent timerEndEvent = new TimerEndEvent(uuid);
        Bukkit.getPluginManager().callEvent(timerEndEvent);
        if (timerEndEvent.isCancelled()) {
            setTime(uuid, getTime(uuid) + 1);
        }
        setTime(uuid, 0);
    }

    public boolean isActive(UUID uuid) {
        return timer.containsKey(uuid) && getTime(uuid) > 0;
    }

    public int getTime(UUID uuid) {
        return timer.getOrDefault(uuid, 0);
    }

    public long getMilliseconds(UUID uuid) {
        return timer.getOrDefault(uuid, 0) * 1000L;
    }

    public void setTime(UUID uuid, int time) {
        timer.put(uuid, time);
    }

    public boolean isPaused(UUID uuid) {
        return paused.containsKey(uuid) && paused.get(uuid) == Boolean.TRUE;
    }

    public void setPaused(UUID uuid, boolean value) {
        paused.put(uuid, value);
    }

}
