package org.piotrwyrw.antares.prison;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.piotrwyrw.antares.prison.constants.MessageConstants;

import java.io.File;

public class Configuration {

    public File file;
    public FileConfiguration configuration;
    public String basic_ticket;
    public World world;
    public int intelliRegenTime;
    public int forceRegenTime;

    public Configuration(String filename) {
        File f = new File(AntaresPrison.getInstance().getDataFolder(), filename);
        if (!f.exists()) {
            AntaresPrison.getInstance().saveResource("config.yml", true);
        }
        this.file = f;
        this.configuration = YamlConfiguration.loadConfiguration(f);
    }

    private String withUnicodePlaceholders(String str) {
        return str
                .replaceAll("\\{\\>\\>\\}", "\u00BB")
                .replaceAll("\\{\\>\\}", "\u27A4")
                .replaceAll("\\{\\->\\}", "\u2911");
    }

    private String safeLoad(String adress, String fallback, boolean placeholders) {
        if (configuration.get(adress) == null)
            return fallback;
        else
            return withUnicodePlaceholders(configuration.getString(adress));
    }

    private String safeLoad(String adress, String fallback) {
        return safeLoad(adress, fallback, false);
    }

    private boolean unknownWorld() {
        System.out.println("!!!!! AntaresPrison Plugin was disabled. Please put a proper world in config.yml !!!!!");
        System.out.println("!!!!! Unknown world: " + world + " !!!!!");
        AntaresPrison.getInstance().getServer().getPluginManager().disablePlugin(AntaresPrison.getInstance());
        return false;
    }

    public boolean loadFromFile() {
        this.basic_ticket = safeLoad("basic_ticket", "tier_1", false);
        String wname = safeLoad("world", "WORLD_NOT_SET", false);

        if (wname.equals("WORLD_NOT_SET") || Bukkit.getWorld(wname) == null) {
            return unknownWorld();
        }

        this.world = Bukkit.getWorld(wname);

        try {
            this.intelliRegenTime = Integer.parseInt(safeLoad("intelli-regen-time", "3"));
            this.forceRegenTime = Integer.parseInt(safeLoad("force-regen-time", "20"));
        } catch (NumberFormatException nfe) {
            this.intelliRegenTime = 3;
            this.forceRegenTime = 20;
        }

        MessageConstants.PREFIX = safeLoad("prefix", MessageConstants.PREFIX);
        MessageConstants.PLUGIN_ENABLE = safeLoad("messages.plugin_enable", MessageConstants.PLUGIN_ENABLE);
        MessageConstants.PLUGIN_DISABLE = safeLoad("messages.plugin_disable", MessageConstants.PLUGIN_DISABLE);
        MessageConstants.MINE_REGEN_TP = safeLoad("messages.mine_regen_tp", MessageConstants.MINE_REGEN_TP);
        MessageConstants.MINE_REGEN_ALL = safeLoad("messages.mine_regen_all", MessageConstants.MINE_REGEN_ALL);
        MessageConstants.PERMISSION_DENIED = safeLoad("messages.permission_denied", MessageConstants.PERMISSION_DENIED);
        MessageConstants.MUST_BE_PLAYER = safeLoad("messages.must_be_player", MessageConstants.MUST_BE_PLAYER);
        MessageConstants.RELOAD_COMPLETE = safeLoad("messages.reload_complete", MessageConstants.RELOAD_COMPLETE);
        MessageConstants.SELLER_RECEIVED = safeLoad("messages.seller_received", MessageConstants.SELLER_RECEIVED);
        MessageConstants.CANT_DO_THAT_HERE = safeLoad("messages.can_not_do_that_here", MessageConstants.CANT_DO_THAT_HERE);
        MessageConstants.CANNOT_ENTER_ROOM = safeLoad("messages.can_not_enter_room", MessageConstants.CANNOT_ENTER_ROOM);
        MessageConstants.ALREADY_HAS_TICKET = safeLoad("messages.already_has_ticket", MessageConstants.ALREADY_HAS_TICKET);
        MessageConstants.NOT_ENOUGH_MONEY = safeLoad("messages.not_enough_money", MessageConstants.NOT_ENOUGH_MONEY);
        MessageConstants.CANNOT_TELEPORT = safeLoad("messages.can_not_teleport", MessageConstants.CANNOT_TELEPORT);

        return true;
    }

}
