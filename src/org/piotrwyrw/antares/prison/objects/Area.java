package org.piotrwyrw.antares.prison.objects;

import org.bukkit.Location;

/**
 * This class represents an area.
 */
public class Area {
    public Location min;
    public Location max;

    public Area(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld())
            throw new IllegalStateException("Both points of the area have to be in the same world!");

        min = new Location(loc1.getWorld(),
                Math.min(loc1.getBlockX(), loc2.getBlockX()),
                Math.min(loc1.getBlockY(), loc2.getBlockY()),
                Math.min(loc1.getBlockZ(), loc2.getBlockZ())
        );

        max = new Location(loc2.getWorld(),
                Math.max(loc1.getBlockX(), loc2.getBlockX()),
                Math.max(loc1.getBlockY(), loc2.getBlockY()),
                Math.max(loc1.getBlockZ(), loc2.getBlockZ())
        );
    }


    public Location getMinimum() {
        return min;
    }

    public Location getMaximum() {
        return max;
    }

    public boolean hasLocation(Location loc) {
        return (loc.getX() <= getMaximum().getX() && loc.getX() >= getMinimum().getX()) &&
                (loc.getY() <= getMaximum().getY() && loc.getY() >= getMinimum().getY()) &&
                (loc.getZ() <= getMaximum().getZ() && loc.getZ() >= getMinimum().getZ());
    }
}