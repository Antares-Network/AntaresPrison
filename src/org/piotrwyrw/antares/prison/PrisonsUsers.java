package org.piotrwyrw.antares.prison;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PrisonsUsers {

    private File file;
    private List<PrisonsUser> users;

    public PrisonsUsers() {
        this.users = new ArrayList<>();
    }

    public PrisonsUsers(String filename) {
        this.file = new File(AntaresPrison.antaresPrison.getDataFolder(), filename);
        this.users = new ArrayList<>();
        if (!file.exists()) {
            try {
                file.createNewFile();
                return;
            } catch (IOException e) {
                AntaresPrison.getInstance().msd.toAllAdmins(MessageConstants.COULD_NOT_CREATE_FILE("players.yml"), true);
                e.printStackTrace();
            }
        }
    }

    public void removeAll() {
        users.clear();
    }

    public void addUser(PrisonsUser user) {
        this.users.add(user);
    }

    public PrisonsUser getUser(UUID uuid) {
        for (int i = 0; i < users.size(); i ++) {
            if (users.get(i).getUuid().toString().equals(uuid.toString()))
                return users.get(i);
        }
        return null;
    }

    public PrisonsUser getUser(Player player) {
        for (int i = 0; i < users.size(); i ++) {
            if (users.get(i).getUuid().toString().equals(player.getUniqueId().toString()))
                return users.get(i);
        }
        return null;
    }

    public void updateUser(PrisonsUser user) {
        if (getUser(user.getUuid()) == null)
            return;
        users.set(users.indexOf(user), user);
    }

    @Deprecated
    public void loadFromFile() {;
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection players = configuration.getConfigurationSection("players");
        if (players == null)
            return;
        for (String key : players.getKeys(false)) {
            if (UUID.fromString(key) == null)
                continue;
            if (configuration.get("players." + key + ".balance") == null)
                continue;
            if (configuration.get("players." + key + ".tickets") == null)
                continue;
            List<String> tickets;
            double balance;
            tickets = configuration.getStringList("players." + key + ".tickets");
            balance = configuration.getDouble("players." + key + ".balance");
            PrisonsUser user = new PrisonsUser(UUID.fromString(key), tickets, balance);
            AntaresPrison.getInstance().users.addUser(user);
        }
    }

    public void loadFromDataBase() {
        DBTools tools = new DBTools("db_users.db");
        if (!tools.tableExists("users")) {
            saveToDataBase();
            return;
        }
        ResultSet result = tools.result_query("SELECT * FROM users;");
        users.clear();
        while (true) {
            try {
                if (!result.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                String uuid = result.getString("uuid");
                ArrayList<String> tickets = new ArrayList<>();
                String[] rslt = StringUtil.fromString(result.getString("tickets"));
                for (String s : rslt)
                    tickets.add(s);
                double bal = result.getDouble("balance");
                System.out.println("Registered: UUID: " + uuid + ", BALANCE: " + bal + ", TICKETS (SIZE): " + tickets.size());
                users.add(new PrisonsUser(UUID.fromString(uuid), tickets, bal));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                continue;
            }
        }
        tools.closeConnection();
    }

    @Deprecated
    public void saveToFile() {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        for (int i = 0; i < users.size(); i ++) {
            PrisonsUser user = users.get(i);
            if (user == null)
                Bukkit.broadcastMessage("user is null");
            if (user.getUuid() == null)
                Bukkit.broadcastMessage("UUID is null");
            String uuid = user.getUuid().toString();
            configuration.set("players." + uuid + ".balance", user.getBalance());
            configuration.set("players." + uuid + ".tickets", user.getTickets());
        }
        try {
            configuration.save(file);
        } catch (IOException e) {
            AntaresPrison.getInstance().msd.toAllAdmins(MessageConstants.COULD_NOT_SAVE("prisons users"), true);
            e.printStackTrace();
        }
    }

    public void saveToDataBase() {
        DBTools tools = new DBTools("db_users.db");
        if (!tools.tableExists("users")) {
            // Create the users table if it doesn't exist yet.
            tools.createTable("users", "uuid varchar(255), tickets varchar(255), balance double");
        }
        for (int i = 0; i < users.size(); i ++) {
            PrisonsUser user = users.get(i);
            String uuid = user.getUuid().toString();
            String tickets = StringUtil.fromList((ArrayList<String>) user.getTickets());
            double balance = user.getBalance();
            if (!tools.ifQuery_where("users", "uuid = \"" + uuid + "\"")) {
                tools.insertTable("users", "\"" + uuid + "\", \"" + tickets + "\", " + balance);
                continue;
            }
            tools.query("UPDATE users SET tickets = \"" + tickets + "\", " + "balance = " + balance + " WHERE uuid = \"" + uuid + "\";");
            System.out.println("Error on UUID [" + uuid + "]");
        }
        tools.closeConnection();
    }

}
