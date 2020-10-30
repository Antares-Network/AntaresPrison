package org.piotrwyrw.antares.prison;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Temporary {

    public HashMap<Player, String> lastMessage = new HashMap<Player, String>();
    public HashMap<Player, String> lastCommand = new HashMap<Player, String>();

    public Temporary() {}

    public void setLastMessage(Player p, String message, boolean erase, long eraseAfter) {
        if (!lastMessage.containsKey(p)) {
            lastMessage.put(p, message);
            return;
        }

        if (!erase)
            return;

        new BukkitRunnable() {

            @Override
            public void run() {
                setLastMessage(p, "");
            }
        }.runTaskLater(AntaresPrison.getInstance(), eraseAfter);

        lastMessage.replace(p, message);
    }

    public void setLastMessage(Player p, String message) {
        setLastMessage(p, message, false, 0);
    }

    public String lastMessageOf(Player p) {
        if (!lastMessage.containsKey(p))
            setLastMessage(p, "", false, 0);
        return lastMessage.get(p);
    }

    public void setLastCommandTemporarily(Player p, String command, boolean erase, long eraseAfter) {
        if (!lastCommand.containsKey(p))
            lastCommand.put(p, command);
        else
            lastCommand.replace(p, command);

        if (!erase)
            return;

        new BukkitRunnable() {

            @Override
            public void run() {
                setLastCommand(p, "");
            }
        }.runTaskLater(AntaresPrison.getInstance(), eraseAfter);
    }

    public void setLastCommand(Player p, String command) {
        setLastCommandTemporarily(p, command, false, 0);
    }

    public String lastCommandOf(Player p) {
        if (!lastCommand.containsKey(p))
            setLastCommand(p, "");
        return lastCommand.get(p);
    }
}
