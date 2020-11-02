package org.piotrwyrw.antares.prison.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.PrisonsUser;
import org.piotrwyrw.antares.prison.PrisonsUsers;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        MessageSender msd = AntaresPrison.getInstance().msd;

        if (!(sender instanceof Player)) {
            msd.toPlayer(MessageConstants.MUST_BE_PLAYER, sender, true);
            return true;
        }

        PrisonsUsers users = AntaresPrison.getInstance().users;
        PrisonsUser user = users.getUser((Player)sender);
        if (args.length == 1) {
            // Own balance
            if (!sender.hasPermission(PermissionConstants.BALANCE_OWN)) {
                msd.toPlayer(MessageConstants.PERMISSION_DENIED, sender, true);
                return true;
            }
            double bal = user.getBalance();

            msd.toPlayer(MessageConstants.BALANCE(bal), sender, true);
            return true;
        } else if (args.length == 2) {
            // Other balance
            if (!sender.hasPermission(PermissionConstants.BALANCE_OTHER)) {
                msd.toPlayer(MessageConstants.PERMISSION_DENIED, sender, true);
                return true;
            }

            String playername = args[1];
            Player player = Bukkit.getPlayer(playername);
            double bal;
            if (player == null) {
                msd.toPlayer(MessageConstants.NO_SUCH_PLAYER(playername), sender, true);
                return true;
            }
            bal = user.getBalance();
            msd.toPlayer(MessageConstants.BALANCE(bal, playername), sender, true);
            return true;
        } else {
            msd.toPlayer(MessageConstants.WRONG_ARGUMENTS, sender, true);
            return true;
        }
    }
}
