package org.piotrwyrw.antares.prison.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;

import java.util.Collection;

public class ChestBreakEvent implements Listener {

    @EventHandler
    public void onEvent(BlockBreakEvent evt) {
        Player p = evt.getPlayer();

        if (p.getWorld() != AntaresPrison.getInstance().world)
            return;

        if (evt.getBlock().getType() != Material.CHEST)
            return;

        if (!p.hasPermission(PermissionConstants.DESTROY_SELL_CHEST))
            return;

        Block b = evt.getBlock();

        Collection<Entity> entities = b.getLocation().getWorld().getNearbyEntities(b.getLocation(), .5, .5, .5);
        Entity[] earr = entities.toArray(new Entity[entities.size()]);
        for (Entity e : earr) {
            if (e.getType() != EntityType.ARMOR_STAND)
                continue;
            if (!e.getCustomName().equals(MessageConstants.NAME_SELL_CHEST_ARMORSTAND))
                continue;
            e.remove();
        }

    }

}
