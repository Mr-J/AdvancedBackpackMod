package mrj.abm.proxy;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import mrj.abm.GuiBackpackBase;
import mrj.abm.GuiBackpackShared;
import mrj.abm.InventoryBackpackBase;
import mrj.abm.InventoryBackpackMagic;
import mrj.abm.ContainerBackpackBase;
import mrj.abm.ContainerBackpackShared;

/**
 * Advanced Backpack Mod
 * 
 * CommonProxy
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class CommonProxy implements IGuiHandler {
   
    // Client stuff
    public void registerRenderers() {
            // Nothing here as the server doesn't render graphics!
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == 0) //Standard Backpack, Standard Gui
		{
			InventoryBackpackBase myBPInv = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
			return new ContainerBackpackBase(myBPInv, player.inventory);
		}
		else if (ID == 1) //Magic Backpack, Standard Gui
		{
			InventoryBackpackMagic myBPInv = new InventoryBackpackMagic(player.inventory.getCurrentItem(), player, -1);
			return new ContainerBackpackBase(myBPInv, player.inventory);
		}
		else if (ID == 2) //Standard Backpack, Shared Gui
		{
			InventoryBackpackBase backpackInventory = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
			//TileEntity te = world.getBlockTileEntity(x,  y,  z);
			//IInventory containerInventory = (IInventory) te;
			IInventory containerInventory = createContainer(world, x, y, z);
			return new ContainerBackpackShared(backpackInventory, containerInventory);
		}
		else if (ID == 3) //Magic Backpack, Shared Gui
		{
		    InventoryBackpackMagic backpackInventory = new InventoryBackpackMagic(player.inventory.getCurrentItem(), player, -1);
            TileEntity te = world.getBlockTileEntity(x,  y,  z);
            IInventory containerInventory = (IInventory) te;
            return new ContainerBackpackShared(backpackInventory, containerInventory);
		}
		else if (ID == 4) //Standard Backpack + EnderChest, Shared Gui
		{
		    InventoryBackpackBase backpackInventory = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
		    IInventory containerInventory = player.getInventoryEnderChest();
		    return new ContainerBackpackShared(backpackInventory, containerInventory);
		}
		else if (ID == 5) //Magic Backpack + EnderChest, Shared Gui
		{
		    InventoryBackpackMagic backpackInventory = new InventoryBackpackMagic(player.inventory.getCurrentItem(), player, -1);
            IInventory containerInventory = player.getInventoryEnderChest();
            return new ContainerBackpackShared(backpackInventory, containerInventory);
		}
		else
		{
			System.out.println("Error, ID is " + ID + ". This should not happen");
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == 0) //Standard Backpack, Standard Gui
		{
			InventoryBackpackBase myBPInv = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
			return new GuiBackpackBase(myBPInv, player.inventory);
		}
		else if (ID == 1) //Magic Backpack, Standard Gui
		{
			InventoryBackpackMagic myBPInv = new InventoryBackpackMagic(player.inventory.getCurrentItem(), player, -1);
			//***********************************************************************
			//THIS IS A QUITE WHACKY FIX FOR THE PROBLEM AND SHOULD BE REPLACED SOON
			// a better approach would be to ask the server for the needed information instead of doing this
			if (player.inventory.getCurrentItem().getTagCompound().getInteger("invSize") - myBPInv.getSizeInventory() > 0)
			{
				myBPInv.increaseSize(player.inventory.getCurrentItem().getTagCompound().getInteger("invSize") - myBPInv.getSizeInventory());
			}
			//***********************************************************************
			
			
			return new GuiBackpackBase(myBPInv, player.inventory);
		}
		else if (ID == 2) //Standard Backpack, Shared Gui
		{
			InventoryBackpackBase backpackInventory = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
			//TileEntity te = world.getBlockTileEntity(x,  y,  z);
			//IInventory containerInventory = (IInventory) te;
			IInventory containerInventory = createContainer(world, x, y, z);
			//System.out.println(world.getBlockId(x, y, z));
			//System.out.println(Block.blocksList[world.getBlockId(x, y, z)]);
			//System.out.println(Block.blocksList[world.getBlockId(x, y, z)].getUnlocalizedName());
			return new GuiBackpackShared(backpackInventory, containerInventory, Block.blocksList[world.getBlockId(x, y, z)].getLocalizedName());
		}
		else if (ID == 3) //Magic Backpack, Shared Gui
		{
		    InventoryBackpackMagic backpackInventory = new InventoryBackpackMagic(player.inventory.getCurrentItem(), player, -1);
		    if (player.inventory.getCurrentItem().getTagCompound().getInteger("invSize") - backpackInventory.getSizeInventory() > 0)
            {
                backpackInventory.increaseSize(player.inventory.getCurrentItem().getTagCompound().getInteger("invSize") - backpackInventory.getSizeInventory());
            }
            TileEntity te = world.getBlockTileEntity(x,  y,  z);
            IInventory containerInventory = (IInventory) te;
            return new GuiBackpackShared(backpackInventory, containerInventory, Block.blocksList[world.getBlockId(x, y, z)].getLocalizedName());
		}
		else if (ID == 4) //Standard Backpack + EnderChest, Shared Gui
        {
		    InventoryBackpackBase backpackInventory = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
            IInventory containerInventory = player.getInventoryEnderChest();
            return new GuiBackpackShared(backpackInventory, containerInventory, Block.blocksList[world.getBlockId(x, y, z)].getLocalizedName());
        }
		else if (ID == 5) //Magic Backpack + EnderChest, Shared Gui
        {
            InventoryBackpackMagic backpackInventory = new InventoryBackpackMagic(player.inventory.getCurrentItem(), player, -1);
            IInventory containerInventory = player.getInventoryEnderChest();
            return new GuiBackpackShared(backpackInventory, containerInventory, Block.blocksList[world.getBlockId(x, y, z)].getLocalizedName());
        }
		else
		{
			System.out.println("Error, ID is " + ID + ". This should not happen");
		}
		return null;
	}
	
	public IInventory createContainer(World world, int x, int y, int z)
	{
	    //TileEntity container = world.getBlockTileEntity(x, y, z);
	    IInventory containerInventory = (IInventory) world.getBlockTileEntity(x, y, z);
	    String blockName = Block.blocksList[world.getBlockId(x, y, z)].getUnlocalizedName();
	    IInventory containerInventory2 = null;
	    if (blockName.equals("tile.chest"))
	    {
	        //check adjacent blocks
	        if (!world.isAirBlock(x + 1, y, z) && "tile.chest".equals(Block.blocksList[world.getBlockId(x + 1, y, z)].getUnlocalizedName()))
	        {
	            containerInventory2 = (IInventory) world.getBlockTileEntity(x + 1, y, z);
	        }
	        else if (!world.isAirBlock(x - 1, y, z) && "tile.chest".equals(Block.blocksList[world.getBlockId(x - 1, y, z)].getUnlocalizedName()))
            {
                IInventory tempInv = containerInventory;
	            containerInventory = (IInventory) world.getBlockTileEntity(x - 1, y, z);
	            containerInventory2 = tempInv;
            }
	        else if (!world.isAirBlock(x, y, z + 1) && "tile.chest".equals(Block.blocksList[world.getBlockId(x, y, z + 1)].getUnlocalizedName()))
            {
                containerInventory2 = (IInventory) world.getBlockTileEntity(x, y, z + 1);
            }
	        else if (!world.isAirBlock(x, y, z - 1) && "tile.chest".equals(Block.blocksList[world.getBlockId(x, y, z - 1)].getUnlocalizedName()))
            {
	            IInventory tempInv = containerInventory;
                containerInventory = (IInventory) world.getBlockTileEntity(x, y, z - 1);
                containerInventory2 = tempInv;
            }
	        else
	        {
	            return containerInventory;
	        }
	        return new InventoryLargeChest("Large Chest", containerInventory, containerInventory2);
	    }
	    else if (blockName.equals("tile.reinforcedChest"))
	    {
	        if (!world.isAirBlock(x + 1, y, z) && "tile.reinforcedChest".equals(Block.blocksList[world.getBlockId(x + 1, y, z)].getUnlocalizedName()) && 
	                world.getBlockMetadata(x, y, z) == world.getBlockMetadata(x + 1, y, z))
            {
                containerInventory2 = (IInventory) world.getBlockTileEntity(x + 1, y, z);
            }
            else if (!world.isAirBlock(x - 1, y, z) && "tile.reinforcedChest".equals(Block.blocksList[world.getBlockId(x - 1, y, z)].getUnlocalizedName()) &&
                    world.getBlockMetadata(x, y, z) == world.getBlockMetadata(x - 1, y, z))
            {
                IInventory tempInv = containerInventory;
                containerInventory = (IInventory) world.getBlockTileEntity(x - 1, y, z);
                containerInventory2 = tempInv;
            }
            else if (!world.isAirBlock(x, y, z + 1) && "tile.reinforcedChest".equals(Block.blocksList[world.getBlockId(x, y, z + 1)].getUnlocalizedName()) &&
                    world.getBlockMetadata(x, y, z) == world.getBlockMetadata(x, y, z + 1))
            {
                containerInventory2 = (IInventory) world.getBlockTileEntity(x, y, z + 1);
            }
            else if (!world.isAirBlock(x, y, z - 1) && "tile.reinforcedChest".equals(Block.blocksList[world.getBlockId(x, y, z - 1)].getUnlocalizedName()) &&
                    world.getBlockMetadata(x, y, z) == world.getBlockMetadata(x, y, z - 1))
            {
                IInventory tempInv = containerInventory;
                containerInventory = (IInventory) world.getBlockTileEntity(x, y, z - 1);
                containerInventory2 = tempInv;
            }
            else
            {
                return containerInventory;
            }
            return new InventoryLargeChest("Large Reinforced Chest", containerInventory, containerInventory2);
	    }
	    else if (blockName.equals("tile.locker"))
	    {
	        if (!world.isAirBlock(x, y + 1, z) && "tile.locker".equals(Block.blocksList[world.getBlockId(x, y + 1, z)].getUnlocalizedName()))
	        {
	            containerInventory2 = (IInventory) world.getBlockTileEntity(x, y + 1, z);
	        }
	        else if (!world.isAirBlock(x, y - 1, z) && "tile.locker".equals(Block.blocksList[world.getBlockId(x, y - 1, z)].getUnlocalizedName()))
	        {
	            IInventory tempInv = containerInventory;
	            containerInventory = (IInventory) world.getBlockTileEntity(x, y - 1, z);
	            containerInventory2 = tempInv;
	        }
	        else
	        {
	            return containerInventory;
	        }
	        return new InventoryLargeChest("Large Locker", containerInventory, containerInventory2);      
	    }    
	    return containerInventory;
	}
}
