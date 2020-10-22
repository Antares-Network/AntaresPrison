package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;

public class EntityExplosionDamageEvent implements Listener {

    @EventHandler
    public void onEvent(EntityDamageEvent evt) {
        if (evt.getEntity().getWorld() != AntaresPrison.getInstance().world)
            return;
        if (evt.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || evt.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            evt.setCancelled(true);
    }

}
