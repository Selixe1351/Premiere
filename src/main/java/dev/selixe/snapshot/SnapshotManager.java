package dev.selixe.snapshot;

import dev.selixe.utils.bukkit.location.Position;
import lombok.Getter;
import org.bukkit.block.BlockState;

import java.util.Map;
import java.util.TreeMap;

/**
 * Copyright (c) 2025 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter
public class SnapshotManager {

    private final Map<Long, Map<Position, BlockState>> snapshots = new TreeMap<>();

}
