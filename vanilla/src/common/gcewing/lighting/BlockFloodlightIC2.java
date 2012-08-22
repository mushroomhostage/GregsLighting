package gcewing.lighting;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class BlockFloodlightIC2 extends BlockFloodlight  {

	public BlockFloodlightIC2(int id) {
		super(id, 6, Material.rock);
	}

	public TileEntity getBlockEntity() {
		return new TEFloodlightIC2();
	}

	public boolean isActive(World world, int x, int y, int z) {
		TEFloodlightIC2 te = (TEFloodlightIC2)world.getBlockTileEntity(x, y, z);
		return te.isActive();
	}

}
