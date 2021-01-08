package org.piotrwyrw.antares.prison.objects;

import org.bukkit.scheduler.BukkitRunnable;
import org.piotrwyrw.antares.prison.AntaresPrison;

public class MysteryCrateGenerator {

    public int interval;
    public int height;

    public MysteryCrateGenerator(int interval, int height) {
        this.interval = interval;
        this.height = height;
    }

    public void startGenerator() {
        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(AntaresPrison.getInstance(), 0, interval);
    }

}
