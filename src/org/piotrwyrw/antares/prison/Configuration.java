package org.piotrwyrw.antares.prison;

import com.mojang.brigadier.Message;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.utils.MessageSender;

import java.io.File;
import java.io.IOException;

public class Configuration {

    public File file;
    public FileConfiguration configuration;
    public String basic_ticket;
    public String world;

    public Configuration(String filename) {
        File f = new File(AntaresPrison.getInstance().getDataFolder(), filename);
        if (!f.exists()) {
            AntaresPrison.getInstance().saveResource("config.yml", true);
        }
        this.file = f;
        this.configuration = YamlConfiguration.loadConfiguration(f);
    }

    private String safeLoad(String adress, String fallback) {
        if (configuration.get(adress) == null)
            return fallback;
        else
            return configuration.getString(adress);
    }

    public boolean loadFromFile() {
        this.basic_ticket = safeLoad("basic_ticket", "tier_1");
        this.world = safeLoad("world", "WORLD_NOT_SET");

        if (world == "WORLD_NOT_SET" || Bukkit.getWorld(world) == null) {
            System.out.println("!!!!! AntaresPrison Plugin was disabled. Please put a proper world in config.yml !!!!!");
            AntaresPrison.getInstance().getServer().getPluginManager().disablePlugin(AntaresPrison.getInstance());
            return false;
        }

        MessageConstants.PREFIX = safeLoad("prefix", MessageConstants.PREFIX);
        MessageConstants.PLUGIN_ENABLE = safeLoad("messages.plugin_enable", MessageConstants.PLUGIN_ENABLE);
        MessageConstants.PLUGIN_DISABLE = safeLoad("messages.plugin_disable", MessageConstants.PLUGIN_DISABLE);
        MessageConstants.MINE_REGEN_TP = safeLoad("messages.mine_regen_tp", MessageConstants.MINE_REGEN_TP);
        MessageConstants.MINE_REGEN_ALL = safeLoad("messages.mine_regen_all", MessageConstants.MINE_REGEN_ALL);
        MessageConstants.PERMISSION_DENIED = safeLoad("messages.permission_denied", MessageConstants.PERMISSION_DENIED);
        MessageConstants.MUST_BE_PLAYER = safeLoad("messages.must_be_player", MessageConstants.MUST_BE_PLAYER);
        MessageConstants.COULD_NOT_SAVE_ECONOMY = safeLoad("messages.could_not_save_economy", MessageConstants.COULD_NOT_SAVE_ECONOMY);
        MessageConstants.COULD_NOT_SAVE_TICKETS = safeLoad("messages.could_not_save_tickets", MessageConstants.COULD_NOT_SAVE_TICKETS);
        MessageConstants.ECONOMY_SAVED = safeLoad("messages.economy_saved", MessageConstants.ECONOMY_SAVED);
        MessageConstants.TICKETS_SAVED = safeLoad("messages.tickets_saved", MessageConstants.TICKETS_SAVED);
        MessageConstants.RELOAD_COMPLETE = safeLoad("messages.reload_complete", MessageConstants.RELOAD_COMPLETE);
        MessageConstants.SELLER_RECEIVED = safeLoad("messages.seller_received", MessageConstants.SELLER_RECEIVED);
        MessageConstants.CANT_DO_THAT_HERE = safeLoad("messages.can_not_do_that_here", MessageConstants.CANT_DO_THAT_HERE);
        MessageConstants.CANNOT_ENTER_ROOM = safeLoad("messages.can_not_enter_room", MessageConstants.CANNOT_ENTER_ROOM);
        MessageConstants.ALREADY_HAS_TICKET = safeLoad("messages.already_has_ticket", MessageConstants.ALREADY_HAS_TICKET);
        MessageConstants.NOT_ENOUGH_MONEY = safeLoad("messages.not_enough_money", MessageConstants.NOT_ENOUGH_MONEY);

        return true;
    }

}
