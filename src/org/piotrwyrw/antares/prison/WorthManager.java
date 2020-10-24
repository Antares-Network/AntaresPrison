package org.piotrwyrw.antares.prison;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class WorthManager {
    public HashMap<Material, Double> worths = new HashMap<Material, Double>();
    public File file;
    public FileConfiguration configuration;

    public WorthManager(String filename) {
        File f = new File(AntaresPrison.getInstance().getDataFolder(), filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                AntaresPrison.getInstance().msd.toAllAdmins(MessageConstants.COULD_NOT_CREATE_FILE(filename), true);
                e.printStackTrace();
            }
        }
        this.file = f;
        this.configuration = YamlConfiguration.loadConfiguration(f);
    }

    public void loadFromFile() {
        Set<String> key_set = configuration.getKeys(false);
        String[] keys = key_set.toArray(new String[key_set.size()]);

        for (String key : keys) {
            if (Material.getMaterial(key.toUpperCase()) == null)
                continue;
            Material m = Material.getMaterial(key.toUpperCase());
            double worth = configuration.getDouble(key);
            if (!worths.containsKey(m))
                worths.put(m, worth);
        }
    }

    public boolean hasWorth(Material material) {
        return worths.containsKey(material);
    }

    public double worthOf(Material material) {
        return worths.get(material);
    }
}
