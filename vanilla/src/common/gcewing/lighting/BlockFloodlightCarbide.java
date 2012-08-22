package gcewing.lighting;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class BlockFloodlightCarbide extends BlockFloodlight {

	public BlockFloodlightCarbide(int id) {
		super(id, 16, Material.rock);
	}
	
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		//System.out.printf("BlockFloodlightCarbide.blockActivated\n");
		//if (!GregsLighting.mod.shiftKeyDown())
		if (!player.isSneaking()) {
			if (!world.isRemote) {
				//System.out.printf("BlockFloodlightCarbide.blockActivated: opening gui\n");
				GregsLighting.openGuiFloodlightCarbide(world, x, y, z, player);
			}
			return true;
		}
		else
			return false;
	}

	public TileEntity getBlockEntity() {
		return new TEFloodlightCarbide();
	}

	public void onBlockRemoval(World world, int x, int y, int z) {
		Utils.dumpInventoryIntoWorld(world, x, y, z);
		super.onBlockRemoval(world, x, y, z);
	}
	
	public boolean isActive(World world, int x, int y, int z) {
		TEFloodlightCarbide te = (TEFloodlightCarbide)world.getBlockTileEntity(x, y, z);
		return te != null && te.isActive();
	}
	
	public void update(World world, int x, int y, int z) {
		if (!world.isRemote) {
			TEFloodlightCarbide te = (TEFloodlightCarbide)world.getBlockTileEntity(x, y, z);
			if (te != null)
				te.refillCarbide();
			super.update(world, x, y, z);
		}
	}

}
