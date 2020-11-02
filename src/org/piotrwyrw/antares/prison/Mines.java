package org.piotrwyrw.antares.prison;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.objects.Area;
import org.piotrwyrw.antares.prison.objects.Mine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Mines {
    public ArrayList<Mine> list = new ArrayList<Mine>();
    public FileConfiguration configuration;
    public File file;

    public Mines(String filename) {
        File f = new File(AntaresPrison.getInstance().getDataFolder(), filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                AntaresPrison.getInstance().msd.toAllAdmins(MessageConstants.COULD_NOT_CREATE_FILE(filename), true);
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(f);
        this.file = f;
    }

    public void loadFromFile() {
        if (configuration.getConfigurationSection("mines") == null) {
            return;
        }
        Set<String> key_set = configuration.getConfigurationSection("mines").getKeys(false);
        String[] keys = key_set.toArray(new String[key_set.size()]);
        for (String key : keys) {
            if (configuration.getConfigurationSection("mines." + key + ".area") == null) {
                continue;
            }
            if (configuration.get("mines." + key + ".materials") == null) {
                continue;
            }

            int fromX, fromY, fromZ, toX, toY, toZ;
            double asX = 0.0, asY = 0.0, asZ = 0.0;
            float asYaw = 0.0f, asPitch = 0.0f;
            String world;
            ArrayList<Material> materials = new ArrayList<Material>();

            try {
                fromX = configuration.getInt("mines." + key + ".area.fromx");
                fromY = configuration.getInt("mines." + key + ".area.fromy");
                fromZ = configuration.getInt("mines." + key + ".area.fromz");
                toX = configuration.getInt("mines." + key + ".area.tox");
                toY = configuration.getInt("mines." + key + ".area.toy");
                toZ = configuration.getInt("mines." + key + ".area.toz");
                world = configuration.getString("mines." + key + ".area.world");
                asX = configuration.getDouble("mines." + key + ".anti-suffocation.x");
                asY = configuration.getDouble("mines." + key + ".anti-suffocation.y");
                asZ = configuration.getDouble("mines." + key + ".anti-suffocation.z");
                asPitch = (float) configuration.getDouble("mines." + key + ".anti-suffocation.pitch");
                asYaw = (float) configuration.getDouble("mines." + key + ".anti-suffocation.yaw");
                if (world == null)
                    continue;
                List<String> list = configuration.getStringList("mines." + key + ".materials");
                for (int i = 0; i < list.size(); i ++) {
                    if (Material.getMaterial(list.get(i).toUpperCase()) == null) {
                        System.out.println("!!! Unknown Material: " + list.get(i).toUpperCase());
                        continue;
                    }
                    materials.add(Material.getMaterial(list.get(i).toUpperCase()));
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                continue;
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                continue;
            }

            Area a = new Area(new Location(Bukkit.getWorld(world), fromX, fromY, fromZ), new Location(Bukkit.getWorld(world), toX, toY, toZ));

            if (asX == 0.0 && asY == 0.0 && asZ == 0.0) {
                asX = a.getMaximum().getX();
                asY = a.getMaximum().getY() + 2;
                asZ = a.getMaximum().getZ();
            }

            if (!mineExists(key)) {
                Mine m = new Mine(new Area(new Location(Bukkit.getWorld(world), fromX, fromY, fromZ), new Location(Bukkit.getWorld(world), toX, toY, toZ)), new Location(Bukkit.getWorld(world), asX, asY, asZ, asYaw, asPitch), materials, key);
                this.list.add(m);
                m.regenerate(true);
                System.out.println("Registered new mine: " + key);
            }
        }
    }

    public int mineIndex(String identifier) {
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).getIdentifier().equalsIgnoreCase(identifier)) {
                return i;
            }
        }
        return -1;
    }

    public boolean mineExists(String identifier) {
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).getIdentifier().equalsIgnoreCase(identifier)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasLocation(Location loc) {
        for (int i = 0; i < list.size(); i ++) {
            Mine m = list.get(i);
            if (m.area.hasLocation(loc))
                return true;
        }
        return false;
    }

    public void inteligentRegenAllMines() {
        for (int i = 0; i < list.size(); i ++) {
            Mine m = list.get(i);
            if (m.countAir() > (m.volume() / 2)) {
                m.regenerate(true);
            }
        }
    }

    public void regenAllMines() {
        for (int i = 0; i < list.size(); i ++ ) {
            list.get(i).regenerate(false);
        }
        AntaresPrison.getInstance().msd.toEveryone(MessageConstants.MINE_REGEN_ALL, true);
    }
}
