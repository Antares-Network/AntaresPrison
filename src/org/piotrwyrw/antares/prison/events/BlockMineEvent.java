package org.piotrwyrw.antares.prison.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.WorthManager;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

import java.util.Random;

public class BlockMineEvent implements Listener {

//    private int freeSpace(Inventory inventory) {
//        int space = 0;
//        for (ItemStack i : inventory.getContents()) {
//            if (i == null) {
//                space ++;
//            } else {
//                if (i.getType() == Material.AIR)
//                    space ++;
//                else
//                    continue;
//            }
//        }
//        return space - 5;
//    }

    @EventHandler
    public void onEvent(BlockBreakEvent evt) {
        if (evt.getPlayer().getWorld() != AntaresPrison.getInstance().world)
            return;

        if (!AntaresPrison.getInstance().mines.hasLocation(evt.getBlock().getLocation()) &&
                !evt.getPlayer().hasPermission(PermissionConstants.MODIFY_WORLD)) {
            MessageSender.toPlayer(MessageConstants.CANT_DO_THAT_HERE, evt.getPlayer(), true);
            evt.setCancelled(true);
            return;
        }

        if (evt.getPlayer().getGameMode() != GameMode.SURVIVAL)
            return;

        evt.setDropItems(false);

        if (!evt.getPlayer().hasPermission(PermissionConstants.AUTOSELL)) {
            //if (freeSpace(evt.getPlayer().getInventory()) > 0) {
                evt.getPlayer().getInventory().addItem(new ItemStack(evt.getBlock().getType(), 1));
                return;
            //} else {
             //   evt.getPlayer().sendTitle("§c§lYOUR INVENTORY", "§7is full");
             //   evt.setCancelled(true);
             //   return;
            //}
        }

        // AutoSell
        if (evt.getPlayer().hasPermission(PermissionConstants.AUTOSELL)) {
            WorthManager worthManager = AntaresPrison.getInstance().worthManager;
            if (!worthManager.hasWorth(evt.getBlock().getType()))
                return;
            Material m = evt.getBlock().getType();
            double worth = worthManager.worthOf(m);
            double bal = AntaresPrison.getInstance().economy.balanceOf(evt.getPlayer()) + worth;
            AntaresPrison.getInstance().economy.balance.replace(evt.getPlayer().getUniqueId(), bal);
            evt.getPlayer().sendTitle("", "§b$" + worth);
        }

        Random rand = new Random();
        ExperienceOrb exp = (ExperienceOrb) evt.getBlock().getWorld().spawnEntity(evt.getBlock().getLocation(), EntityType.EXPERIENCE_ORB);
        exp.setExperience(rand.nextInt(50));
    }

}
