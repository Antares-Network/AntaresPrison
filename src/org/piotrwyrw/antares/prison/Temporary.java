package org.piotrwyrw.antares.prison;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Temporary {

    public HashMap<Player, String> lastMessage = new HashMap<Player, String>();

    public Temporary() {}

    public void setLastMessage(Player p, String message) {
        if (!lastMessage.containsKey(p)) {
            lastMessage.put(p, message);
            return;
        }
        lastMessage.replace(p, message);
    }

    public String lastMessageOf(Player p) {
        if (!lastMessage.containsKey(p))
            setLastMessage(p, "");
        return lastMessage.get(p);
    }

}
