package gcewing.lighting;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class Water {

	public static boolean isWaterItem(ItemStack stack) {
		if (stack != null) {
			Item item = stack.getItem();
			if (item == Item.bucketWater)
				return true;
			String name = item.getItemName();
			if (name.equals("item.itemCellWater")) // IndustrialCraft water cell
				return true;
			if (name.equals("item.waterCan")) // Forestry water can
				return true;
			if (name.equals("item.waxCapsuleWater")) // Forestry water capsule
				return true;
			if (name.equals("item.refractoryWater")) // Forestry refractory water capsule
				return true;
			System.out.printf("Water.isWaterItem: Unknown item '%s'\n", name);
		}
		return false;
	}
	
	public static ItemStack emptyContainerFor(ItemStack stack) {
		Item item = stack.getItem();
		Item container = item.getContainerItem();
		if (container != null)
			return new ItemStack(container, 1);
		else
			return null;
	}

}
