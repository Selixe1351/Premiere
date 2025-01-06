package dev.selixe.snapshot;

import dev.selixe.Premiere;
import dev.selixe.utils.bukkit.location.Position;
import org.bukkit.World;
import org.bukkit.block.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2025 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */
public interface Snapshotable {

    /**
     * Captures a snapshot of a predefined area
     *
     * @return the timestamp of the capture
     */
    default long capture() {
        long now = System.currentTimeMillis();

        Map<Long, Map<Position, BlockState>> snapshots =
                Premiere.getInstance().getSnapshotManager().getSnapshots();

        List<Position> positions = getPositions();

        Map<Position, BlockState> snapshot = new HashMap<>();

        for (Position position : positions) {
            BlockState state = position.toLocation(getWorld()).getBlock().getState();
            snapshot.put(position, state);
        }

        snapshots.put(now, snapshot);

        return now;
    }

    /**
     * Revert the area to state of the timestamp
     *
     * @param timestamp the timestamp of the capture
     */
    default void revert(long timestamp) {
        Map<Long, Map<Position, BlockState>> snapshots =
                Premiere.getInstance().getSnapshotManager().getSnapshots();

        if (!snapshots.containsKey(timestamp)) {
            return;
        }

        Map<Position, BlockState> snapshot = snapshots.get(timestamp);

        for (Map.Entry<Position, BlockState> entry : snapshot.entrySet()) {
            Position position = entry.getKey();
            BlockState savedState = entry.getValue();

            BlockState currentState = position.toLocation(getWorld()).getBlock().getState();
            currentState.setType(savedState.getType());
            currentState.setBlockData(savedState.getBlockData());
            currentState.update(true, false);
        }
    }

    /**
     * Get all the positions to snapshot
     * @return the positions of which to capture
     */
    List<Position> getPositions();

    /**
     * Get the world of which to capture the positions in
     * @return the world to capture
     */
    World getWorld();
}
