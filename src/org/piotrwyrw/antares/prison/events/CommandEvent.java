package org.piotrwyrw.antares.prison.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;

public class CommandEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerCommandPreprocessEvent evt) {
        AntaresPrison.getInstance().temporary.setLastCommandTemporarily(evt.getPlayer(), evt.getMessage(), true, 20 * 15);
    }
}
