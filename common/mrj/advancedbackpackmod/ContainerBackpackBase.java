package mrj.advancedbackpackmod;

//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
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
	//boolean containerPickedUp = false;
	
	public ContainerBackpackBase(InventoryBackpackBase myBPInv, InventoryPlayer myPlayerInv)
	{
		containerInv = myBPInv;
		invSize = containerInv.getSizeInventory();
		//x = myBPInv.getSizeInventoryX();
		//y = myBPInv.getSizeInventoryY();
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
			invRow = (containerInv.getSizeInventory() / invCol + 2);
		}
		else
		{
			invCol = 10;
			while ((containerInv.getSizeInventory() / invCol) > invCol)
			{
				//System.out.println("containerInv.getSizeInventory() / invCol = " + containerInv.getSizeInventory() / invCol +
				//		" invCol = " + invCol + " => resizing");
				invCol++;
			}
			invRow = (containerInv.getSizeInventory() / invCol);
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
	
		//calculate x & y from the inventory size
		/**
		

		int currentX = 8;
		int currentY = 18;
		/**if (invCol <= 9)
		{
			//set the container inv slots
			//float offset = rest/2;
			for (int row = 0; row < invRow; row++)
			{
				for (int col = 0; col < invCol; col++)
				{
					this.addSlotToContainer(new Slot(containerInv, col + row * invCol, currentX, currentY));
					currentX = currentX + 18;
				}
				currentY = currentY + 18;
			}
			currentX = 8;
			if (rest > 0)
			{
				currentX = currentX + 9 * rest;
				for (int i = 0; i < rest; i++)
				{
					this.addSlotToContainer(new Slot(containerInv, i + invRow * invCol, currentX, currentY));
				}
				currentX = currentX + 18;
			}
			//add upper seperator y-value and reset x
			currentY = currentY + 14;
			currentX = 8;
			
			//set the player inv slots
			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 9; j++)
				{
					this.addSlotToContainer(new Slot(myPlayerInv, (i + 1) * 9 + j, currentX, currentY)); 
					currentX = currentX + 18;
				}
				currentY = currentY + 18;				
			}
			//add lower seperator y-value and reset x
			currentY = currentY + 4;
			currentX = 8;
			
			//set the player hotbar slots
			for (int i = 0; i < 9; i++)
			{
				this.addSlotToContainer(new Slot(myPlayerInv, i, 8 + i * 18, 36 + (invRow + 3) * 18)); //18 + y * 18 + 14 + 3 * 18 + 4
			}
		}
		else //colInv > 9
		{
			//set the container inv slots
			//float offset = rest/2;
			for (int row = 0; row < invRow; row++)
			{
				for (int col = 0; col < invCol; col++)
				{
					this.addSlotToContainer(new Slot(containerInv, col + row * invCol, 8 + col * 18, 18 + row * 18));
				}
			}
			//set the player inv slots
			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 9; j++)
				{
					this.addSlotToContainer(new Slot(myPlayerInv, (i + 1) * 9 + j, 8 + rest * 9 + j * 18, 32 + (invRow + i) * 18)); //last element = 18 + invRow * 18 + 14 + i * 18
				}
			}
			//set the player hotbar slots
			for (int i = 0; i < 9; i++)
			{
				this.addSlotToContainer(new Slot(myPlayerInv, i, 8 + rest * 9 + i * 18, 36 + (invRow + 3) * 18)); //18 + y * 18 + 14 + 3 * 18 + 4
			}
		}
		
		/**for (int row = 0; row < y; row++)
		{
			for (int col = 0; col < x; col++)
			{
				//container inventory
				this.addSlotToContainer(new Slot(containerInv, col + row * x, 8 + col * 18, 18 + row * 18));
				//this.addSlotToContainer(new Slot(containerInv, col + row * x, 8 + col * 18, row * 18));
			}
		}
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				//player inventory
				this.addSlotToContainer(new Slot(myPlayerInv, (i + 1) * 9 + j, 8 + j * 18, 18 + y * 18 + 14 + i * 18));
				//this.addSlotToContainer(new Slot(myPlayerInv, (i + 1) * 9 + j, 8 + j * 18, y * 18 + 14 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			//player bar
			this.addSlotToContainer(new Slot(myPlayerInv, i, 8 + i * 18, 18 + y * 18 + 14 + 3 * 18 + 4));
			//this.addSlotToContainer(new Slot(myPlayerInv, i, 8 + i * 18, y * 18 + 14 + 3 * 18 + 4));
		}**/
	

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
        	
        	//System.out.println("itemID = "+ tempStack2.itemID);
        	if (tempStack2.itemID == ConfigurationStore.BACKPACK_BASE)
        	{
        		//true if the current stack is a ItemBackpackBase Item
        		//replace fixed number with dynamic id
        		InventoryBackpackBase chkInv = new InventoryBackpackBase(tempStack2, myPlayer);
        		if (chkInv.getName() == containerInv.getName())
        		{
        			//System.out.println("container can not be inserted into itself");
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
			//System.out.println("tried to move a opened container around, dont do that!");
			return tmpSlot.getStack();
		}
		return super.slotClick(slotID, buttonPressed, flag, player);
    }
}
