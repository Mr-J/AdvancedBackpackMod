package mrj.abm;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemNetherPowerCore extends Item {

	public ItemNetherPowerCore(int par1) {
		super(par1);
		setCreativeTab(CreativeTabs.tabMisc);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
    {
		this.itemIcon = iconRegister.registerIcon("advancedbackpackmod:netherpowercore");
    }
}
