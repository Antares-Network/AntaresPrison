package org.piotrwyrw.antares.prison.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;

public class ChestPlaceEvent implements Listener {

    @EventHandler
    public void onEvent(BlockPlaceEvent evt) {
        Player p = evt.getPlayer();

        if (p.getWorld() != AntaresPrison.getInstance().world)
            return;

        if (evt.getBlock().getType() != Material.CHEST)
            return;

        if (!p.hasPermission(PermissionConstants.PLACE_SELL_CHEST))
            return;

        Chest chest = (Chest) evt.getBlock().getState();

        if (!chest.getCustomName().equals(MessageConstants.NAME_SELL_CHEST))
            return;

        Location amd = new Location(chest.getWorld(), chest.getLocation().getX() + 0.5d, chest.getLocation().getY() - 0.8d, chest.getLocation().getZ() + 0.5d);
        ArmorStand armorStand = (ArmorStand) chest.getWorld().spawnEntity(amd, EntityType.ARMOR_STAND);
        armorStand.setCustomName(MessageConstants.NAME_SELL_CHEST_ARMORSTAND);
        armorStand.setCustomNameVisible(true);
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCollidable(false);
    }

}
