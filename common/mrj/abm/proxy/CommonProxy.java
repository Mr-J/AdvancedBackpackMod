package mrj.abm.proxy;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
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
		if (ID == 0)
		{
			InventoryBackpackBase myBPInv = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
			return new ContainerBackpackBase(myBPInv, player.inventory);
		}
		else if (ID == 1)
		{
			InventoryBackpackMagic myBPInv = new InventoryBackpackMagic(player.inventory.getCurrentItem(), player, -1);
			return new ContainerBackpackBase(myBPInv, player.inventory);
		}
		else if (ID == 2)
		{
			InventoryBackpackBase backpackInventory = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
			TileEntity te = world.getBlockTileEntity(x,  y,  z);
			IInventory containerInventory = (IInventory) te;
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
		if (ID == 0)
		{
			InventoryBackpackBase myBPInv = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
			return new GuiBackpackBase(myBPInv, player.inventory);
		}
		else if (ID == 1)
		{
			//CLIENT SIDED CONSTRUCTION OF INVENTORYBACKPACKMAGIC IS A PROBLEM
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
		else if (ID == 2)
		{
			InventoryBackpackBase backpackInventory = new InventoryBackpackBase(player.inventory.getCurrentItem(), player, 0);
			TileEntity te = world.getBlockTileEntity(x,  y,  z);
			IInventory containerInventory = (IInventory) te;
			System.out.println(world.getBlockId(x, y, z));
			System.out.println(Block.blocksList[world.getBlockId(x, y, z)]);
			System.out.println(Block.blocksList[world.getBlockId(x, y, z)].getUnlocalizedName());
			return new GuiBackpackShared(backpackInventory, containerInventory, Block.blocksList[world.getBlockId(x, y, z)].getLocalizedName());
		}
		else
		{
			System.out.println("Error, ID is " + ID + ". This should not happen");
		}
		return null;
	}
}
