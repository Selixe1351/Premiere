package dev.selixe.utils.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.selixe.Premiere;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@UtilityClass
public class BungeeUtils {

    public void sendToServer(Player player, String server) {
        Premiere.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(Premiere.getInstance(), "BungeeCord");

        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);

            player.sendPluginMessage(Premiere.getInstance(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
