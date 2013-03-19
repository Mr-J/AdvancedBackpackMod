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
		//this.xSize = 176;
		//this.ySize = 222;
		this.xSize = 14 + (myContainer.x * 18);
		this.ySize = 114 + (myContainer.y * 18); //17 + rows * 18 + 14 + 3 * 18 + 4 + 1 * 18 + 7
	}

	protected void drawGuiContainerForegroundLayer()
	{
		fontRenderer.drawString("INSERT NAME HERE", 10, 10, 0x005939);
	}	
	
	/**@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//this.mc.renderEngine.func_98187_b("mods/advancedbackpackmod/textures/gui/container.png");
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/container.png");
		//mc.renderEngine.bindTexture(myTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}**/
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F,  1.0F,  1.0F,  1.0F);
		int columns = myContainer.x;
		int rows = myContainer.y;
		//this.xSize = 14 + (columns * 18);
		//this.ySize = 114 + (rows * 18); 
		int startX = (this.width - this.xSize)/2;
		int startY = (this.height - this.ySize)/2;
		
		drawContainerTop(startX, startY, columns);
		drawContainerMiddle(startX, startY + 17, columns, rows);
		drawContainerBottom(startX, startY + 35 + (rows + 4) * 18, columns);
	}
	
	private void drawContainerTop(int x, int y, int columns)
	{
		int currentX = x;
		//draw the top left corner
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiTopLeft.png");
		drawTexturedModalRect(currentX, y, 0, 0, 7, 17);
		//next element will be +18 on the x-axis
		currentX = currentX + 7;
		//draw the top middle parts
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiTopMiddle.png");
		for (int i = 1; i <= columns; i++)
		{
			drawTexturedModalRect(currentX, y, 0, 0, 18, 17);
			currentX = currentX + 18;
		}
		//draw the top right corner
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiTopRight.png");
		drawTexturedModalRect(currentX, y, 0, 0, 18, 17);
	}
	
	private void drawContainerMiddle(int x, int y, int columns, int rows) {
		int currentX = x;
		int currentY = y;
		
		//draw the container inventory
		for (int i = 1; i <= rows; i++)
		{
			mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlotLeft.png");
			drawTexturedModalRect(currentX, currentY, 0, 0, 7, 18);
			currentX = currentX + 7;
			mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlot.png");
			for (int j = 1; j <= columns; j++)
			{
				drawTexturedModalRect(currentX, currentY, 0, 0, 18, 18);
				currentX = currentX + 18;
			}
			mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlotRight.png");
			drawTexturedModalRect(currentX, currentY, 0, 0, 7, 18);
			currentY = currentY + 18;
			currentX = x;
		}
		
		//draw the separator to the player inventory
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiUpperSeperatorLeft.png");
		drawTexturedModalRect(currentX, currentY, 0, 0, 7, 14);
		currentX = currentX + 7;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiUpperSeperatorMiddle.png");
		for (int i = 1; i <= columns; i++)
		{
			drawTexturedModalRect(currentX, currentY, 0, 0, 18, 14);
			currentX = currentX + 18;
		}
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiUpperSeperatorRight.png");
		drawTexturedModalRect(currentX, currentY, 0, 0, 7, 14);
		currentX = x;
		currentY = currentY + 14;
		
		//draw the player inventory
		//reuse of the container inventory code
		for (int i = 1; i <= 3; i++)
		{
			mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlotLeft.png");
			drawTexturedModalRect(currentX, currentY, 0, 0, 7, 18);
			currentX = currentX + 7;
			mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlot.png");
			for (int j = 1; j <= columns; j++)
			{
				drawTexturedModalRect(currentX, currentY, 0, 0, 18, 18);
				currentX = currentX + 18;
			}
			mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlotRight.png");
			drawTexturedModalRect(currentX, currentY, 0, 0, 7, 18);
			currentY = currentY + 18;
			currentX = x;
		}
		
		//draw the separator to the player hotbar
		//reuse of the upper seperator code
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiLowerSeperatorLeft.png");
		drawTexturedModalRect(currentX, currentY, 0, 0, 7, 4);
		currentX = currentX + 7;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiLowerSeperatorMiddle.png");
		for (int i = 1; i <= columns; i++)
		{
			drawTexturedModalRect(currentX, currentY, 0, 0, 18, 4);
			currentX = currentX + 18;
		}
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiLowerSeperatorRight.png");
		drawTexturedModalRect(currentX, currentY, 0, 0, 7, 4);
		currentX = x;
		currentY = currentY + 4;
		
		//draw the player hotbar
		//reuse of the container inventory code
		//maybe just a single line in a method and using that for all inventory lines?
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlotLeft.png");
		drawTexturedModalRect(currentX, currentY, 0, 0, 7, 18);
		currentX = currentX + 7;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlot.png");
		for (int j = 1; j <= columns; j++)
		{
			drawTexturedModalRect(currentX, currentY, 0, 0, 18, 18);
			currentX = currentX + 18;
		}
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlotRight.png");
		drawTexturedModalRect(currentX, currentY, 0, 0, 7, 18);
		currentY = currentY + 18;
		currentX = x;
	}
	
	private void drawContainerBottom(int x, int y, int columns) {
		int currentX = x;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiBottomLeft.png");
		drawTexturedModalRect(currentX, y, 0, 0, 7, 7);
		currentX = currentX + 7;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiBottomMiddle.png");
		for (int i = 1; i <= columns; i++)
		{
			drawTexturedModalRect(currentX, y, 0, 0, 18, 7);
			currentX = currentX + 18;
		}
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiBottomRight.png");
		drawTexturedModalRect(currentX, y, 0, 0, 7, 7);
		
	}
		
	public void onGuiClosed()
	{
		super.onGuiClosed();
		inventorySlots.getSlot(0).onSlotChanged();
	}
}
