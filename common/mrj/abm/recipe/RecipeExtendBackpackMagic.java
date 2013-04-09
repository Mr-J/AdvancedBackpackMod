package mrj.abm.recipe;

import mrj.abm.InventoryBackpackBase;
import mrj.abm.InventoryBackpackMagic;
import mrj.abm.ItemBackpackBase;
import mrj.abm.ItemBackpackMagic;
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

public class RecipeExtendBackpackMagic implements IRecipe {
	
	int[] recipeComponents = {Item.silk.itemID, Item.enderPearl.itemID, Item.silk.itemID,
									Item.emerald.itemID, ConfigurationStore.BACKPACK_MAGIC_ID, Item.emerald.itemID,
									Item.silk.itemID, Item.blazeRod.itemID, Item.silk.itemID};
	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world) {

		if (inventoryCrafting.getSizeInventory() < 9)
		{
			//inventory smaller than 3*3 -> crafting not possible
			return false;
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
		NBTTagCompound nbtTagCompound = inventoryCrafting.getStackInRowAndColumn(1,1).getTagCompound();
		if (nbtTagCompound != null)
		{
			if (nbtTagCompound.getInteger("invSize") >= ConfigurationStore.BACKPACK_MAGIC_MAX_SIZE)
			{
				return false;
			}
		}		
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
		ItemStack itemStack = null;
		if (inventoryCrafting.getStackInRowAndColumn(1,1).getItem() instanceof ItemBackpackMagic)
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
			if (nbtTagCompound.getInteger("invSize") > 0)
			{
				nbtTagCompound.setInteger("invSize", nbtTagCompound.getInteger("invSize") + 
						ConfigurationStore.BACKPACK_MAGIC_UPGRADE_INCREMENT);
			}
			else
			{
				nbtTagCompound.setInteger("invSize", ConfigurationStore.BACKPACK_MAGIC_START_SIZE + ConfigurationStore.BACKPACK_MAGIC_UPGRADE_INCREMENT);
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
