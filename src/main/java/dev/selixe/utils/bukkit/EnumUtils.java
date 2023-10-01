package dev.selixe.utils.bukkit;

import lombok.experimental.UtilityClass;

import java.util.Random;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@UtilityClass
public class EnumUtils {

    public <T extends Enum<?>> T randomEnum(Class<T> clazz){
        return clazz.getEnumConstants()[new Random().nextInt(clazz.getEnumConstants().length)];
    }

}
