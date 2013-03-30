package mrj.advancedbackpackmod.recipe;

import mrj.advancedbackpackmod.config.ConfigurationStore;
import mrj.advancedbackpackmod.ItemBackpackBase;
import mrj.advancedbackpackmod.ItemBackpackMagic;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Advanced Backpack Mod
 * 
 * RecipeColorBackpack
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class RecipeColorBackpack implements IRecipe {

	public static final String[] colorNames = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world) {
		boolean colorFound = false;
		boolean backpackFound = false;
		for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++)
		{
			if (inventoryCrafting.getStackInSlot(i) != null)
			{
				if (inventoryCrafting.getStackInSlot(i).itemID == Item.dyePowder.itemID)
				{
					if (colorFound)
					{
						return false;
					}
					else
					{
						colorFound = true;
					}
				}
				else if (inventoryCrafting.getStackInSlot(i).itemID == ConfigurationStore.BACKPACK_BASE_ID ||
						inventoryCrafting.getStackInSlot(i).itemID == ConfigurationStore.BACKPACK_MAGIC_ID)
				{
					if (backpackFound)
					{
						return false;
					}
					else
					{
						backpackFound = true;
					}
				}
			}
		}
		if (colorFound && backpackFound)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
		int color = -1;
		ItemStack itemStack = null;
		for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++)
		{
			if (inventoryCrafting.getStackInSlot(i) != null) 
			{
				if (inventoryCrafting.getStackInSlot(i).itemID == Item.dyePowder.itemID)
				{
					//System.out.println(inventoryCrafting.getStackInSlot(i) + " detected");
					color = MathHelper.clamp_int(inventoryCrafting.getStackInSlot(i).getItemDamage(), 0, 15);
					//System.out.println("color = " + color);
				}
				else if (inventoryCrafting.getStackInSlot(i).itemID == ConfigurationStore.BACKPACK_BASE_ID ||
						inventoryCrafting.getStackInSlot(i).itemID == ConfigurationStore.BACKPACK_MAGIC_ID)
				{
					itemStack = inventoryCrafting.getStackInSlot(i);
				}
				else
				{
					return null;
				}
			}
			
		}
		ItemStack tmpStack = null;
		if (color >= 0 && itemStack != null)
		{
			//System.out.println("setting color to " + colorNames[color]);
			tmpStack = itemStack.copy();
			((ItemBackpackBase)tmpStack.getItem()).setColor(tmpStack, color);
		}
		else
		{
			return null;
		}
		return tmpStack;
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

}
