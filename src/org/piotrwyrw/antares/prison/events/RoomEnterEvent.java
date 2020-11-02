package org.piotrwyrw.antares.prison.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.PrisonsUser;
import org.piotrwyrw.antares.prison.PrisonsUsers;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.objects.Room;

public class RoomEnterEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerMoveEvent evt) {
        if (evt.getPlayer().getWorld() != AntaresPrison.getInstance().world)
            return;
        if (evt.getPlayer().hasPermission(PermissionConstants.SKIP_ROOM))
            return;
        if (evt.getFrom().getX() == evt.getTo().getX() && evt.getFrom().getY() == evt.getTo().getY() && evt.getFrom().getZ() == evt.getTo().getZ())
            return;
        PrisonsUsers users = AntaresPrison.getInstance().users;
        PrisonsUser user = users.getUser(evt.getPlayer());
        for (int i = 0; i < AntaresPrison.getInstance().rooms.rooms.size(); i ++) {
            Room r = AntaresPrison.getInstance().rooms.rooms.get(i);
            if (!r.area.hasLocation(evt.getFrom()) && r.area.hasLocation(evt.getTo())) {
                if (user.hasTicket(r.requiredTicket)) {
                    continue;
                }
                Vector vel = evt.getTo().toVector().subtract(evt.getPlayer().getLocation().toVector()).normalize();
                vel = vel.multiply(-3);
                Location veloc = vel.toLocation(evt.getPlayer().getWorld());
                Location nloc = new Location(veloc.getWorld(), veloc.getX(), 0, veloc.getZ());
                evt.getPlayer().teleport(evt.getPlayer().getLocation().add(nloc));
                AntaresPrison.getInstance().msd.toPlayer(MessageConstants.CANNOT_ENTER_ROOM, evt.getPlayer(), true);
                break;
            }
        }
    }

}
