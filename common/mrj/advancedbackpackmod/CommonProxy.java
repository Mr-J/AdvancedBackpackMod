package mrj.advancedbackpackmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import mrj.advancedbackpackmod.InventoryBackpackBase;

public class CommonProxy implements IGuiHandler {
    public static String ITEMS_PNG = "/mrj/advancedbackpackmod/resources/backpack32.png";
    public static String BLOCK_PNG = "/mrj/advancedbackpackmod/resources/block32.png";
    public static String BAG_PNG = "/mrj/advancedbackpackmod/resources/container.png";
   
    // Client stuff
    public void registerRenderers() {
            // Nothing here as the server doesn't render graphics!
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == 0)
		{
			return new ContainerBackpackBase(9, 6, player.inventory);
		}
		else
		{
			System.out.println("Error, ID is " + ID + ". This should not happen");
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == 0)
		{
			return new GuiBackpackBase(player.inventory);
		}
		else
		{
			System.out.println("Error, ID is " + ID + ". This should not happen");
		}
		// TODO Auto-generated method stub
		return null;
	}
}
