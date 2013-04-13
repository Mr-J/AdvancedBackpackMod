Advanced Backpack Mod
---------------------

Author: MrJ
Current Version: 0.4.4beta

This mod offers a quite flexible kind of backpack. There are several options which can be configured 
in the configuration file of the mod to individually adjust the backpack to the needs of every user.
See the configuration section for details. There is also a recipe to color the backpacks and another 
recipe to upgrade the storage size of a backpack, see the recipe section for details. The standard 
functionality is the usual one for backpacks, right click to open it. The additional function is if you
shift-rightclick a supported container (like vanilla chests), a direct transfer gui between backpack and
chest will open for you (this currently works only for the bag of holding). The Portable Pocketdimension
will remember its inventory (per color) even if its destroyed, so if you recraft it later your items will
still be there.

######################
# Known Bugs/Issues: #
######################

- connected containers like double chest are only shown as the single clicked chest part in the gui, 
	but can be accessed individually. shift rightclick the chest part you want to see in the gui


############
# Recipes: #
############

The recipes are not displayed properly here, so please take a look at the thread in the minecraft forum:
www.minecraftforum.net/topic/1765606-151forge-advanced-backpack-mod/

##################
# Configuration: #
##################

The mod will create a config file in [MINECRAFTFOLDER]\config\abm
There are some options which can be configured:
	- base_start_size
	- base_max_size
    - base_upgrade_increment
	- magic_start_size
	- magic_max_size
    - magic_upgrade_increment
	- fixed_column_size
The start size is the basic size a backpack has when it is crafted. This applies only to first 
crafting for portable pocketdimensions since they remember their size and contents afterwards.
Max size is the maximum size a backpack can get through upgrading and the upgrade increment is the
number of additional slots per upgrade.
The fixed column size is for players which do not want a gui with variable column size and 
prefer the standard minecraft column size (9 by default). There is also an option for setting the
item IDs .

	

Changelog
- 0.1alpha: 	basic inventory mostly working, nothing special there at the moment
- 0.1.1alpha: 	config file option added
- 0.2alpha: 	working with Minecraft 1.5
- 0.2.1alpha: 	container gui is now modular, different inventory sizes possible, needs some tweaking
- 0.3alpha: 	inventories with variable sizes now available, config file option coming soon
- 0.3.1alpha: 	recipe added: leather/enderpearl/leather, leather/chest/leather, leather/emerald/leather
- 0.3.2alpha: 	dropping-items-from-players-hotbar glitch is gone, upgrade recipe is ready
				string/enderpearl/string, emerald/[the backpack]/emerald, string/blaze rod/string = +3 slots
- 0.3.3alpha: 	some more config options, generals options include:
				- a base size for the backpack (default 27)
				- a maximum size for the backpack (default 108)
				- a number for the additional slots per upgrade (default 3)
				- an option to draw the inventory with fixed column size 9 or variable depending on slots (default false)
- 0.3.4alpha: 	now with colors! backpack + dye = dyed backpack
- 0.3.5alpha: 	"magic" backpacks with persistent inventories available, currently with no recipe and no different icons per color but in a working state
- 0.3.6alpha: 	all backpacks can now dyed & some preparations for adding a shift-click functionality, currently doing nothing
- 0.3.7alpha: 	- new item: nether power core (redstone/redstone/redstone, redstone/netherstar/redstone, redstone/redstone/redstone) used for crafting the
				magic backpack (now named Portable Pocketdimension), 
				- Backpack name changed to Bag of Holding
				- crafting recipe for magic backpack added (leather/enderpearl/leather, leather/enderchest/leather, leather/netherpowercore/leather
				- some internal changes, should have no visible effect
- 0.3.9alpha:	- magic backpacks should now work correctly
- 0.4.0alpha:	complete new functionality: 
				- you can now shift-right click a container to open a direct transfer gui between backpack & container, 
				this works currently only for standard backpacks (and is mostly untested with containers from mods)
- 0.4.1alpha:	- currently limited to a few containers (vanilla chest, chest from ironchests2, ee3 alchemical chests, most containers from better storage)
				- feel free to ask me for adding of additional containers
- 0.4.2beta:	first public release
- 0.4.3beta:	- shared inventory works now for vanilla ender chests too
- 0.4.4beta:	- shared inventory is now displayed properly for double chests (and lockers) too