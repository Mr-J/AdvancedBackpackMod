package mrj.advancedbackpackmod;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
//import mrj.advancedbackpackmod.InventoryBackpackBase;
import mrj.advancedbackpackmod.ContainerBackpackBase;

public class GuiBackpackBase extends GuiContainer {
	

	ContainerBackpackBase myContainer;
	
	public GuiBackpackBase(InventoryBackpackBase myBPInv, InventoryPlayer myPlayerInv)
	{
		super(new ContainerBackpackBase(myBPInv, myPlayerInv));
		myContainer = (ContainerBackpackBase) super.inventorySlots;
		if (myContainer.invCol >= 9)
		{
			this.xSize = 14 + (myContainer.invCol * 18);
		}
		else
		{
			this.xSize = 176;//14 + 9 * 18;
		}
		this.ySize = 114 + (myContainer.invRow * 18); //17 + rows * 18 + 14 + 3 * 18 + 4 + 1 * 18 + 7
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int var1, int var2)
	{
		fontRenderer.drawString("Backpack ("+ myContainer.invSize+" Slots)", 8, 6, 4210752);
		fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F,  1.0F,  1.0F,  1.0F);
		int columns = Math.max(myContainer.invCol, 9);
		int rows = myContainer.invRow;
		int startX = (this.width - this.xSize)/2;
		int startY = (this.height - this.ySize)/2;
		
		drawContainerTop(startX, startY, columns);
		drawContainerMiddle(startX, startY + 17, columns, rows);
		if (myContainer.invSize > columns * rows)
		{
			drawContainerBottom(startX, startY + 35 + (rows + 5) * 18, columns);
		}
		else
		{
			drawContainerBottom(startX, startY + 35 + (rows + 4) * 18, columns);
		}
		
		
	}
	
	private void drawContainerMiddle(int x, int y, int columns, int rows)
	{
		int currentX = x;
		int currentY = y;
		int rest = myContainer.invSize - (myContainer.invCol * myContainer.invRow);
		for (int i = 1; i <= rows; i++)
		{
			drawContainerSlotLine(x, currentY, columns - myContainer.invCol, myContainer.invCol);
			currentY = currentY + 18;
		}
		if (rest > 0)
		{
			drawContainerSlotLine(x, currentY, columns - rest, rest);
			currentY = currentY + 18;
		}
		drawContainerUpperSeperator(currentX, currentY, columns);
		currentY = currentY + 14;
		for (int i = 1; i <= 3; i++)
		{
			drawContainerSlotLine(x, currentY, columns - 9, 9);
			currentY = currentY + 18;
		}
		drawContainerLowerSeperator(currentX, currentY, columns);
		currentY = currentY + 4;
		drawContainerSlotLine(x, currentY, columns - 9, 9);
	}
	
	private void drawContainerSlotLine(int x, int y, int fillers, int slots)
	{
		int currentX = x;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlotLeft.png");
		drawTexturedModalRect(currentX, y, 0, 0, 7, 18);
		currentX = currentX + 7;
		if (fillers > 0)
		{
			mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiHalfFiller.png");
			for (int i = 1; i <= fillers; i++)
			{	
				drawTexturedModalRect(currentX, y, 0, 0, 9, 18);
				currentX = currentX + 9;
			}
		}
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlot.png");
		for (int i = 1; i <= slots; i++)
		{
			drawTexturedModalRect(currentX, y, 0, 0, 18, 18);
			currentX = currentX + 18;
		}
		if (fillers > 0)
		{
			mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiHalfFiller.png");
			for (int i = 1; i <= fillers; i++)
			{	
				drawTexturedModalRect(currentX, y, 0, 0, 9, 18);
				currentX = currentX + 9;
			}
		}
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiSlotRight.png");
		drawTexturedModalRect(currentX, y, 0, 0, 7, 18);
	}
	
	private void drawContainerUpperSeperator (int x, int y, int columns)
	{
		int currentX = x;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiUpperSeperatorLeft.png");
		drawTexturedModalRect(currentX, y, 0, 0, 7, 14);
		currentX = currentX + 7;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiUpperSeperatorMiddle.png");
		for (int i = 1; i <= columns; i++)
		{
			drawTexturedModalRect(currentX, y, 0, 0, 18, 14);
			currentX = currentX + 18;
		}
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiUpperSeperatorRight.png");
		drawTexturedModalRect(currentX, y, 0, 0, 7, 14);
	}
	
	private void drawContainerLowerSeperator (int x, int y, int columns)
	{
		int currentX = x;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiLowerSeperatorLeft.png");
		drawTexturedModalRect(currentX, y, 0, 0, 7, 4);
		currentX = currentX + 7;
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiLowerSeperatorMiddle.png");
		for (int i = 1; i <= columns; i++)
		{
			drawTexturedModalRect(currentX, y, 0, 0, 18, 4);
			currentX = currentX + 18;
		}
		mc.renderEngine.func_98187_b("/mods/advancedbackpackmod/textures/gui/guiLowerSeperatorRight.png");
		drawTexturedModalRect(currentX, y, 0, 0, 7, 4);
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
