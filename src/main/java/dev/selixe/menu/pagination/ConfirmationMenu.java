package dev.selixe.menu.pagination;

import dev.selixe.menu.Button;
import dev.selixe.menu.Menu;
import dev.selixe.menu.buttons.BackButton;
import dev.selixe.menu.buttons.DisplayButton;
import dev.selixe.utils.bukkit.ItemBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ConfirmationMenu extends Menu {

    private final Runnable runnable;
    private final ItemStack is;
    private final Menu oldMenu;

    @Override
    public String getTitle(Player paramPlayer) {
        return "Confirm";
    }

    @Override
    public Map<Integer, Button> getButtons(Player paramPlayer) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        for (int i : new int[]{1, 2, 3, 10, 11, 12, 19, 20, 21}) {
            buttons.put(i, new DisplayButton(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).name(" ").build()));
        }

        buttons.put(13, new DisplayButton(is));

        for (int i : new int[]{5, 6, 7, 14, 15, 16, 23, 24, 25}) {
            buttons.put(i, new DisplayButton(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).name(" ").build()));
        }

        buttons.put(11, new ConfirmationButton());
        buttons.put(15, new CancelButton());

        if(oldMenu != null) {
            buttons.put(22, new BackButton(oldMenu));
        }

        return buttons;
    }

    public class ConfirmationButton extends Button {

        @Override
        public ItemStack getButtonItem(Player p0) {
            return new ItemBuilder(Material.SLIME_BALL).name("&a&lConfirm").build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            runnable.run();
            player.closeInventory();
        }
    }

    public class CancelButton extends Button {
        @Override
        public ItemStack getButtonItem(Player p0) {
            return new ItemBuilder(Material.INK_SAC).data(1).name("&c&lCancel").build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (oldMenu != null) {
                oldMenu.openMenu(player);
            } else {
                player.closeInventory();
            }
        }
    }

}
