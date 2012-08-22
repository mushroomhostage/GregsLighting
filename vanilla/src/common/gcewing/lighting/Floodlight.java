package gcewing.lighting;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class Floodlight {

	static boolean activeAt(World world, int x, int y, int z) {
		// True if the block at x,y,z is either an active floodlight block or a
		// floodlight beam block, or is illuminated by such a block from above
		// through transparent blocks.
		int h = world.getHeight();
		while (y < h) {
			int id = world.getBlockId(x, y, z);
			if (isFloodlightBlockID(id)) {
				BlockFloodlight block = (BlockFloodlight)Block.blocksList[id];
				return block.isActive(world, x, y, z);
			}
			else if (id == GregsLighting.floodlightBeam.blockID)
				return true;
			else if (id == 0 || Block.blocksList[id].isOpaqueCube())
				return false;
			y += 1;
		}
		return false;
	}
	
	static boolean isFloodlightBlockID(int id) {
		return (id == GregsLighting.floodlight.blockID
			|| id == GregsLighting.floodlightCarbide.blockID
			|| (GregsLighting.floodlightIC2 != null && id == GregsLighting.floodlightIC2.blockID)
			);
	}

	static void projectBeam(World world, int x, int y, int z) {
		// Add floodlight beam blocks downwards starting from the given coords
		// until an opaque block is found.
		traceBeam(world, x, y, z, 0, GregsLighting.floodlightBeam.blockID);
	}
	
	static void removeBeam(World world, int x, int y, int z) {
		// Remove floodlight beam blocks downwards starting from the given coords
		// until an empty or opaque block is found.
		traceBeam(world, x, y, z, GregsLighting.floodlightBeam.blockID, 0);
	}
	
	static void traceBeam(World world, int x, int y, int z, int oldID, int newID) {
		while (y >= 0) {
			int id = world.getBlockId(x, y, z);
			if (id == oldID)
				world.setBlockWithNotify(x, y, z, newID);
			else if (id != 0 && Block.blocksList[id].isOpaqueCube())
				break;
			y -= 1;
		}
	}
	
}
