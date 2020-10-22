package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;

public class CreeperExplodeEvent implements Listener {

    @EventHandler
    public void onEvent(EntityExplodeEvent evt) {
        if (evt.getEntity().getWorld() != AntaresPrison.getInstance().world)
            return;
        evt.setCancelled(true);
    }

}
