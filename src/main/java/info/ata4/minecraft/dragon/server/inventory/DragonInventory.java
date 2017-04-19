package info.ata4.minecraft.dragon.server.inventory;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import info.ata4.minecraft.dragon.DragonMounts;
import info.ata4.minecraft.dragon.server.entity.EntityTameableDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * <p>A "fake" inventory used by {@link ContainerDragonGui} to allow items such as saddles
 * to be equipped to a tamed dragon. The inventory is "fake" because it is not persistent and exists only
 * as long as the GUI that requires it is open. However, changes to the inventory's contents <i>do</i> have
 * persistent effects, as follows:</p>
 * 
 * <ul><li><b>Slot 0:</b> The dragon's "saddled" state will be updated when a saddle is added to or removed from this slot.</li>
 * <li><b>Slot 1:</b> Currently unused, placing items into this slot has no effect.
 * Intended to be used for dragon armor in future.</li></ul>
 * @author TerrorBite
 *
 */
public class DragonInventory extends InventoryBasic {
	
	private EntityTameableDragon dragon;
	private final static Item SADDLE = Item.REGISTRY.getObject(new ResourceLocation("minecraft:saddle"));

	/**
	 * Constructs a new <code>DragonInventory</code> instance for the given dragon.
	 * @param dragon The dragon whose state will be used to initialize this inventory, and whose state
	 * will be affected by changes to this inventory.
	 */
	public DragonInventory(EntityTameableDragon dragon) {
		super(dragon.getName(), dragon.hasCustomName(), 2);
		this.dragon = dragon;
		if(dragon.isServer()) {
			super.setInventorySlotContents(0, dragon.isSaddled() ? new ItemStack(SADDLE) : null);
		}
	}
	
	/**
	 * In addition to the functionality of {@link InventoryBasic#setInventorySlotContents(int, ItemStack)},
	 * this method updates the dragon's state upon certain changes to inventory slots. See the description of
	 * {@link DragonInventory} for a full list.
	 * @param slot The slot number to update.
	 * @param stack The {@link ItemStack} to place into the slot.
	 */
	@Override
	public void setInventorySlotContents(int slot, @Nullable ItemStack stack) {
		super.setInventorySlotContents(slot, stack);
		if(dragon.isServer()) {
			switch(slot) {
			case 0:
				// Saddle slot
				if(stack != null) {
					if(stack.getItem() instanceof ItemSaddle) {
						LogWrapper.log("DragonMounts", Level.INFO, "Saddle was placed onto %s", dragon.getName());
						this.dragon.setSaddled(true);
					}
					else {
						LogWrapper.log("DragonMounts", Level.INFO, "Unknown item placed into %s", dragon.getName());
					}
				} else {
					LogWrapper.log("DragonMounts", Level.INFO, "Saddle was removed from %s", dragon.getName());
					this.dragon.setSaddled(false);
				}
			default:
				return;
			}
		}
	}
}
