package dev.selixe.menu.pagination;

import dev.selixe.Premiere;
import dev.selixe.menu.Button;
import dev.selixe.menu.util.Heads;
import dev.selixe.menu.util.InventoryUpdate;
import dev.selixe.utils.bukkit.ItemBuilder;
import dev.selixe.utils.bukkit.SkullCreator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PageButton extends Button {
    private final int mod;
    private final PaginatedMenu menu;

    public PageButton(final int mod, final PaginatedMenu menu) {
        this.mod = mod;
        this.menu = menu;
    }

    @Override
    public ItemStack getButtonItem(final Player player) {
        ItemBuilder builder = new ItemBuilder(SkullCreator.itemFromBase64(this.mod > 0 ? Heads.NEXT_PAGE.getBase() : Heads.PREVIOUS_PAGE.getBase()))
                .name(ChatColor.translateAlternateColorCodes('&', (this.mod > 0) ? "&8\u25b6 &7Next Page" : "&8\u25c0 &7Previous Page"));
        return builder.build();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
        if (this.hasNext(player)) {
            this.menu.modPage(player, this.mod);
            Button.playNeutral(player);
        }
    }

    private boolean hasNext(final Player player) {
        final int pg = this.menu.getPage() + this.mod;
        return pg > 0 && this.menu.getPages(player) >= pg;
    }
}
