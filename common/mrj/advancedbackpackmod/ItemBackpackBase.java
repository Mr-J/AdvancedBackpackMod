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

/**
 * Advanced Backpack Mod
 * 
 * ItemBackpackBase
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ItemBackpackBase extends Item {
	
	//boolean currentlyUsed;
	//public int currentColor;
	public static final String[] colorNames = new String[] {"black", "red", "green", 
																"brown", "blue", "purple", 
																"cyan", "silver", "gray", 
																"pink", "lime", "yellow", 
																"lightBlue", "magenta", "orange", "white"};
	
	public static final String[] colorNumbers = new String[] {"191919", "CC4C4C", "667F33", "7F664C", "3366CC", "B266E5", 
													"4C99B2", "999999", "4C4C4C", "F2B2CC", "7FCC19", "E5E533", 
													"99B2F2", "E57FD8", "F2B233", "FFFFFF"};
	
	protected Icon[] icons;
	
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
			if(!myPlayer.isSneaking())
			{
				myPlayer.openGui(AdvancedBackpackMod.instance, 0, myPlayer.worldObj, (int)myPlayer.posX, (int)myPlayer.posY, (int)myPlayer.posZ);
			}
			else
			{
				System.out.println("player is sneaking, use shared inventory mode for backpackbase");
			}
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
		/**icons = new Icon[17];
		
		//icons[0] = iconRegister.func_94245_a("advancedbackpackmod:backpack32");
		icons[0] = iconRegister.registerIcon("advancedbackpackmod:backpack32");
		
		for (int i = 1; i < 17; i++)
		{
			//icons[i] = iconRegister.func_94245_a("advancedbackpackmod:backpack32" + colorNames[i-1]);
			icons[i] = iconRegister.registerIcon("advancedbackpackmod:backpack32" + colorNames[i-1]);
		}**/
		icons = new Icon[2];
		icons[0] = iconRegister.registerIcon("advancedbackpackmod:backpack32colorless");
		icons[1] = iconRegister.registerIcon("advancedbackpackmod:backpack32outline");
    }
	
	@Override
	public Icon getIcon(ItemStack itemStack, int renderPass)
	{
		//System.out.println("currentColor = " + currentColor);
		//System.out.println("icon index = " + (((ItemBackpackBase)itemStack.getItem()).currentColor)+1);
		//return icons[((ItemBackpackBase)itemStack.getItem()).currentColor+1];
		//System.out.println(icons[0]);
		
		//return icons[getColor(itemStack) + 1];
		if (renderPass != 1)
		{
			return icons[0];
		}
		else
		{
			return icons[1];
		}
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
		nbtTagCompound.setInteger("color", color);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {

        if (renderPass == 1)
            return Integer.parseInt(colorNumbers[15], 16);
        else {
            int bagColor = this.getColor(itemStack);

            if (bagColor < 0) {
                return Integer.parseInt("4D250B", 16);
            }
            else
            {
            	return Integer.parseInt(colorNumbers[bagColor], 16);
            }
        }
    }
}
