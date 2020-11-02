package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.PrisonsUser;
import org.piotrwyrw.antares.prison.PrisonsUsers;
import org.piotrwyrw.antares.prison.utils.ListUtil;

public class PlayerJoinedEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent evt) {
        PrisonsUsers users = AntaresPrison.getInstance().users;
        if (users.getUser(evt.getPlayer().getUniqueId()) != null)
            return;
        users.addUser(new PrisonsUser(evt.getPlayer().getUniqueId(), ListUtil.empty(), 0.0d));
    }

}
