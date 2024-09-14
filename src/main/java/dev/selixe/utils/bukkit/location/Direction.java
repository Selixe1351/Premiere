package dev.selixe.utils.bukkit.location;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public enum Direction {

    NORTH, SOUTH, EAST, WEST;

    public Direction getOpposite() {
        return switch (this) {
            case EAST -> WEST;
            case WEST -> EAST;
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
        };
    }

    public static Direction getFromYaw(float yaw) {
        double rotation = ((yaw - 90.0f) % 360.0f);

        if (rotation < 0.0) {
            rotation += 360.0;
        }

        if (0.0 <= rotation && rotation < 45.0) {
            return WEST;
        }
        if (45.0 <= rotation && rotation < 135.0) {
            return NORTH;
        }
        if (135.0 <= rotation && rotation < 225.0) {
            return EAST;
        }
        return SOUTH;
    }
}
