package info.ata4.minecraft.dragon.server.handler;

import info.ata4.minecraft.dragon.DragonMounts;
import info.ata4.minecraft.dragon.client.gui.DragonGui;
import info.ata4.minecraft.dragon.server.entity.EntityTameableDragon;
import info.ata4.minecraft.dragon.server.inventory.ContainerDragonGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * <p>The GuiHandler for DragonMounts, it handles all player.openGui() requests.</p>
 * <p>Currently the only GUI that is available is {@link #DRAGON_GUI}.
 * @author TerrorBite
 *
 */
public class GuiHandler implements IGuiHandler {

	/**
	 * This value indicates that a {@link DragonGui} should be opened.
	 */
	public static final int DRAGON_GUI = 0;
	
	/**
	 * <p>Called by Forge on the client side. Should return an instance of GuiContainer.</p>
	 * 
	 * <p>Mod code SHOULD NOT directly call this method; instead it should call {@link EntityPlayer#openGui(Object, int, World, int, int, int)}.</p>
	 * @param guiID The integer ID of the GUI that we are being asked to open. Currently, {@link #DRAGON_GUI} is the only option.
	 * @param player The player who will be shown this GUI.
	 * @param world The world in which the block or entity associated with this GUI resides.
	 * @param x Either the x-coordinate of the block associated with this GUI, or the Entity ID of the entity associated with this GUI.
	 * @param y The y-coordinate of the block associated with this GUI. Ignored if the GUI is associated with an entity instead.
	 * @param z The z-coordinate of the block associated with this GUI. Ignored if the GUI is associated with an entity instead.
	 */
	@Override
	public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z) {
		switch (guiID) {
		case DRAGON_GUI:
			return new DragonGui(player, (EntityTameableDragon)world.getEntityByID(x));
		default:
			return null;
		}
	}

	/**
	 * <p>Called by Forge on the server side. Should return an instance of Container.</p>
	 * 
	 * <p>Mod code SHOULD NOT directly call this method; instead it should call {@link EntityPlayer#openGui(Object, int, World, int, int, int)}.</p>
	 * @param guiID The integer ID of the GUI that we are being asked to open. Currently, {@link #DRAGON_GUI} is the only option.
	 * @param player The player who will be shown this GUI.
	 * @param world The world in which the block or entity associated with this GUI resides.
	 * @param x Either the x-coordinate of the block associated with this GUI, or the Entity ID of the entity associated with this GUI.
	 * @param y The y-coordinate of the block associated with this GUI. Ignored if the GUI is associated with an entity instead.
	 * @param z The z-coordinate of the block associated with this GUI. Ignored if the GUI is associated with an entity instead.
	 */
	@Override
	public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z) {
		switch (guiID) {
		case DRAGON_GUI:
			return new ContainerDragonGui(player, (EntityTameableDragon)world.getEntityByID(x));
		default:
			return null;
		}
	}

}
