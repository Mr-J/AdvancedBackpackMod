package mrj.abm;

import java.util.UUID;

import mrj.abm.config.ConfigurationStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Advanced Backpack Mod
 * 
 * InventoryBackpackBase
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class InventoryBackpackBase extends InventoryBackpackGeneral {
	
	//protected ItemStack[] myInventory;
	//private int size;
	protected String uniqueID;
	
	public InventoryBackpackBase(ItemStack itemStack, EntityPlayer myPlayer, int invSize)
	{
		super(itemStack, myPlayer, invSize);
		//default values
		uniqueID = "";
		if (!itemStack.hasTagCompound())
		{
			itemStack.stackTagCompound = new NBTTagCompound();	
			//Generate a random ID for every backpack
			uniqueID = UUID.randomUUID().toString();
		}
		//readInvSizeFromNBT(itemStack.getTagCompound());
		size = readInvSizeFromNBT(itemStack.getTagCompound());
		
		myInventory = new ItemStack[size];
		readFromNBT(itemStack.getTagCompound());
	}

	@Override
	public String getInvName() {
		return this.uniqueID;
	}
	
	@Override
	public int readInvSizeFromNBT(NBTTagCompound myCompound)
	{
		if (myCompound != null)
		{
			 NBTTagCompound contentTag = ((NBTTagCompound) myCompound.getTag("abminventory"));
			 if (contentTag == null)
			 {
				 return ConfigurationStore.BACKPACK_BASE_START_SIZE;
			 }
		}
		return  myCompound.getInteger("invSize");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound myCompound)
    {
        NBTTagCompound contentTag = ((NBTTagCompound) myCompound.getTag("abminventory"));
        if (contentTag == null)
        {
        	return;
        }
        
        if ("".equals(uniqueID))
        {
           	uniqueID = myCompound.getString("uniqueID");
        	if ("".equals(uniqueID))
        	{
        		uniqueID = UUID.randomUUID().toString();
        	}
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

	@Override
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
    
}
