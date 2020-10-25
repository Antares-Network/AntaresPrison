# AntaresPrison :jack_o_lantern:
[![Discord](https://discordapp.com/api/guilds/649703068799336454/widget.png)](https://discordapp.com/invite/KKYw763)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/piotrwyrw/AntaresPrison?style=social)


A Prisons plugin for any Spigot or Spigot fork, created for the [Antares Network](https://playantares.com/) Minecraft server.


# Antares Network Server [![Antares](https://cdn.discordapp.com/icons/649703068799336454/1a7ef8f706cd60d62547d2c7dc08d6f0.png)](https://discordapp.com/invite/KKYw763)
Join the Antares Network Discord server at https://discord.gg/KKYw763  
Our Minecraft server with the plugin installed: **mc.playantares.com**  
It has 4 gamemodes: Factions, Skyblock, Creative Plots, and Prisons

# List of commands
* **/prisons help** -- Displays a list of the Plugin's commands
* **/prisons regen** -- Regen all mines
* **/prisons regen {mine}** -- Regen a particular mine
* **/prisons balance** -- Your prison balance
* **/prisons balance {player}** -- Prison balance of a player
* **/prisons minelist** -- List all mines
* **/prisons reload** -- Reload the plugin
* **/prisons getseller** -- Get a seller chest

# List of Permissions
* **prisons.receive_admin_messages** --  allows the player to see .. well, the admin messages, about errors, infos, etc.
* **prisons.regen** -- allows the player to manually regen mines
* **prisons.own_balance**  -- allows the player to check their own balance with **/prisons balance**
* **prisons.other_balance** -- allows the player to check other players balances
* **prisons.list_mines** -- allows the player to list the mines and their X,Y,Z coordinates
* **prisons.autosell** -- allows the player to autosell 
* **prisons.reload** -- allows the player to reload the plugin
* **prisons.get_seller** -- allows the player to recieve a Seller Chest with **/prisons getseller**
* **prisons.place_seller** -- allows the player to place the seller chest down
* **prisons.destroy_seller** -- allows the player to destroy the seller chest
* **prisons.modify_world** -- allows the player to break blocks that arent in the mine
* **prisons.damage_entities** -- allows the player to damage entities in the mining world, like players and mobs 
* **prisons.place_sign** -- allows the player to place the rankup signs
* **prisons.skip_room** -- allows the player to enter a room they have not bought the tier for yet


# Tutorials
## Creating a rankup sign
To create a rankup sign, you must use the format below.
```c
[prison:ticket]  
{ticket name}
{price}
```
### Example
```c
[prison:ticket]
tier_2
20000
```

## Adding a mine
To add a mine, you have to work with the `mines.yml` file.
This file is generated when the plugin enables for the first time.
[Material Cheatsheet](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)

##### Example file:
```yml
mines:

  # The name of the mine, so how you reference to this mine in a command
  my_custom_mine:
    area:
      # Coordinates from
      fromx: 0
      fromy: 0
      fromz: 0
      
      # Coordinates to
      tox: 10
      toy: 10
      toz: 10
      
      # Location world
      world: 'prisons'
      
    # Materials that will spawn in the mine (See cheatsheet)
    materials:
      - 'STONE'
      - 'IRON_ORE'
      - 'GOLD_ORE'
      - 'COBBLESTONE'
   ...
```

## Adding a room
To add a room, you have to work with the `rooms.yml` file.
This file is generated when the plugin enables for the first time.

##### Example file:
```yml

rooms:
  tier_1:
    ticket: 'tier_1'
    area:
      fromx: -8
      fromy: 26
      fromz: -76
      tox: 24
      toy: 4
      toz: -110
      world: 'prisons'
  tier_2:
    ticket: 'tier_2'
    area:
      fromx: -8
      fromy: 26
      fromz: -111
      tox: 24
      toy: 4
      toz: -143
      world: 'prisons'
   ...
      
```

# Other config files
The **economy.yml, tickets.yml, and worth.yml** do not need to be configured and are managed automatically. If you want to change the worth of blocks in the mines change that in **worth.yml**

# IMPORTANT DISCLAIMER

* This plugin does **not** generate the world for you and you **must generate your own prisons world**  and then assign the coordinates of all of the mines in the **mines.yml**. You also need to tell the plugin where the doors to each of your rooms are in the **rooms.yml** file.
