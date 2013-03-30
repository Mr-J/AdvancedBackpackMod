package mrj.advancedbackpackmod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
	
	/**@Override
	public Icon getIcon(ItemStack itemStack, int renderPass)
	{
		return this.iconIndex;
	}**/
	
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
}
