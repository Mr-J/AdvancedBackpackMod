package mrj.advancedbackpackmod.recipe;

import java.util.ArrayList;

import mrj.advancedbackpackmod.ItemBackpackBase;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ExtendBackpacks implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world) {

		ItemStack upgradeBag = null;
		
		if (inventoryCrafting.getSizeInventory() < 9)
		{
			return false;
		}
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		// TODO Auto-generated method stub
		return null;
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
