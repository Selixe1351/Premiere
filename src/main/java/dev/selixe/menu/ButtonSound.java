package dev.selixe.menu;

import org.bukkit.Sound;

public enum ButtonSound {
    CLICK(Sound.UI_BUTTON_CLICK),
    SUCCESS(Sound.ENTITY_PLAYER_LEVELUP),
    FAIL(Sound.BLOCK_GRASS_BREAK);

    private final Sound sound;

    ButtonSound(final Sound sound) {
        this.sound = sound;
    }

    public Sound getSound() {
        return this.sound;
    }
}
