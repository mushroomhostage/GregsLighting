package gcewing.lighting;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class ContainerFLCBase extends Container {

	TEFloodlightCarbide te;
	
	public ContainerFLCBase(InventoryPlayer pi, TEFloodlightCarbide te) {
		this.te = te;
		addSlot(new Slot(te, 0, 44, 8));
		addSlot(new Slot(te, 1, 116, 60));
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				this.addSlot(new Slot(pi, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		for (int i = 0; i < 9; ++i)
			this.addSlot(new Slot(pi, i, 8 + i * 18, 142));
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 */
	public ItemStack transferStackInSlot(int par1) {
		// TODO
		return null;
	}

}
