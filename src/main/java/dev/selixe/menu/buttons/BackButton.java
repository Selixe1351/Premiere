package dev.selixe.menu.buttons;

import dev.selixe.menu.Button;
import dev.selixe.menu.Menu;
import dev.selixe.utils.bukkit.ColorUtils;
import dev.selixe.utils.bukkit.Constants;
import dev.selixe.utils.bukkit.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BackButton extends Button {
    private final Menu back;

    public BackButton(final Menu back) {
        this.back = back;
    }

    @Override
    public ItemStack getButtonItem(final Player player) {
        final ItemBuilder item = new ItemBuilder(Material.ARROW).name(ColorUtils.translate("&8" + Constants.ARROW_LEFT + " &7Go back"));
        return item.build();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
        Button.playNeutral(player);
        this.back.openMenu(player);
    }
}
