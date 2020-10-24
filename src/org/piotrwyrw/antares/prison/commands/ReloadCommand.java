package org.piotrwyrw.antares.prison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        MessageSender msd = AntaresPrison.getInstance().msd;

        if (!sender.hasPermission(PermissionConstants.RELOAD)) {
            msd.toPlayer(MessageConstants.PERMISSION_DENIED, sender, true);
            return true;
        }
        AntaresPrison.getInstance().onDisable();
        AntaresPrison.getInstance().onEnable();
        msd.toAllAdmins(MessageConstants.RELOAD_COMPLETE, true);
        return false;
    }
}
