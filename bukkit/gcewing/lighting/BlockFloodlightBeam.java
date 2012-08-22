package gcewing.lighting;

import forge.ITextureProvider;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Material;
import net.minecraft.server.World;

public class BlockFloodlightBeam extends Block implements ITextureProvider
{
    public BlockFloodlightBeam(int var1)
    {
        super(var1, 1, Material.REPLACEABLE_PLANT);
        this.a(1.0F);
        this.f(0);
        this.a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    public String getTextureFile()
    {
        return "/gcewing/lighting/resources/textures.png";
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean a()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World var1, int var2, int var3, int var4)
    {
        return null;
    }

    /**
     * The type of render function that is called for this block
     */
    public int c()
    {
        return -1;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        if (Floodlight.activeAt(var1, var2, var3 + 1, var4))
        {
            Floodlight.projectBeam(var1, var2, var3 - 1, var4);
        }
        else
        {
            Floodlight.removeBeam(var1, var2, var3, var4);
        }
    }
}
