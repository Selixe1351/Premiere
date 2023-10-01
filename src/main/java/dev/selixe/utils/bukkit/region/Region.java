package dev.selixe.utils.bukkit.region;

import com.google.common.collect.Lists;
import dev.selixe.utils.bukkit.location.Position;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;

import java.util.*;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter
@Setter
public class Region implements Cloneable, ConfigurationSerializable {

    private String world;
    private int maxX;
    private int maxZ;
    private int minX;
    private int minZ;

    public Region(World world, int maxX, int maxZ, int minX, int minZ) {
        this.world = world.getName();
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.minX = minX;
        this.minZ = minZ;
    }

    public Position getMaxPosition() {
        return new Position(maxX, 256, maxZ);
    }

    public Location getMaxLocation() {
        return new Location(Bukkit.getWorld(world), maxX, 256, maxZ);
    }

    public Position getMinPosition() {
        return new Position(minX, 0, minZ);
    }

    public Location getMinLocation() {
        return new Location(Bukkit.getWorld(world), minX, 0, minZ);
    }

    public Position getCenter() {
        return new Position(maxX + minX / 2.0, 365 + 0 / 2.0, maxZ + minZ / 2.0);
    }

    public boolean contains(Region cuboid) {
        return cuboid.getWorld().equals(world) &&
                cuboid.getMinX() >= minX && cuboid.getMaxX() <= maxX &&
                cuboid.getMinZ() >= minZ && cuboid.getMaxZ() <= maxZ;
    }

    public boolean contains(int x, int z) {
        return x >= minX && x <= maxX &&
                z >= minZ && z <= maxZ;
    }

    public boolean contains(Position position) {
        return contains((int) position.getX(), (int) position.getZ());
    }

    public boolean contains(Location location) {
        return contains(location.getBlockX(), location.getBlockZ());
    }

    public boolean overlaps(Region cuboid) {
        return cuboid.getWorld().equals(world) && !(
                cuboid.getMinX() > maxX ||
                        cuboid.getMinZ() > maxZ ||
                        minZ > cuboid.getMaxX() ||
                        minZ > cuboid.getMaxZ());
    }

    public boolean overlaps(int x, int z) {
        return !(x > maxX || z > maxZ || minZ > x || minZ > z);
    }

    public boolean overlaps(Position position) {
        return overlaps((int) position.getX(), (int) position.getZ());
    }

    public boolean overlaps(Location location) {
        return overlaps(location.getBlockX(), location.getBlockZ());
    }

    public List<Chunk> getChunks() {
        List<Chunk> toReturn = Lists.newArrayList();
        World w = Bukkit.getWorld(this.world);
        int x1 = this.minX & -0x10;
        int x2 = this.maxX & -0x10;
        int z1 = this.minZ & -0x10;
        int z2 = this.maxZ & -0x10;
        int x3 = x1;

        while (x3 <= x2) {
            int z3 = z1;
            while (z3 <= z2) {
                toReturn.add(w.getChunkAt(x3 >> 4, z3 >> 4));
                z3 += 16;
            }

            x3 += 16;
        }

        return toReturn;
    }

    public List<Position> getPositions() {
        Position pos1 = this.getMaxPosition();
        Position pos2 = this.getMinPosition();
        List<Position> positions = new ArrayList<>();

        int topBlockX = (int) Math.max(pos1.getX(), pos2.getX());
        int bottomBlockX = (int) Math.min(pos1.getX(), pos2.getX());

        int topBlockY = (int) Math.max(pos1.getY(), pos2.getY());
        int bottomBlockY = (int) Math.min(pos1.getY(), pos2.getY());

        int topBlockZ = (int) Math.max(pos1.getY(), pos2.getY());
        int bottomBlockZ = (int) Math.min(pos1.getY(), pos2.getY());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    positions.add(new Position(x, y, z));
                }
            }
        }

        return positions;
    }

    @Override
    public Region clone() {
        try {
            return (Region) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    @Override
    public String toString() {
        return "Cuboid{" +
                "world='" + world + '\'' +
                ", maxX=" + maxX +
                ", maxZ=" + maxZ +
                ", minX=" + minX +
                ", minZ=" + minZ +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxX, maxZ, minX, minZ);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("world", this.world);

        data.put("maxX", this.maxX);
        data.put("maxZ", this.maxZ);

        data.put("minX", this.minX);
        data.put("minZ", this.minZ);

        return data;
    }

    public static Region deserialize(Map<String, Object> args) {
        World world = Bukkit.getWorld((String) args.get("world"));
        if (world == null) {
            throw new IllegalArgumentException("unknown world");
        }

        return new Region(world, NumberConversions.toInt(args.get("maxX")), NumberConversions.toInt(args.get("maxZ")), NumberConversions.toInt(args.get("minX")), NumberConversions.toInt(args.get("minZ")));
    }
}
