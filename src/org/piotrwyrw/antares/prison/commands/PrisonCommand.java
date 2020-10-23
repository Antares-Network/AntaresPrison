package org.piotrwyrw.antares.prison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class PrisonCommand implements CommandExecutor {

    void help(CommandSender sender) {
        MessageSender.toPlayer(MessageConstants.HELP, sender, false);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("prison")) {
            if (args.length == 0) {
                MessageSender.toPlayer(MessageConstants.PLUGIN_SUMMARY, sender, false);
                return true;
            } else if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    help(sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("regen")) {
                    return new RegenCommand().onCommand(sender, cmd, label, args);
                } else if (args[0].equalsIgnoreCase("balance")) {
                    return new BalanceCommand().onCommand(sender, cmd, label, args);
                } else if (args[0].equalsIgnoreCase("minelist")) {
                    return new MineListCommand().onCommand(sender, cmd, label, args);
                } else if (args[0].equalsIgnoreCase("reload")) {
                    return new ReloadCommand().onCommand(sender, cmd, label, args);
                } else if (args[0].equalsIgnoreCase("getseller")) {
                    return new GetSellChestCommand().onCommand(sender, cmd, label, args);
                } else {
                    help(sender);
                    return true;
                }
            } else {
                help(sender);
            }
        }
        return true;
    }
}
