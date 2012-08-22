package gcewing.lighting;

import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class BlockFloodlightIC2 extends BlockFloodlight
{
    public BlockFloodlightIC2(int var1)
    {
        super(var1, 6, Material.STONE);
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity a_()
    {
        return new TEFloodlightIC2();
    }

    public boolean isActive(World var1, int var2, int var3, int var4)
    {
        TEFloodlightIC2 var5 = (TEFloodlightIC2)var1.getTileEntity(var2, var3, var4);
        return var5.isActive();
    }
}
