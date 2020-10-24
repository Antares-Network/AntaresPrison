package org.piotrwyrw.antares.prison.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.Economy;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MessageSender.toPlayer(MessageConstants.MUST_BE_PLAYER, sender, true);
            return true;
        }

        Economy economy = AntaresPrison.getInstance().economy;

        if (args.length == 1) {
            // Own balance
            if (!sender.hasPermission(PermissionConstants.BALANCE_OWN)) {
                MessageSender.toPlayer(MessageConstants.PERMISSION_DENIED, sender, true);
                return true;
            }

            double bal = economy.balanceOf((Player) sender);

            MessageSender.toPlayer(MessageConstants.BALANCE(bal), sender, true);
            return true;
        } else if (args.length == 2) {
            // Other balance
            if (!sender.hasPermission(PermissionConstants.BALANCE_OTHER)) {
                MessageSender.toPlayer(MessageConstants.PERMISSION_DENIED, sender, true);
                return true;
            }

            String playername = args[1];
            Player player = Bukkit.getPlayer(playername);
            double bal;
            if (player == null) {
                MessageSender.toPlayer(MessageConstants.NO_SUCH_PLAYER(playername), sender, true);
                return true;
            }
            bal = economy.balanceOf(player);
            MessageSender.toPlayer(MessageConstants.BALANCE(bal, playername), sender, true);
            return true;
        } else {
            MessageSender.toPlayer(MessageConstants.WRONG_ARGUMENTS, sender, true);
            return true;
        }
    }
}
