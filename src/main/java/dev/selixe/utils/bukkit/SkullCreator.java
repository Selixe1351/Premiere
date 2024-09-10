package dev.selixe.utils.bukkit;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.UUID;

@UtilityClass
public class SkullCreator {
    private boolean warningPosted = false;
    private Field blockProfileField;
    private Method metaSetProfileMethod;
    private Field metaProfileField;

    public ItemStack createSkull() {
        checkLegacy();

        try {
            return new ItemStack(Material.valueOf("PLAYER_HEAD"));
        } catch (IllegalArgumentException var1) {
            return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short)3);
        }
    }

    public ItemStack itemFromName(String name) {
        return itemWithName(createSkull(), name);
    }

    public ItemStack itemFromUuid(UUID id) {
        return itemWithUuid(createSkull(), id);
    }

    public ItemStack itemFromUrl(String url) {
        return itemWithUrl(createSkull(), url);
    }

    public ItemStack itemFromBase64(String base64) {
        return itemWithBase64(createSkull(), base64);
    }

    public ItemStack itemWithName(ItemStack item, String name) {
        notNull(item, "item");
        notNull(name, "name");
        SkullMeta meta = (SkullMeta)item.getItemMeta();
        meta.setOwner(name);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack itemWithUuid(ItemStack item, UUID id) {
        notNull(item, "item");
        notNull(id, "id");
        SkullMeta meta = (SkullMeta)item.getItemMeta();
        meta.setOwner(String.valueOf(Bukkit.getOfflinePlayer(id)));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack itemWithUrl(ItemStack item, String url) {
        notNull(item, "item");
        notNull(url, "url");
        return itemWithBase64(item, urlToBase64(url));
    }

    public ItemStack itemWithBase64(ItemStack item, String base64) {
        notNull(item, "item");
        notNull(base64, "base64");
        if (!(item.getItemMeta() instanceof SkullMeta)) {
            return null;
        } else {
            SkullMeta meta = (SkullMeta)item.getItemMeta();
            mutateItemMeta(meta, base64);
            item.setItemMeta(meta);
            return item;
        }
    }

    public void blockWithName(Block block, String name) {
        notNull(block, "block");
        notNull(name, "name");
        Skull state = (Skull)block.getState();
        state.setOwner(String.valueOf(Bukkit.getOfflinePlayer(name)));
        state.update(false, false);
    }

    public void blockWithUuid(Block block, UUID id) {
        notNull(block, "block");
        notNull(id, "id");
        setToSkull(block);
        Skull state = (Skull)block.getState();
        state.setOwner(String.valueOf(Bukkit.getOfflinePlayer(id)));
        state.update(false, false);
    }

    public void blockWithUrl(Block block, String url) {
        notNull(block, "block");
        notNull(url, "url");
        blockWithBase64(block, urlToBase64(url));
    }

    public void blockWithBase64(Block block, String base64) {
        notNull(block, "block");
        notNull(base64, "base64");
        setToSkull(block);
        Skull state = (Skull)block.getState();
        mutateBlockState(state, base64);
        state.update(false, false);
    }

    private void setToSkull(Block block) {
        checkLegacy();

        try {
            block.setType(Material.valueOf("PLAYER_HEAD"), false);
        } catch (IllegalArgumentException var3) {
            block.setType(Material.valueOf("SKULL"), false);
            Skull state = (Skull)block.getState();
            state.setType(Material.PLAYER_HEAD);
            state.update(false, false);
        }

    }

    private void notNull(Object o, String name) {
        if (o == null) {
            throw new NullPointerException(name + " should not be null!");
        }
    }

    private String urlToBase64(String url) {
        URI actualUrl;
        try {
            actualUrl = new URI(url);
        } catch (URISyntaxException var3) {
            throw new RuntimeException(var3);
        }

        String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl.toString() + "\"}}}";
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }

    private GameProfile makeProfile(String b64) {
        UUID id = new UUID((long)b64.substring(b64.length() - 20).hashCode(), (long)b64.substring(b64.length() - 10).hashCode());
        GameProfile profile = new GameProfile(id, "Player");
        profile.getProperties().put("textures", new Property("textures", b64));
        return profile;
    }

    private void mutateBlockState(Skull block, String b64) {
        try {
            if (blockProfileField == null) {
                blockProfileField = block.getClass().getDeclaredField("profile");
                blockProfileField.setAccessible(true);
            }

            blockProfileField.set(block, makeProfile(b64));
        } catch (IllegalAccessException | NoSuchFieldException var3) {
            var3.printStackTrace();
        }

    }

    private void mutateItemMeta(SkullMeta meta, String b64) {
        try {
            if (metaSetProfileMethod == null) {
                metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                metaSetProfileMethod.setAccessible(true);
            }

            metaSetProfileMethod.invoke(meta, makeProfile(b64));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException var5) {
            try {
                if (metaProfileField == null) {
                    metaProfileField = meta.getClass().getDeclaredField("profile");
                    metaProfileField.setAccessible(true);
                }

                metaProfileField.set(meta, makeProfile(b64));
            } catch (IllegalAccessException | NoSuchFieldException var4) {
                var4.printStackTrace();
            }
        }

    }

    private void checkLegacy() {
        try {
            Material.class.getDeclaredField("PLAYER_HEAD");
            Material.valueOf("SKULL");
            if (!warningPosted) {
                Bukkit.getLogger().warning("SKULLCREATOR API - Using the legacy bukkit API with 1.13+ bukkit versions is not supported!");
                warningPosted = true;
            }
        } catch (IllegalArgumentException | NoSuchFieldException var1) {
        }

    }
}
