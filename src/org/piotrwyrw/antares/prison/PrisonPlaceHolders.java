package org.piotrwyrw.antares.prison;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.List;

public class PrisonPlaceHolders extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "prisons";
    }

    @Override
    public String getAuthor() {
        return "&piotrwyrw";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        PrisonsUsers users = AntaresPrison.getInstance().users;
        PrisonsUser user = users.getUser(p);
        if (identifier.equalsIgnoreCase("balance")) {
            double bal = user.getBalance();
            return "$" + bal;
        } else if (identifier.equalsIgnoreCase("version")) {
            return "v" + AntaresPrison.getInstance().getDescription().getVersion();
        } else if (identifier.equalsIgnoreCase("tier")) {
            List<String> tickets = user.getTickets();
            String tier = tickets.get(tickets.size() - 1).replace('_', ' ');
            return tier;
        } else {
            return "Unknown placeholder";
        }
    }
}
