package mrj.advancedbackpackmod;

//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrj.advancedbackpackmod.config.ConfigurationStore;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
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
				/**This is currently in development
				 * 
				 * will create a shared inventory for 
				 * backpack and a clicked container
				 * 
				**/
				System.out.println("player is sneaking, use shared inventory mode for backpackbase");
				MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(myWorld, myPlayer, true);
				if (movingobjectposition != null)
				{
					if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
		            {
		                int i = movingobjectposition.blockX;
		                int j = movingobjectposition.blockY;
		                int k = movingobjectposition.blockZ;
		                
		               if (myWorld.getBlockTileEntity(i, j, k) != null)
		               {
		            	   if (BlockContainer.class.isAssignableFrom(((Object)myWorld.getBlockTileEntity(i,  j, k).getBlockType()).getClass()))
		            	   {
		            		   System.out.println("this is a subclass of BlockContainer");
		            		   try 
		            		   {
		            			   IInventory testInv = (IInventory) myWorld.getBlockTileEntity(i, j, k);
			            		   System.out.println("inventory size = " + testInv.getSizeInventory());
		            		   }
		            		   catch(ClassCastException e)
		            		   {
		            			   System.out.println("has no IInventory");
		            		   }
		            		   
		            	   }
		               }             
		            }
				}
			}
		}		
		return myStack;
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		checkForSizeUpdate(itemStack, entity);
		
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

	public void checkForSizeUpdate(ItemStack itemStack, Entity entity)
	{
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
		if (nbtTagCompound == null)
		{
			nbtTagCompound = new NBTTagCompound();
			itemStack.setTagCompound(nbtTagCompound);
		}
		else if (nbtTagCompound.getInteger("increaseSize") > 0)
		{
			InventoryBackpackBase tempInv = new InventoryBackpackBase(itemStack, (EntityPlayer) entity, 0);
			if (tempInv.getSizeInventory() + ConfigurationStore.BACKPACK_BASE_UPGRADE_INCREMENT * 
					nbtTagCompound.getInteger("increaseSize") <= ConfigurationStore.BACKPACK_BASE_MAX_SIZE)
			{
				tempInv.increaseSize(ConfigurationStore.BACKPACK_BASE_UPGRADE_INCREMENT * nbtTagCompound.getInteger("increaseSize"));
			}
			else
			{
				tempInv.increaseSize(ConfigurationStore.BACKPACK_BASE_MAX_SIZE - tempInv.getSizeInventory());
			}
			tempInv.writeToNBT(nbtTagCompound);
			nbtTagCompound.setInteger("increaseSize", 0);
		}
	}
	
	protected boolean containerMatchesItem(Container openContainer) {
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
