package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;

public class TNTExplodeEvent implements Listener {

    @EventHandler
    public void onEvent(BlockExplodeEvent evt) {
        if (evt.getBlock().getWorld() != AntaresPrison.getInstance().world)
            return;
        evt.setCancelled(true);
    }

}
