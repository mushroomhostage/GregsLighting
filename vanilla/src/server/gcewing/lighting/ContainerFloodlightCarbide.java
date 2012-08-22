//------------------------------------------------------------------------------
//
//   Greg's Lighting - ContainerFloodlightCarbide - Server
//
//------------------------------------------------------------------------------


package gcewing.lighting;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class ContainerFloodlightCarbide extends ContainerFLCBase {

	public ContainerFloodlightCarbide(InventoryPlayer pi, TEFloodlightCarbide te) {
		super(pi, te);
	}

	public void onCraftGuiOpened(ICrafting crafter) {
		super.onCraftGuiOpened(crafter);
		sendStateTo(crafter);
	}
	
	public void updateCraftingResults() {
		super.updateCraftingResults();
		for (int i = 0; i < crafters.size(); i++) {
			ICrafting crafter = (ICrafting)crafters.get(i);
			sendStateTo(crafter);
		}
	}
	
	void sendStateTo(ICrafting crafter) {
		crafter.updateCraftingInventoryInfo(this, 0, te.waterLevel);
		crafter.updateCraftingInventoryInfo(this, 1, te.carbideLevel);
	}

}
