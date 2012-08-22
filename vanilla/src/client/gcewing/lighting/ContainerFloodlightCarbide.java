//------------------------------------------------------------------------------
//
//   Greg's Lighting - ContainerFloodlightCarbide - Client
//
//------------------------------------------------------------------------------


package gcewing.lighting;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class ContainerFloodlightCarbide extends ContainerFLCBase {

	public ContainerFloodlightCarbide(InventoryPlayer pi, TEFloodlightCarbide te) {
		super(pi, te);
	}

	public void updateProgressBar(int i, int value) {
		//System.out.printf("ContainerFloodlightCarbide.updateProgressBar: %d %d\n", i, value);
		switch (i) {
			case 0: te.waterLevel = value;
			case 1: te.carbideLevel = value;
		}
	}
	
}
