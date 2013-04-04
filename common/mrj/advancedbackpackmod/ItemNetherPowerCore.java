package mrj.advancedbackpackmod;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemNetherPowerCore extends Item {

	public ItemNetherPowerCore(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
	}
	
	public void updateIcons(IconRegister iconRegister)
    {
		this.iconIndex = iconRegister.registerIcon("advancedbackpackmod:netherpowercore");
    }
}
