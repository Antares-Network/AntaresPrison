package org.piotrwyrw.antares.prison.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class GetSellChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        MessageSender msd = AntaresPrison.getInstance().msd;

        if (!sender.hasPermission(PermissionConstants.GET_SELLER)) {
            msd.toPlayer(MessageConstants.PERMISSION_DENIED, sender, true);
            return true;
        }

        if (!(sender instanceof Player)) {
            msd.toPlayer(MessageConstants.MUST_BE_PLAYER, sender, true);
            return true;
        }

        if (args.length != 1) {
            msd.toPlayer(MessageConstants.WRONG_ARGUMENTS, sender, true);
            return true;
        }

        ItemStack chest = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = chest.getItemMeta();
        meta.setDisplayName(MessageConstants.NAME_SELL_CHEST);
        chest.setItemMeta(meta);
        ((Player)sender).getInventory().addItem(chest);
        msd.toPlayer(MessageConstants.SELLER_RECEIVED, sender, true);

        return true;
    }
}
