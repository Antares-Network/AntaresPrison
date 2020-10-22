package org.piotrwyrw.antares.prison.constants;

import org.bukkit.ChatColor;

public class MessageConstants {

    public static String NAME_SELL_CHEST = "§6§lSell";
    public static String NAME_SELL_CHEST_ARMORSTAND = "§6§k||| §bSell here §6§k|||";
    public static String SIGN_FIRST_LINE = "§8§k||| §6§lRankup §8§k|||";

    public static String PREFIX = "&6&lPrison &8» ";

    public static String[] PLUGIN_SUMMARY = {
            "&6              AntaresPrison",
            "&7  You are Running AntaresPrison v1.0",
            "&7        For help use &c/prison help"
    };

    public static String[] HELP = {
            "&n&8o-------------------------------------------o",
            "&b/prison help &8-&e Show this help page",
            "&b/prison regen &8-&e Regen all mines",
            "&b/prison regen <m> &8-&e Regen a particular mine",
            "&b/prison balance &8-&e Your prison balance ",
            "&b/prison balance <p> &8-&e Prison balance of a player",
            "&b/prison minelist &8-&e List all mines",
            "&b/prison reload &8-&e Reload the plugin",
            "&b/prison getseller &8-&e Get a seller chest",
            "&n&8o-------------------------------------------o"
    };

    public static String PLUGIN_ENABLE = "&7Enabling plugin ..";

    public static String PLUGIN_DISABLE = "&7Disabling plugin ..";

    public static String MINE_REGEN_TP = "&7You have been teleported, because you were in a mine that was regenerating.";

    public static String MINE_REGEN_ALL = "&7All mines have been regenerated.";

    public static String PERMISSION_DENIED = "&cPermission denied.";

    public static String MUST_BE_PLAYER = "&cYou must be a player to excecute this command.";

    public static String COULD_NOT_SAVE_ECONOMY = "&cCould not save economy.";

    public static String COULD_NOT_SAVE_TICKETS = "&cCould not save tickets.";

    public static String ECONOMY_SAVED = "&7Economy was saved to a file.";

    public static String TICKETS_SAVED = "&7Tickets were saved to a file";

    public static String RELOAD_COMPLETE = "&7Reload complete.";

    public static String SELLER_RECEIVED = "&7You received a seller chest.";

    public static String CANT_DO_THAT_HERE = "&7You can not do that here!";

    public static String CANNOT_ENTER_ROOM = "&7You can not enter this room yet.";

    public static String ALREADY_HAS_TICKET = "&7You already own this ticket.";

    public static String NOT_ENOUGH_MONEY = "&7You do not have enough money.";

    public static  String COULD_NOT_CREATE_FILE(String filename) {
        return "&cCould not create file &b" + filename;
    }

    public static  String NO_SUCH_MINE(String mine) {
        return "&cThere's no such mine &b" + mine;
    }

    public static  String NO_SUCH_PLAYER(String player) {
        return "&cThere's no such player &b" + player;
    }

    public static  String MINE_REGEN(String mine) {
        return "&7The mine &b" + mine + " &7was regenerated.";
    }

    public static  String WRONG_ARGUMENTS = "&cWrong arguments.";

    public static  String BALANCE(double bal) {
        return "&7Your balance is &b$" + bal;
    }

    public static  String BALANCE(double bal, String who) {
        return "&7The balance of &b" + who + " &7is &b$" + bal;
    }

    public static  String construct(String message, boolean withPrefix) {
        return ChatColor.translateAlternateColorCodes('&', withPrefix ? PREFIX + message : message);
    }

    public static  String construct(String message) {
        return construct(message, false);
    }

}
