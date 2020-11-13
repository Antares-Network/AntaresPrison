package org.piotrwyrw.antares.prison;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.piotrwyrw.antares.prison.commands.PrisonCommand;
import org.piotrwyrw.antares.prison.constants.MessageConstants;
import org.piotrwyrw.antares.prison.events.*;
import org.piotrwyrw.antares.prison.objects.MineAutoRegen;
import org.piotrwyrw.antares.prison.utils.ListUtil;
import org.piotrwyrw.antares.prison.utils.MessageSender;

public class AntaresPrison extends JavaPlugin {

    static AntaresPrison antaresPrison;
    public Mines mines;
    public WorthManager worthManager;
    public World world;
    public MineAutoRegen autoRegen;
    public Rooms rooms;
    public Configuration config;
    public MessageSender msd;
    public PrisonsUsers users;

    private boolean checkPlaceholderAPI() {
        if (!getServer().getPluginManager().getPlugin("PlaceholderAPI").isEnabled()) {
            System.out.println("!!!!! No PlaceholderAPI found. !!!!!");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }

    private boolean checkMultiverseCore() {
        if (!getServer().getPluginManager().getPlugin("Multiverse-Core").isEnabled()) {
            System.out.println("!!!!! No Multiverse-Core found. !!!!!");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }

    @Override
    public void onEnable() {
        antaresPrison = this;

        this.msd = new MessageSender();

        msd.toAllAdmins(MessageConstants.PLUGIN_ENABLE, true);

        if (!checkPlaceholderAPI())
            return;

        if (!checkMultiverseCore())
            return;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        MessageConstants.updatePluginSummary();


        users = new PrisonsUsers();
        users.loadFromDataBase();
        mines = new Mines("mines.yml");
        worthManager = new WorthManager("worth.yml");
        rooms = new Rooms("rooms.yml");
        autoRegen = new MineAutoRegen();
        config = new Configuration("config.yml");

        if (!config.loadFromFile())
            return;

        this.world = config.world;

        mines.loadFromFile();
        worthManager.loadFromFile();
        rooms.loadFromFile();

        mines.regenAllMines();

        autoRegen.startBoth(config.intelliRegenTime, config.forceRegenTime);

        PrisonPlaceHolders ph = new PrisonPlaceHolders();
        ph.register();

        registerCommands();
        registerEvents();

        // Add all online users [Do not delete!]
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (users.getUser(p) != null)
                continue;
            users.addUser(new PrisonsUser(p.getUniqueId(), ListUtil.empty(), 0.0d));
        }
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
        getServer().getPluginManager().registerEvents(new TeleportEvent(), this);
        getServer().getPluginManager().registerEvents(new CommandEvent(), this);
    }

    private void registerCommands() {
        getCommand("prisons").setExecutor(new PrisonCommand());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        msd.toAllAdmins(MessageConstants.PLUGIN_DISABLE, true);
        users.saveToDataBase();
        Bukkit.getScheduler().cancelTasks(this);
        users = null;
        mines = null;
        worthManager = null;
        rooms = null;
        autoRegen = null;
        world = null;
        config = null;
        msd = null;
    }

    public static AntaresPrison getInstance() {
        return antaresPrison;
    }

}
