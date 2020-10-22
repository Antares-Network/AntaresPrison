package org.piotrwyrw.antares.prison.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class EntityDamageEvent implements Listener {

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent evt) {
        if (evt.getDamager().getWorld() != AntaresPrison.getInstance().world)
            return;
        if (evt.getDamager().getType() != EntityType.PLAYER)
            return;

        Player p = (Player) evt.getDamager();

        if (p.hasPermission(PermissionConstants.DAMAGE_ENTITIES))
            return;

        MessageSender.toPlayer(MessageConstants.CANT_DO_THAT_HERE, p, true);
        evt.setCancelled(true);
    }

}
