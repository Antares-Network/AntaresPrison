package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class InteractEntityEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerInteractEntityEvent evt) {
        MessageSender msd = AntaresPrison.getInstance().msd;
        if (evt.getPlayer().getWorld() != AntaresPrison.getInstance().world)
            return;
        if (evt.getPlayer().hasPermission(PermissionConstants.MODIFY_WORLD))
            return;
        evt.setCancelled(true);
        msd.toPlayer(MessageConstants.CANT_DO_THAT_HERE, evt.getPlayer(), true);
    }

}
