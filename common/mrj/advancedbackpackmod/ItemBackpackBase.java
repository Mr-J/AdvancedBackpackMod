package mrj.advancedbackpackmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBackpackBase extends Item {
	
	boolean currentlyUsed;
	
	public ItemBackpackBase(int id)
	{
		super(id);
		
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
		setIconIndex(0);
		setItemName("Base test backpack");
		currentlyUsed = false;
	}
	
	public String getTextureFile()
	{
		return CommonProxy.ITEMS_PNG;
	}
	
	public ItemStack onItemRightClick(ItemStack myStack, World myWorld, EntityPlayer myPlayer) 
	{
		if (!myWorld.isRemote)
		{
			myPlayer.openGui(AdvancedBackpackMod.instance, 0, myPlayer.worldObj, (int)myPlayer.posX, (int)myPlayer.posY, (int)myPlayer.posZ);
		}		
		return myStack;
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		if (world.isRemote || !isCurrentItem)
		{
			return;
		}
		if(((EntityPlayer) entity).openContainer == null || ((EntityPlayer) entity).openContainer instanceof ContainerPlayer)
		{
			return;
		}
		if (containerMatchesItem(((EntityPlayer) entity).openContainer))
		{
			ContainerBackpackBase myContainer = (ContainerBackpackBase) ((EntityPlayer) entity).openContainer;
			myContainer.saveToNBT(itemStack);
		}
	}

	private boolean containerMatchesItem(Container openContainer) {
			return openContainer instanceof ContainerBackpackBase;
	}

	
}
