package org.piotrwyrw.antares.prison;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.objects.Area;
import org.piotrwyrw.antares.prison.objects.Room;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Rooms {
    public ArrayList<Room> rooms = new ArrayList<Room>();
    public File file;
    public FileConfiguration configuration;

    public Rooms(String filename) {
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
        if (configuration.getConfigurationSection("rooms") == null) {
            return;
        }
        Set<String> key_set = configuration.getConfigurationSection("rooms").getKeys(false);
        String[] keys = key_set.toArray(new String[key_set.size()]);
        for (String key : keys) {
            if (configuration.getConfigurationSection("rooms." + key + ".area") == null) {
                continue;
            }
            if (configuration.get("rooms." + key + ".ticket") == null) {
                continue;
            }

            int fromX, fromY, fromZ, toX, toY, toZ;
            String world;
            String ticket;

            try {
                fromX = configuration.getInt("rooms." + key + ".area.fromx");
                fromY = configuration.getInt("rooms." + key + ".area.fromy");
                fromZ = configuration.getInt("rooms." + key + ".area.fromz");
                toX = configuration.getInt("rooms." + key + ".area.tox");
                toY = configuration.getInt("rooms." + key + ".area.toy");
                toZ = configuration.getInt("rooms." + key + ".area.toz");
                world = configuration.getString("rooms." + key + ".area.world");
                if (world == null) {
                    continue;
                }
                ticket = configuration.getString("rooms." + key + ".ticket");
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                continue;
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                continue;
            }
            if (!roomExists(key)) {
                Room r = new Room(new Area(new Location(Bukkit.getWorld(world), fromX, fromY, fromZ), new Location(Bukkit.getWorld(world), toX, toY, toZ)), ticket, key);
                System.out.println("Registered new room: " + key + ", with ticket: " + ticket);
                this.rooms.add(r);
            }
        }
    }

    public boolean roomExists(String name) {
        for (int i = 0; i < rooms.size(); i ++) {
            if (rooms.get(i).name.equals(name))
                return true;
        }
        return false;
    }

}
