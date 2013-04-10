package mrj.abm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import mrj.abm.config.ConfigurationStore;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;

/**
 * Advanced Backpack Mod
 * 
 * ContainerBackpackShared
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ContainerBackpackShared extends Container {

	//int invRow, invCol, colPlayer, invSize, rest;
	int invBackpackRow, invBackpackCol, invBackpackSize, invBackpackRest, 
		invContainerRow, invContainerCol, invContainerSize, invContainerRest;
	
	InventoryBackpackGeneral inventoryBackpack;
	IInventory inventoryContainer;
	boolean updateNotification;
	String containerName;
	
	public ContainerBackpackShared(InventoryBackpackGeneral backpackInventory, IInventory containerInventory)
	{
		containerName = containerInventory.getInvName();
		updateNotification = false;
		inventoryBackpack = backpackInventory;
		inventoryContainer = containerInventory;
		
		invBackpackSize = inventoryBackpack.getSizeInventory();
		invContainerSize = inventoryContainer.getSizeInventory();
		
		//find the right sizes for columns & rows of the backpack inventory
		if (invBackpackSize < 55)
		{
			if (invBackpackSize < 9)
			{
				invBackpackCol = invBackpackSize;
			}
			else
			{
				invBackpackCol = 9;
			}
			invBackpackRow = (inventoryBackpack.getSizeInventory() / invBackpackCol);
		}
		else
		{
			if (ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE == false)
			{
				invBackpackCol = 10;
				invBackpackRow = (inventoryBackpack.getSizeInventory()/ invBackpackCol);
				while (invBackpackRow > (invBackpackCol - 2))
				{
					invBackpackCol++;
					invBackpackRow = (inventoryBackpack.getSizeInventory() / invBackpackCol);
				}
			}
			else
			{
				invBackpackCol = 9;
				invBackpackRow = (inventoryBackpack.getSizeInventory() / invBackpackCol);
			}
		}
		invBackpackRest = inventoryBackpack.getSizeInventory() - (invBackpackCol * invBackpackRow);
		
		//do the same for the container inventory
		if (invContainerSize < 55)
		{
			if (invContainerSize < 9)
			{
				invContainerCol = invContainerSize;
			}
			else
			{
				invContainerCol = 9;
			}
			invContainerRow = (inventoryContainer.getSizeInventory() / invContainerCol);
		}
		else
		{
			if (ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE == false)
			{
				invContainerCol = 10;
				invContainerRow = (inventoryContainer.getSizeInventory()/ invContainerCol);
				while (invContainerRow > (invContainerCol - 2))
				{
					invContainerCol++;
					invContainerRow = (inventoryContainer.getSizeInventory() / invContainerCol);
				}
			}
			else
			{
				invContainerCol = 9;
				invContainerRow = (inventoryContainer.getSizeInventory() / invContainerCol);
			}
		}
		invContainerRest = inventoryContainer.getSizeInventory() - (invContainerCol * invContainerRow);
		
		
		//default starting position of the first container inventory slot
		int positionBackpackX = 8;
		int positionContainerX = 8;
		int positionY = 18;
		
		if (invBackpackCol <= invContainerCol)
		{
			//backpack inventory columns smaller than container inventory columns
			//use container inventory columns as reference for max width
			positionBackpackX = positionBackpackX + (invContainerCol - invBackpackCol) * 9;
		}
		else
		{
			//backpack inventory columns bigger than container inventory columns
			//use backpack inventory columns as reference for max width
			positionContainerX = positionContainerX + (invBackpackCol - invContainerCol) * 9;
		}
		
		//start making the slot lines
		for (int i = 0; i < invContainerRow; i++)
		{
			setSlotLine(inventoryContainer, i * invContainerCol, invContainerCol, positionContainerX, positionY);
			positionY = positionY + 18;
		}
		if (invContainerRest > 0)
		{
			setSlotLine(inventoryContainer, invContainerRow * invContainerCol, invContainerRest, positionContainerX + (invContainerCol - invContainerRest) * 9, positionY);
			positionY = positionY + 18;
		}
		//seperator
		positionY = positionY + 14;
		
		for (int i = 0; i < invBackpackRow; i++)
		{
			setSlotLine(inventoryBackpack, i * invBackpackCol, invBackpackCol, positionBackpackX, positionY);
			positionY = positionY + 18;
		}
		if (invBackpackRest > 0)
		{
			setSlotLine(inventoryBackpack, invBackpackRow * invBackpackCol, invBackpackRest, positionBackpackX + (invBackpackCol - invBackpackRest) * 9, positionY);
			positionY = positionY + 18;
		}
		
		/**if (invCol <= 9)
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
		setSlotLine(myPlayerInv, 0, 9, positionPlayerX, positionY);**/
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
	
	//public void saveToNBT(ItemStack itemStack, TileEntity tileEntity)
	public void saveToNBT(ItemStack itemStack)
	{
		if (!itemStack.hasTagCompound())
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
		inventoryBackpack.writeToNBT(itemStack.getTagCompound());
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer myPlayer, int targetSlot)
    {
        ItemStack tempStack = null;
        /**
         * needs to be rewritten
         * 
         */
        //System.out.println("transferStackInSlot in ContainerBackpackShared invoked");
        Slot invSlot = (Slot)this.inventorySlots.get(targetSlot);
        
        if (invSlot != null && invSlot.getHasStack())
        {
            ItemStack tempStack2 = invSlot.getStack();
            
            /**if (tempStack2.itemID == ConfigurationStore.BACKPACK_BASE_ID)
            {
                //true if the current stack is a ItemBackpackBase Item
                
                InventoryBackpackBase chkInv = new InventoryBackpackBase(tempStack2, myPlayer, 0);
                if (chkInv.getName() == containerInv.getName())
                {
                    return tempStack;
                }
            }**/
            
            tempStack = tempStack2.copy();
            
            //if (toSlot < this.x * this.y)
            if (targetSlot < invBackpackSize)
            {
                //if(!this.mergeItemStack(tempStack2, this.x * this.y, this.inventorySlots.size(), true))
                if(!this.mergeItemStack(tempStack2, invBackpackSize, this.inventorySlots.size(), true))
                {
                    //System.out.println("here2");
                    return null;
                }
            }
            //else if (!this.mergeItemStack(tempStack2, 0, this.x * this.y, false))
            else if (!this.mergeItemStack(tempStack2, 0, invBackpackSize, false))
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
        updateNotification = true;
        return tempStack;
    }
	
	/**
	 * old version of slotClick, doesnt work for shared inventories
	 */
	
	/**@Override
	public ItemStack slotClick(int slotID, int buttonPressed, int flag, EntityPlayer player)
    {
		Slot tmpSlot;
		if (slotID >= 0 && slotID < inventorySlots.size())
		{
			tmpSlot = (Slot) inventorySlots.get(slotID);
		}
		else
		{
			//should never pick a item from a slot greater than the number of inventory slots
			tmpSlot = null;
		}
		
		if (tmpSlot != null && tmpSlot.isSlotInInventory(player.inventory, player.inventory.currentItem))
		{
			//can not pick the current opened container up
			return tmpSlot.getStack();
		}
		updateNotification = true;
		return super.slotClick(slotID, buttonPressed, flag, player);
    }**/
	
	/**@Override
	public ItemStack slotClick(int slotID, int buttonPressed, int flag, EntityPlayer player)
    {
		 //just a replacement to check out the parameters of this method
		System.out.println("slotClick in ContainerBackpackShared invoked");
		System.out.println("slotID clicked = " + slotID);
		System.out.println("buttonPressed = " + buttonPressed);
		System.out.println("flag = " + flag);
		System.out.println("player = " + player);
		updateNotification = true;
		return super.slotClick(slotID, buttonPressed, flag, player);
    }**/
	
	/********************************************************************************************************
	 * slotClick section
	 * 
	 * since the slotClick method is a bit complicated it gets its own section in this mod
	 * also all helper variables & functions will be here
	 * 
	 */
	
	/*
	 * these 3 variables are private in container, so we can not access them here
	 * we mirror and rename these locally here
	 * private int field_94536_g = 0;
	 * seems to hold the state of the dragged multislot placement of an itemstack
	 * renamed to distributeState
     * 0 = not started
     * 1 = currently placing
     * 2 = drag operation done, place into the slots
     * private final Set field_94537_h = new HashSet();  
	 */
    private int pressedKeyInRange = -1;
    private int distributeState = 0;
    private final Set distributeSlotSet = new HashSet();
    
    /** 
     * the slotClick method (from net.minecraft.inventory.Container.java)
     * handles every click performed on a container, this happens normally 
     * in a gui
     * 
     * @param targetSlotID the ID of the clicked slot, the slotClick method is performed on this slot 
     * @param mouseButtonPressed the pressed mouse button when slotClick was invoked, notice that this has not to be the "real" mouse
     * @param flag a range of flag indicating different things 
     * @param player the player performing the click
     * 
     * values for mouseButtonPressed:
     * 0 = left button clicked
     * 1 = right button clicked
     * 2 = middle (third) button clicked / also left button pressed & hold (only with item@cursor)
     * 6 = right button pressed & hold
     * 
     * values for flag:
     * 0 = standard single click
     * 1 = single click + shift modifier
     * 2 = hotbar key is pressed (keys 0-9)
     * 3 = click with the middle button
     * 4 = click outside of the current gui window
     * 5 = button pressed & hold with the cursor holding an itemstack
     * 6 = double left click
     **/
    @Override
    public ItemStack slotClick(int targetSlotID, int mouseButtonPressed, int flag, EntityPlayer player)
    {
        //System.out.println("slotClick in ContainerBackpackShared invoked");
        ItemStack returnStack = null;
        //InventoryPlayer inventoryplayer = player.inventory;
        InventoryPlayer invPlayer = player.inventory;
        IInventory inventoryplayer = (IInventory) inventoryContainer;
        //kind of a multipurpose variable
        int sizeOrID;
        ItemStack movedItemStack;        

		/*
		 * PART 1: DRAGGED DISTRIBUTION
		 * This is a special case where the itemStack the mouseCursor currently holds
		 * is distributed over several fields of a container, which is only 
		 * done if the a mouse button is pressed and hold (flag == 5)
		 */
        if (flag == 5)   
        {
            int currentDistributeState = this.distributeState;
            this.distributeState = checkForPressedButton(mouseButtonPressed);
            
            /*
             * if distributeState is neither 1 nor 2 AND
			 * currentDistributeState != distributestate 
			 * then reset the distributestate and the distributeSlotSet
			 */
            if ((currentDistributeState != 1 || this.distributeState != 2) && currentDistributeState != this.distributeState)
            {
                this.resetDistributionVariables();
            }
            /*
             * else if the player current hold nothing 
             * on his mouse cursor (no stack picked up)
             */
            else if (invPlayer.getItemStack() == null)
            {
                this.resetDistributionVariables();
            }
            else if (this.distributeState == 0)
            {
                this.pressedKeyInRange = checkForPressedButton2(mouseButtonPressed);
                
                //true for 0 or 1
                if (checkValue(this.pressedKeyInRange))				
                {
                    this.distributeState = 1;
                    this.distributeSlotSet.clear();
                }
                else
                {
                    this.resetDistributionVariables();
                }
            }
            else if (this.distributeState == 1)
            {
            	//get the slot for which the click is performed
                Slot currentTargetSlot = (Slot)this.inventorySlots.get(targetSlotID);
                
                if (currentTargetSlot != null && 
                stackFitsInSlot(currentTargetSlot, invPlayer.getItemStack(), true) &&				
                currentTargetSlot.isItemValid(invPlayer.getItemStack()) &&
                invPlayer.getItemStack().stackSize > this.distributeSlotSet.size() && 
                this.alwaysTrue(currentTargetSlot))
				
				{
                	/*
                	 * add the slot to the set
                	 * (to which the itemstack will be distributed)
                	 */
                    this.distributeSlotSet.add(currentTargetSlot);
                }
            }
            else if (this.distributeState == 2)
            {
                if (!this.distributeSlotSet.isEmpty())
                {
                	/*
                	 * if the distributeSlotSet has entries
                	 * get a copy of the current itemstack (the picked up stack)
                	 * get the size of the stack
                	 * get an iterator over the distributeSlotSet 
                	 */
                    movedItemStack = invPlayer.getItemStack().copy();
                    sizeOrID = invPlayer.getItemStack().stackSize;
                    Iterator iterator = this.distributeSlotSet.iterator();					
                    while (iterator.hasNext())
                    {
                        Slot currentSlotOfSet = (Slot)iterator.next();
                        
                        if (currentSlotOfSet != null && 
                        stackFitsInSlot(currentSlotOfSet, invPlayer.getItemStack(), true) && 
                        currentSlotOfSet.isItemValid(invPlayer.getItemStack()) && 
                        invPlayer.getItemStack().stackSize >= this.distributeSlotSet.size() && 
                        this.alwaysTrue(currentSlotOfSet))
                        {
                            ItemStack targetSlotNewStack = movedItemStack.copy();
                            int currentSlotStackSize = currentSlotOfSet.getHasStack() ? currentSlotOfSet.getStack().stackSize : 0;                            
                            setSlotStack(this.distributeSlotSet, this.pressedKeyInRange, targetSlotNewStack, currentSlotStackSize);
                            
                            /*
                             * if the targetSlotNewStack is greater than allowed, 
                             * reduce its size to an allowed value
                             */
                            if (targetSlotNewStack.stackSize > targetSlotNewStack.getMaxStackSize())
                            {
                            	targetSlotNewStack.stackSize = targetSlotNewStack.getMaxStackSize();
                            }

                            if (targetSlotNewStack.stackSize > currentSlotOfSet.getSlotStackLimit())
                            {
                            	targetSlotNewStack.stackSize = currentSlotOfSet.getSlotStackLimit();
                            }
                            /* sizeOrID was the stacksize of the itemstack
                             * the player currently has picked up, its reduced
                             * to the number of items remaining picked up
                             */
                            sizeOrID -= targetSlotNewStack.stackSize - currentSlotStackSize;
                            currentSlotOfSet.putStack(targetSlotNewStack);
                        }
                    }
					//set the stacksize of the picked up stack to the rest number
                    movedItemStack.stackSize = sizeOrID;

                    if (movedItemStack.stackSize <= 0)
                    {
                        movedItemStack = null;
                    }
                    invPlayer.setItemStack(movedItemStack);
                }
                this.resetDistributionVariables();
            }
            else
            {
                this.resetDistributionVariables();
            }
        }
        else if (this.distributeState != 0)
        {
            this.resetDistributionVariables();
        }
        /*
         * PART 2: NORMAL SLOTCLICK
         * this part handles all other slotClicks which do
         * not distribute an itemstack over several slots
         */
        else
        {
            /*
             *multipurpose variable, mostly used for holding
             *the number of items to be transfered, if used 
             *otherwise it will be commented seperately 
             */
            Slot targetSlotCopy;
            
            int transferItemCount;
            ItemStack targetSlotItemStack;

            /*
             *only for a standard or shift click AND 
             *a left or right button click
             */
            if ((flag == 0 || flag == 1) && (mouseButtonPressed == 0 || mouseButtonPressed == 1))
            {
                //System.out.println("standard left or right click performed");
                //if the targetSlotID is not valid
                if (targetSlotID == -999)
                {
                    if (invPlayer.getItemStack() != null && targetSlotID == -999)
                    {
                        /*
                         * on leftclick drop the complete itemstack from the inventory
                         * on rightclick drop a single item from the itemstack
                         */
                        if (mouseButtonPressed == 0)
                        {
                            player.dropPlayerItem(invPlayer.getItemStack());
                            invPlayer.setItemStack((ItemStack)null);
                        }

                        if (mouseButtonPressed == 1)
                        {
                            player.dropPlayerItem(invPlayer.getItemStack().splitStack(1));

                            if (invPlayer.getItemStack().stackSize == 0)
                            {
                                invPlayer.setItemStack((ItemStack)null);
                            }
                        }
                    }
                }
                //if a click with shift modifier is performed (clicking while holding down shift)
                else if (flag == 1)
                {
                	//for an invalid slot return null
                    if (targetSlotID < 0)
                    {
                        return null;
                    }
                    targetSlotCopy = (Slot)this.inventorySlots.get(targetSlotID);
					
                    //if targetSlotCopy is not null and the stack inside the slot can be moved
                    if (targetSlotCopy != null && targetSlotCopy.canTakeStack(player))
                    {
                        //transfer the picked up stack to targetSlotID in the targetinventory
                        movedItemStack = this.transferStackInSlot(player, targetSlotID);
						//if the movedItemStack was not transferred completely
                        if (movedItemStack != null)
                        {
                            //here used to store an ID
                            sizeOrID = movedItemStack.itemID;
							//set the return value to the rest
                            returnStack = movedItemStack.copy();

                            if (targetSlotCopy != null && targetSlotCopy.getStack() != null && targetSlotCopy.getStack().itemID == sizeOrID)
                            {
                                //retry with the shift-click 
                                this.retrySlotClick(targetSlotID, mouseButtonPressed, true, player);
                            }
                        }
                    }
                }
                //if a click with NO shift modifier is performed
                else
                {
                    if (targetSlotID < 0)
                    {
                        return null;
                    }
                    targetSlotCopy = (Slot)this.inventorySlots.get(targetSlotID);
                    /*
                     * if the target slot is not empty
                     * save its itemstack
                     * save the itemstack to be transferred in cursorItemStack
                     */
                    if (targetSlotCopy != null)
                    {
                        /*
                         * movedItemStack is here used to store the target Slot stack
                         * instead of the currently moved itemstack
                         */
                        movedItemStack = targetSlotCopy.getStack();
                        ItemStack cursorItemStack = invPlayer.getItemStack();
                        /*
                         * if the targetSlot contains an itemstack,
                         * exchange it with the currently picked up stack
                         */
                        if (movedItemStack != null)
                        {
                            returnStack = movedItemStack.copy();
                        }

						//if the target slot is empty
                        if (movedItemStack == null)
                        {
                            /*
                             * if the stack in the players hand is not null 
                             * and can be placed into the target slot
                             */
                            if (cursorItemStack != null && targetSlotCopy.isItemValid(cursorItemStack))
                            {
                                /*
                                 * if mousebutton 1 is pressed, we place the whole stack
                                 * if mousebutton 2 is pressed, we place a single item from the stack 
                                 */
                                transferItemCount = mouseButtonPressed == 0 ? cursorItemStack.stackSize : 1;
                                
                                if (transferItemCount > targetSlotCopy.getSlotStackLimit())
                                {

                                    transferItemCount = targetSlotCopy.getSlotStackLimit();
                                }
                                targetSlotCopy.putStack(cursorItemStack.splitStack(transferItemCount));

                                //no more items picked up -> picked up itemstack is replaced by null stack 
                                if (cursorItemStack.stackSize == 0)
                                {
                                    invPlayer.setItemStack((ItemStack)null);
                                }
                            }
                        }
                        /*
                         * if the target slot is not empty AND
                         * if the stack in the target slot can be moved (always true in Container.java)
                         */
                        else if (targetSlotCopy.canTakeStack(player))
                        {
							/*
							 * if the player currently holds no itemstack in his hand
							 * we try to [PICK UP] the itemstack from the target Slot
							 */
                            if (cursorItemStack == null)
                            {
								/*
								 * leftclick -> transferItemCount = stackSize of the clicked stack
								 * rightclick -> transferItemCount = half of the clicked stack
								 * decrease the stacksize in the slotcopy by transferItemCount
								 * and set stack as the picked up slot
								 */
                                transferItemCount = mouseButtonPressed == 0 ? movedItemStack.stackSize : (movedItemStack.stackSize + 1) / 2;
								targetSlotItemStack = targetSlotCopy.decrStackSize(transferItemCount);
                                invPlayer.setItemStack(targetSlotItemStack);

                                /*
                                 * if the size of the item in the inventory
                                 * slot is now 0 put a null itemstack there
                                 */
                                if (movedItemStack.stackSize == 0)
                                {
                                    targetSlotCopy.putStack((ItemStack)null);
                                }
								//call the onSlotChanged -> onInventoryChanged for the inventory containing targetSlotCopy
                                targetSlotCopy.onPickupFromSlot(player, invPlayer.getItemStack());
                            }
                            /*
                             * check if its possible to place the itemstack in the players hand into the slot
                             * (this only fails for armor slots)
                             * this part is for [PUTTING A PICKED UP ITEMSTACK INTO A SLOT]
                             */
                            else if (targetSlotCopy.isItemValid(cursorItemStack))
                            {
								/*
								 * if both itemstack are of the same kind
								 * with matching itemID, itemDamage & equal ItemStackTags
								 */
                                if (movedItemStack.itemID == cursorItemStack.itemID && movedItemStack.getItemDamage() == cursorItemStack.getItemDamage() && ItemStack.areItemStackTagsEqual(movedItemStack, cursorItemStack))
                                {
									/*
									 * leftclick -> transferItemCount = stackSize of the clicked stack
									 * rightclick -> transferItemCount = single item from the stack
									 */
                                    transferItemCount = mouseButtonPressed == 0 ? cursorItemStack.stackSize : 1;
									
                                    /*
									 * transferItemCount is the amount of items to be placed
									 * targetSlotCopy.getSlotStackLimit() - movedItemStack.stackSize) is the rest capacity of targetSlotCopy
									 * if transferItemCount is greater, set it to the rest capacity
									 */
                                    if (transferItemCount > targetSlotCopy.getSlotStackLimit() - movedItemStack.stackSize)
                                    {
                                        transferItemCount = targetSlotCopy.getSlotStackLimit() - movedItemStack.stackSize;
                                    }

									/*
									 * cursorItemStack.getMaxStackSize() - movedItemStack.stackSize) is the rest
									 * of the number of items of this type allowed for a single slot
                                     * if transferItemCount is greater, set it to the allowed rest
									 */
                                    if (transferItemCount > cursorItemStack.getMaxStackSize() - movedItemStack.stackSize)
                                    {
                                        transferItemCount = cursorItemStack.getMaxStackSize() - movedItemStack.stackSize;
                                    }
									//split the itemstack the player currently hold according to transferItemCount
                                    cursorItemStack.splitStack(transferItemCount);

                                    if (cursorItemStack.stackSize == 0)
                                    {
                                        invPlayer.setItemStack((ItemStack)null);
                                    }

									//increase the target slots stacksize by transferItemCount
                                    movedItemStack.stackSize += transferItemCount;
                                }
                                else if (cursorItemStack.stackSize <= targetSlotCopy.getSlotStackLimit())
                                {
									//exchange the stacks
                                    targetSlotCopy.putStack(cursorItemStack);
                                    invPlayer.setItemStack(movedItemStack);
                                }
                            }
							/*
							 * this should only be executed if the (targetSlotCopy.isItemValid(cursorItemStack))
							 * returned false (which should only happen for armor slots)
							 */
                            else if (movedItemStack.itemID == cursorItemStack.itemID && cursorItemStack.getMaxStackSize() > 1 && (!movedItemStack.getHasSubtypes() || movedItemStack.getItemDamage() == cursorItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(movedItemStack, cursorItemStack))
                            {
								//transferItemCount is set to the stacksize in the targetslot
                                transferItemCount = movedItemStack.stackSize;

								/*
								 * if there is a item in the targetslot and the combined size 
								 * of the targetslot and the itemstack currently hold by the 
								 * player is smaller than the max stacksize for the held itemstack 
								 */
								
                                if (transferItemCount > 0 && transferItemCount + cursorItemStack.stackSize <= cursorItemStack.getMaxStackSize())
                                {
									/*
									 * increse the stacksize of the held item by transferItemCount and
									 * decrease the stacksize in the targetslot by transferItemCount 
									 */
                                    cursorItemStack.stackSize += transferItemCount;									
                                    movedItemStack = targetSlotCopy.decrStackSize(transferItemCount);

                                    if (movedItemStack.stackSize == 0)
                                    {
                                        targetSlotCopy.putStack((ItemStack)null);
                                    }
                                    targetSlotCopy.onPickupFromSlot(player, invPlayer.getItemStack());
                                }
                            }
                        }

						//update the target slot
                        targetSlotCopy.onSlotChanged();
                    }
                }
            }
            /*
             * if a hotbar key is pressed (flag == 2)
             */
            else if (flag == 2 && mouseButtonPressed >= 0 && mouseButtonPressed < 9)
            {
                targetSlotCopy = (Slot)this.inventorySlots.get(targetSlotID);

				/*
				 * since x.canTakeStack always returns true this is always executed
				 * (at least in Container.java)
                 * this checks if the slots stack can be taken or is not movable
				 */
                if (targetSlotCopy.canTakeStack(player))
                {
                    movedItemStack = inventoryplayer.getStackInSlot(mouseButtonPressed);
					/*
					 * flag is true if the hotbar slot is not empty or
					 * the inventory of targetSlotCopy is the same as the players inventory 
                     * and itemstack from the hotbar can be put in targetSlotCopy 
					 */
                    boolean flag2 = movedItemStack == null || targetSlotCopy.inventory == inventoryplayer && targetSlotCopy.isItemValid(movedItemStack);
                    transferItemCount = -1;

                    if (!flag2)
                    {
						/*
						 * transferItemCount is used to save the first empty slot of
						 * the player inventory and set flag2 to true if one is found
						 */
                        //transferItemCount = inventoryplayer.getFirstEmptyStack();
                        transferItemCount = emulateGetFirstEmptyStack(inventoryplayer);
                        flag2 |= transferItemCount > -1;
                    }

					/*
					 * flag is now true either if there is nothing to place OR
					 * the target slot is part of the players inventory and the placement would be valid (no armor slot) OR
                     * the players inventory got a free slot
					 */
					
                    if (targetSlotCopy.getHasStack() && flag2)
                    {
						/*
						 * save the contents of targetSlotCopy (target slot) to targetSlotItemStack and
						 * set targetSlotItemStack into players inventoryslots[hotbarnumber-pressed] 
						 */
                        targetSlotItemStack = targetSlotCopy.getStack();
                        inventoryplayer.setInventorySlotContents(mouseButtonPressed, targetSlotItemStack);

						/*
						 * if inventory type of targetSlotCopy is not the same as inventoryplayer OR targetSlotCopy is an armor slot AND
						 * movedItemStack (the itemstack from the hotbar slot) is not null 
						 */
                        if ((targetSlotCopy.inventory != inventoryplayer || !targetSlotCopy.isItemValid(movedItemStack)) && movedItemStack != null)
                        {
							/*
							 * if there is no free slot in the inventory
							 * try to add the itemstack to the players inventory
							 * decrease the stack in targetSlotCopy by the size of targetSlotItemStack
							 * put a null stack in targetSlotCopy
							 * update targetSlotCopy
							 */
                            if (transferItemCount > -1)
                            {
                                //inventoryplayer.addItemStackToInventory(movedItemStack);
                                emulateAddItemStackToInventory(movedItemStack, inventoryplayer, player);
                                targetSlotCopy.decrStackSize(targetSlotItemStack.stackSize);
                                targetSlotCopy.putStack((ItemStack)null);
                                targetSlotCopy.onPickupFromSlot(player, targetSlotItemStack);
                            }
                        }
                        else
                        {
							/*
							 * reduce the stack number in targetSlotCopy to 0
							 * put the stack from the hotbar into slot 2
							 * update targetSlotCopy 
							 */
                            targetSlotCopy.decrStackSize(targetSlotItemStack.stackSize);							
                            targetSlotCopy.putStack(movedItemStack);							
                            targetSlotCopy.onPickupFromSlot(player, targetSlotItemStack);
                        }
                    }
					/*
					 * if targetSlotCopy (copy of target slots) is empty AND
					 * the input is not null AND 
					 * targetSlotCopy is valid for the stack (targetSlotCopy is no armor slot)
					 */
                    else if (!targetSlotCopy.getHasStack() && movedItemStack != null && targetSlotCopy.isItemValid(movedItemStack))
                    {
						/*
						 * set the hotbar slot to null and put the movedItemStack 
						 * (stack from the hotbar) into the target slot (targetSlotCopy)
						 */
                        inventoryplayer.setInventorySlotContents(mouseButtonPressed, (ItemStack)null);
                        targetSlotCopy.putStack(movedItemStack);
                    }
                }
            }
			/*
			 * if the pressed mouse button is the middle button and
			 * the player is in creative mode and
			 * has currently no stack in his hand and
			 * the target slot is greater/equal to zero
			 */
            else if (flag == 3 && player.capabilities.isCreativeMode && invPlayer.getItemStack() == null && targetSlotID >= 0)
            {
                targetSlotCopy = (Slot)this.inventorySlots.get(targetSlotID);
				
				/*
				 * if there was a stack in the targetslot
				 * get a copy of the itemstack in targetSlotCopy and save it into movedItemStack
				 * set the stacksize to max stacksize
				 * give movedItemStack to the player (into his hands)
				 * (basicly the cloning of an item) 
				 */
                if (targetSlotCopy != null && targetSlotCopy.getHasStack())
                {
                    movedItemStack = targetSlotCopy.getStack().copy();
                    movedItemStack.stackSize = movedItemStack.getMaxStackSize();
                    invPlayer.setItemStack(movedItemStack);
                }
            }
			/*
			 * if the player clicks outside of the gui and
			 * he has an itemstack in his hands and
             * and the targetslot is greater/equal to zero
			 */
            else if (flag == 4 && invPlayer.getItemStack() == null && targetSlotID >= 0)
            {
                targetSlotCopy = (Slot)this.inventorySlots.get(targetSlotID);
				
				/*
				 * if there is a stack in the targetslot
				 * moveItemStack size is 1 if leftclick or the stacksize of targetSlotCopy if rightclicked
				 * update targetSlotCopy
				 * drop movedItemStack at players position
				 */
                if (targetSlotCopy != null && targetSlotCopy.getHasStack())
                {
                    movedItemStack = targetSlotCopy.decrStackSize(mouseButtonPressed == 0 ? 1 : targetSlotCopy.getStack().stackSize);
                    targetSlotCopy.onPickupFromSlot(player, movedItemStack);
                    player.dropPlayerItem(movedItemStack);
                }
            }
			/*
			 * if the player performs a double leftclick and
			 * the targetslot is greater/equal to zero
			 */
            else if (flag == 6 && targetSlotID >= 0)
            {
				/*
				 * save the target slot to targetSlotCopy
				 * save the itemstack in the players hands into movedItemStack
				 */
                targetSlotCopy = (Slot)this.inventorySlots.get(targetSlotID);
                movedItemStack = invPlayer.getItemStack();

				/*
				 * if the player currently holds an item AND
				 * the target slot is null OR slot has no stack OR the itemstack in targetSlotCopy cannot be picked up
				 */
                if (movedItemStack != null && (targetSlotCopy == null || !targetSlotCopy.getHasStack() || !targetSlotCopy.canTakeStack(player)))
                {
					/*
					 * sizeOrID is 0 if left button was clicked, size of the inventory - 1 else
					 * transferItemCount is 0 if left button was clicked, -1 else
                     * used here to define the iteration direction for the inventorySlots
					 */
                    sizeOrID = mouseButtonPressed == 0 ? 0 : this.inventorySlots.size() - 1;
                    transferItemCount = mouseButtonPressed == 0 ? 1 : -1;
                    
					//repeat 2 times
                    for (int l1 = 0; l1 < 2; ++l1)
                    {
						/*
						 * repeat as long as
						 * currentSlotID >= 0 AND
                         * currentSlotID is smaller than the inventory size AND
                         * size of movedItemStack is smaller than its max size
                         * then increase/decrease currentSlotID by transferItemCount 
                         * which is 1 or -1, depending on the mouse button that was clicked)
						 */

                        for (int currentSlotID = sizeOrID; currentSlotID >= 0 && 
                        currentSlotID < this.inventorySlots.size() && 
                        movedItemStack.stackSize < movedItemStack.getMaxStackSize(); currentSlotID += transferItemCount)
                        {
                            Slot currentSlotCopy = (Slot)this.inventorySlots.get(currentSlotID);
							
                            /*if currentSlotCopy has content AND
                             *the combined stacksize of currentSlotCopy + movedItemStack is smaller than the maxstacksize for movedItemStack AND 
                             *the itemstack in currentSlotCopy can be modified AND
                             *this is always true (and so doesnt matter here)
                             *l1 is not 0 OR the stacksize in currentSlotCopy is not the maximum stacksize for this slot
                             */
							
                            if (currentSlotCopy.getHasStack() && 
                            stackFitsInSlot(currentSlotCopy, movedItemStack, true) && 
                            currentSlotCopy.canTakeStack(player) && 
                            this.func_94530_a(movedItemStack, currentSlotCopy) && 
                            (l1 != 0 || currentSlotCopy.getStack().stackSize != currentSlotCopy.getStack().getMaxStackSize()))
                            {
								/*
								 * transferItemCount2 is minimum of (the maxstacksize for handheld item - its current size) and 
								 * the size of the stack in currentSlotCopy
								 * since transferItemCount is currently used otherwise, transferItemCount2 is used
								 * decrease currentSlotCopy stacksize by j2 and save the stack to decreasedStack
								 * increase the handheld stacksize by transferItemCount2
								 * (effectively transfer items from the inventory to the players hand until its full)
								 */
                                int transferItemCount2 = Math.min(movedItemStack.getMaxStackSize() - movedItemStack.stackSize, currentSlotCopy.getStack().stackSize);
                                ItemStack decreasedStack = currentSlotCopy.decrStackSize(transferItemCount2);
                                movedItemStack.stackSize += transferItemCount2;
                                
                                if (decreasedStack.stackSize <= 0)
                                {
                                    currentSlotCopy.putStack((ItemStack)null);
                                }
                                currentSlotCopy.onPickupFromSlot(player, decreasedStack);
                            }
                        }
                    }
                }
				//update the whole container and send the changes
                this.detectAndSendChanges();
            }
        }
        updateNotification = true;
		//return any remains of the operation
        return returnStack;
    }
	
    /*
     * need to overwrite the container.java method to call
     * the modified slotClick instead of the container method
     */
    @Override
    protected void retrySlotClick(int targetSlotID, int mouseButtonPressed, boolean flag, EntityPlayer entity)
    {
    	//a retry of slotClick with flag 1 (shift click)
        this.slotClick(targetSlotID, mouseButtonPressed, 1, entity);
    }
    
    /**
     * [Helper Methods]
     * these methods can be found in net.minecraft.inventory.Container.java
     * they are needed by slotClick and are provided here in a renamed version
     */
    
    /**
     * This is a renamed version of the method
     * func_94532_c in net.minecraft.inventory.Container.java
     * this is not needed but i dont like non-sense method names
     * 
     * @param mouseButtonPressed can be {0,1,2,6} from what i observed
     * @return 2 if mouseButtonPressed is 6, 0 else
     * 
     * if there are other values possible for mouseButtonPressed
     * the method returns the following:
     * 0  -> 0
     * 1  -> 1
     * 2  -> 2
     * 3  -> 3
     * for values over 3 the assignments restarts from the top 
     * (so 4 is 0, 5 is 1...)
     */
    public static int checkForPressedButton(int mouseButtonPressed)
    {
        return mouseButtonPressed & 3;
    }
    
    /**
     * This is a renamed version of the method 
     * func_94533_d in net.minecaft.inventory.Container.java
     * this is not needed but i dont like non-sense method names 
     */
    protected void resetDistributionVariables()
    {
        this.distributeState = 0;
        this.distributeSlotSet.clear();
    }
    
   
    /**
     * This is a renamed version of the method
     * func_94529_b in net.minecraft.inventory.Container.java
     * this is not needed but i dont like non-sense method names
     * 
     * @param mouseButtonPressed can be {0,1,2,6} from what i observed
     * @return 1 if mouseButtonPressed is 6, 0 else
     * 
     * if there are other values possible for mouseButtonPressed
     * the method returns the following:
     * 0-3  -> 0
     * 4-7  -> 1
     * 8-11 -> 2
     * 12+  -> 3
     */
    public static int checkForPressedButton2(int mouseButtonPressed)
    {
        return mouseButtonPressed >> 2 & 3;
    }
    
    /**
     * This is a renamed version of the method 
     * func_94528_d in net.minecraft.inventory.Container.java
     * this is not needed but i dont like non-sense method names
     * 
     * @param value
     * @return
     */
    public static boolean checkValue(int value)
    {
        return value == 0 || value == 1;
    }
    
    /**
     * This is a renamed version of the method
     * func_94527_a in net.minecraft.inventory.Container.java
     * this is not needed but i dont like non-sense method names
     * 
     * The method return a bool if a given itemstack fits into
     * a given slot, the bool input argument rules if the size of
     * the stack matters or not
     * 
     * @param slot is the target slot
     * @param itemStack is the itemstack which should fit into slot
     * @param sizeMatters rules if the size of itemstack matters
     * @return true if the stack fits
     */
    public static boolean stackFitsInSlot(Slot slot, ItemStack itemStack, boolean sizeMatters)
    {
        boolean flag1 = slot == null || !slot.getHasStack();

        if (slot != null && slot.getHasStack() && itemStack != null && itemStack.isItemEqual(slot.getStack()) && ItemStack.areItemStackTagsEqual(slot.getStack(), itemStack))
        {
            int i = sizeMatters ? 0 : itemStack.stackSize;
            flag1 |= slot.getStack().stackSize + i <= itemStack.getMaxStackSize();
        }

        return flag1;
    }
    
    /**
     * This is a renamed version of the method
     * func_94531_b in net.minecraft.inventory.Container.java
     * this is not needed but i dont like non-sense method names
     * 
     * not sure about this method, since its return always true
     * maybe this is modified for other containers
     * 
     * @param slot
     */
    public boolean alwaysTrue(Slot slot)
    {
        return true;
    }
    
    /**
     * This is a renamed version of the method 
     * func_94525_a in net.minecraft.inventory.Container.java
     * this is not needed but i dont like non-sense method names
     * 
     * @param slotSet is the set of slots for the current distribution
     * @param stackSizeSelector is the number which is added to the current processed stack 
     * @param stackToResize is stack that will be placed in the processed slot
     * @param currentSlotStackSize is the size of the itemstack in the current slot
     */
    public static void setSlotStack(Set slotSet, int stackSizeSelector, ItemStack stackToResize, int currentSlotStackSize)
    {
        switch (stackSizeSelector)
        {
            case 0:
            	stackToResize.stackSize = MathHelper.floor_float((float)stackToResize.stackSize / (float)slotSet.size());
                break;
            case 1:
            	stackToResize.stackSize = 1;
        }

        stackToResize.stackSize += currentSlotStackSize;
    }
    
    /**
     * This a copy of mergeItemStack in
     * net.minecraft.inventory.Container.java
     * this is not needed but i think this method needs some commentation
     * and a rename of its variables to easily understandable names
     */
    @Override
    protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4)
    {
        boolean flag1 = false;
        //true if it was possible to place the input into the inventory
        int k = par2;

        if (par4)
        {
            k = par3 - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (par1ItemStack.isStackable())
        {
        //Try only if there ItemStack is stackable
            while (par1ItemStack.stackSize > 0 && (!par4 && k < par3 || par4 && k >= par2))
            {
            //Only if the current Stack is existent && (!par4 && par2 < par3 || par4 && par3-1 >= par2) 
                slot = (Slot)this.inventorySlots.get(k);
                //Start at slot k
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.itemID == par1ItemStack.itemID && (!par1ItemStack.getHasSubtypes() || 
                    //Only if there is an itemstack in this slots & its of the same type as the stack to be merged & has no subtypes
                    par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1))
                    //Or if they have the same damage value and matching StackTags
                {
                    int l = itemstack1.stackSize + par1ItemStack.stackSize;
                    //l is the combined stackSize of input + currentInSlot

                    if (l <= par1ItemStack.getMaxStackSize())
                    {
                        //if l is smaller than the allowed maximum for the slot
                        //set the input size to 0, set the slot to l
                        //call an update for the slot and set flag1 (i'm done?) to true
                        par1ItemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize())
                    {
                        //if l is bigger than the allowed maximum for the slot
                        //input is to input-(maxStackSize-slotStackSize), slotStackSize is set to maxStackSize
                        //call an update for the slot and set flag1 (i'm done?) to true
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = par1ItemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (par4)
                {
                //if par4 is true count down
                    --k;
                }
                else
                {
                //if par4 is false count up
                    ++k;
                }
            }
        }

        if (par1ItemStack.stackSize > 0)
        {
        //if the input was not completely merged before reset k
            if (par4)
            {
                k = par3 - 1;
            }
            else
            {
                k = par2;
            }

            while (!par4 && k < par3 || par4 && k >= par2)
            {
                //search for an empty slot
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null)
                {
                //put the input into an empty slot
                    slot.putStack(par1ItemStack.copy());
                    slot.onSlotChanged();
                    par1ItemStack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (par4)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        return flag1;
    }
    
    
    /**
     * New Helper Methods
     * since the Inventoryplayer is replaced by the more generic IInventory (for Containers)
     * some methods of inventoryplayer have to be emulated here
     */
    
    public int emulateGetFirstEmptyStack(IInventory inventory)
    {
        for (int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            if (inventory.getStackInSlot(i) == null)
            {
                return i;
            }
        }

        return -1;
    }
    
    public boolean emulateAddItemStackToInventory(ItemStack par1ItemStack, IInventory targetInventory, EntityPlayer player)
    {
        if (par1ItemStack == null)
        {
            return false;
        }
        else
        {
            try
            {
                int i;

                if (par1ItemStack.isItemDamaged())
                {
                    //i = this.getFirstEmptyStack();
                    i = emulateGetFirstEmptyStack(targetInventory);

                    if (i >= 0)
                    {
                        //this.mainInventory[i] = ItemStack.copyItemStack(par1ItemStack);
                        //this.mainInventory[i].animationsToGo = 5;
                        targetInventory.setInventorySlotContents(i, par1ItemStack);
                        
                        par1ItemStack.stackSize = 0;
                        return true;
                    }
                    //else if (this.player.capabilities.isCreativeMode)
                    else if (player.capabilities.isCreativeMode)
                    {
                        par1ItemStack.stackSize = 0;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    do
                    {
                        i = par1ItemStack.stackSize;
                        //par1ItemStack.stackSize = this.storePartialItemStack(par1ItemStack);
                        par1ItemStack.stackSize = emulateStorePartialItemStack(par1ItemStack, targetInventory);
                    }
                    while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < i);

                    //if (par1ItemStack.stackSize == i && this.player.capabilities.isCreativeMode)
                    if (par1ItemStack.stackSize == i && player.capabilities.isCreativeMode)
                    {
                        par1ItemStack.stackSize = 0;
                        return true;
                    }
                    else
                    {
                        return par1ItemStack.stackSize < i;
                    }
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
                crashreportcategory.addCrashSection("Item ID", Integer.valueOf(par1ItemStack.itemID));
                crashreportcategory.addCrashSection("Item data", Integer.valueOf(par1ItemStack.getItemDamage()));
                //crashreportcategory.addCrashSectionCallable("Item name", new CallableItemName(this, par1ItemStack));
                throw new ReportedException(crashreport);
            }
        }
    }
    
    private int emulateStorePartialItemStack(ItemStack par1ItemStack, IInventory targetInventory)
    {
        int i = par1ItemStack.itemID;
        int j = par1ItemStack.stackSize;
        int k;

        if (par1ItemStack.getMaxStackSize() == 1)
        {
            //k = this.getFirstEmptyStack();
            k = emulateGetFirstEmptyStack(targetInventory);

            if (k < 0)
            {
                return j;
            }
            else
            {
                //if (this.mainInventory[k] == null)
                if (targetInventory.getStackInSlot(i) == null)
                {
                    //this.mainInventory[k] = ItemStack.copyItemStack(par1ItemStack);
                    targetInventory.setInventorySlotContents(i, par1ItemStack);
                }

                return 0;
            }
        }
        else
        {
            //k = this.storeItemStack(par1ItemStack);
            k = emulateStoreItemStack(par1ItemStack, targetInventory);

            if (k < 0)
            {
                //k = this.getFirstEmptyStack();
                k = emulateGetFirstEmptyStack(targetInventory);
            }

            if (k < 0)
            {
                return j;
            }
            else
            {
                //if (this.mainInventory[k] == null)
                if (targetInventory.getStackInSlot(i) == null)
                {
                    //this.mainInventory[k] = new ItemStack(i, 0, par1ItemStack.getItemDamage());
                    targetInventory.setInventorySlotContents(k, new ItemStack(i, 0, par1ItemStack.getItemDamage()));

                    if (par1ItemStack.hasTagCompound())
                    {
                        //this.mainInventory[k].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
                        ItemStack tempStack = targetInventory.getStackInSlot(k);
                        tempStack.setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
                        targetInventory.setInventorySlotContents(k, tempStack);
                    }
                }

                int l = j;

                //if (j > this.mainInventory[k].getMaxStackSize() - this.mainInventory[k].stackSize)
                if (j > targetInventory.getStackInSlot(k).getMaxStackSize() - targetInventory.getStackInSlot(k).stackSize)
                {
                    //l = this.mainInventory[k].getMaxStackSize() - this.mainInventory[k].stackSize;
                    l = targetInventory.getStackInSlot(k).getMaxStackSize() - targetInventory.getStackInSlot(k).stackSize;
                }

                //if (l > this.getInventoryStackLimit() - this.mainInventory[k].stackSize)
                if (l > targetInventory.getInventoryStackLimit() - targetInventory.getStackInSlot(k).stackSize)
                {
                    //l = this.getInventoryStackLimit() - this.mainInventory[k].stackSize;
                    l = targetInventory.getInventoryStackLimit() - targetInventory.getStackInSlot(k).stackSize;
                }

                if (l == 0)
                {
                    return j;
                }
                else
                {
                    j -= l;
                    //this.mainInventory[k].stackSize += l;
                    ItemStack tempStack = targetInventory.getStackInSlot(k);
                    tempStack.stackSize += 1;
                    targetInventory.setInventorySlotContents(k, tempStack); 
                    //this.mainInventory[k].animationsToGo = 5;
                    return j;
                }
            }
        }
    }
    
    private int emulateStoreItemStack(ItemStack par1ItemStack, IInventory targetInventory)
    {
        //for (int i = 0; i < this.mainInventory.length; ++i)
        for (int i = 0; i < targetInventory.getSizeInventory(); i++)
        {
            ItemStack tempStack = targetInventory.getStackInSlot(i);
            //if (this.mainInventory[i] != null && 
            //this.mainInventory[i].itemID == par1ItemStack.itemID && 
            //this.mainInventory[i].isStackable() && 
            //this.mainInventory[i].stackSize < this.mainInventory[i].getMaxStackSize() && 
            //this.mainInventory[i].stackSize < this.getInventoryStackLimit() && 
            //(!this.mainInventory[i].getHasSubtypes() || this.mainInventory[i].getItemDamage() == par1ItemStack.getItemDamage()) && 
            //ItemStack.areItemStackTagsEqual(this.mainInventory[i], par1ItemStack))
            if (tempStack != null &&
            tempStack.itemID == par1ItemStack.itemID &&
            tempStack.isStackable() &&
            tempStack.stackSize < tempStack.getMaxStackSize() &&
            tempStack.stackSize < targetInventory.getInventoryStackLimit() &&
            (!tempStack.getHasSubtypes() || tempStack.getItemDamage() == par1ItemStack.getItemDamage()) &&
            ItemStack.areItemStackTagsEqual(tempStack, par1ItemStack))
            {
                return i;
            }
        }

        return -1;
    }
}