package mrj.advancedbackpackmod;

import mrj.advancedbackpackmod.config.ConfigurationStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class ContainerBackpackBase extends Container {

	int invRow, invCol, colPlayer, invSize, rest;
	InventoryBackpackBase containerInv;
	
	public ContainerBackpackBase(InventoryBackpackBase myBPInv, InventoryPlayer myPlayerInv)
	{
		containerInv = myBPInv;
		invSize = containerInv.getSizeInventory();
		if (invSize < 55)
		{
			if (invSize < 9)
			{
				invCol = invSize;
			}
			else
			{
				invCol = 9;
			}
			invRow = (containerInv.getSizeInventory() / invCol);
		}
		else
		{
			invCol = 10;
			invRow = (containerInv.getSizeInventory()/ invCol);
			while (invRow > (invCol - 2))
			{
				invCol++;
				invRow = (containerInv.getSizeInventory() / invCol);
			}
		}
		rest = containerInv.getSizeInventory() - (invCol * invRow);
		
		//default starting position of the first container inventory slot
		int positionBackpackX = 8;
		int positionPlayerX = 8;
		int positionY = 18;
		if (invCol < 9)
		{
			//inventory Columns < 9; inventory is smaller than player inv/hotbar
			positionBackpackX = positionBackpackX + (9 - invCol) * 9;
		}
		else if (invCol > 9)
		{
			//inventory columns > 9; inventory is bigger than player inv/hotbar
			positionPlayerX = positionPlayerX + (invCol - 9) * 9;
		}
		
		//set the container slots
		for (int i = 0; i < invRow; i++)
		{
			setSlotLine(myBPInv, i * invCol, invCol, positionBackpackX, positionY);
			positionY = positionY + 18; 
		}
		//the rest slots, not enough for a full line
		if (rest > 0)
		{
			setSlotLine(myBPInv, invRow * invCol, rest, positionBackpackX + (invCol - rest) * 9, positionY);
			positionY = positionY + 18;
		}
		//upper seperator
		positionY = positionY + 14;
		
		//set the player inventory slots
		for (int i = 0; i < 3; i++)
		{
			setSlotLine(myPlayerInv, 9 * (i + 1), 9, positionPlayerX, positionY);
			positionY = positionY + 18;
		}
		
		//lower seperator
		positionY = positionY + 4;
		
		//set the player hotbar slots
		setSlotLine(myPlayerInv, 0, 9, positionPlayerX, positionY);
	}
	
	public void setSlotLine(IInventory targetInv, int startSlot, int slots, int x, int y)
	{
		int currentX = x;
		for (int i = 0; i < slots; i++)
		{
			this.addSlotToContainer(new Slot(targetInv, startSlot + i, currentX, y));
			currentX = currentX + 18;
		}
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer myPlayer) {
		 
		return true;
	}
	
	public void saveToNBT(ItemStack itemStack)
	{
		if (!itemStack.hasTagCompound())
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
		containerInv.writeToNBT(itemStack.getTagCompound());
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer myPlayer, int toSlot)
    {
        ItemStack tempStack = null;
        Slot invSlot = (Slot)this.inventorySlots.get(toSlot);
        
        if (invSlot != null && invSlot.getHasStack())
        {
        	ItemStack tempStack2 = invSlot.getStack();
        	
        	if (tempStack2.itemID == ConfigurationStore.BACKPACK_BASE)
        	{
        		//true if the current stack is a ItemBackpackBase Item
        		//replace fixed number with dynamic id
        		InventoryBackpackBase chkInv = new InventoryBackpackBase(tempStack2, myPlayer);
        		if (chkInv.getName() == containerInv.getName())
        		{
        			return tempStack;
        		}
        	}
        	
        	tempStack = tempStack2.copy();
        	
        	//if (toSlot < this.x * this.y)
        	if (toSlot < invSize)
        	{
        		//if(!this.mergeItemStack(tempStack2, this.x * this.y, this.inventorySlots.size(), true))
        		if(!this.mergeItemStack(tempStack2, invSize, this.inventorySlots.size(), true))
	        	{
        			return null;
        		}
        	}
        	//else if (!this.mergeItemStack(tempStack2, 0, this.x * this.y, false))
        	else if (!this.mergeItemStack(tempStack2, 0, invSize, false))
        	{
        		return null;
        	}
        	
        	if (tempStack2.stackSize == 0)
        	{
        		invSlot.putStack((ItemStack)null);
        	}
        	else 
        	{
        		invSlot.onSlotChanged();
        	}
        }
        return tempStack;
    }
	
	@Override
	public ItemStack slotClick(int slotID, int buttonPressed, int flag, EntityPlayer player)
    {
		Slot tmpSlot;
		if (slotID >= 0 && slotID < inventorySlots.size())
		{
			tmpSlot = (Slot) inventorySlots.get(slotID);
		}
		else
		{
			tmpSlot = null;
		}
		
		if (tmpSlot != null && tmpSlot.isSlotInInventory(player.inventory, player.inventory.currentItem))
		{
			return tmpSlot.getStack();
		}
		return super.slotClick(slotID, buttonPressed, flag, player);
    }
}
