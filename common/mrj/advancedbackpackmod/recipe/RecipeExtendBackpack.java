package mrj.advancedbackpackmod.recipe;

import mrj.advancedbackpackmod.AdvancedBackpackMod;
import mrj.advancedbackpackmod.InventoryBackpackBase;
import mrj.advancedbackpackmod.ItemBackpackBase;
import mrj.advancedbackpackmod.config.ConfigurationStore;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeExtendBackpack implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world) {

		//ItemStack upgradeBag = null;
		
		if (inventoryCrafting.getSizeInventory() < 9)
		{
			//inventory smaller than 3*3 -> crafting not possible
			return false;
		}
		
		int[] recipeComponents = {Item.silk.itemID, Item.enderPearl.itemID, Item.silk.itemID,
									Item.emerald.itemID, ConfigurationStore.BACKPACK_BASE_ID, Item.emerald.itemID,
									Item.silk.itemID, Item.blazeRod.itemID, Item.silk.itemID};
		
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
							/**System.out.println("slot " + i + "/" + j + " not matching");
							System.out.println("slot is ID" + inventoryCrafting.getStackInRowAndColumn(i, j).itemID );
							System.out.println("required ID is " + recipeComponents[(i * 3) + j]);**/
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
		
		InventoryBackpackBase tmpInv = new InventoryBackpackBase(inventoryCrafting.getStackInRowAndColumn(1,1), null);
		if (tmpInv.getSizeInventory() >= ConfigurationStore.BACKPACK_MAX_SIZE)
		{
			return false;
		}
		
		//upgradeBag = inventoryCrafting.getStackInRowAndColumn(1, 1);
		/**if (!(upgradeBag.getItem() instanceof ItemBackpackBase))
		{
			//no backpack a position 1,1 (second column/row in game)
			return false;
		}**/
		
		
		
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

		ItemStack itemStack = null;
		//ItemBackpackBase upgradeBP = null;
		
		itemStack = new ItemStack(AdvancedBackpackMod.baseBackpack);
		
		//upgradeBP = (ItemBackpackBase) itemStack.getItem();
		//upgradeBP.increaseSize(4);
		if (inventoryCrafting.getStackInRowAndColumn(1,1).getItem() instanceof ItemBackpackBase)
		{
			//System.out.println("item in slot 1/1 is backpack, copy its contents to new backpack");
			InventoryBackpackBase tempInv = new InventoryBackpackBase(inventoryCrafting.getStackInRowAndColumn(1,1), null);
			InventoryBackpackBase upgradeInv;
			if (tempInv.getSizeInventory() + ConfigurationStore.BACKPACK_UPGRADE_INCREMENT <= ConfigurationStore.BACKPACK_MAX_SIZE)
			{
				upgradeInv = new InventoryBackpackBase(itemStack, null, tempInv.getSizeInventory() + ConfigurationStore.BACKPACK_UPGRADE_INCREMENT);
			}
			else
			{
				upgradeInv = new InventoryBackpackBase(itemStack, null, ConfigurationStore.BACKPACK_MAX_SIZE);
			}
			for (int i = 0; i < tempInv.getSizeInventory(); i++)
			{
				//System.out.println("copying slot " + i + " now");
				if (tempInv.getStackInSlot(i) != null)
				{
					//System.out.println("content of slot is " + tempInv.getStackInSlot(i).toString());
				}
				upgradeInv.setInventorySlotContents(i, tempInv.getStackInSlot(i));
				if (upgradeInv.getStackInSlot(i) != null)
				{
					//System.out.println("content of new slot is " + upgradeInv.getStackInSlot(i).toString());
				}
				tempInv.setInventorySlotContents(i, null);
				upgradeInv.writeToNBT(itemStack.stackTagCompound);
			}
			((ItemBackpackBase)itemStack.getItem()).setColor(itemStack, ((ItemBackpackBase)inventoryCrafting.getStackInRowAndColumn(1,1).getItem()).getColor(inventoryCrafting.getStackInRowAndColumn(1,1)));
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
