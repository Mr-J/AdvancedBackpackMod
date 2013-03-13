package mrj.advancedbackpackmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBackpackBase extends Item {
	
	public ItemBackpackBase(int id)
	{
		super(id);
		
		//Constructor Config
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
		setIconIndex(0);
		setItemName("Base test backpack");
	}
	
	public String getTextureFile()
	{
		return CommonProxy.ITEMS_PNG;
	}
	
	public ItemStack onItemRightClick(ItemStack myStack, World myWorld, EntityPlayer myPlayer) 
	{
		if (!myWorld.isRemote)
		{
			System.out.println("Item was rightclicked at position" + myPlayer.posX + "/" + myPlayer.posY + "/" + myPlayer.posZ);
			myPlayer.openGui(AdvancedBackpackMod.instance, 0, myPlayer.worldObj, (int)myPlayer.posX, (int)myPlayer.posY, (int)myPlayer.posZ);
		}
		/**else
		{
			return myStack;		
		}**/
		
		return myStack;
	}
}
