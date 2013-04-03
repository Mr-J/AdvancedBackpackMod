package mrj.advancedbackpackmod;

import java.util.UUID;

import mrj.advancedbackpackmod.config.ConfigurationStore;
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
	
	/**public InventoryBackpackBase(ItemStack itemStack, EntityPlayer myPlayer)
	{
		//default values
		uniqueID = "";
		size = ConfigurationStore.BACKPACK_BASE_SIZE;
		if (!itemStack.hasTagCompound())
		{
			itemStack.stackTagCompound = new NBTTagCompound();	
			//Generate a random ID for every backpack
			uniqueID = UUID.randomUUID().toString();
		}
		else
		{
			//read inventory size from nbt
			readInvSizeFromNBT(itemStack.getTagCompound());
		}
		
		myInventory = new ItemStack[size];
		readFromNBT(itemStack.getTagCompound());
		
		//new InventoryBackpackBase(itemStack, myPlayer, ConfigurationStore.BACKPACK_BASE_SIZE);
	}**/
	
	public InventoryBackpackBase(ItemStack itemStack, EntityPlayer myPlayer, int invSize)
	{
		super(itemStack, myPlayer, invSize);
		//default values
		uniqueID = "";
		if (!itemStack.hasTagCompound())
		{
			/**if (invSize == 0)
			{
				size = ConfigurationStore.BACKPACK_BASE_START_SIZE;
			}
			else
			{
				size = invSize;
			}**/
			itemStack.stackTagCompound = new NBTTagCompound();	
			//Generate a random ID for every backpack
			uniqueID = UUID.randomUUID().toString();
		}
		//else
		//{
			//read inventory size from nbt
			readInvSizeFromNBT(itemStack.getTagCompound());
		//}
		
		myInventory = new ItemStack[size];
		readFromNBT(itemStack.getTagCompound());
	}

	@Override
	public String getName() {
		return this.uniqueID;
	}
	
	@Override
	public void readInvSizeFromNBT(NBTTagCompound myCompound)
	{
		if (myCompound != null)
		{
			 NBTTagCompound contentTag = ((NBTTagCompound) myCompound.getTag("abminventory"));
			 if (contentTag == null)
			 {
				 size = ConfigurationStore.BACKPACK_BASE_START_SIZE;
			 }
			 else
			 {
				 size =  myCompound.getInteger("invSize");
			 }
		}
		else
		{
			System.out.println("FATAL ERROR");
		}
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
        		System.out.println("still no uniqueID given");
        		uniqueID = UUID.randomUUID().toString();
        	}
        }
        else
        {
        	System.out.println("uniqueID != \"\"");
        	System.out.println("uniqueID = " + uniqueID);
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
        
        System.out.println("uniqueID set to " + uniqueID);
    }
    
}
