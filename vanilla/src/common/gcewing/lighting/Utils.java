package gcewing.lighting;

import java.util.Random;
import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class Utils {

	public static Random random = new Random();

	public static void dumpInventoryIntoWorld(World world, int x, int y, int z) {
		IInventory te = (IInventory)world.getBlockTileEntity(x, y, z);
		if (te != null) {
			for (int i = 0; i < te.getSizeInventory(); ++i) {
				ItemStack stack = te.getStackInSlot(i);
				if (stack != null) {
					float dx = random.nextFloat() * 0.8F + 0.1F;
					float dy = random.nextFloat() * 0.8F + 0.1F;
					float dz = random.nextFloat() * 0.8F + 0.1F;
					while (stack.stackSize > 0) {
						int n = random.nextInt(21) + 10;
						if (n > stack.stackSize) {
							n = stack.stackSize;
						}
						stack.stackSize -= n;
						EntityItem entity = new EntityItem(world, x + dx, y + dy, z + dz, new ItemStack(stack.itemID, n, stack.getItemDamage()));
						if (stack.hasTagCompound()) {
							entity.item.setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
						}
						float f = 0.05F;
						entity.motionX = random.nextGaussian() * f;
						entity.motionY = random.nextGaussian() * f + 0.2F;
						entity.motionZ = random.nextGaussian() * f;
						world.spawnEntityInWorld(entity);
					}
				}
			}
		}
	}

}
