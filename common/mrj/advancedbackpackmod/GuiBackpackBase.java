package mrj.advancedbackpackmod;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import mrj.advancedbackpackmod.InventoryBackpackBase;
import mrj.advancedbackpackmod.ContainerBackpackBase;

public class GuiBackpackBase extends GuiContainer {
	
	//int x = 248;
	//int y = 186;
	
	public GuiBackpackBase(InventoryPlayer myPlayerInv)
	{
		super(new ContainerBackpackBase(9, 6, myPlayerInv));
		this.xSize = 248;
		this.ySize = 186;
	}

	protected void drawGuiContainerForegroundLayer()
	{
		fontRenderer.drawString("INSERT NAME HERE", 10, 10, 0x005939);
	}	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int myTexture = this.mc.renderEngine.getTexture("/mrj/advancedbackpackmod/resources/container.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(myTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
	
	public void onGuiClosed()
	{
		super.onGuiClosed();
		//inventorySlots.getSlot(0).onSlotChanged();
	}
}
