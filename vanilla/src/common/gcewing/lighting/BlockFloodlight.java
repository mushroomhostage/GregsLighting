package gcewing.lighting;

import java.util.ArrayList;
import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class BlockFloodlight extends BlockContainer implements ITextureProvider {

	public BlockFloodlight(int id) {
		super(id, 0, Material.rock);
	}
	
	public BlockFloodlight(int id, int tex, Material mat) {
		super(id, tex, mat);
	}
	
	public String getTextureFile() {
		return "/gcewing/lighting/resources/textures.png";
	}
	
	public int getBlockTextureFromSideAndMetadata(int side, int data) {
		int base = blockIndexInTexture;
		if (data > 0)
			base += 3;
		switch (side) {
			case 0: return base; // bottom
			case 1: return base + 2; // top
			default: return base + 1; // sides
		}
	}

	public void addCreativeItems(ArrayList itemList) {
		itemList.add(new ItemStack(this, 1));
	}
	
	public void onBlockAdded(World world, int x, int y, int z) {
		update(world, x, y, z);
	}

	public void onBlockRemoval(World world, int x, int y, int z) {
		update(world, x, y, z);
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID) {
		update(world, x, y, z);
	}

	public TileEntity getBlockEntity() {
		return null;
	}

	public void update(World world, int x, int y, int z) {
		if (!world.isRemote) {
			//System.out.printf("BlockFloodlight.update: %d, %d, %d\n", x, y, z);
			boolean active = Floodlight.activeAt(world, x, y, z);
			setIlluminated(world, x, y, z, active);
			if (active)
				Floodlight.projectBeam(world, x, y-1, z);
			else
				Floodlight.removeBeam(world, x, y-1, z);
		}
	}
	
	public void setIlluminated(World world, int x, int y, int z, boolean state) {
		//System.out.printf("BlockFloodlight.setIlluminated: %s\n", state);
		int oldData = world.getBlockMetadata(x, y, z);
		int newData = (oldData & 0xe) | (state ? 1 : 0);
		if (oldData != newData) {
			world.setBlockMetadata(x, y, z, newData);
			world.markBlockNeedsUpdate(x, y, z);
		}
	}

	public boolean isActive(World world, int x, int y, int z) {
		return world.isBlockIndirectlyGettingPowered(x, y, z);
	}
	
}
