package dev.selixe.utils.safe;

import lombok.AllArgsConstructor;

/**
 * Copyright (c) 2023 Selixe
 * <p> 
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@AllArgsConstructor
public class SafeDouble {

    private double value;

    public String getAsString() {
        return String.valueOf(getAsDouble());
    }

    public boolean getAsBoolean() {
        if (value == 0.0) return false;
        return value == 1.0;
    }

    public long getAsLong() {
        return Long.parseLong(getAsString());
    }

    public int getAsInt() {
        return (int) Math.max(value, 0);
    }

    public SafeDouble getAsObject() {
        return new SafeDouble(value);
    }

    public float getAsFloat() {
        return Float.parseFloat(getAsString());
    }

    public char getAsChar() {
        return getAsString().charAt(0);
    }

    public double getAsDouble() {
        return Math.max(value, 0.0);
    }

    public byte getAsByte() {
        return Byte.parseByte(getAsString());
    }

    public short getAsShort() {
        return Short.parseShort(getAsString());
    }

    @Override
    public String toString() {
        return "SafeDouble{" +
                "value=" + getAsDouble() +
                '}';
    }
}
