package org.piotrwyrw.antares.prison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.Mines;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.constants.PermissionConstants;
import org.piotrwyrw.antares.prison.objects.Mine;
import org.piotrwyrw.antares.prison.utils.ListUtil;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class MineListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Mines mines = AntaresPrison.getInstance().mines;

        if (!sender.hasPermission(PermissionConstants.MINE_LIST)) {
            MessageSender.toPlayer(MessageConstants.PERMISSION_DENIED, sender, true);
            return true;
        }

        for (int i = 0; i < mines.list.size(); i ++) {
            Mine mine = mines.list.get(i);
            String[] summary = {
                    "&8---------- &6" + mine.getIdentifier() + " &8----------",
                    "&7Materials: &6" + ListUtil.toString(mine.materials),
                    "&7From: &6" + mine.area.min.getBlockX() + ", " + mine.area.min.getBlockY() + ", " + mine.area.min.getBlockZ(),
                    "&7To: &6" + mine.area.max.getBlockX() + ", " + mine.area.max.getBlockY() + ", " + mine.area.max.getBlockZ(),
                    "&7World: &6" + mine.area.min.getWorld().getName(),
                    ""
            };
            MessageSender.toPlayer(summary, sender, false);
        }
        return true;
    }
}
