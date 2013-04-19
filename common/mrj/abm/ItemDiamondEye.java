package mrj.abm;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemDiamondEye extends Item {

	public ItemDiamondEye(int par1) {
		super(par1);
		setCreativeTab(CreativeTabs.tabMisc);
		// TODO Auto-generated constructor stub
	}
	
	public void updateIcons(IconRegister iconRegister)
    {
		this.iconIndex = iconRegister.registerIcon("advancedbackpackmod:diamondeye");
    }
}
