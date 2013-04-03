package mrj.advancedbackpackmod;

import mrj.advancedbackpackmod.config.ConfigurationStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
	public EntityPlayer invOwner;
	
	public InventoryBackpackMagic(ItemStack itemStack, EntityPlayer myPlayer, int color) {
		super(itemStack, myPlayer, 0);
		invOwner = myPlayer;
		if (color < 0)
		{
			currentColor = readColorFromNBT(itemStack);
		}
		else
		{
			currentColor = color;
		}
		//size = readInvSizeFromNBT(invOwner, color);
		size = readInvSizeFromNBT(invOwner, currentColor);
		
		myInventory = new ItemStack[size];
		System.out.println("InventoryBackpackMagic: invOwner = " + invOwner);
		System.out.println("InventoryBackpackMagic: myInventory.length in constructor " + myInventory.length);
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
			if(itemStack.getTagCompound().hasKey("color"))
			{
				return itemStack.getTagCompound().getInteger("color");
			}
			else
			{
				return -1;
			}
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
				System.out.println("InventoryBackpackMagic: invSize is " + colorInventoryMagic.getInteger("invSize"));
				System.out.println("InventoryBackpackMagic: color is " + color);
				return colorInventoryMagic.getInteger("invSize");
			}
			else
			{
				//System.out.println("no color inventory tag found, invSize = 27");
				return ConfigurationStore.BACKPACK_MAGIC_START_SIZE;
			}
		}
		else
		{
			//System.out.println("no inventoryMagic tag found, invSize = 27");
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
					for (int i = 0; i < myList.tagCount() && i < myInventory.length; i++)
					{
						//System.out.print("loading index "+ i);
						NBTTagCompound indexTag = (NBTTagCompound) myList.tagAt(i);
						int index = indexTag.getInteger("index");
						try {
							super.myInventory[index] = ItemStack.loadItemStackFromNBT(indexTag);
							System.out.println("loaded " + super.myInventory[index] + " into slot " + index);
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
		else
		{
			System.out.println("WARNING: NO INVENTORY TAG FOUND FOR THE invOwner");
			System.out.println("invOwner = " +invOwner);
		}
    }

	@Override
    public void writeToNBT(NBTTagCompound myCompound)
    {
		System.out.println("InventoryBackpackMagic: ************************writing to nbt************************");
		System.out.println("InventoryBackpackMagic: invOwner = " + invOwner);
		System.out.println("InventoryBackpackMagic: myCompound = " + myCompound);
		System.out.println("InventoryBackpackMagic: invOwner.getEntityData() = " + invOwner.getEntityData());
		//get the basic player compound & get the magic inventory root tag
		NBTTagCompound playerCompound = invOwner.getEntityData();
		NBTTagCompound abmInventoryMagic = (NBTTagCompound) playerCompound.getTag("abmInventoryMagic");
		
		if (abmInventoryMagic == null)
		{
			System.out.println("InventoryBackpackMagic: abmInventoryMagic == null");
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
            	//System.out.println("writing slot " + i + " to nbt");
                NBTTagCompound indexTag = new NBTTagCompound();
                indexList.appendTag(indexTag);
                indexTag.setInteger("index", i);
                myInventory[i].writeToNBT(indexTag);
            }
        }
		System.out.println("InventoryBackpackMagic: inventory length is " + myInventory.length);
		
		System.out.println("InventoryBackpackMagic: invSize = " + size);
		System.out.println("InventoryBackpackMagic: readInvSizeFromNBT = " + readInvSizeFromNBT(invOwner, currentColor));
		System.out.println("InventoryBackpackMagic: currentColor = " + currentColor);
		contentTag.setTag("indexList", indexList);
		colorInventoryMagic.setTag("abminventory", contentTag);
		colorInventoryMagic.setInteger("invSize", size);
		abmInventoryMagic.setTag("inventoryMagic" + currentColor, colorInventoryMagic);
		playerCompound.setTag("abmInventoryMagic", abmInventoryMagic);
		
		myCompound.setInteger("invSize", size);
		
		System.out.println("InventoryBackpackMagic: invOwner.getEntityData() = " + invOwner.getEntityData());
		//System.out.println(((NBTTagCompound)((NBTTagCompound)invOwner.getEntityData().getTag("abmInventoryMagic")).getTag("inventoryMagic"+currentColor)).getInteger("invSize"));
		System.out.println("InventoryBackpackMagic: ************************writing to nbt done************************");
    }

}
