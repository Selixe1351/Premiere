package dev.selixe.menu.buttons;


import dev.selixe.menu.Button;
import dev.selixe.menu.util.Heads;
import dev.selixe.utils.bukkit.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SoonButton extends Button {
    @Override
    public ItemStack getButtonItem(Player p0) {
        return new ItemBuilder(Heads.SOON.build()).name("&7&lSOON").lore(
                "&7This feature is coming soon!"
        ).build();
    }
}
