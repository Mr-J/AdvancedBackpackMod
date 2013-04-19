package mrj.abm.recipe;

import mrj.abm.InventoryBackpackBase;
import mrj.abm.ItemBackpackBase;
import mrj.abm.config.ConfigurationStore;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Advanced Backpack Mod
 * 
 * RecipeExtendBackpackBase
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class RecipeExtendBackpackBase implements IRecipe {
	
	int[] recipeComponents = {Item.silk.itemID, Item.enderPearl.itemID, Item.silk.itemID,
							    Item.emerald.itemID, ConfigurationStore.BACKPACK_BASE_ID, Item.emerald.itemID,
								Item.silk.itemID, Item.blazeRod.itemID, Item.silk.itemID};
	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world) {

		if (inventoryCrafting.getSizeInventory() < 9)
		{
			//inventory smaller than 3*3 -> crafting not possible
			return false;
		}
		
		if (ConfigurationStore.BACKPACK_ENABLE_EASIER_RECIPES)
		{
		    recipeComponents[3] = Item.diamond.itemID;
		    recipeComponents[5] = Item.diamond.itemID;
		}
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				try
				{
					if (inventoryCrafting.getStackInRowAndColumn(j, i) != null)
					{
						if (inventoryCrafting.getStackInRowAndColumn(j, i).itemID != recipeComponents[(i * 3) + j])
						{
							return false;
						}
					}
					else
					{
						return false;
					}
					
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					return false;
				}
			}
		}
		
		InventoryBackpackBase tmpInv = new InventoryBackpackBase(inventoryCrafting.getStackInRowAndColumn(1,1), null, 0);
		if (tmpInv.getSizeInventory() >= ConfigurationStore.BACKPACK_BASE_MAX_SIZE)
		{
			return false;
		}
		else if(inventoryCrafting.getStackInRowAndColumn(1,1).getTagCompound() != null)
		{
			if(inventoryCrafting.getStackInRowAndColumn(1,1).getTagCompound().getInteger("increaseSize") > 0)
			{
				if(tmpInv.getSizeInventory() + 
						inventoryCrafting.getStackInRowAndColumn(1,1).getTagCompound().getInteger("increaseSize") * 
						ConfigurationStore.BACKPACK_BASE_UPGRADE_INCREMENT >= ConfigurationStore.BACKPACK_BASE_MAX_SIZE)
				{
					return false;
				}
			}
		}		
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
		ItemStack itemStack = null;
		if (inventoryCrafting.getStackInRowAndColumn(1,1).getItem() instanceof ItemBackpackBase)
		{
			itemStack = inventoryCrafting.getStackInRowAndColumn(1,1).copy();
			NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
			if (nbtTagCompound == null)
			{
				nbtTagCompound = new NBTTagCompound();
				itemStack.setTagCompound(nbtTagCompound);
			}
			if (nbtTagCompound.getInteger("increaseSize") > 0)
			{
				nbtTagCompound.setInteger("increaseSize", nbtTagCompound.getInteger("increaseSize") + 1);
			}
			else
			{
				nbtTagCompound.setInteger("increaseSize", 1);
			}
			
			return itemStack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

}
