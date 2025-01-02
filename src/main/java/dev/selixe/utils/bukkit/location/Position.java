package dev.selixe.utils.bukkit.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter @Setter @AllArgsConstructor
public class Position implements Cloneable, ConfigurationSerializable {

    private double x;
    private double y;
    private double z;

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld("world"), x, y, z);
    }

    public Location toLocation(String world) {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public static Position ofLocation(Location location) {
        return new Position(location.getX(), location.getY(), location.getZ());
    }

    public Block getBlock(String world) {
        return toLocation(world).getBlock();
    }

    public Chunk getChunk(String world) {
        return toLocation(world).getChunk();
    }

    public Position add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Position subtract(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z = z;
        return this;
    }

    public double distance(Position loc) {
        return Math.sqrt(distanceSquared(loc));
    }

    public double distanceSquared(Position o) {
        if (o == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null position");
        }
        return NumberConversions.square(x - o.x) + NumberConversions.square(y - o.y) + NumberConversions.square(z - o.z);
    }

    @Override
    public String toString() {
        return "Position{x=" + x + ",y=" + y + ",z=" + z + "}";
    }

    @Override
    public Position clone() {
        try {
            return (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(position.x, x) == 0 && Double.compare(position.y, y) == 0 && Double.compare(position.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Utility
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("x", this.x);
        data.put("y", this.y);
        data.put("z", this.z);
        return data;
    }

    public static Position deserialize(Map<String, Object> args) {
        return new Position(NumberConversions.toDouble(args.get("x")), NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z")));
    }
}
