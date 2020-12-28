package org.piotrwyrw.antares.prison.objects;

import org.bukkit.Location;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents an area.
 */
public class Area {
    public Location min;
    public Location max;

    public Area(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld())
            throw new IllegalStateException("Both points of the area have to be in the same world!");

        min = minLocation(loc1, loc2);
        max = maxLocation(loc1, loc2);
    }

    public Location maxLocation(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld())
            throw new IllegalStateException("Both points of the area have to be in the same world!");

        return new Location(loc1.getWorld(),
                Math.max(loc1.getBlockX(), loc2.getBlockX()),
                Math.max(loc1.getBlockY(), loc2.getBlockY()),
                Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
    }

    public Location minLocation(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld())
            throw new IllegalStateException("Both points of the area have to be in the same world!");

        return new Location(loc1.getWorld(),
                Math.min(loc1.getBlockX(), loc2.getBlockX()),
                Math.min(loc1.getBlockY(), loc2.getBlockY()),
                Math.min(loc1.getBlockZ(), loc2.getBlockZ()));
    }

    public Area merge(Area ar1, Area ar2) {
        // Area 1
        Location loc1 = ar1.getMaximum();
        Location loc2 = ar1.getMinimum();

        // Area 2
        Location loc3 = ar2.getMaximum();
        Location loc4 = ar1.getMinimum();

        Location loc5 = maxLocation(loc1, loc3);
        Location loc6 = minLocation(loc2, loc4);

        return new Area(loc5, loc6);
    }

    // Returns a random location in the area
    public Location randomLocation() {
        return randomLocation('0', 0);
    }

    // Returns a random location in the area
    public Location randomLocation(char fixedComponent, int value) {
        if ("xyz0".contains(String.valueOf(fixedComponent)))
            throw new IllegalStateException("Unknown component '" + fixedComponent + "'");
        return new Location(min.getWorld(),
                (fixedComponent == 'x') ? value : (ThreadLocalRandom.current().nextInt(min.getBlockX(), max.getBlockX() + 1)),
                (fixedComponent == 'y') ? value : (ThreadLocalRandom.current().nextInt(min.getBlockY(), max.getBlockY() + 1)),
                (fixedComponent == 'z') ? value : (ThreadLocalRandom.current().nextInt(min.getBlockZ(), max.getBlockZ() + 1)));
    }

    public Location getMinimum() {
        return min;
    }

    public Location getMaximum() {
        return max;
    }

    public int volume() {
        int a = getMaximum().getBlockX() - getMinimum().getBlockX();
        int b = getMaximum().getBlockY() - getMinimum().getBlockY();
        int c = getMaximum().getBlockZ() - getMinimum().getBlockZ();
        return a * b * c;
    }
    
    public boolean hasLocation(Location loc) {
        return (loc.getX() <= getMaximum().getX() && loc.getX() >= getMinimum().getX()) &&
                (loc.getY() <= getMaximum().getY() && loc.getY() >= getMinimum().getY()) &&
                (loc.getZ() <= getMaximum().getZ() && loc.getZ() >= getMinimum().getZ());
    }
}