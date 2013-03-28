package mrj.advancedbackpackmod;

//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemBackpackBase extends Item {
	
	//boolean currentlyUsed;
	//public int currentColor;
	public static final String[] colorNames = new String[] {"black", "red", "green", 
																"brown", "blue", "purple", 
																"cyan", "silver", "gray", 
																"pink", "lime", "yellow", 
																"lightBlue", "magenta", "orange", "white"};
	
	private Icon[] icons;
	
	public ItemBackpackBase(int id)
	{
		super(id);
		
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
		//currentColor = -1;
		//currentlyUsed = false;
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

	
	//register icons
	@Override
	@SideOnly(Side.CLIENT)
    //public void func_94581_a(IconRegister iconRegister)
	public void updateIcons(IconRegister iconRegister)
    {
		icons = new Icon[17];
		
		//icons[0] = iconRegister.func_94245_a("advancedbackpackmod:backpack32");
		icons[0] = iconRegister.registerIcon("advancedbackpackmod:backpack32");
		
		for (int i = 1; i < 17; i++)
		{
			//icons[i] = iconRegister.func_94245_a("advancedbackpackmod:backpack32" + colorNames[i-1]);
			icons[i] = iconRegister.registerIcon("advancedbackpackmod:backpack32" + colorNames[i-1]);
		}
    }
	
	@Override
	public Icon getIcon(ItemStack itemStack, int renderPass)
	{
		//System.out.println("currentColor = " + currentColor);
		//System.out.println("icon index = " + (((ItemBackpackBase)itemStack.getItem()).currentColor)+1);
		//return icons[((ItemBackpackBase)itemStack.getItem()).currentColor+1];
		//System.out.println(icons[0]);
		return icons[getColor(itemStack) + 1];
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {

        return true;
    }
	
	/**@Override
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1)
    {
        getIcon(super., par1);
    }**/
	
	public int getColor(ItemStack itemStack)
	{
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
		
		if (nbtTagCompound == null)
		{
			return -1;
		}
		if (nbtTagCompound.hasKey("color"))
		{
			return nbtTagCompound.getInteger("color");
		}
		else
		{
			return -1;			
		}
	}
	
	public void setColor(ItemStack itemStack, int color)
	{
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
		if (nbtTagCompound == null)
		{
			nbtTagCompound = new NBTTagCompound();
			itemStack.setTagCompound(nbtTagCompound);
		}
		//if (nbtTagCompound.hasKey("color"))
		//{
			nbtTagCompound.setInteger("color", color);
		//}
	}

}
