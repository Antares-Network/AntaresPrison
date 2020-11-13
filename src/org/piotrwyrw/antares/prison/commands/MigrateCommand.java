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
        msd.toPlayer("&7Attempting to migrate into users.yml ...", sender, true);
        File eco = new File(AntaresPrison.getInstance().getDataFolder(), "economy.yml");
        File tickets = new File(AntaresPrison.getInstance().getDataFolder(), "tickets.yml");
        if (!eco.exists() || !tickets.exists()) {
            msd.toPlayer("&7Failed to migrate.", sender, true);
            return true;
        }
        FileConfiguration eco_conf = YamlConfiguration.loadConfiguration(eco);
        FileConfiguration tickets_conf = YamlConfiguration.loadConfiguration(tickets);
        ConfigurationSection eco_main = eco_conf.getConfigurationSection("economy");
        ConfigurationSection tickets_main = tickets_conf.getConfigurationSection("tickets");
        if (eco_main == null || tickets_main == null) {
            msd.toPlayer("&7Failed to migrate: Wrong configuration sections.", sender, true);
            return true;
        }
        try {
            PrisonsUsers users = AntaresPrison.getInstance().users;
            for (String key : eco_main.getKeys(false)) {
                UUID uuid = UUID.fromString(key);
                double bal = eco_conf.getDouble("economy." + key + ".money");
                PrisonsUser user = users.getUser(UUID.fromString(key));
                if (user == null) {
                    PrisonsUser pu = new PrisonsUser(uuid, ListUtil.empty(), bal);
                    users.addUser(pu);
                    continue;
                }
                user.setBalance(bal);
                users.updateUser(user);
            }
            for (String key : tickets_main.getKeys(false)) {
                UUID uuid = UUID.fromString(key);
                List<String> list_tickets = tickets_conf.getStringList("tickets." + key + ".tickets");
                PrisonsUser user = users.getUser(UUID.fromString(key));
                if (user == null) {
                    PrisonsUser pu = new PrisonsUser(uuid, list_tickets, 0.0d);
                    users.addUser(pu);
                    continue;
                }
                user.setTickets(list_tickets);
                users.updateUser(user);
            }
        } catch (NullPointerException npe) {
            msd.toPlayer("&7Failed to migrate: NullPointerException (see console for more details).", sender, true);
            npe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            msd.toPlayer("&7Failed to migrate: IllegalArgumentException (see console for more details).", sender, true);
            iae.printStackTrace();
        }
        msd.toPlayer("&7Migration successful.", sender, true);
        msd.toPlayer("&7Reloading ..", sender, true);
        AntaresPrison.getInstance().onDisable();
        AntaresPrison.getInstance().onEnable();
        msd.toAllAdmins(MessageConstants.RELOAD_COMPLETE, true);
        return true;
    }
}
