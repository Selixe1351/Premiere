package dev.selixe.utils.time;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class Cooldown<T> {

    private final long cooldown;

    public Cooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    private final Map<T, Long> timer = Maps.newHashMap();

    public void set(T key) {
        timer.put(key, System.currentTimeMillis() + cooldown);
    }

    public void remove(T key) {
        timer.remove(key);
    }

    public boolean active(T key) {
        return timer.containsKey(key) && timer.get(key) > System.currentTimeMillis();
    }
}
