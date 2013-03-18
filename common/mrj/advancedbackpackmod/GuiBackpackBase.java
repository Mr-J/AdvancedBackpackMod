package mrj.advancedbackpackmod;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
//import mrj.advancedbackpackmod.InventoryBackpackBase;
import mrj.advancedbackpackmod.ContainerBackpackBase;

public class GuiBackpackBase extends GuiContainer {
	
	//int x = 248;
	//int y = 186;
	ContainerBackpackBase myContainer;
	
	public GuiBackpackBase(InventoryBackpackBase myBPInv, InventoryPlayer myPlayerInv)
	{
		super(new ContainerBackpackBase(myBPInv, myPlayerInv));
		myContainer = (ContainerBackpackBase) super.inventorySlots;
		this.xSize = 176;
		this.ySize = 222;
	}

	protected void drawGuiContainerForegroundLayer()
	{
		fontRenderer.drawString("INSERT NAME HERE", 10, 10, 0x005939);
	}	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//this.mc.renderEngine.func_98187_b("mods/advancedbackpackmod/textures/gui/container.png");
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/container.png");
		//mc.renderEngine.bindTexture(myTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
	
	public void onGuiClosed()
	{
		super.onGuiClosed();
		inventorySlots.getSlot(0).onSlotChanged();
	}
}
