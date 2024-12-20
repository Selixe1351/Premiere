package dev.selixe.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class PotionEffectEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Player player;
    @Getter
    private final PotionEffectType effect;
    @Getter
    @Setter
    private boolean cancelled;

    public PotionEffectEndEvent(Player player, PotionEffectType effect) {
        this.player = player;
        this.effect = effect;
    }


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static class PotionEffectEndListener implements Listener {

        @EventHandler
        public void onPotionEffectAdd(EntityPotionEffectEvent event) {
            if (event.getEntity() instanceof Player player) {
                if (event.getNewEffect() == null && event.getOldEffect() != null) {
                    Bukkit.getPluginManager().callEvent(new PotionEffectEndEvent(player, event.getOldEffect().getType()));
                }
            }
        }
    }

}