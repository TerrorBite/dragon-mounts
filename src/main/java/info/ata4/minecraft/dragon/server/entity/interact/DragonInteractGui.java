package info.ata4.minecraft.dragon.server.entity.interact;

import info.ata4.minecraft.dragon.DragonMounts;
import info.ata4.minecraft.dragon.server.entity.EntityTameableDragon;
import info.ata4.minecraft.dragon.server.handler.GuiHandler;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

/**
 * Opens the Dragon GUI when interacting with a dragon while crouching.
 * 
 * @author TerrorBite <terrorbite at lethargiclion.net>
 *
 */
public class DragonInteractGui extends DragonInteract {

	public DragonInteractGui(EntityTameableDragon dragon) {
		super(dragon);
	}

	@Override
	public boolean interact(EntityPlayer player, ItemStack item) {

		if (player.isSneaking()) {
			if (dragon.isServer()) {
				// Don't run this code on client side.
				
				if (dragon.isTamedFor(player)) {
					// Dragon is tamed and owned by us. Open the dragon GUI
					player.openGui(DragonMounts.instance, GuiHandler.DRAGON_GUI, player.getEntityWorld(), dragon.getEntityId(), 0, 0);
					
				} else if (dragon.isTamed()) {
					// This dragon is tamed, but we don't own it
					// Try and get owner's name, use generic string if owner not online
					String ownerName = dragon.getOwner() == null ? "another player" : dragon.getOwner().getName();
					player.addChatMessage(new TextComponentString(
							String.format("You can't control %s's dragon.", ownerName)));
					
				} else {
					// This dragon is NOT tamed
					player.addChatMessage(new TextComponentString("You can't control an untamed dragon."));
				}
			}
			// Player is sneaking. Indicate a match, i.e. interaction was handled.
			// Other interaction handlers won't be checked.
			// Returns true regardless if this code runs on server or client - we want to stop the event
			return true;
		}
		// Player is not sneaking, pass on to other interaction handlers
		return false;
	}

}
