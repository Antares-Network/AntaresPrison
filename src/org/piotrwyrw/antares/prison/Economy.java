package org.piotrwyrw.antares.prison;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Economy {
    public HashMap<UUID, Double> balance = new HashMap<UUID, Double>();
    public FileConfiguration configuration;
    public File file;

    public Economy(String filename) {
        File f = new File(AntaresPrison.getInstance().getDataFolder(), filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                AntaresPrison.getInstance().msd.toAllAdmins(MessageConstants.COULD_NOT_CREATE_FILE(filename), true);
                e.printStackTrace();
            }
        }
        this.configuration = YamlConfiguration.loadConfiguration(f);
        this.file = f;
    }

    public void saveToFile() {
        MessageSender msd = AntaresPrison.getInstance().msd;

        for (UUID uuid : balance.keySet().toArray(new UUID[balance.keySet().size()])) {
            configuration.set("economy." + uuid.toString() + ".money", balance.get(uuid));
        }
        try {
            configuration.save(file);
            msd.toAllAdmins(MessageConstants.ECONOMY_SAVED, true);
        } catch (IOException e) {
            msd.toAllAdmins(MessageConstants.COULD_NOT_SAVE_ECONOMY, true);
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        if (configuration.getConfigurationSection("economy") == null) {
            return;
        }

        Set<String> keys_set = configuration.getConfigurationSection("economy").getKeys(false);
        String[] keys = keys_set.toArray(new String[keys_set.size()]);
        for (String key : keys) {
            if (UUID.fromString(key) == null)
                continue;
            if (configuration.get("economy." + key + ".money") == null) {
                configuration.set("economy." + key + ".money", 0.0d);
            }
            addBalance(key, configuration.getDouble("economy." + key + ".money"));
        }
        try {
            configuration.save(file);
        } catch (IOException e) {
            AntaresPrison.getInstance().msd.toAllAdmins(MessageConstants.COULD_NOT_SAVE_ECONOMY, true);
            e.printStackTrace();
        }

    }

    public boolean exists(Player p) {
        return balance.containsKey(p.getUniqueId());
    }

    public void addBalance(Player p, double money) {
        balance.put(p.getUniqueId(), money);
    }

    public void addBalance(String uuid, double money) {
        balance.put(UUID.fromString(uuid), money);
    }

    public void removeAll() {
        balance.clear();
    }

    public double balanceOf(Player p) {
        return balance.get(p.getUniqueId());
    }
}
