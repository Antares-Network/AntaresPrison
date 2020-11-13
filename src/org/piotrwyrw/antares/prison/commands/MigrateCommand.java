package org.piotrwyrw.antares.prison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.PrisonsUser;
import org.piotrwyrw.antares.prison.PrisonsUsers;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.utils.ListUtil;
import org.piotrwyrw.antares.prison.utils.MessageSender;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class MigrateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(PermissionConstants.RELOAD))
            return true;
        MessageSender msd = AntaresPrison.getInstance().msd;
        msd.toPlayer("&7Attempting to migrate into database ...", sender, true);

        AntaresPrison.getInstance().users.loadFromFile();
        AntaresPrison.getInstance().users.saveToDataBase();

        msd.toPlayer("&7Migration successful.", sender, true);
        msd.toPlayer("&7Reloading ..", sender, true);
        AntaresPrison.getInstance().onDisable();
        AntaresPrison.getInstance().onEnable();
        msd.toAllAdmins(MessageConstants.RELOAD_COMPLETE, true);
        return true;
    }
}
