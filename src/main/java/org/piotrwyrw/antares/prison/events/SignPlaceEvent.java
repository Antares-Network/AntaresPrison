package org.piotrwyrw.antares.prison.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;

public class SignPlaceEvent implements Listener {

    @EventHandler
    public void onEvent(SignChangeEvent evt) {
        if (evt.getPlayer().getWorld() != AntaresPrison.getInstance().world)
            return;

        if (!evt.getLine(0).contains("[prison:ticket]"))
            return;

        if (!evt.getPlayer().hasPermission(PermissionConstants.PLACE_SIGN))
            return;

        String ticket = evt.getLine(1);
        double price = Double.parseDouble(evt.getLine(2));

        evt.setLine(0, MessageConstants.SIGN_FIRST_LINE);
        evt.setLine(1, ticket);
        evt.setLine(2, String.valueOf(price));
    }

}
