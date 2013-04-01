package mrj.advancedbackpackmod;

import mrj.advancedbackpackmod.config.ConfigurationStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Advanced Backpack Mod
 * 
 * InventoryBackpackMagic
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class InventoryBackpackMagic extends InventoryBackpackGeneral {

	private int currentColor;
	private EntityPlayer invOwner;
	
	public InventoryBackpackMagic(ItemStack itemStack, EntityPlayer myPlayer, int color) {
		super(itemStack, myPlayer, 0);
		invOwner = myPlayer;
		if (color < 0)
		{
			currentColor = readColorFromNBT(itemStack);
		}
		size = readInvSizeFromNBT(invOwner, color);
		
		myInventory = new ItemStack[size];
		readFromNBT(itemStack.getTagCompound());
	}
	
	public int readColorFromNBT(ItemStack itemStack)
	{
		if (!itemStack.hasTagCompound())
		{
			NBTTagCompound nbtTagCompound = new NBTTagCompound();
			itemStack.setTagCompound(nbtTagCompound);
			nbtTagCompound.setInteger("color", -1);
			return -1;
		}
		else
		{
			return itemStack.getTagCompound().getInteger("color");
		}
	}
	
	public int readInvSizeFromNBT(EntityPlayer entityPlayer, int color)
	{
		NBTTagCompound playerCompound = entityPlayer.getEntityData();
		NBTTagCompound abmInventoryMagic = (NBTTagCompound) playerCompound.getTag("abmInventoryMagic");

		if (abmInventoryMagic != null)
		{
			NBTTagCompound colorInventoryMagic = (NBTTagCompound) abmInventoryMagic.getTag("inventoryMagic"+color);
			if (colorInventoryMagic != null)
			{
				return colorInventoryMagic.getInteger("invSize");
			}
			else
			{
				return ConfigurationStore.BACKPACK_MAGIC_START_SIZE;
			}
		}
		else
		{
			return ConfigurationStore.BACKPACK_MAGIC_START_SIZE;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound myCompound)
    {
		NBTTagCompound playerCompound = invOwner.getEntityData();
		NBTTagCompound abmInventoryMagic = (NBTTagCompound) playerCompound.getTag("abmInventoryMagic");
		
		if (abmInventoryMagic != null)
		{
			//general magic inventory tag found
			
			NBTTagCompound colorInventoryMagic = (NBTTagCompound) abmInventoryMagic.getTag("inventoryMagic" + currentColor);
			//System.out.println("loading from inventoryMagic" + currentColor);
			if (colorInventoryMagic != null)
			{
				//System.out.println("load: colorInventory tag found");
				//color specific inventory tag found
				NBTTagCompound contentTag = (NBTTagCompound) colorInventoryMagic.getTag("abminventory");
				if (contentTag != null)
				{
					//System.out.println("content tag found");
					NBTTagList myList = contentTag.getTagList("indexList");
					for (int i = 0; i < myList.tagCount() && i < super.myInventory.length; i++)
					{
						//System.out.print("loading index "+ i);
						NBTTagCompound indexTag = (NBTTagCompound) myList.tagAt(i);
						int index = indexTag.getInteger("index");
						try {
							super.myInventory[index] = ItemStack.loadItemStackFromNBT(indexTag);
							//System.out.println(" successful");
						} 
						catch ( NullPointerException npe ) 
						{
							super.myInventory[index] = null;
							//System.out.println(" failed");
						}
					}
				}
			}
		}
		
		
		
        /**NBTTagCompound contentTag = ((NBTTagCompound) myCompound.getTag("abminventory"));
        if (contentTag == null)
        {
        	return;
        }
        
        if (super.uniqueID == "")
        {
        	super.uniqueID = myCompound.getString("uniqueID");
        }
        
        NBTTagList myList = contentTag.getTagList("indexList");
        for (int i = 0; i < myList.tagCount() && i < super.myInventory.length; i++)
        {
        	NBTTagCompound indexTag = (NBTTagCompound) myList.tagAt(i);
        	int index = indexTag.getInteger("index");
    		try {
    			super.myInventory[index] = ItemStack.loadItemStackFromNBT(indexTag);
    		} catch ( NullPointerException npe ) {
    			super.myInventory[index] = null;
    		}
        }**/

    }

	@Override
    public void writeToNBT(NBTTagCompound myCompound)
    {
		//get the basic player compound & get the magic inventory root tag
		NBTTagCompound playerCompound = invOwner.getEntityData();
		NBTTagCompound abmInventoryMagic = (NBTTagCompound) playerCompound.getTag("abmInventoryMagic");
		
		if (abmInventoryMagic == null)
		{
			//no tag available -> create it
			abmInventoryMagic = new NBTTagCompound();
		}
		
		NBTTagCompound colorInventoryMagic = new NBTTagCompound();
		NBTTagCompound contentTag = new NBTTagCompound();
		NBTTagList indexList = new NBTTagList();
		
		for (int i = 0; i < this.myInventory.length; i++)
        {
            if (this.myInventory[i] != null && this.myInventory[i].stackSize > 0)
            {
                NBTTagCompound indexTag = new NBTTagCompound();
                indexList.appendTag(indexTag);
                indexTag.setInteger("index", i);
                myInventory[i].writeToNBT(indexTag);
            }
        }
		
		contentTag.setTag("indexList", indexList);
		colorInventoryMagic.setTag("abminventory", contentTag);
		colorInventoryMagic.setInteger("invSize", size);
		abmInventoryMagic.setTag("inventoryMagic" + currentColor, colorInventoryMagic);
		playerCompound.setTag("abmInventoryMagic", abmInventoryMagic);
		
		
    }

}
