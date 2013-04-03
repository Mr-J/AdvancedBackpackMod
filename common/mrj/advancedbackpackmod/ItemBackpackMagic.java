package mrj.advancedbackpackmod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrj.advancedbackpackmod.config.ConfigurationStore;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * Advanced Backpack Mod
 * 
 * ItemBackpackMagic
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ItemBackpackMagic extends ItemBackpackBase {

	public ItemBackpackMagic(int id) {
		super(id);
		
	}

	public ItemStack onItemRightClick(ItemStack myStack, World myWorld, EntityPlayer myPlayer) 
	{
		if (!myWorld.isRemote)
		{
			if (!myPlayer.isSneaking())
			{
				myPlayer.openGui(AdvancedBackpackMod.instance, 1, myPlayer.worldObj, (int)myPlayer.posX, (int)myPlayer.posY, (int)myPlayer.posZ);
			}
			else
			{
				System.out.println("player is sneaking, use shared inventory mode for backpackmagic");
			}
		}		
		return myStack;
	}
	
	@Override
    //public void func_94581_a(IconRegister iconRegister)
	public void updateIcons(IconRegister iconRegister)
    {
        //this.iconIndex = iconRegister.func_94245_a("advancedbackpackmod:backpackmagic32");
		//this.iconIndex = iconRegister.registerIcon("advancedbackpackmod:backpackmagic32");
		icons = new Icon[2];
		icons[0] = iconRegister.registerIcon("advancedbackpackmod:backpack32colorless");
		icons[1] = iconRegister.registerIcon("advancedbackpackmod:backpackmagic32outline");
    }
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		if (!world.isRemote)
		{
			checkForSizeUpdate(itemStack, entity);
		}
		
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
	
	@Override
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
			InventoryBackpackMagic tempInv = new InventoryBackpackMagic(itemStack, (EntityPlayer) entity, ((ItemBackpackMagic)itemStack.getItem()).getColor(itemStack));
			if (tempInv.getSizeInventory() + ConfigurationStore.BACKPACK_MAGIC_UPGRADE_INCREMENT * 
					nbtTagCompound.getInteger("increaseSize") <= ConfigurationStore.BACKPACK_MAGIC_MAX_SIZE)
			{				
				tempInv.increaseSize(ConfigurationStore.BACKPACK_MAGIC_UPGRADE_INCREMENT * nbtTagCompound.getInteger("increaseSize"));
			}
			else
			{
				tempInv.increaseSize(ConfigurationStore.BACKPACK_MAGIC_MAX_SIZE - tempInv.getSizeInventory());
			}
			tempInv.writeToNBT(nbtTagCompound);
			nbtTagCompound.setInteger("increaseSize", 0);
		}
	}
	
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
}
