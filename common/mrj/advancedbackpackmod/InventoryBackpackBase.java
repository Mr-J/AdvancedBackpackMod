package mrj.advancedbackpackmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryBackpackBase implements IInventory {
	
	private ItemStack[] myInventory;
	//private int invSizeX;
	//private int invSizeY;
	
	public InventoryBackpackBase(int x, int y)
	{
		myInventory = new ItemStack[x * y];
	}

	@Override
	public int getSizeInventory() {
		return this.myInventory.length;
	}
	
	/**public int getSizeInventoryX() {
		return this.invSizeX;
	}
	
	public int getSizeInventoryY() {
		return this.invSizeY;
	}**/

	@Override
	public ItemStack getStackInSlot(int var1) {
		return myInventory[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onInventoryChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * Writes the inventory out as a list of compound tags. This is where the slot indices are used (+100 for armor, +80
     * for crafting).
     */
    public NBTTagList writeToNBT(NBTTagList myNBTTagList)
    {
        NBTTagCompound myNBTTagComp;

        for (int i = 0; i < this.myInventory.length; i++)
        {
            if (this.myInventory[i] != null)
            {
                myNBTTagComp = new NBTTagCompound();
                myNBTTagComp.setByte("Slot", (byte)i);
                this.myInventory[i].writeToNBT(myNBTTagComp);
                myNBTTagList.appendTag(myNBTTagComp);
            }
        }

        return myNBTTagList;
    }

    /**
     * Reads from the given tag list and fills the slots in the inventory with the correct items.
     */
    public void readFromNBT(NBTTagList myNBTTagList)
    {
        /**this.myInventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < myNBTTagList.tagCount(); i++)
        {
            NBTTagCompound myNBTTagComp = (NBTTagCompound)myNBTTagList.tagAt(i);
            int j = myNBTTagComp.getByte("Slot") & 0xff;
            ItemStack tempStack = ItemStack.loadItemStackFromNBT(myNBTTagComp);

            if (tempStack == null)
            {
            	if ( j >= 0 && j < myInventory.length)
            	{
            		myInventory[j] = tempStack;
            	}
            	else
            	{
            		System.out.println("Index access error, j out of array bounds");
            	}
            }
        }
        onInventoryChanged();**/
    }

}
