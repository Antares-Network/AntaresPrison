package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;

public class BurnEvent implements Listener {

    @EventHandler
    public void onEvent(BlockBurnEvent evt) {
        if (evt.getBlock().getWorld() != AntaresPrison.getInstance().world)
            return;
        evt.setCancelled(true);
    }

}
