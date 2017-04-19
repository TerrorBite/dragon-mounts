package info.ata4.minecraft.dragon.server.inventory;

import org.apache.logging.log4j.Level;

import info.ata4.minecraft.dragon.server.entity.EntityTameableDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LogWrapper;
import info.ata4.minecraft.dragon.client.gui.DragonGui; // used in javadoc

/**
 * <p>Handles inventory in the GUI that is opened when a player shift-right-clicks on their tamed dragon.
 * Extends {@link Container}, which handles synchronizing changes to the GUI's inventory slots between the
 * client and the server.</p>
 * 
 * <p>Dragons do not actually have an inventory of their own, so this class creates an internal instance of
 * {@link DragonInventory} which is used while the GUI is open. Changes to that inventory trigger updates to
 * the dragon's internal state. When the GUI is closed, the inventory instance is discarded.</p>
 * 
 * <p>This class is responsible for specifying what inventory slots appear in the GUI and where they are located,
 * but does not actually draw the GUI. For that, see {@link DragonGui}.</p>
 * 
 * @author TerrorBite
 * @see DragonGui
 * @see DragonInventory
 *
 */
public class ContainerDragonGui extends Container {
	
	private EntityTameableDragon dragon;
	private IInventory inv;

	/**
	 * Constructor for this class.
	 * @param player The player who is interacting with their dragon.
	 * @param dragon The dragon being interacted with.
	 */
	public ContainerDragonGui(EntityPlayer player, EntityTameableDragon dragon) {
		this.dragon = dragon;
		this.inv = new DragonInventory(dragon);
		
		// Saddle slot (dragon slot 0)
		// Container slot ID 0
		this.addSlotToContainer(new SaddleSlot(inv, 0, 8, 18));
		
	    // Player's non-hotbar inventory (player slots 9-35)
		// Container slot IDs 1-27
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }

	    // Player's hotbar (player slots 0-8)
	    // Container slot IDs 28-36
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(player.inventory, x, 8 + x * 18, 142));
	    }
	}

	@Override
	/**
	 * Gets whether the player is able to interact with the dragon that owns this container.
	 * I don't know if this method ever actually gets called, because we are handling
	 * player interactions ourselves in <code>DragonInteractGui</code>.
	 */
	public boolean canInteractWith(EntityPlayer player) {
		// Dragon is tamed by this player and player is within 8 blocks of the dragon
		return this.dragon.isTamedFor(player) && (this.dragon.getDistanceToEntity(player) < 8.0f);
	}
	
	/**
	 * This method handles shift-clicking items to and from the slots in the inventory.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
		// Begin boilerplate code
	    ItemStack previous = null;
	    Slot sourceSlot = (Slot) this.inventorySlots.get(fromSlot);

	    if (sourceSlot != null && sourceSlot.getHasStack()) {
	        ItemStack current = sourceSlot.getStack();
	        previous = current.copy();
	        // End boilerplate
	        
	        
	        if(fromSlot < 1) { // Shift-click: Dragon => Player
	        	// Note, mergeItemStack() will modify the ItemStack passed in as it moves items around
	        	if(!this.mergeItemStack(current, 1, 36, true)) return null;
	        }
	        else { // Shift-click: Player => Dragon
	        	if(current.getItem() instanceof ItemSaddle) {
	        		if(this.getSlot(0).getHasStack()) {
	        			// saddle slot is full
	        			return null;
	        		}
		        	this.getSlot(0).putStack(previous);
		        	current.stackSize = 0;
	        	}	        		
	        	//TODO: Handle dragon armor being shift-clicked in
	        	else return null; // item won't fit, do nothing

	        }
	        
	        
	        // More boilerplate code
	        if (current.stackSize == 0)
	            sourceSlot.putStack((ItemStack) null);
	        else
	            sourceSlot.onSlotChanged();

	        if (current.stackSize == previous.stackSize)
	            return null;
	        sourceSlot.onPickupFromSlot(player, current);
	    }
	    return previous;
	    // end boilerplate
	}
}
