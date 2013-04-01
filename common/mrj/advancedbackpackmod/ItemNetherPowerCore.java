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
		/**icons = new Icon[17];
		
		//icons[0] = iconRegister.func_94245_a("advancedbackpackmod:backpack32");
		icons[0] = iconRegister.registerIcon("advancedbackpackmod:backpack32");
		
		for (int i = 1; i < 17; i++)
		{
			//icons[i] = iconRegister.func_94245_a("advancedbackpackmod:backpack32" + colorNames[i-1]);
			icons[i] = iconRegister.registerIcon("advancedbackpackmod:backpack32" + colorNames[i-1]);
		}**/
		//icons = new Icon[2];
		//icons[0] = iconRegister.registerIcon("advancedbackpackmod:backpack32colorless");
		//icons[1] = iconRegister.registerIcon("advancedbackpackmod:backpack32outline");
		this.iconIndex = iconRegister.registerIcon("advancedbackpackmod:netherpowercore");
    }
}
