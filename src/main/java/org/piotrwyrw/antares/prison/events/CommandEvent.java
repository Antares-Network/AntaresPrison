package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.PrisonsUser;
import org.piotrwyrw.antares.prison.PrisonsUsers;

public class CommandEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerCommandPreprocessEvent evt) {
        PrisonsUsers users = AntaresPrison.getInstance().users;
        PrisonsUser user = users.getUser(evt.getPlayer());
        user.setLastCommand(evt.getMessage(), true);
        users.updateUser(user);
    }
}
