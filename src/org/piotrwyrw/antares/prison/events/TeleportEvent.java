package org.piotrwyrw.antares.prison.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class TeleportEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerTeleportEvent evt) {
        MessageSender msd = AntaresPrison.getInstance().msd;
        Location from = evt.getFrom();
        Location to = evt.getTo();

        // We don't care
        if (evt.getPlayer().hasPermission(PermissionConstants.SKIP_ROOM))
            return;

        // We don't care either
        if (from.getWorld() != AntaresPrison.getInstance().world && to.getWorld() != AntaresPrison.getInstance().world)
            return;

        // We still don't care
        if (to.getWorld() != AntaresPrison.getInstance().world)
            return;

        evt.setCancelled(true);
        msd.toPlayer(MessageConstants.CANNOT_TELEPORT, evt.getPlayer(), true);
    }
}
