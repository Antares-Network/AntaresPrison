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


# How-To's
### To create a "rankup" sign, type it like this:  
[prison:ticket]  
tier_#  
{value}  




# IMPORTANT DISCLAIMER

* This plugin does **not** generate the world for you and you **must generate your own prisons world**  and then assign the coordinates of all of the mines in the **mines.yml**. You also need to tell the plugin where the doors to each of your rooms are in the **rooms.yml** file.
