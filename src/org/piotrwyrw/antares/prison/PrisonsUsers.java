package org.piotrwyrw.antares.prison;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.piotrwyrw.antares.prison.constants.MessageConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrisonsUsers {

    private File file;
    private List<PrisonsUser> users;

    public PrisonsUsers(String filename) {
        this.file = new File(AntaresPrison.antaresPrison.getDataFolder(), filename);
        this.users = new ArrayList<>();
        if (!file.exists()) {
            try {
                file.createNewFile();
                return;
            } catch (IOException e) {
                AntaresPrison.getInstance().msd.toAllAdmins(MessageConstants.COULD_NOT_CREATE_FILE("players.yml"), true);
                e.printStackTrace();
            }
        }
    }

    public void addUser(PrisonsUser user) {
        this.users.add(user);
    }

    public PrisonsUser getUser(UUID uuid) {
        for (int i = 0; i < users.size(); i ++) {
            if (users.get(i).getUuid().toString().equals(uuid.toString()))
                return users.get(i);
        }
        return null;
    }

    public PrisonsUser getUser(Player player) {
        for (int i = 0; i < users.size(); i ++) {
            if (users.get(i).getUuid().toString().equals(player.getUniqueId().toString()))
                return users.get(i);
        }
        return null;
    }

    public void updateUser(PrisonsUser user) {
        if (getUser(user.getUuid()) == null)
            return;
        users.set(users.indexOf(user), user);
    }

    /**
     * Load users from file
     */
    public void loadFromFile() {;
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection players = configuration.getConfigurationSection("players");
        if (players == null)
            return;
        for (String key : players.getKeys(false)) {
            if (UUID.fromString(key) == null)
                continue;
            if (configuration.get("players." + key + ".balance") == null)
                continue;
            if (configuration.get("players." + key + ".tickets") == null)
                continue;
            List<String> tickets;
            double balance;
            tickets = configuration.getStringList("players." + key + ".tickets");
            balance = configuration.getDouble("players." + key + ".balance");
            PrisonsUser user = new PrisonsUser(UUID.fromString(key), tickets, balance);
            AntaresPrison.getInstance().users.addUser(user);
        }
    }

    /**
     * Save users to file
     */
    public void saveToFile() {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        for (int i = 0; i < users.size(); i ++) {
            PrisonsUser user = users.get(i);
            if (user == null)
                Bukkit.broadcastMessage("user is null");
            if (user.getUuid() == null)
                Bukkit.broadcastMessage("UUID is null");
            String uuid = user.getUuid().toString();
            configuration.set("players." + uuid + ".balance", user.getBalance());
            configuration.set("players." + uuid + ".tickets", user.getTickets());
        }
        try {
            configuration.save(file);
        } catch (IOException e) {
            AntaresPrison.getInstance().msd.toAllAdmins(MessageConstants.COULD_NOT_SAVE("prisons users"), true);
            e.printStackTrace();
        }
    }

}
