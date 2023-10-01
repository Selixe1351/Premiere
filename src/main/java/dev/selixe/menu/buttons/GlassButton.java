package dev.selixe.menu.buttons;

import dev.selixe.menu.Button;
import dev.selixe.utils.bukkit.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter @Setter
public class GlassButton extends Button {

    private Material material;

    public GlassButton() {
        this.material = Material.GRAY_STAINED_GLASS_PANE;
    }

    public GlassButton(Material material) {
        this.material = material;
    }

    @Override
    public ItemStack getButtonItem(final Player player) {
        return new ItemBuilder(material).build();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
    }
}
