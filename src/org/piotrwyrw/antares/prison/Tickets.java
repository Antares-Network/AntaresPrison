package org.piotrwyrw.antares.prison;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.utils.ListUtil;
import org.piotrwyrw.antares.prison.utils.MessageSender;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Tickets {
    public HashMap<UUID, List<String>> tickets = new HashMap<UUID, List<String>>();
    public File file;
    public FileConfiguration configuration;

    public Tickets(String filename) {
        File f = new File(AntaresPrison.getInstance().getDataFolder(), filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                MessageSender.toAllAdmins(MessageConstants.COULD_NOT_CREATE_FILE(filename), true);
                e.printStackTrace();
            }
        }
        this.file = f;
        this.configuration = YamlConfiguration.loadConfiguration(f);
    }

    public void addTicket(Player p, String ticket) {
        addTicket(p.getUniqueId(), ticket);
    }

    public void addTicket(UUID uuid, String ticket) {
        if (!this.tickets.containsKey(uuid)) {
            List<String> tickets = new ArrayList<String>();
            this.tickets.put(uuid, tickets);
        }
        List<String> tickets = this.tickets.get(uuid);
        tickets.add(ticket);
        this.tickets.replace(uuid, tickets);
        saveToFile();
    }

    public boolean hasTicket(Player p, String ticket) {
        if (!this.tickets.containsKey(p.getUniqueId()))
            return false;
        return this.tickets.get(p.getUniqueId()).contains(ticket);
    }

    public void saveToFile() {
        Set<UUID> keys = tickets.keySet();
        UUID[] keyarr = keys.toArray(new UUID[keys.size()]);
        for (UUID uuid : keyarr) {
            configuration.set("tickets." + uuid.toString() + ".tickets", tickets.get(uuid));
        }
        try {
            configuration.save(this.file);
            MessageSender.toAllAdmins(MessageConstants.TICKETS_SAVED, true);
        } catch (IOException e) {
            MessageSender.toAllAdmins(MessageConstants.COULD_NOT_SAVE_TICKETS, true);
            e.printStackTrace();
        }
    }

    public void addEveryone(String ticket) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!tickets.containsKey(p.getUniqueId())) {
                addTicket(p.getUniqueId(), ticket);
                continue;
            }
            if (tickets.get(p.getUniqueId()).contains(ticket))
                continue;
            addTicket(p.getUniqueId(), ticket);
        }
    }

    public void loadFromFile() {
        ConfigurationSection section = configuration.getConfigurationSection("tickets");
        if (section == null)
            return;
        Set<String> keys = section.getKeys(false);
        String[] keyarr = keys.toArray(new String[keys.size()]);
        for (String key : keyarr) {
            if (configuration.get("tickets." + key + ".tickets") == null)
                continue;
            List<String> tickets = configuration.getStringList("tickets." + key + ".tickets");
            System.out.println(key + " | " + ListUtil.stringListToString((ArrayList) tickets));
            this.tickets.put(UUID.fromString(key), tickets);
        }
    }

}
