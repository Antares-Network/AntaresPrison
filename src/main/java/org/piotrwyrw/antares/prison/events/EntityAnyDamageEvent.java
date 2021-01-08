package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;

public class EntityAnyDamageEvent implements Listener {

    @EventHandler
    public void onEvent(EntityDamageEvent evt) {
        if (evt.getEntity().getWorld() != AntaresPrison.getInstance().world)
            return;

        // Block explosion
        if (evt.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || evt.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            evt.setCancelled(true);

        // Fall
        if (evt.getCause() == EntityDamageEvent.DamageCause.FALL || evt.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            evt.setCancelled(true);

        // Suffocation
        if (evt.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION || evt.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            evt.setCancelled(true);

        // Fire
        if (evt.getCause() == EntityDamageEvent.DamageCause.FIRE || evt.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            evt.setCancelled(true);
    }

}
