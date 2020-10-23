package org.piotrwyrw.antares.prison.objects;


public class Room {
    public Area area;
    public String name;
    public String requiredTicket;

    public Room(Area area, String requiredTicket, String name) {
        this.area = area;
        this.requiredTicket = requiredTicket;
        this.name = name;
    }

}
