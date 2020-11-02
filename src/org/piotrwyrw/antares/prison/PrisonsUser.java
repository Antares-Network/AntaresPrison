package org.piotrwyrw.antares.prison;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class PrisonsUser {

    private UUID uuid;
    private List<String> tickets;
    private double balance;
    private String lastCommand;
    private String lastMessage;

    public PrisonsUser(UUID uuid, List<String> tickets, double balance) {
        this.tickets = tickets;
        this.balance = balance;
        this.uuid = uuid;
        this.lastCommand = "";
        this.lastMessage = "";
    }

    /**
     * !! Use isOnline first for safety !!
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(uuid) != null;
    }

    public boolean hasTicket(String ticket) {
        return tickets.contains(ticket);
    }

    public void setLastMessage(String message, boolean erase) {
        this.lastMessage = message;
        if (!erase)
            return;
        new BukkitRunnable(){

            @Override
            public void run() {
                lastMessage = "";
            }
        }.runTaskLater(AntaresPrison.getInstance(), 20 * 20);
    }

    public void setLastCommand(String command, boolean erase) {
        this.lastCommand = command;
        if (!erase)
            return;
        new BukkitRunnable(){

            @Override
            public void run() {
                lastCommand = "";
            }
        }.runTaskLater(AntaresPrison.getInstance(), 20 * 20);
    }

    public void addTicket(String ticket) {
        if (tickets.contains(ticket))
            return;
        tickets.add(ticket);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
