package dev.selixe.menu.pagination;

import dev.selixe.menu.Button;
import dev.selixe.utils.bukkit.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class JumpToPageButton extends Button {
    private final int page;
    private final PaginatedMenu menu;
    private final boolean current;

    public JumpToPageButton(final int page, final PaginatedMenu menu, final boolean current) {
        this.page = page;
        this.menu = menu;
        this.current = current;
    }

    @Override
    public ItemStack getButtonItem(final Player player) {
        final ItemBuilder item = new ItemBuilder(this.current ? Material.MAP : Material.PAPER, this.page);
        item.name(ChatColor.translateAlternateColorCodes('&', "&7Page " + this.page));
        if (this.current) {
            item.lore("&7Current Page");
        }
        return item.build();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
        this.menu.modPage(player, this.page - this.menu.getPage());
        Button.playNeutral(player);
    }
}
