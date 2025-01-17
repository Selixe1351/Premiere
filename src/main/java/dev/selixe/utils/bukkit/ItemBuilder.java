package dev.selixe.utils.bukkit;

import com.google.common.base.Preconditions;
import dev.selixe.Premiere;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class ItemBuilder implements Cloneable{
    private final ItemStack stack;
    private ItemMeta meta;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, byte id) {
        this.stack = new ItemStack(material, 1, (short)0, id);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, (byte)0);
    }

    public ItemBuilder(ItemStack stack) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");
        this.stack = stack;
    }

    public ItemBuilder amount(int amount) {
        this.stack.setAmount(amount);
        return this;
    }

    public ItemBuilder model(int model) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }

        meta.setCustomModelData(model);
        return this;
    }

    public ItemBuilder durability(short amount) {
        this.stack.setDurability(amount);
        return this;
    }

    public ItemBuilder(Material material, int amount, byte data) {
        Preconditions.checkNotNull(material, "Material cannot be null");
        Preconditions.checkArgument(amount > 0, "Amount must be positive");
        this.stack = new ItemStack(material, amount, (short)data);
    }

    public ItemBuilder name(String name) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }

        this.meta.setDisplayName(ColorUtils.translate(name));
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }

        this.meta.addEnchant(enchantment, level, false);
        return this;
    }

    public ItemBuilder unsafeEnchant(Enchantment enchantment, int level) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }

        this.meta.addEnchant(enchantment, level, true);
        return this;
    }


    public ItemBuilder unenchant(Enchantment enchantment) {
        this.stack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder flag(ItemFlag itemFlag) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }

        this.stack.addItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder glow(Boolean glow) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }

        this.meta.addEnchant(Enchantment.UNBREAKING, 10, glow);
        this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder lore(List<String> list) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }

        this.meta.setLore(ColorUtils.translate(list));
        return this;
    }

    public ItemBuilder lore(String... lore) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }

        this.meta.setLore(ColorUtils.translate(Arrays.asList(lore)));
        return this;
    }


    public ItemBuilder data(int data) {
        this.stack.setDurability((short)data);
        return this;
    }

    public ItemBuilder makeUnique() {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }
        if (this.meta != null) {
            this.meta.getPersistentDataContainer().set(
                    new NamespacedKey(Premiere.getInstance(), "unique_id"),
                    PersistentDataType.STRING,
                    UUID.randomUUID().toString()
            );
            this.stack.setItemMeta(this.meta);
        }

        return this;
    }

    public static boolean isSimilar(ItemStack one, ItemStack two) {
        if (one == null || two == null) return false;

        if (one.getType() != two.getType()) return false;

        ItemMeta metaOne = one.getItemMeta();
        ItemMeta metaTwo = two.getItemMeta();

        if (metaOne == null && metaTwo == null) return true;

        if (metaOne == null || metaTwo == null) return false;

        String nameOne = metaOne.hasDisplayName() ? metaOne.getDisplayName() : null;
        String nameTwo = metaTwo.hasDisplayName() ? metaTwo.getDisplayName() : null;
        if (nameOne != null && nameTwo != null && !nameOne.equalsIgnoreCase(nameTwo)) return false;
        if ((nameOne == null) != (nameTwo == null)) return false;

        List<String> loreOne = metaOne.hasLore() ? metaOne.getLore() : null;
        List<String> loreTwo = metaTwo.hasLore() ? metaTwo.getLore() : null;
        if (loreOne != null && loreTwo != null && !loreOne.equals(loreTwo)) return false;
        return (loreOne == null) == (loreTwo == null);
    }

    public ItemStack build() {
        if (this.meta != null) {
            if (this.meta.hasDisplayName()) {
                this.meta.setDisplayName(ColorUtils.translate(this.meta.getDisplayName()));
            }
            if (this.meta.hasLore()) {
                this.meta.setLore(ColorUtils.translate(this.meta.getLore()));
            }
            this.stack.setItemMeta(this.meta);
        }

        return this.stack;
    }

    @Override
    public ItemBuilder clone() {
        try {
            return (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
}
