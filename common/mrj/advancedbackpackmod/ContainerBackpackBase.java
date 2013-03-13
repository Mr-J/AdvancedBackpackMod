package mrj.advancedbackpackmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;


public class ContainerBackpackBase extends Container {

	public ContainerBackpackBase(int x, int y, InventoryPlayer myPlayerInv)
	{
		//System.out.println("i'm here");
		/**for (int row = 0; row < y; row++)
		{
			System.out.println("row = " + row);
			for (int col = 0; col < x; col++)
			{
				System.out.println("col = " + col);
				//container inventory
				this.addSlotToContainer(new Slot(myPlayerInv, col + row * x + 9, 8 + col * 8, 18 + row * 18));
			}
		}
		System.out.println("part 1 done");
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				//player inventory
				this.addSlotToContainer(new Slot(myPlayerInv, i * 9 + j, 8 + j * 18, 18 + y * 18 + 14));
			}
		}
		System.out.println("part 2 done");
		for (int i = 0; i < 9; i++)
		{
			//players bar
			this.addSlotToContainer(new Slot(myPlayerInv, i, 8 + i * 18, 18 + y * 18 + 14 + 3 * 18 + 4));
		}
		System.out.println("part 3 done");**/
	}

	@Override
	public boolean canInteractWith(EntityPlayer myPlayer) {
		 
		return true;
	}
}
