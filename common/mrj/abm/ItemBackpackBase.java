package mrj.abm;

//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrj.abm.config.ConfigurationStore;
import net.minecraft.block.Block;
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
	
	private static final String[] acceptedContainers = new String[] {"tile.chest", "tile.hopper",
	    "tile.alchemicalChest", "tile.IronChest", 
	    "tile.reinforcedChest", "tile.locker"};
	
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
		            			   System.out.println(Block.blocksList[myWorld.getBlockId(i, j, k)].getUnlocalizedName());
		            			   System.out.println(Block.blocksList[myWorld.getBlockId(i, j, k)].getLocalizedName());
			            		   System.out.println("inventory size = " + testInv.getSizeInventory());
			            		   if (checkContainer(Block.blocksList[myWorld.getBlockId(i, j, k)].getUnlocalizedName()))
			            		   {
			            		       myPlayer.openGui(AdvancedBackpackMod.instance, 2, myPlayer.worldObj, i, j, k);
			            		   }
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
		//System.out.println("current opened container =");
		//System.out.println(((EntityPlayer) entity).openContainer);
		int containerType = containerMatchesItem(((EntityPlayer) entity).openContainer);
		//System.out.println("containerType = " + containerType);
		if (containerType == 0) 
		{
		    //System.out.println("containerType = 0");
	        ContainerBackpackBase myContainer = (ContainerBackpackBase) ((EntityPlayer) entity).openContainer;
            if (myContainer.updateNotification)
            {
                System.out.println("saving inventory");
                myContainer.saveToNBT(itemStack);
                myContainer.updateNotification = false;
            }
		}
		else if (containerType == 1)
		{
		    //System.out.println("containerType = 1");
	        ContainerBackpackShared myContainer = (ContainerBackpackShared) ((EntityPlayer) entity).openContainer;
            if (myContainer.updateNotification)
            {
                System.out.println("saving inventory");
                myContainer.saveToNBT(itemStack);
                myContainer.updateNotification = false;
            }
		}
		/**if (containerMatchesItem(((EntityPlayer) entity).openContainer) >= 0)
		{
			ContainerBackpackBase myContainer = (ContainerBackpackBase) ((EntityPlayer) entity).openContainer;
			if (myContainer.updateNotification)
			{
				System.out.println("saving inventory");
				myContainer.saveToNBT(itemStack);
				myContainer.updateNotification = false;
			}
		}**/
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
	
	protected int containerMatchesItem(Container openContainer) {
		//return openContainer instanceof ContainerBackpackBase;
	    //System.out.println(openContainer.getClass());
	    
	    //System.out.println(ContainerBackpackBase.class.isAssignableFrom(openContainer.getClass()));
	    //System.out.println(ContainerBackpackShared.class.isAssignableFrom(openContainer.getClass()));
	    if (ContainerBackpackBase.class.isAssignableFrom(openContainer.getClass()))
	    {
	        //System.out.println("open container matches containerbackpackbase");
	        return 0;
	    }
	    else if (ContainerBackpackShared.class.isAssignableFrom(openContainer.getClass()))
	    {
	        //System.out.println("open container matches containerbackpackshared");
	        return 1;
	    }
	    else
	    {
	        return -1;
	    }
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
    
    public boolean checkContainer(String name)
    {
        String blockName;
        if (name.contains("@"))
        {
            blockName = name.substring(0, name.indexOf("@") - 1);
        }
        else
        {
            blockName = name;
        }
        
        for (int i = 0; i < acceptedContainers.length; i++)
        {
            if (blockName.contains(acceptedContainers[i]))
            {
                return true;
            }
        }
        return false;
    }
}