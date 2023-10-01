package dev.selixe.menu.buttons;

import com.google.common.collect.ImmutableList;
import dev.selixe.menu.Button;
import dev.selixe.utils.bukkit.ColorUtils;
import dev.selixe.utils.bukkit.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class NumberButton<T> extends Button {

    private final T target;
    private final Material material;
    private final String trait;
    private final String description;
    private final BiConsumer<T, Integer> writeFunction;
    private final Function<T, Integer> readFunction;
    private final Consumer<T> saveFunction;

    public NumberButton(T target, Material material, String trait, String description, BiConsumer<T, Integer> writeFunction, Function<T, Integer> readFunction) {
        this(target, material, trait, description, writeFunction, readFunction, (i) -> {});
    }

    public NumberButton(T target, Material material, String trait, String description, BiConsumer<T, Integer> writeFunction, Function<T, Integer> readFunction, Consumer<T> saveFunction) {
        this.target = target;
        this.trait = trait;
        this.material = material;
        this.description = description;
        this.writeFunction = writeFunction;
        this.readFunction = readFunction;
        this.saveFunction = saveFunction;
    }

    public String getName(Player player) {
        return trait;
    }

    public List<String> getDescription(Player player) {
        return ImmutableList.of(
            ColorUtils.GRAY + this.description,
            "",
                ColorUtils.GREEN + ColorUtils.BOLD + "LEFT-CLICK " + ChatColor.GRAY + "to +1",
                ColorUtils.GREEN + ColorUtils.BOLD + "SHIFT LEFT-CLICK " + ChatColor.GRAY + "to +10",
            "",
                ColorUtils.RED + ColorUtils.BOLD + "RIGHT-CLICK " + ChatColor.GRAY + "to -1",
                ColorUtils.RED + ColorUtils.BOLD + "SHIFT RIGHT-CLICK " + ChatColor.GRAY + "to -10"
        );
    }

    public Material getMaterial(Player player) {
        return this.material;
    }

    public int getAmount(Player player) {
        return readFunction.apply(target);
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        int current = readFunction.apply(target);
        int change = clickType.isShiftClick() ? 10 : 1;

        if (clickType.isRightClick()) {
            change = -change;
        }

        writeFunction.accept(target, current + change);
        saveFunction.accept(target);

        player.sendMessage(ChatColor.GREEN + "Changed " + trait + ColorUtils.GREEN + " to " + ColorUtils.GOLD + (current + change));
    }

    @Override
    public ItemStack getButtonItem(Player p0) {
        return new ItemBuilder(getMaterial(p0))
                .name(getName(p0))
            .lore(getDescription(p0))
            .amount(getAmount(p0))
            .build();
    }
}