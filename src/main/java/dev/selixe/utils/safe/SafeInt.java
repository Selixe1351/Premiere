package dev.selixe.utils.safe;

import lombok.AllArgsConstructor;

/**
 * Copyright (c) 2023 Selixe
 * <p> 
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@AllArgsConstructor
public class SafeInt {

    private int value;

    public String getAsString() {
        return String.valueOf(getAsInt());
    }


    public boolean getAsBoolean() {
        if (value == 0) return false;
        return value == 1;
    }

    public long getAsLong() {
        return Long.parseLong(getAsString());
    }

    public int getAsInt() {
        return Math.max(value, 0);
    }

    public SafeInt getAsObject() {
        return new SafeInt(value);
    }

    public float getAsFloat() {
        return Float.parseFloat(getAsString());
    }

    public char getAsChar() {
        return getAsString().charAt(0);
    }

    public double getAsDouble() {
        return Double.parseDouble(getAsString());
    }

    public byte getAsByte() {
        return Byte.parseByte(getAsString());
    }

    public short getAsShort() {
        return Short.parseShort(getAsString());
    }

    @Override
    public String toString() {
        return "SafeInt{" +
                "value=" + getAsInt() +
                '}';
    }
}
