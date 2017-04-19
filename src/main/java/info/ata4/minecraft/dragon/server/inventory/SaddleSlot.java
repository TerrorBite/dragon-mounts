package info.ata4.minecraft.dragon.server.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import info.ata4.minecraft.dragon.server.inventory.ContainerDragonGui; // used by javadoc

/**
 * A slot designed to accept a single saddle and nothing else. Used by {@link ContainerDragonGui}.
 * @author TerrorBite
 *
 */
public class SaddleSlot extends Slot {

	public SaddleSlot(IInventory inv, int index, int xPos, int yPos) {
		super(inv, index, xPos, yPos);
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	public boolean isItemValid(ItemStack stack) {
		return stack == null || stack.getItem() instanceof net.minecraft.item.ItemSaddle;
	}

}
