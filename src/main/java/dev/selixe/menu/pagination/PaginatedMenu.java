package dev.selixe.menu.pagination;

import dev.selixe.menu.Button;
import dev.selixe.menu.Menu;
import dev.selixe.menu.buttons.BackButton;
import dev.selixe.menu.buttons.GlassButton;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Getter @Setter
public abstract class PaginatedMenu extends Menu {
    private int page;
    private Material glassColor;

    public PaginatedMenu() {
        this.page = 1;
        this.setUpdateAfterClick(false);
        this.glassColor = Material.GRAY_STAINED_GLASS_PANE;
    }

    @Override
    public String getTitle(final Player player) {
        return ChatColor.translateAlternateColorCodes('&', this.getPrePaginatedTitle(player));
    }

    public final void modPage(final Player player, final int mod) {
        this.page += mod;
        this.getButtons().clear();
        this.openMenu(player);
    }

    public final int getPages(final Player player) {
        final int buttonAmount = this.getAllPagesButtons(player).size();
        if (buttonAmount == 0) {
            return 1;
        }
        return (int) Math.ceil(buttonAmount / (double) this.getMaxItemsPerPage(player));
    }

    @Override
    public final Map<Integer, Button> getButtons(final Player player) {
        final int minIndex = (int) ((this.page - 1) * (double) this.getMaxItemsPerPage(player));
        final int maxIndex = (int) (this.page * (double) this.getMaxItemsPerPage(player));
        final HashMap<Integer, Button> buttons = new HashMap<>();
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 36, 37, 38, 40, 42, 43, 44}) {
            buttons.put(i, new GlassButton(getGlassColor()));
        }
        buttons.put(39, new PageButton(-1, this));
        buttons.put(41, new PageButton(1, this));
        for (final Map.Entry<Integer, Button> entry : this.getAllPagesButtons(player).entrySet()) {
            int ind = entry.getKey();
            if (ind >= minIndex && ind < maxIndex) {
                ind -= (int) (this.getMaxItemsPerPage(player) * (double) (this.page - 1)) - 11;

                if (ind > 15 && ind <= 20) {
                    ind += 4;
                } else if (ind > 20 && ind <= 25) {
                    ind += 8;
                }
                buttons.put(ind, entry.getValue());
            }
        }
        final Map<Integer, Button> global = this.getGlobalButtons(player);
        if (global != null) {
            buttons.putAll(global);
        }

        if (backButton() != null) {
            buttons.put(40, new BackButton(this.backButton()));
        }

        return buttons;
    }

    public int getMaxItemsPerPage(final Player player) {
        return 27;
    }

    public Map<Integer, Button> getGlobalButtons(final Player player) {
        return null;
    }

    public abstract String getPrePaginatedTitle(final Player p0);

    public abstract Map<Integer, Button> getAllPagesButtons(final Player p0);

    public Menu backButton() {
        return null;
    }

    @Override
    protected void surroundButtons(boolean full, Map<Integer, Button> buttons, final ItemStack itemStack) {
        IntStream.range(0, getSize()).filter(slot -> (buttons.get(slot) == null)).forEach(slot -> {
            if (slot < 9 || slot > getSize() - 10 || (full && (slot % 9 == 0 || (slot + 1) % 9 == 0)))
                buttons.put(slot, new Button() {
                    public ItemStack getButtonItem(Player player) {
                        return itemStack;
                        }
                });
        });
    }

    public int getPage() {
        return this.page;
    }
}
