package dev.selixe.menu;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public abstract class Button {
    public static Button placeholder(final Material material, final byte data, final String... title) {
        return new Button() {
            @Override
            public ItemStack getButtonItem(final Player player) {
                final ItemStack it = new ItemStack(material, 1, data);
                final ItemMeta meta = it.getItemMeta();
                meta.setDisplayName(StringUtils.join(title));
                it.setItemMeta(meta);
                return it;
            }
        };
    }


    public static Button fromItem(final ItemStack item) {
        return new Button() {
            public ItemStack getButtonItem(Player player) {
                return item;
            }
        };
    }

    public static Button fromItem(final ItemStack item, final Consumer<Player> consumer) {
        return new Button() {
            public ItemStack getButtonItem(Player player) {
                return item;
            }

            public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
                if (consumer != null) {
                    consumer.accept(player);
                }

            }
        };
    }

    public static void playFail(final Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 20.0f, 0.1f);
    }

    public static void playSuccess(final Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 20.0f, 15.0f);
    }

    public static void playNeutral(final Player player) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 20.0f, 1.0f);
    }

    public static void playSound(final Player player, final ButtonSound buttonSound) {
        player.playSound(player.getLocation(), buttonSound.getSound(), 20.0f, 1.0f);
    }

    public abstract ItemStack getButtonItem(final Player p0);

    public void clicked(final Player player, final int slot, final ClickType clickType, final int hotbarButton) {
    }

    public boolean shouldCancel(final Player player, final int slot, final ClickType clickType) {
        return true;
    }

    public boolean shouldUpdate(final Player player, final int slot, final ClickType clickType) {
        return false;
    }
}
