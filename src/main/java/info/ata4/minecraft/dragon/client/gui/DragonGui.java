package info.ata4.minecraft.dragon.client.gui;

import org.lwjgl.opengl.GL11;

import info.ata4.minecraft.dragon.server.entity.EntityTameableDragon;
import info.ata4.minecraft.dragon.server.inventory.ContainerDragonGui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/**
 * <p>Represents the graphical interface that is opened when a player shift-right-clicks on their tamed dragon.</p>
 * 
 * <p>This class handles drawing the interface. For inventory management, see {@link ContainerDragonGui}.</p>
 * 
 * @see ContainerDragonGui
 * @author TerrorBite
 *
 */
public class DragonGui extends GuiContainer {

	private EntityTameableDragon dragon;
	private EntityPlayer player;
	
	public DragonGui(EntityPlayer player, EntityTameableDragon dragon) {
		super(new ContainerDragonGui(player, dragon));
		this.dragon = dragon;
		this.player = player;
		
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float time, int mouseX, int mouseY) {
		// Use built-in horse GUI as the base image
		this.mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/gui/container/horse.png"));
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		this.renderDragon(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString(this.dragon.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.player.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 93, 4210752);
	}
	
	private void renderDragon(int mouseX, int mouseY) {
		// Dragons are big, so we will disable drawing outside of the window
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		
		// Note: glScissor() takes screen coordinates, so we need to know Minecraft's GUI scaling factor.
		int scale = mc.displayHeight / this.height;
		// OpenGL screen coordinates have an origin in the bottom left.
		GL11.glScissor(scale*(this.guiLeft+26), mc.displayHeight-scale*(this.guiTop+70), scale*52, scale*52);
		
		// Just in case, ensure color is set correctly for drawing textures
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		// The black window starts at 26,18 in GUI coordinates, and is 52x52 in size
		// Calculate the center of that window in GUI coordinates.
		int dragonX = this.guiLeft + 52, dragonY = this.guiTop + 44;
		// Render the dragon in the target area. Shift render location down because mob positions are based at their feet.
		// Parameters: X_pos, Y_pos, size, X_look, Y_look, entity
		GuiInventory.drawEntityOnScreen(dragonX, dragonY+22, 14, dragonX-mouseX, dragonY-mouseY, this.dragon);
		
		// Disable scissoring
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
	
}
