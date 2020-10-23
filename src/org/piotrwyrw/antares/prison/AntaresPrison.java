package org.piotrwyrw.antares.prison;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.piotrwyrw.antares.prison.commands.PrisonCommand;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.events.*;
import org.piotrwyrw.antares.prison.objects.MineAutoRegen;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class AntaresPrison extends JavaPlugin {

    static AntaresPrison antaresPrison;
    public Mines mines;
    public Economy economy;
    public WorthManager worthManager;
    public World world;
    public MineAutoRegen autoRegen;
    public Rooms rooms;
    public Tickets tickets;
    public Configuration config;

    private void noPlaceholderAPI() {
        System.out.println("!!!!! No PlaceholderAPI found. !!!!!");
        getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public void onEnable() {
        MessageSender.toAllAdmins(MessageConstants.PLUGIN_ENABLE, true);
        antaresPrison = this;

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            noPlaceholderAPI();
            return;
        }

        if (!getServer().getPluginManager().getPlugin("PlaceholderAPI").isEnabled()) {
            noPlaceholderAPI();
            return;
        }

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        mines = new Mines("mines.yml");
        economy = new Economy("economy.yml");
        worthManager = new WorthManager("worth.yml");
        rooms = new Rooms("rooms.yml");
        tickets = new Tickets("tickets.yml");
        autoRegen = new MineAutoRegen();
        config = new Configuration("config.yml");

        if (!config.loadFromFile())
            return;
        mines.loadFromFile();
        economy.loadFromFile();
        worthManager.loadFromFile();
        rooms.loadFromFile();
        tickets.loadFromFile();

        mines.regenAllMines();

        World w = Bukkit.getWorld(config.world);
        if (w != null)
            this.world = w;

        autoRegen.startInteligent(3);

        PrisonPlaceHolders ph = new PrisonPlaceHolders();
        ph.register();

        registerCommands();
        registerEvents();

    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinedEvent(), this);
        getServer().getPluginManager().registerEvents(new BlockMineEvent(), this);
        getServer().getPluginManager().registerEvents(new ChestCloseEvent(), this);
        getServer().getPluginManager().registerEvents(new ChestPlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new ChestBreakEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityExplosionDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new TNTExplodeEvent(), this);
        getServer().getPluginManager().registerEvents(new CreeperExplodeEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new BurnEvent(), this);
        getServer().getPluginManager().registerEvents(new RoomEnterEvent(), this);
        getServer().getPluginManager().registerEvents(new SignPlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new SignInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new BlockPlacedEvent(), this);
    }

    private void registerCommands() {
        getCommand("prison").setExecutor(new PrisonCommand());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        MessageSender.toAllAdmins(MessageConstants.PLUGIN_DISABLE, true);
        Bukkit.getScheduler().cancelTasks(this);
        economy.saveToFile();
        tickets.saveToFile();
        mines = null;
        worthManager = null;
        economy = null;
        rooms = null;
        autoRegen = null;
        world = null;
        tickets = null;
    }

    public static AntaresPrison getInstance() {
        return antaresPrison;
    }

}
