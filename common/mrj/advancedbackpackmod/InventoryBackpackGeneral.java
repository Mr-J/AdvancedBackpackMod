package mrj.advancedbackpackmod;

//import java.util.UUID;

//import mrj.advancedbackpackmod.config.ConfigurationStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;

/**
 * Advanced Backpack Mod
 * 
 * InventoryBackpackGeneral
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class InventoryBackpackGeneral implements IInventory {
	protected ItemStack[] myInventory;
	protected int size;
	
	public InventoryBackpackGeneral(ItemStack itemStack, EntityPlayer myPlayer, int invSize)
	{

	}
	
	@Override
	public int getSizeInventory() {
		return this.myInventory.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int var1) {
		return myInventory[var1];
	}

	@Override
	public ItemStack decrStackSize(int slot, int number) {
		if (myInventory[slot] == null)
		return null;
		ItemStack returnStack;
		if (myInventory[slot].stackSize > number)
		{
			returnStack = myInventory[slot].splitStack(number);
		}
		else
		{
			returnStack = myInventory[slot];
			myInventory[slot] = null;
		}
		onInventoryChanged();
		return returnStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack returnStack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return returnStack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		if (0 <= slot && slot < size)
		{
			myInventory[slot] = itemStack;
		}
		
	}

	@Override
	public void onInventoryChanged() {
		for (int i = 0; i < size; i++)
		{
			ItemStack tempStack = getStackInSlot(i);
			if (tempStack != null && tempStack.stackSize == 0)
			{
				setInventorySlotContents(i, null);
			}
		}
	}

	public void readInvSizeFromNBT(NBTTagCompound myCompound)
	{
		//This needs to be overwritten
	}
	
	public void readFromNBT(NBTTagCompound myCompound)
    {
		//This needs to be overwritten
    }

    public void writeToNBT(NBTTagCompound myCompound)
    {
    	//This needs to be overwritten
    }

	public void increaseSize(int i) {
		ItemStack[] newInventory = new ItemStack[size + i];
		System.arraycopy(myInventory, 0, newInventory, 0, size);
		size = size + i;
		
	}
	
	public String getName() {
		//Overwrite this
		return "";
	}
	
	//*********************************************************************
	//UNUSED METHODS
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer myPlayer) {
		return true;
	}
	
	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}
	
	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return true;
	}
}
