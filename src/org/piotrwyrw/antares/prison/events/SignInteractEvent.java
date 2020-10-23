package org.piotrwyrw.antares.prison.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class SignInteractEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerInteractEvent evt) {
        if (evt.getPlayer().getWorld() != AntaresPrison.getInstance().world)
            return;
        if (evt.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (evt.getClickedBlock() == null)
            return;
        if (!(evt.getClickedBlock().getState() instanceof Sign))
            return;

        Sign s = (Sign) evt.getClickedBlock().getState();

        if (!s.getLine(0).equals(MessageConstants.SIGN_FIRST_LINE))
            return;

        String ticket = s.getLine(1);
        double  price;
        try {
            price = Double.parseDouble(s.getLine(2));
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return;
        }
        if (AntaresPrison.getInstance().tickets.hasTicket(evt.getPlayer(), ticket)) {
            MessageSender.toPlayer(MessageConstants.ALREADY_HAS_TICKET, evt.getPlayer(), true);
            return;
        }
        if (AntaresPrison.getInstance().economy.balanceOf(evt.getPlayer()) < price) {
            MessageSender.toPlayer(MessageConstants.NOT_ENOUGH_MONEY, evt.getPlayer(), true);
            return;
        }

        double bal = AntaresPrison.getInstance().economy.balanceOf(evt.getPlayer());
        bal -= price;
        AntaresPrison.getInstance().tickets.addTicket(evt.getPlayer().getUniqueId(), ticket);
        AntaresPrison.getInstance().economy.balance.replace(evt.getPlayer().getUniqueId(), bal);
        evt.getPlayer().sendTitle("§a§lBOUGHT", ticket);
    }

}
