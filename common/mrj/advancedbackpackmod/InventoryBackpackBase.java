package mrj.advancedbackpackmod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryBackpackBase implements IInventory {
	
	private ItemStack[] myInventory;
	private int invSizeX;
	private int invSizeY;
	private int size;
	private String uniqueID;
	
	public InventoryBackpackBase(int x, int y, ItemStack itemStack, EntityPlayer myPlayer)
	{
		myInventory = new ItemStack[x * y];
		invSizeX = x;
		invSizeY = y;
		size = x * y;
		uniqueID = "";
		
		if (!itemStack.hasTagCompound())
		{
			itemStack.stackTagCompound = new NBTTagCompound();	
			//TEST
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar cal = Calendar.getInstance();
			String random = (new Random(50000)).toString();
			uniqueID = dateFormat.format(cal.getTime()) + random + myPlayer.username;
		}
		readFromNBT(itemStack.getTagCompound());
	}

	public String getName() {
		return this.uniqueID;
	}
	
	@Override
	public int getSizeInventory() {
		return this.myInventory.length;
	}
	
	public int getSizeInventoryX() {
		return this.invSizeX;
	}
	
	public int getSizeInventoryY() {
		return this.invSizeY;
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
    }

    
}