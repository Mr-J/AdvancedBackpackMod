package mrj.advancedbackpackmod;

/**import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;**/
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryBackpackBase implements IInventory {
	
	private ItemStack[] myInventory;
	private int size;
	private String uniqueID;
	
	
	//will be deprecated soon
	public InventoryBackpackBase(ItemStack itemStack, EntityPlayer myPlayer)
	{
		//default values
		uniqueID = "";
		size = 195;
		if (!itemStack.hasTagCompound())
		{
			itemStack.stackTagCompound = new NBTTagCompound();	
			//Generate a random ID for every backpack
			/**DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar cal = Calendar.getInstance();
			String random = (new Random(50000)).toString();
			uniqueID = dateFormat.format(cal.getTime()) + random + myPlayer.username;**/
			uniqueID = UUID.randomUUID().toString();
		}
		else
		{
			//read inventory size from nbt
			readInvSizeFromNBT(itemStack.getTagCompound());
		}
		
		myInventory = new ItemStack[size];
		readFromNBT(itemStack.getTagCompound());
	}
	
	public InventoryBackpackBase(ItemStack itemStack, EntityPlayer myPlayer, int invSize)
	{
		//default values
		uniqueID = "";
		if (!itemStack.hasTagCompound())
		{
			if (invSize == 0)
			{
				size = 36;
			}
			else
			{
				size = invSize;
			}
			itemStack.stackTagCompound = new NBTTagCompound();	
			//Generate a random ID for every backpack
			/**DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar cal = Calendar.getInstance();
			String random = (new Random(50000)).toString();
			uniqueID = dateFormat.format(cal.getTime()) + random + myPlayer.username;**/
			uniqueID = UUID.randomUUID().toString();
		}
		else
		{
			//read inventory size from nbt
			readInvSizeFromNBT(itemStack.getTagCompound());
		}
		
		myInventory = new ItemStack[size];
		readFromNBT(itemStack.getTagCompound());
	}

	public String getName() {
		return this.uniqueID;
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

	@Override
	public boolean isUseableByPlayer(EntityPlayer myPlayer) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
	
	public void readInvSizeFromNBT(NBTTagCompound myCompound)
	{
        NBTTagCompound contentTag = ((NBTTagCompound) myCompound.getTag("abminventory"));
        if (contentTag == null)
        {
        	return;
        }
        size =  myCompound.getInteger("invSize");
	}
	
	
	
	public void readFromNBT(NBTTagCompound myCompound)
    {
        NBTTagCompound contentTag = ((NBTTagCompound) myCompound.getTag("abminventory"));
        if (contentTag == null)
        {
        	return;
        }
        
        if (uniqueID == "")
        {
        	uniqueID = myCompound.getString("uniqueID");
        }
        
        NBTTagList myList = contentTag.getTagList("indexList");
        for (int i = 0; i < myList.tagCount() && i < myInventory.length; i++)
        {
        	NBTTagCompound indexTag = (NBTTagCompound) myList.tagAt(i);
        	int index = indexTag.getInteger("index");
    		try {
    			myInventory[index] = ItemStack.loadItemStackFromNBT(indexTag);
    		} catch ( NullPointerException npe ) {
    			myInventory[index] = null;
    		}
        }

    }

    public void writeToNBT(NBTTagCompound myCompound)
    {
        NBTTagList myList = new NBTTagList();

        for (int i = 0; i < this.myInventory.length; i++)
        {
            if (this.myInventory[i] != null && this.myInventory[i].stackSize > 0)
            {
                NBTTagCompound indexTag = new NBTTagCompound();
                myList.appendTag(indexTag);
                indexTag.setInteger("index", i);
                myInventory[i].writeToNBT(indexTag);
            }
        }
        NBTTagCompound contentTag = new NBTTagCompound();
        contentTag.setTag("indexList", myList);
        myCompound.setTag("abminventory", contentTag);
        myCompound.setString("uniqueID", uniqueID);
        myCompound.setInteger("invSize", size);
    }

	@Override
	public boolean func_94042_c() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean func_94041_b(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}

	public void increaseSize(int i) {
		ItemStack[] newInventory = new ItemStack[size + i];
		System.arraycopy(myInventory, 0, newInventory, 0, size);
		size = size + i;
		
	}

    
}
