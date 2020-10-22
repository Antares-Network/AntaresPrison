package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.Economy;

public class PlayerJoinedEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent evt) {
        Economy economy = AntaresPrison.getInstance().economy;

        AntaresPrison.getInstance().tickets.addEveryone(AntaresPrison.getInstance().config.basic_ticket);

        if (!economy.exists(evt.getPlayer())) {
            economy.addBalance(evt.getPlayer(), 0.0d);
            economy.saveToFile();
        }

    }

}
