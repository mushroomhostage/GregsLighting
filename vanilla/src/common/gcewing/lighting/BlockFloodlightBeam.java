package gcewing.lighting;

import java.util.ArrayList;
import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class BlockFloodlightBeam extends Block implements ITextureProvider {

	public BlockFloodlightBeam(int id) {
		super(id, 1, Material.vine /*Material.circuits*/);
		setLightValue(1.0F);
		setLightOpacity(0);
		setBlockBounds(0F, 0F, 0F, 0F, 0F, 0F);
	}
	
	public String getTextureFile() {
		return "/gcewing/lighting/resources/textures.png";
	}

	public boolean isOpaqueCube() {
			return false;
	}
    
	public boolean renderAsNormalBlock() {
			return false;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
			return null;
	}

	public int getRenderType() {
			return -1;
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID) {
		if (Floodlight.activeAt(world, x, y+1, z))
			Floodlight.projectBeam(world, x, y-1, z);
		else
			Floodlight.removeBeam(world, x, y, z);
	}

}
