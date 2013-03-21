package mrj.advancedbackpackmod;

//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
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
		currentlyUsed = false;
	}
	
	//OBSOLETE
	/**public String getTextureFile()
	{
		return CommonProxy.ITEMS_PNG;
	}**/
	
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

	@Override
    public void func_94581_a(IconRegister iconRegister)
    {
        this.iconIndex = iconRegister.func_94245_a("advancedbackpackmod:backpack32");
    }
}
