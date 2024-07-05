package dev.selixe.menu.buttons;

import dev.selixe.menu.Button;
import dev.selixe.menu.Menu;
import dev.selixe.menu.util.Heads;
import dev.selixe.utils.bukkit.ColorUtils;
import dev.selixe.utils.bukkit.Constants;
import dev.selixe.utils.bukkit.ItemBuilder;
import dev.selixe.utils.bukkit.SkullCreator;
import org.bukkit.ChatColor;
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
        return new ItemBuilder(SkullCreator.itemFromBase64(Heads.PREVIOUS_PAGE.getBase()))
                .name(ChatColor.translateAlternateColorCodes('&', "&8\u25c0 &7Previous Page")).build();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
        Button.playNeutral(player);
        this.back.openMenu(player);
    }
}
