package org.piotrwyrw.antares.prison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.Mines;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class RegenCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        MessageSender msd = AntaresPrison.getInstance().msd;

        if (!sender.hasPermission(PermissionConstants.REGEN)) {
            msd.toPlayer(MessageConstants.PERMISSION_DENIED, sender, true);
            return true;
        }

        Mines mines = AntaresPrison.getInstance().mines;

        if (args.length == 1) {
            // In this case, regenerate all mines
            mines.regenAllMines();
            return true;
        } else if (args.length == 2) {
            // In this case, regenerate one particular mine
            String mine = args[1];
            int index = 0;

            if (!mines.mineExists(mine)) {
                msd.toPlayer(MessageConstants.NO_SUCH_MINE(mine), sender, true);
                return true;
            }

            index = mines.mineIndex(mine);

            mines.list.get(index).regenerate(true);
            return true;
        } else {
            msd.toPlayer(MessageConstants.WRONG_ARGUMENTS, sender, true);
            return true;
        }
    }
}
