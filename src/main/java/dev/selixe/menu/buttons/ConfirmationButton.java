package dev.selixe.menu.buttons;

import dev.selixe.menu.Button;
import dev.selixe.menu.TypeCallback;
import dev.selixe.utils.bukkit.ColorUtils;
import dev.selixe.utils.bukkit.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ConfirmationButton extends Button {
    private final boolean confirm;
    private final TypeCallback<Boolean> callback;
    private final boolean closeAfterResponse;

    public ConfirmationButton(final boolean confirm, final TypeCallback<Boolean> callback, final boolean closeAfterResponse) {
        this.confirm = confirm;
        this.callback = callback;
        this.closeAfterResponse = closeAfterResponse;
    }

    @Override
    public ItemStack getButtonItem(final Player player) {
        final ItemBuilder item = new ItemBuilder(this.confirm ? Material.LIME_WOOL : Material.RED_WOOL);
        item.name(ColorUtils.translate(this.confirm ? "&aConfirm" : "&cCancel"));
        return item.build();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
        if (this.confirm) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 20.0f, 0.1f);
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 20.0f, 0.1f);
        }
        if (this.closeAfterResponse) {
            player.closeInventory();
        }
        this.callback.callback(this.confirm);
    }
}
