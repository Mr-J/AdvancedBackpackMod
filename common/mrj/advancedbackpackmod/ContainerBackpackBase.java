package mrj.advancedbackpackmod;

//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
import mrj.advancedbackpackmod.config.ConfigurationStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class ContainerBackpackBase extends Container {

	int x, y;
	InventoryBackpackBase containerInv;
	boolean containerPickedUp = false;
	
	public ContainerBackpackBase(InventoryBackpackBase myBPInv, InventoryPlayer myPlayerInv)
	{
		containerInv = myBPInv;
		x = myBPInv.getSizeInventoryX();
		y = myBPInv.getSizeInventoryY();
		for (int row = 0; row < y; row++)
		{
			for (int col = 0; col < x; col++)
			{
				//container inventory
				this.addSlotToContainer(new Slot(containerInv, col + row * x, 8 + col * 18, 18 + row * 18));
			}
		}
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				//player inventory
				this.addSlotToContainer(new Slot(myPlayerInv, (i + 1) * 9 + j, 8 + j * 18, 18 + y * 18 + 14 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			//player bar
			this.addSlotToContainer(new Slot(myPlayerInv, i, 8 + i * 18, 18 + y * 18 + 14 + 3 * 18 + 4));
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
        	
        	System.out.println("itemID = "+ tempStack2.itemID);
        	if (tempStack2.itemID == ConfigurationStore.BACKPACK_BASE)
        	{
        		//true if the current stack is a ItemBackpackBase Item
        		//replace fixed number with dynamic id
        		InventoryBackpackBase chkInv = new InventoryBackpackBase(9, 6, tempStack2, myPlayer);
        		if (chkInv.getName() == containerInv.getName())
        		{
        			//System.out.println("container can not be inserted into itself");
        			return tempStack;
        		}
        	}
        	
        	tempStack = tempStack2.copy();
        	
        	if (toSlot < this.x * this.y)
        	{
        		if(!this.mergeItemStack(tempStack2, this.x * this.y, this.inventorySlots.size(), true))
	        	{
        			return null;
        		}
        	}
        	else if (!this.mergeItemStack(tempStack2, 0, this.x * this.y, false))
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
