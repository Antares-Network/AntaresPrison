package org.piotrwyrw.antares.prison.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.PrisonsUser;
import org.piotrwyrw.antares.prison.PrisonsUsers;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class SignInteractEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerInteractEvent evt) {
        MessageSender msd = AntaresPrison.getInstance().msd;

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

        PrisonsUsers users = AntaresPrison.getInstance().users;
        PrisonsUser user = users.getUser(evt.getPlayer());

        String ticket = s.getLine(1);
        double  price;
        try {
            price = Double.parseDouble(s.getLine(2));
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return;
        }
        if (user.hasTicket(ticket)) {
            msd.toPlayer(MessageConstants.ALREADY_HAS_TICKET, evt.getPlayer(), true);
            return;
        }
        if (user.getBalance() < price) {
            msd.toPlayer(MessageConstants.NOT_ENOUGH_MONEY, evt.getPlayer(), true);
            return;
        }

        double bal = user.getBalance();
        bal -= price;
        user.setBalance(bal);
        user.addTicket(ticket);
        users.updateUser(user);
        evt.getPlayer().sendTitle("§a§lBOUGHT", ticket);
    }

}
