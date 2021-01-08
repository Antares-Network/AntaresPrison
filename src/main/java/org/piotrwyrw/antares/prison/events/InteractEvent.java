package org.piotrwyrw.antares.prison.events;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class InteractEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerInteractEvent evt) {
        MessageSender msd = AntaresPrison.getInstance().msd;

        if (evt.getPlayer().getWorld() != AntaresPrison.getInstance().world)
            return;
        if (evt.getAction() == Action.RIGHT_CLICK_BLOCK && evt.getClickedBlock().getType() == Material.OAK_SIGN)
            return;
        if (evt.getPlayer().getItemInHand() != null)
            return;
        if (evt.getPlayer().hasPermission(PermissionConstants.MODIFY_WORLD))
            return;
        if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (evt.getClickedBlock().getType() == Material.CHEST) {
                Chest c = (Chest) evt.getClickedBlock().getState();
                if (c.getCustomName().equals(MessageConstants.NAME_SELL_CHEST))
                    return;
            }
            evt.setCancelled(true);
            msd.toPlayer(MessageConstants.CANT_DO_THAT_HERE, evt.getPlayer(), true);
            return;
        }
    }

}
