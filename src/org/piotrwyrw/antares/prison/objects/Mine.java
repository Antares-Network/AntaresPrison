package org.piotrwyrw.antares.prison.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a mine.
 */
public class Mine {
    public Area area;
    public ArrayList<Material> materials;
    public String identifier;

    public Mine(Area area, ArrayList<Material> materials, String identifier) {
        this.area = area;
        this.materials = materials;
        this.identifier = identifier;
    }

    private void rescuePlayers() {
        // Check for players in the area to avoid suffocation
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLocation().getWorld() != area.getMinimum().getWorld())
                continue;
            Location loc = p.getLocation();
            if (area.hasLocation(p.getLocation())) {
                p.teleport(new Location(area.getMaximum().getWorld(), area.getMaximum().getX(), area.getMaximum().getY() + 2, area.getMaximum().getZ()));
                MessageSender.toPlayer(MessageConstants.MINE_REGEN_TP, p, true);
            }
        }
    }

    private Material randomMaterial() {
        Random random = new Random();
        return materials.get(random.nextInt(materials.size()));
    }

    public void regenerate(boolean sendMessage) {
        if (area.min.getWorld() != AntaresPrison.getInstance().world || area.max.getWorld() != AntaresPrison.getInstance().world)
            return;
        rescuePlayers();
        for (int x = area.getMinimum().getBlockX(); x < area.getMaximum().getBlockX() + 1; x ++) {
            for (int y = area.getMinimum().getBlockY(); y < area.getMaximum().getBlockY() + 1; y ++) {
                for (int z = area.getMinimum().getBlockZ(); z < area.getMaximum().getBlockZ() + 1; z ++) {
                    area.getMinimum().getWorld().getBlockAt(x, y, z).setType(randomMaterial());
                }
            }
        }
        if (sendMessage) {
            MessageSender.toEveryone(MessageConstants.MINE_REGEN(identifier), true);
        }
    }

    public int countAir() {
        int air = 0;
        for (int x = area.getMinimum().getBlockX(); x < area.getMaximum().getBlockX() + 1; x ++) {
            for (int y = area.getMinimum().getBlockY(); y < area.getMaximum().getBlockY() + 1; y ++) {
                for (int z = area.getMinimum().getBlockZ(); z < area.getMaximum().getBlockZ() + 1; z ++) {
                    if (AntaresPrison.getInstance().world.getBlockAt(x, y, z).getType() == Material.AIR)
                        air ++;
                }
            }
        }
        return air;
    }

    public int volume() {
        int a = area.getMaximum().getBlockX() - area.getMinimum().getBlockX();
        int b = area.getMaximum().getBlockY() - area.getMinimum().getBlockY();
        int c = area.getMaximum().getBlockZ() - area.getMinimum().getBlockZ();
        return a * b * c;
    }

    public void regenerate() {
        regenerate(false);
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
